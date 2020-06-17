import scipy.sparse as ss
from scipy.sparse import linalg
import csv
import numpy as np
import time
from os import system
from sklearn.preprocessing import normalize
import resource


# klasa odpowiadająca za wyświetlanie progresu w konsoli
class Log(object):
    def __init__(self):
        self.log = [
            'czas: 0s',
            'pamięć: 0 MB']
        self.startTime = time.time()

    def add(self, text):
        self.log.append(text)
        return self

    def progress(self, text, procent):
        item = -1
        for i in range(len(self.log)):
            if text in self.log[i]:
                item = i
                break
        progress = ' ' + ''.join(['#' for i in range(int(procent / 2))]) + \
            ''.join(['.' for i in range(int(50 - procent / 2 + 0.5))]) + \
            f' {procent}%'
        if item == -1:
            self.log.append(text + progress)
        else:
            self.log[item] = text + progress

    def show(self):
        system('clear')
        for line in self.log:
            print(line)

    def updateMemoryAndTime(self):
        memoryUsage = resource.getrusage(resource.RUSAGE_SELF).ru_maxrss
        self.log[0] = f'czas: {self.formatTime(time.time() - self.startTime)}'
        self.log[1] = f'pamięć: {self.formatMemory(memoryUsage)}'
        return self

    def formatTime(self, sek):
        min = int(sek / 60)
        s = int(sek % 60)
        if min > 0:
            return f'{min} min {s} s'
        else:
            return f'{s} s'

    def formatMemory(self, mem):
        kb = int(mem % 10**3)
        mb = int(mem % 10**6 / 10**3)
        gb = int(mem % 10**9 / 10**6)
        if gb > 0:
            return f'{gb}.{int(mb / 100)} GB'
        elif mb > 0:
            return f'{mb}.{int(kb / 100)} MB'
        else:
            return f'{kb} kB'


if __name__ == '__main__':
    userId = 100        # proponowanie filmów dla użytkownika nr 100
    topResults = 10     # ilość wynikow do pokazania (najlepsze 10)
    log = Log()

    # sprawdzenie ilości filmów i użytkowników
    with open("ml-latest/movies.csv", 'r') as f:
        numberOfMovies = int(list(f)[-1].split(',')[0]) + 1
    with open("ml-latest/ratings.csv", 'r') as f:
        numberOfUsers = int(list(f)[-1].split(',')[0]) + 1

    if userId >= numberOfUsers:
        # nie ma takiego uzytkownika w bazie
        print(f'No user {userId} in ratings.csv')
        exit(0)

    log.updateMemoryAndTime()
    log.add(f'wykryto {numberOfUsers} użytkowników i {numberOfMovies} filmów')
    log.show()

    # stworzenie pustych macierzy
    allRatings = ss.lil_matrix((numberOfUsers, numberOfMovies))
    userRating = ss.lil_matrix((1, numberOfMovies))

    # wczytanie danych z ratings.csv i wpisanie do macierzy
    with open("ml-latest/ratings.csv", 'r') as f:
        data = csv.DictReader(f)
        i = 0
        for row in data:
            uId = int(row['userId'])
            mId = int(row['movieId'])
            rat = float(row['rating'])
            if uId != userId:
                allRatings[uId, mId] = rat
            else:
                userRating[0, mId] = rat
            i += 1
            if i % 280000 == 0:
                procent = int(i / 280000)
                log.progress('generowanie macierzy', procent)
                log.updateMemoryAndTime().show()

    log.progress('generowanie macierzy', 100)
    log.progress('obliczenia', 0)
    log.updateMemoryAndTime().show()

    # OBLICZENIE REKOMENDACJI
    # znormalizowane macierze
    normalizedRatings = normalize(allRatings, axis=0)
    normalizedUserRating = normalize(userRating).T
    log.progress('obliczenia', 20)
    log.updateMemoryAndTime().show()

    # podobieństwo cosinusowe z każdym użytkownikiem (mnożenia macierzowe)
    probCos = np.dot(normalizedRatings, normalizedUserRating)
    log.progress('obliczenia', 40)
    log.updateMemoryAndTime().show()

    # znormalizowane podobieństwo (nasz profil filmowy)
    userProfile = normalize(probCos, axis=0)
    log.progress('obliczenia', 60)
    log.updateMemoryAndTime().show()

    # porównujemy oceny filmów z otrzymanym profilem uzytkownika
    recommendatoins = np.dot(normalizedRatings.T, userProfile)
    log.progress('obliczenia', 80)
    log.updateMemoryAndTime().show()

    # indeksy najwyższych 'topResults' elementów
    rec = recommendatoins.toarray().reshape((numberOfMovies))
    movieIndexes = np.flip(np.argpartition(rec, -topResults)[-topResults:])
    movieProbabilities = rec[movieIndexes]
    log.progress('obliczenia', 100)
    log.updateMemoryAndTime().show()

    # wyświetlanie nazw filmów
    with open("ml-latest/movies.csv", 'r') as f:
            movies = list(csv.reader(f, delimiter=","))

    log.add('\nREKOMENDACJE:')
    for i in range(len(movieIndexes)):
        prob = movieProbabilities[i]
        index = movieIndexes[i]
        name = movies[index][1]
        log.add('%.5f\t%s' % (prob, name))
    log.updateMemoryAndTime().show()
