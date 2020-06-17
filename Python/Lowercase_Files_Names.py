# Kamil Matejuk
import os
import sys

def renameLowercase(basePath):
	try:
		for file in os.listdir(basePath):
			os.rename(os.path.join(basePath, file), 
		  			  os.path.join(basePath, file.lower()))
	except FileNotFoundError:
		print("Can't find file \"" + basePath + "\"")


if __name__ == "__main__":
	if len(sys.argv) > 1:
		renameLowercase(sys.argv[1])
	else:
		print("Specify directory")
