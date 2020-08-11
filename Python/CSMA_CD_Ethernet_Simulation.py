from os import system
import threading
import time
import sys
import collections
import random
import copy

# Napisz program do symulowania ethernetowej metody dostepu do
# medium transmisyjnego (CSMA/CD). Wspólne łącze realizowane jest
# za pomocą tablicy: propagacja sygnału symulowana jest za pomoca
# propagacji wartości do sąsiednich komórek.

timing = 0.3    # sek - czas pomiędzy iteracjami / szybkość propagacji sygnału
log = []        # log wydarzeń
spacesBetween = 20    # odległości między komputerami w sieci


class Medium(object):
    def __init__(self, numberOfComputers):
        self.medium = ['_' for i in range(1 + spacesBetween * (numberOfComputers-1))]
        self.bits = []
        self.jams = []
        self.collision = self.medium.copy()         # kolizje pzresyłane na innej amplitudzie np zwykły sygnał +- 5V, kolicje +- 15V
        self.col = []
        # transmission_delay = frame_size * timing         # czas potrzebny na wepchnięcie wszytskich bitów z ramki do kabla
        # propagation_delay = medium_length * timimg       # czas potrzebny na przejście początku ramki od nadawny do odbiorcy
        # transmission_delay > 2 * propagation_delay
        # frame_size > 2 * medium_length
        self.minFrameSize = 2 * len(self.medium)
        self.propagation_delay = 2 * len(self.medium) * timing
        
    def start(self):
        threading.Thread(target=self.propagate).start()
    
    def propagate(self):
        while True:
            # wyświetl medium
            m = ['_' for m in self.medium]
            for bit in self.bits:
                m[bit[1]] = bit[0]
            for j in self.jams:
                m[j[0]] = 'j'
            self.medium = m
            system('clear')
            stations = [' ' for m in self.medium]
            for i in range(numberOfComputers):
                stations[i * spacesBetween] = i+1
            for s in stations:
                print(s, end="")
            print()
            for m in self.medium:
                print(m, end="")
            print()
            # wyświetl kolizje
            cm = ['_' for c in self.collision]
            if len(self.col) == 2:
                [start, end] = self.col
                for i in range(start, end+1):
                    if 0 <= i < len(self.collision):
                        cm[i] = 'c'
            self.collision = cm
            for c in self.collision:
                print(c, end="")
            print()
            
            for l in log:
                print(l)

            threading.Thread(target=self.singlePropagate).start()
            time.sleep(timing)
    
    def singlePropagate(self):
        # sprawdz kolizje
        if len(self.col) == 0:
            self.checkCollisions()

        # przesuń medium
        newbits = []
        for bit in self.bits:
            bit[1] += bit[2]
            if 0 <= bit[1] < len(self.medium):
                newbits.append(bit)
        self.bits = copy.deepcopy(newbits)
        newjams = []
        for jam in self.jams:
            jam[0] += jam[1]
            if 0 <= jam[0] < len(self.medium):
                newjams.append(jam)
        self.jams = copy.deepcopy(newjams)
        jamspos = [i[0] for i in self.jams]
        b = []
        for bit in self.bits:
            if bit[1] not in jamspos:
                b.append(bit)
        self.bits = copy.deepcopy(b)
        # przesuń kolizje
        if len(self.col) == 2:
            [start, end] = self.col
            start -= 1
            end += 1
            if start >= 0 and end < len(self.collision):
                self.col = [start, end]
            elif start < 0 and end < len(self.collision):
                start = 0
                self.col = [start, end]
            elif start >= 0 and end >= len(self.collision):
                end = len(self.collision) - 1
                self.col = [start, end]
            else:
                self.col = []
    
    def checkCollisions(self):
        if len(self.jams) > 0:
            return False

        diff = {}
        for bit in self.bits:
            if bit[0] != 'j':
                if bit[0] not in diff:
                    diff[bit[0]] = set()
                diff[bit[0]].add(bit[1])
        pos = []
        for i in diff:
            pos += list(diff[i])
        rep = [i for i in pos if pos.count(i) > 1]
        if len(rep) > 5:
            i = rep[0]
            self.col = [i, i]
    
    def push(self, bit, position):
        # wartość, pozycja, kierunek
        self.bits.append([bit, position, +1])
        self.bits.append([bit, position, -1])
    
    def get(self, position):
        return self.medium[position]

    def isCollision(self, position):
        return self.collision[position] == 'c' or self.medium[position] == 'j'
    
    def jam(self, position, length):
        for i in range(length):
            self.jams.append([position, +1])
            self.jams.append([position, -1])
            time.sleep(timing)
        

class Computer(object):
    def __init__(self, name, position, medium):
        self.name = name
        self.position = position
        self.medium = medium
    
    def send(self, waiting=0, attempt=0):
        maxAttempt = 5
        frame = ''.join(str(self.name) for i in range(self.medium.minFrameSize))

        # wait until no other comp is transmiting
        while self.medium.get(self.position) != '_' or self.medium.isCollision(self.position):
            time.sleep(random.randint(0, 10) * timing)
        
        # wait given time (for collisions)
        time.sleep(waiting)

        # wait until no other comp is transmiting
        while self.medium.get(self.position) != '_' or self.medium.isCollision(self.position):
            time.sleep(random.randint(0, 10) * timing)
        
        # send
        log.append(f'{self.name} started sending message')
        leftToSend = len(frame)
        for bit in frame:
            self.medium.push(bit, self.position)
            if self.medium.isCollision(self.position):
                log.append(f'{self.name} seen collision')
                if attempt < maxAttempt:
                    attempt += 1
                    self.medium.jam(self.position, leftToSend)
                    t = self.medium.propagation_delay * random.randint(0, 2**attempt)
                    log.append(f'{self.name} waits {t} sec, attempt: {attempt}')
                    self.send(t, attempt)
                else:
                    log.append(f'{self.name} tried sending more then {maxAttempt} times, aborts')
                return
            time.sleep(timing)
            leftToSend -= 1
        log.append(f'{self.name} send message succesfully')


if __name__ == '__main__':
    # utworzenie komputerów
    numberOfComputers = 3
    medium = Medium(numberOfComputers)
    computers = []
    for i in range(numberOfComputers):
        position = i * spacesBetween
        name = i + 1
        computers.append(Computer(name, position, medium))
    # uruchomienie
    medium.start()
    time.sleep(1)
    # wysłanie
    threading.Thread(target=computers[0].send).start()
    threading.Thread(target=computers[1].send).start()
    time.sleep(5)
    threading.Thread(target=computers[2].send).start()
    
