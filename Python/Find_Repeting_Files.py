# Kamil Matejuk
import os
import sys
import hashlib

# Znajdź i wypisz zduplikowane pliki w danym katalogu. Pliki rozumiane są jako identyczne, jeżeli mają taki sam rozmiar i wartość funckji haszującej

def checkRepeating(basePath):
	hashesAndSizes = []
	repeating = []
	try:
		for root, dirs, files in os.walk(basePath, topdown=False):
			for name in files:
				paths = os.path.join(root, name)
				hashesAndSizes.append([getHash(paths),os.path.getsize(paths),paths])
		hashesAndSizes.sort()
		i = 0
		while i < len(hashesAndSizes)-1:
			while i < len(hashesAndSizes)-1 and hashesAndSizes[i][0] == hashesAndSizes[i+1][0] and hashesAndSizes[i][1] == hashesAndSizes[i+1][1]:
				if hashesAndSizes[i][0] != "Cannot read file":
					print(hashesAndSizes[i][2])
				i += 1
			print(hashesAndSizes[i][2])
			print("------------------")
			i += 1
	except FileNotFoundError:
		print("Can't find file \"" + basePath + "\"")

def getHash(fileName):
	try:
		with open(fileName) as f:
			text = f.read()
		return hashlib.md5(text.encode()).hexdigest()

	except FileNotFoundError:
		print("Cannot find file \"" + fileName + "\"")
	except UnicodeDecodeError:
		return "Cannot read file"


if __name__ == "__main__":
	if len(sys.argv) > 1:
		checkRepeating(sys.argv[1])
	else:
		print("Specify directory")
