import sys
import random


class Node:
	def __init__(self, value):
		self.value = value
		self.children = []

	def addChild(self, child):
		self.children.append(child)

	def getChildren(self):
		return self.children


def generateTree(height, minValue, maxValue):
	try:
		height = int(height)
	except ValueError:
		print("Tree height should be an Integer")
	else:
		if height < 0:
			return None
		root = Node(random.randint(minValue, maxValue))
		latest = [root]
		print("Tree: ")
		while height > 0:
			print([i.value for i in latest])
			newlatest = []
			for node in latest:
				for i in range(random.randint(0,3)):
					newnode = Node(random.randint(minValue, maxValue))
					node.addChild(newnode)
					newlatest.append(newnode)
			if len(newlatest) == 0:
				newnode = Node(random.randint(minValue, maxValue))
				latest[0].addChild(newnode)
				newlatest.append(newnode)
			latest = newlatest
			height -= 1
		print([i.value for i in latest])
		print()
		return root


def depthFirstSearch(root):
	yield root.value
	for c in root.getChildren():
		yield from depthFirstSearch(c)


def breadthFirstSearch(root):
	visited = []
	queue = []
	visited.append(root)
	queue.append(root)
	while queue:
		s = queue.pop(0) 
		yield s.value
		for c in s.getChildren():
			if c not in visited:
				visited.append(c)
				queue.append(c)


if __name__ == '__main__':
	if len(sys.argv) > 1:
		rootNode = generateTree(sys.argv[1], 1, 100)
		print('DFS:', [ i for i in depthFirstSearch(rootNode) ])
		print('BFS:', [ i for i in breadthFirstSearch(rootNode) ])
	else:
		print("Specify tree height as first parameter")