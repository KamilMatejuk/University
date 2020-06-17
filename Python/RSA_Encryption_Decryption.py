# Kamil Matejuk
# algorytm RSA https://en.wikipedia.org/wiki/RSA_(cryptosystem)#Encryption
import sys
import math
import random
import time


def generateKeys(bits):
	length = int(math.log(2) / math.log(10) * int(bits))
	p = int(''.join(str(random.randint(0,9)) for i in range(length)))
	while not isPrime(p,20):
		p = int(''.join(str(random.randint(0,9)) for i in range(length)))
	q = int(''.join(str(random.randint(0,9)) for i in range(length)))
	while not isPrime(q,20) and p != q:
		q = int(''.join(str(random.randint(0,9)) for i in range(length)))
	n = p*q
	fi = lowestCommonMultiple(p-1, q-1)
	e = coprime(fi)
	d = modularInverse(e,fi)
	# print("p", p)
	# print("q",q)
	# print("n",n)
	# print("fi", fi)
	# print("e",e)
	# print("d",d)
	with open('key.pub', 'w+') as f:
		f.write(str(n)+"\n")
		f.write(str(e))
	with open('key.prv', 'w+') as f:
		f.write(str(d))


def greatestCommonDivisor(a,b):
	# największy wspólny dzielnik
	while(b):
		a, b = b, a % b
	return a


def lowestCommonMultiple(a,b):
	# najmiejsza wspólna wielokrotność
	gcd = greatestCommonDivisor(a,b)
	return (a*b) // gcd


def coprime(fi):
	length = random.randint(1, len(str(fi-1)))
	e = int(''.join(str(random.randint(0,9)) for i in range(length)))
	while e >= fi or e <= 1 or greatestCommonDivisor(e,fi) != 1:
		length = random.randint(1, len(str(fi-1)))
		e = int(''.join(str(random.randint(0,9)) for i in range(length)))
	return e


def modularInverse(a,b):
	# zwraca x: (x*a) % b == 1
	x0, x1, y0, y1 = 0, 1, 1, 0
	tempB = b
	while a != 0:
		q, a, tempB = tempB // a, tempB % a, a
		y0, y1 = y1, y0 - q * y1
		x0, x1 = x1, x0 - q * x1
	g = tempB
	x = x0
	if g == 1:
		return x % b


def modularExponentiation(a,b,c):
	# Modular exponentiation by repeated squaring
	# zwraca x: (a^b) % c == x

	if b == 1:
		return a % c
	if b%2 == 0: #even
		x = modularExponentiation(a, b//2, c)
		return (x*x)%c
	elif b%2 == 1: #odd
		x = modularExponentiation(a, (b-1)//2, c)
		return ((x*x)%c * a) % c


def isPrime(n, trials):
	if n != int(n):
		return False
	if n in [0,1,4,6,8,9]:
		return False
	if n in [2,3,5,7]:
		return True
	if n % 2 == 0:
		return False

	# postać n = d * 2^r + 1
	r = 0
	d = n - 1
	while d%2 == 0:
		d //= 2
		r += 1
	assert(2**r * d == n-1)
	
	# test Millera-Robina	
	def millerRabinTest(a):
		# pow (a,b,c) = a^b % c
		if pow(a, d, n) == 1:
			return False
		for i in range(r):
			if pow(a, 2**i * d, n) == n-1:
				return False
		return True

	for i in range(trials):
		a = random.randrange(2, n)
		if millerRabinTest(a):
			return False
	return True


def encrypt(text):
	try:
		with open('key.pub') as f:
			n = int(f.readline())
			e = int(f.readline())
		decimal = ""
		for letter in text:
			decimal += (format(ord(letter), 'd').zfill(3))[0:3]
		# decimal = text
		print(modularExponentiation(int(decimal), e, n))
	except FileNotFoundError:
		print("Cannot find public key")


def decrypt(text):
	try:
		with open('key.prv') as f:
			d = int(f.readline())
		with open('key.pub') as f:
			n = int(f.readline())
		plain = modularExponentiation(int(text), d, n)
		# print(plain)

		x = len(str(plain)) + ((0 - len(str(plain))) % 3)
		plain = format(plain).zfill(x)
		plain2 = [ plain[i:i+3] for i in range(0, len(str(plain)), 3) ]
		plain3 = [chr(int(p)) for p in plain2]
		print(''.join(plain3))
		# print(plain2)
		# print((end-start),"s")
	except FileNotFoundError:
		print("Cannot find private key")
	# except UnicodeDecodeError:
	# 	print("Cannot decode")


if __name__ == "__main__":
	if len(sys.argv) == 3:
		if sys.argv[1] == "--gen-keys":
			generateKeys(sys.argv[2])
		elif sys.argv[1] == "--encrypt":
			encrypt(sys.argv[2])
		elif sys.argv[1] == "--decrypt":
			decrypt(sys.argv[2])
