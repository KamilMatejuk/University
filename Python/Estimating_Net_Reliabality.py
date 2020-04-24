# Kamil Matejuk
import random
import time

# Napisz program szacujący niezawodność sieci

# G - graf spójny (|V|=n, E)
# N - macierz n*n natężeń strumienia pakietów (ilość pakietów przesłanych w ciagu sekundy i->j)
# c(krawędź) - funkcja przepustowości (max liczba bitów którą można wprowadzić w ciągu sekundy)
# a(krawędź) - funkcja przepływu (rzeczywista liczba pakietów wprowadzona w ciągu sekundy)
# a ma realizować macierz N
# dla każdego kanału e: c(e) > a(e)

# niezawodność -> prawdopodobieństwo, że w przedziale czasowym sieć zachowuje T<T_max, gdzie
# T - średnie opóźnienie pakietu w sieci
# G - suma wszystkich elemetów macierzy N
# m - srednia wielkość pakietu w bitach
# T = 1/G * sum[po każdej krawędzi ](a(e) / (c(e)/m - a(e)))
# p - prawdopodobienstwo nieuszkodzenia każdej krawędzi w dowolnym interwale czasu

# 1(N,p,T_max, topologia sieci) - program szacujący niezawodność
# 2 jak zmienia się niezawodność w zależności od N
# 3 jak zmienia się niezawodność w zależności od przepustowości
# 4 jak zmienia się niezawodność w zależności od topologii (dodawanie krawędzi)

def countTime(func):
	def wrapper(*args):
		startTime = time.time()
		value = func(*args)
		endTime = time.time() - startTime
		print("Czas wykonywania funkcji '%s' wynosi %fs" % (func.__name__, endTime))
		return value
	return wrapper


def printMatrix(matrix):
	for row in matrix:
		print(row)

# @countTime
def generujGraf(numV, numE):
	V = [ i+1 for i in range(numV) ]

	if numE < numV-1:	numE = numV-1
	elif numE > (numV * (numV-1))/2:	numE = (numV * (numV-1))/2

	E = []
	E.append((1,2))
	i = 1
	# po jednej krawędzi dla każdego nowego wierzchołka do losowej z poprzednich wierzchołków
	while i < numV-1:
		e = (E[random.randint(0,len(E)-1)][random.randint(0,1)], i+2)
		if e not in E and e[0] < e[1]:
			E.append(e)
			i += 1
	# dodatkowe losowe krawędzie
	while i < numE:
		e = (random.randint(1,numV), random.randint(1,numV))
		if e not in E and e[0] < e[1]:
			E.append(e)
			i += 1
 
	return V, E

# @countTime
def generujMacierzNatezen(n, maxpackets):
	natezenia = [[random.randint(0,maxpackets) for i in range(n)] for j in range(n)]
	return natezenia

def srednienatezenie(N):
	temp = [ sum(i)/len(i) for i in N ]
	return sum(temp)/len(temp) 

# @countTime
def edgesWithP(E, p):
	# zwraca zbiór krawędzi bez tych które zawiodły z prawdopodobieństwem p
	Eprim = []
	for e in E:
		if random.random() < p:
			Eprim.append(e)
	return Eprim


def c(E,avg): # przepustowość
	delta = avg/2
	return [ random.randint(int(avg-delta), int(avg+delta)) for e in E ]

def a(E,przeplywy,cList): # przepływ
	a = []
	for i in range(len(E)):
		przeplyw = przeplywy[i]
		a.append(min(przeplyw, cList[i]-1))
	return a

def G(N):
	return sum(sum(row) for row in N)

def T(E,N,a, c, m):
	try:
		t = (1/G(N)) * sum(a[e] / (c[e]/m - a[e]) for e in range(len(E)))
	except ZeroDivisionError:
		t = 1.0 # overflow 
	return t

# @countTime
def generujMacierzSasiedztwa(n,E):
	return [[1 if (i+1,j+1) in E or (j+1,i+1) in E else 0 for i in range(n)] for j in range(n)]

# @countTime
def generateRoutes(N,E):
	# trasa z kazdego do kazdego woerzcholka
	t = time.time()
	n = len(N)
	S = generujMacierzSasiedztwa(n, E)
	routes = [[findRoute(i+1,j+1, S) for i in range(n)] for j in range(n)]
	# potem zsumowac dla poszczegolnych krawedzi i taki będzie pzrepływ a(e), 
	# przekazac wynikowa tabele do T()
	a = [0 for e in E]
	for i in range(len(N)):
		for j in range(i, len(N[i])):
			packets = N[i][j]
			route = str(routes[i][j]).split(',')
			for k in range(len(route)-1):
				if int(route[k]) < int(route[k+1]):
					e = (int(route[k]), int(route[k+1]))
				else:
					e = (int(route[k+1]), int(route[k]))
				index = E.index(e)
				a[index] += packets
	return a

# @countTime	
def findRoute(i,j,S):
	maxtime = 0.1 # sekund
	# print(f'finding route for {i}-{j}')
	if i==j:
		return ''
	routes = [str(i)]
	newroutes = []
	t = time.time()
	for x in range(len(S)*len(S)):
		for r in routes:
			newi = int(r.split(',')[-1])
			neighboursI = [k+1 for k in range(len(S[newi-1])) if S[newi-1][k] == 1]
			allprevchars = [','.join(routes).split(',')]
			if (time.time()-t > maxtime):
				return ''
			for n in neighboursI:
				if n == j:
					return (str(r) + ',' + str(n))
				if str(n) not in allprevchars:
					newroutes.append(str(r) + ',' + str(n))
		routes = newroutes.copy()


def main():
	# dane
	n = 20
	graphV, graphE = generujGraf(n, 1.5*n)
	N = generujMacierzNatezen(n,10)
	p = 0.85
	T_max = 0.5

	# program
	okCounter = 0
	allCounter = 0
	sumT = 0
	for sek in range(100):
		Ep = edgesWithP(graphE,p)
	 	cList = c(Ep, 100)
	 	aList = a(Ep, generateRoutes(N,Ep), cList)

	 	t = T(Ep, N, aList, cList, m=1)
	 	if t < T_max:
	 		okCounter += 1
	 	allCounter += 1
	 	sumT += t
	 	print(sek,'\t',t)
	print(f'Prawdopodobienstwo P(T < {T_max}) wynosi {int(100*okCounter/allCounter)}%')
	print(f'Średnia wartość T wynosi {sumT/allCounter}')


if __name__ == '__main__':
	main()
