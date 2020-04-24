#!/usr/bin/python

#  Kamil Matejuk

import copy
import random
from maze import findStart, showMaze


def createInitial(maze):
	n = len(maze)
	m = len(maze[0])
	minlength = int(0.20 * (n-1) * (m-1))	# 20% of area of maze
	maxlength = int(1.00 * (n-1) * (m-1)) 	# 100% of area of maze
	length = random.randint(minlength, maxlength)
	return createPath(length)
	

def createPath(length):
	length = int(length)
	moves = ['U','D','L','R']
	opposite = ['D','U','R','L']
	path = ''
	prev = random.choice(moves)
	for l in range(length):
		# choose next move such as it won't cancel prev one e.g. U-D
		op = opposite[moves.index(prev)]
		m = moves.copy()
		m.remove(op)
		path += random.choice(m)
		prev = path[-1]
	return path


def trimPath(maze, path):
	posX, posY = findStart(maze)
	for i in range(len(path)):
		if path[i] == 'U':	posY -= 1
		elif path[i] == 'D':	posY += 1
		elif path[i] == 'L':	posX -= 1
		elif path[i] == 'R':	posX += 1
		if posY < 0 or posY >= len(maze) or posX < 0 or posX >= len(maze[0]):
			return path[:i]
		elif 0 <= posY < len(maze) and 0 <= posX < len(maze[posY]) and maze[posY][posX] == 8:
			return path[:i+1]
		elif 0 <= posY < len(maze) and 0 <= posX < len(maze[posY]) and maze[posY][posX] == 1:
			return path[:i]
	return path


def drawPath(maze, path):
	m = copy.deepcopy(maze)
	startX, startY = findStart(maze)
	x, y = startX, startY
	for i in range(len(path)):
		if path[i]	 == 'U':	y -= 1
		elif path[i] == 'D':	y += 1
		elif path[i] == 'L':	x -= 1
		elif path[i] == 'R':	x += 1
		if x != startX or y != startY:
			m[y][x] = '.'
	showMaze(m)