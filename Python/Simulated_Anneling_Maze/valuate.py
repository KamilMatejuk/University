#!/usr/bin/python

#  Kamil Matejuk

import math
import random
from maze import findStart


def valuatePath(maze, path):

	if checkIfExits(maze, path):
		return valuateExitingPath(maze, path)
	else:
		return valuateNonExitingPath(maze, path)


def valuateExitingPath(maze, path):
	vd = 10	# długość jest zła
	# dystans się nie liczy
	vz = 10	# zwroty są złe
	vs = 10	# sąsiedztwo ścian jest złe
	ve = -100000	# wyjście jest dobre

	moves = ['U','D','L','R']
	opposite = ['D','U','R','L']

	value = 0
	x, y = findStart(maze)
	for i in range(len(path)):
		if path[i]	 == 'U':	y -= 1
		elif path[i] == 'D':	y += 1
		elif path[i] == 'L':	x -= 1
		elif path[i] == 'R':	x += 1
		neighbours = [check(maze,x,y-1), check(maze,x+1,y), check(maze,x,y+1), check(maze,x-1,y)]
		# sąsiedztwo ścian
		if 1 in neighbours:
			value += vs
		# zwroty
		if i > 0 and path[i] == opposite[moves.index(path[i-1])]:
			value += vz
	# długość
	value += vd * len(path)
	# wyjście
	value += ve

	return value
	

def valuateNonExitingPath(maze, path):
	# długość sie nie liczy
	vd = -2	# dystans jest dobry
	vs = -10	# sąsiedztwo ścian jest dobre
	ve = -50	# sąsiedztwo wyjścia jest dobre
	
	moves = ['U','D','L','R']
	opposite = ['D','U','R','L']

	value = 0
	startX, startY = findStart(maze)
	x, y = startX, startY
	for i in range(len(path)):
		if path[i]	 == 'U':	y -= 1
		elif path[i] == 'D':	y += 1
		elif path[i] == 'L':	x -= 1
		elif path[i] == 'R':	x += 1
		neighbours = [check(maze,x,y-1), check(maze,x+1,y), check(maze,x,y+1), check(maze,x-1,y)]
		# sąsiedztwo ścian
		if 1 in neighbours:
			value += vs
		# sąsiedztwo wyjścia
		if 8 in neighbours:
			value += ve
	# dystans
	d = (startX-x) + (startY-y)
	value += vd * d

	return value


def check(maze, x, y):
	try:
		number = maze[y][x]
	except IndexError:
		# 7 - is not inside maze
		number = 7
	finally:
		return number


def checkIfExits(maze, path):
	posX, posY = findStart(maze)
	for i in range(len(path)):
		if path[i] == 'U':	posY -= 1
		elif path[i] == 'D':	posY += 1
		elif path[i] == 'L':	posX -= 1
		elif path[i] == 'R':	posX += 1
	return 0 <= posY < len(maze) and 0 <= posX < len(maze[posY]) and maze[posY][posX] == 8
