#!/usr/bin/python

#  Kamil Matejuk


import random

from maze import * 
from valuate import * 
from path import *


def p(x, y, maze, T):
	fx = valuatePath(maze, x)
	fy = valuatePath(maze, y)
	if fy <= fx:
		return 1
	else:
		df = (fx - fy)
		return math.exp(df / T)


def neighbour(maze, path):
	moves = ['U','D','L','R']
	opposite = ['D','U','R','L']
	turnLeft = ['L','R','D','U']
	turnRight = ['R','L','U','D']

	p1 = 0.33	# zamiana jednej losowej literki
	p2 = 0.33	# przekręcenie o 90 stopni od jakiegoś pkt
	p3 = 0.33	# dodanie do długości

	p4 = 0.6	# usuwanie zwrotów
	p5 = 0.4	# usuwanie pętli

	if len(path) == 0:
		# w pierwszym kroku wchodzi w ścianę
		path = createInitial(maze)

	if checkIfExits(maze, path):
		rand = random.random()
		if rand < p4:
			# usuwanie zwrotów
			j = -1
			for k in range(10):
				i = random.randint(0, len(path)-1)
				op = opposite[moves.index(path[i])]
				j = path.find(op, i)
				if j != -1:
					break
			if j != -1:
				if i > j:
					i,j = j,i
				path = path[:i] + path[i+1:j] + path[j+1:]
		else:	# p5
			# usuwanie pętli
			positions = []
			x, y = 0, 0
			for i in range(len(path)):
				if path[i]	 == 'U':	y -= 1
				elif path[i] == 'D':	y += 1
				elif path[i] == 'L':	x -= 1
				elif path[i] == 'R':	x += 1
				pos = (x, y)
				if pos in positions:
					k = positions.index(pos)
					j = k-1
					if i > j:
						i,j = j,i
					path = path[:i+1] + path[j+1:]
					break
				else:
					positions.append(i)
					positions.append(pos)
	else:
		rand = random.random()
		if rand < p1:	# p1
			# zamiana jednej losowej literki
			i = random.randint(0, len(path)-1)
			prev = path[i]
			m = moves.copy()
			m.remove(prev)
			new = random.choice(m)
			# path = path[:i] + new + path[i+1:]
			path = path[:i] + path[i+1:] # usuniecie
		
		elif rand < p1 + p2: # p2
			# przekręcenie o 90 stopni od jakiegoś pkt
			i = random.randint(0, len(path)-1)
			path2 = path[:i]
			if random.random()>0.5:
				turn = turnLeft.copy()
			else:
				turn = turnRight.copy()
			for j in range(i, len(path)):
				path2 += turnLeft[moves.index(path[j])]
			path = path2

		else:	# p3
			# dodanie do długości
			length = int(random.random()*len(path)/2) if len(path)>10 else len(path)
			path = path[:-1] + createPath(length)
	return path
