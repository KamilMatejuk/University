import random
import numpy as np


class Population(object):

    def __init__(self, size, startingX, function):
        self.function = function
        self.particles = [{         # lista cząstek z niezerową prędkością
            # pozycja
            'x': startingX.copy(),
            # wektor prędkości (-1, 1)
            # TODO predkość równowmiernie 360 stopni
            'v': self.RandomNSphere(len(startingX), 1),
            # najlepsza pozycja tej cząsteczki
            'best': startingX.copy()
        } for i in range(size)]
        self.globalBest = self.getBest()
        # print('starting:', startingX, function(startingX.copy()))
        # print('starting best:', self.globalBest, function(self.globalBest.copy()))

    def RandomNSphere(self, n, magnitude):
        # uniform random distribution on n-sphere
        u = np.random.normal(0, 1, n)
        d = np.sum(u**2) ** 0.5
        x = [magnitude*i/d for i in u]
        return x

    def getBest(self):
        mini = 10**100
        miniPos = []
        for p in self.particles:
            f = self.function(p['x'])
            if f < mini:
                mini = f
                miniPos = p['x'].copy()
        return miniPos
    
    def getGlobalBest(self):
        return self.globalBest.copy()
    
    def finalCondition(self):
        # każda cząsteczka ma zerową prędkość
        return len(self.particles) == 0
    
    def move(self, alpha, beta, eta):
        # dla każdej cząstki

        # obliczenie nowej prędkości
        for p in self.particles:
            # dla każdego wymiaru
            for d in range(len(p['x'])):                
                b = random.random() * beta
                e = random.random() * eta
                vi = p['v'][d]
                xi = p['x'][d]
                bestxi = p['best'][d]
                minxi = self.globalBest[d]
                p['v'][d] = alpha * vi + b * (bestxi - xi) + e * (minxi - xi)
        
        # usunięcie cząstek o zerowej prędkości
        newPart = []
        for p in self.particles:
            if p['v'] != [0 for i in range(len(p['v']))]:
                newPart.append(p)
        self.particles = newPart.copy()

        # zaktualizowanie czasteczek
        for p in self.particles:
            # przesuń o wyliczony wektor V
            for i in range(len(p['x'])):
                p['x'][i] += p['v'][i]
                if p['x'][i] > 5:
                    p['x'][i] = 5
                if p['x'][i] < -5:
                    p['x'][i] = -5
            # uaktualnienie best
            if self.function(p['best']) > self.function(p['x']):
                p['best'] = p['x'].copy()
            # uaktualnienie globalBest
            if self.function(self.globalBest.copy()) > self.function(p['x']):
                self.globalBest = p['x'].copy()
