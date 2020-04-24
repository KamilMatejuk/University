#!/usr/bin/python

#  Kamil Matejuk


def printMatrix(M):
	for line in M:
		for n in line:
			print(str(n), end=" ")
		print()


def showMaze(maze):
	for line in maze:
		for n in line:
			if n == 0:
				print(' ', end=" ")
			elif n == 1:
				print('#', end=" ")
			elif n == 5:
				print('@', end=" ")
			elif n == 8:
				print(' ', end=" ")
			else:
				print(str(n), end=" ")
		print()


def findStart(maze):
	for y in range(len(maze)):
		for x in range(len(maze[y])):
			if maze[y][x] == 5:
				posX = x
				posY = y
				break
	return posX, posY