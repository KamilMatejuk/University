# Kamil Matejuk


import time
from Population import Population


def main(maxTime, startingX, function):
    startTime = time.time()
    swarmSize = 100
    population = Population(swarmSize, startingX, function)
    ''' 
    prędkość = alpha * (poprzednia prędkość)
                + beta * (odległość od swojego najlepszego)
                + eta * (odległość od globalnego najlepszego)
    '''
    # współczynnik hamowania
    startAlpha = 0.95
    endAlpha = 0.4
    # współczynnik istotności najlepszego swojego wyniku
    startBeta = 1
    endBeta = 2
    # współczynnik istotności najlepszego wyniku globalnego
    startEta = 1
    endEta = 2


    numberOfIterations = maxTime * 90000 / swarmSize
    iter = 0

    while (time.time() - startTime) < maxTime and not population.finalCondition():
        i = min([(iter / numberOfIterations), 1])
        alpha = startAlpha - i * (startAlpha - endAlpha)
        beta  = startBeta  - i * (startBeta  - endBeta)
        eta   = startEta   - i * (startEta   - endEta)
        population.move(alpha, beta, eta)
        iter += 1

    position = population.getGlobalBest()
    value = function(position)
    # wypisanie wyniku
    for p in position:
        print(p, end=" ")
    print(value)
