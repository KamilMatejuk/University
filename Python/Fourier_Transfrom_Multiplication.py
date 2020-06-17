# Kamil Matejuk
from cmath import exp
import math
import random
import numpy as np
import time
import multiprocessing 


class FastBigNum1:
	# z wykorzystaniem kodów ze polecenia

	def __init__(self, number):
		self.numberList = [int(number[i]) for i in range(len(number)-1,-1,-1)] + [0 for i in range(len(number))]

	def __mul__(self, other):
		if not isinstance(other, FastBigNum1):
			return 0
		X = self.numberList
		Y = other.numberList
		Xstar = self.dft(X,len(X))
		Ystar = self.dft(Y,len(Y))
		Zstar = [ Xstar[i]*Ystar[i] for i in range(min(len(Xstar), len(Ystar)))]
		Z = self.idft(Zstar,len(Zstar))
		return sum(Z[i] * 10**i for i in range(len(Z)))

	def __str__(self):
		return str(sum(self.numberList[i] * 10**i for i in range(len(self.numberList))))

	def omega(self,k,n):
		return exp(-2j*k*math.pi/n)

	def dft(self,x,n):
		return [sum(x[i]*self.omega(i*k,n) if i<len(x) else 0 for i in range(n)) for k in range(n)]

	def idft(self,x,n):
		return [int(round(sum(x[i]*self.omega(-i*k,n) if i<len(x) else 0 for i in range(n)).real)/n) for k in range(n)]


'''class FastBigNum2:
	# moja własna wersja

	def __init__(self, number):
		self.numberList = [int(number[i]) for i in range(len(number)-1,-1,-1)] + [0 for i in range(len(number))]

	def __mul__(self, other):
		if not isinstance(other, FastBigNum2):
			return 0
		X = self.numberList
		Y = other.numberList
		Zstar = self.multiplyfft(X,Y)
		Z = self.idft(Zstar)
		return sum(Z[i] * 10**i for i in range(len(Z)))

	def __str__(self):
		return str(sum(self.numberList[i] * 10**i for i in range(len(self.numberList))))

	def multiplyfft(self,x,y):
		z = []
		n = min(len(x),len(y))
		for k in range(n):
			w = math.e**((-2j*math.pi/n)*k)
			fftxk = sum(x[j] * w**j for j in range(n))
			fftyk = sum(y[j] * w**j for j in range(n))
			z.append(fftxk*fftyk)
		return z

	def idft(self,z):
		n = len(z)
		zz = []
		for j in range(n):
			el = (1/n) * sum(z[k] * math.e**((2j*math.pi/n)*k*j) for k in range(n))
			zz.append(int(round(el.real)))
		return zz'''


class FastBigNum2:
	# moja własna wersja

	def __init__(self, number):
		self.numberList = [int(number[i]) for i in range(len(number)-1,-1,-1)] + [0 for i in range(len(number))]

	def __mul__(self, other):
		if not isinstance(other, FastBigNum2):
			return 0
		X = self.numberList
		Y = other.numberList
		Zstar = self.multiplyfft(X,Y)
		Z = self.idft(Zstar)
		return sum(Z[i] * 10**i for i in range(len(Z)))

	def __str__(self):
		return str(sum(self.numberList[i] * 10**i for i in range(len(self.numberList))))

	def multiplyfft(self,x,y):
		n = min(len(x),len(y))
		pool = multiprocessing.Pool()
		return pool.map(self.sumk, [[x,y,k] for k in range(n)])

	def sumk(self,arr):
		[x,y,k] = arr
		n = min(len(x),len(y))
		w = math.e**((-2j*math.pi/n)*k)
		fftxk = sum(x[j] * w**j for j in range(n))
		fftyk = sum(y[j] * w**j for j in range(n))
		return fftxk*fftyk

	def idft(self,z):
		n = len(z)
		pool = multiprocessing.Pool()
		return pool.map(self.sumz, [[z,n,j] for j in range(n)])

	def  sumz(self,arr):
		[z,n,j] = arr
		el = (1/n) * sum(z[k] * math.e**((2j*math.pi/n)*k*j) for k in range(n))
		return int(round(el.real))


class FastBigNum3:
	# z wykorzystaniem paczki numpy

	def __init__(self, number):
		self.numberList = [int(number[i]) for i in range(len(number)-1,-1,-1)] + [0 for i in range(len(number))]

	def __mul__(self, other):
		if not isinstance(other, FastBigNum3):
			return 0
		X = self.numberList
		Y = other.numberList
		Xstar = np.fft.fft(X)
		Ystar = np.fft.fft(Y)
		Zstar = [ Xstar[i]*Ystar[i] for i in range(min(len(Xstar), len(Ystar)))]
		Z = [int(round(z)) for z in np.real(np.fft.ifft(Zstar)) ]
		s = sum(Z[i] * 10**i for i in range(len(Z)))
		return s

	def __str__(self):
		return str(sum(self.numberList[i] * 10**i for i in range(len(self.numberList))))


def times(length, count):
	tOne = 0
	tTwo = 0
	tThree = 0
	for i in range(count):
		A = ''.join([random.choice("0123456789") for i in range(length)])
		B = ''.join([random.choice("0123456789") for i in range(length)])
		a = FastBigNum1(A)
		b = FastBigNum1(B)
		t = time.time()
		a*b
		tOne += (time.time() - t) / count
		a = FastBigNum2(A)
		b = FastBigNum2(B)
		t = time.time()
		a*b
		tTwo += (time.time() - t) / count
		a = FastBigNum3(A)
		b = FastBigNum3(B)
		t = time.time()
		a*b
		tThree += (time.time() - t) / count
		print('Długość:', length, 'próba:', i+1)
	return [tOne, tTwo, tThree]


def generateStats():
	numberOfTries = 1
	with open('fastbignum_benchmark.txt','w+') as f:
		# w kolumnach odpowiednio rozmiar danych oraz pierwsza, druga i trzecia implementacja
		f.write('100\t'+'\t'.join([str(t) for t in times(100,numberOfTries)])+'\n')
		f.write('1000\t'+'\t'.join([str(t) for t in times(1000,numberOfTries)])+'\n')
		f.write('10000\t'+'\t'.join([str(t) for t in times(10000,numberOfTries)])+'\n')
		f.write('100000\t'+'\t'.join([str(t) for t in times(100000,numberOfTries)])+'\n')
		f.write('500000\t'+'\t'.join([str(t) for t in times(500000,numberOfTries)])+'\n')
		f.write('1000000\t'+'\t'.join([str(t) for t in times(1000000,numberOfTries)])+'\n')


if __name__ == '__main__':
	A = ''.join([random.choice("0123456789") for i in range(1000)])
	B = ''.join([random.choice("0123456789") for i in range(1000)])
	a = FastBigNum1(A)
	b = FastBigNum1(B)
	assert a*b == int(A)*int(B)
	a = FastBigNum2(A)
	b = FastBigNum2(B)
	assert a*b == int(A)*int(B)
	a = FastBigNum3(A)
	b = FastBigNum3(B)
	assert a*b == int(A)*int(B)

	generateStats()
