# Kamil Matejuk
import random
import time
from graph import Graph


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


def generujGraf(numV, numE):
    # graf spójny o v wierzchołkach, e krawędziach, oraz średniej przepustowości a
    g = Graph()
    for i in range(numV):
        g.add_node(i)
    
    if numE < numV-1:	numE = numV-1
    elif numE > (numV * (numV-1))/2:	numE = (numV * (numV-1))/2

    E = []
    V = [i+1 for i in range(numV)]
    random.shuffle(V)
    i = 1
    while i < len(V):
        e = (V[i-1], V[i])
        E.append(e)
        i += 1
    while i < numE:
        e = (random.randint(1, numV+1), random.randint(1, numV+1))
        if e not in E:
            E.append(e)
            i += 1

    edges_dict = []
    for (v1, v2) in E:
        g.add_edge(v1, v2, 1)
        g.add_edge(v2, v1, 1)
        item = {
            'nodes': (v1, v2),
            'przepustowosc': 0,
            'przeplyw': 0
        }
        edges_dict.append(item)
    return g, edges_dict

def generujMacierzNatezen(graph, maxpackets):
    n = len(graph.nodes())
    natezenia = [[random.randint(0, maxpackets) if i != j else 0 for i in range(n)] for j in range(n)]
    return natezenia

def generujPrzepustowosci(edges_dict, avg):
    for item in edges_dict:
        c = random.randint(int(0.5 * avg), int(1.5 * avg))
        item['przepustowosc'] = c

def usunZawodzaceKrawedzie(graph, p):
    # zwraca zbiór krawędzi bez tych które zawiodły z prawdopodobieństwem p
    g = graph.copy()
    for e in graph.edges():
        if random.random() < p:
            (v1, v2, c) = e
            g.del_edge(v1, v2)
    return g

def generujPrzeplywy(graph, edges_dict, N):
    for item in edges_dict:
        item['przeplyw'] = 0
    packets = []
    for i in range(len(N)):
        for j in range(len(N[i])):
            (length, path) = graph.shortest_path(i+1, j+1)
            if len(path) > 1:
                value = N[i][j]
                packets.append(value)
                # dla każdej krawędzi ze ścieżki dodać wartość przepływu
                for k in range(1, len(path)):
                    v1 = path[k-1]
                    v2 = path[k]
                    for item in edges_dict:
                        if item['nodes'] == (v1,v2) or item['nodes'] == (v2, v2):
                            item['przeplyw'] += value
                            break
    m = sum(packets) / len(packets)
    return m

def overflow(edges_dict, m):
    # czy pzrzepływ przekroczył przepustowość
    for item in edges_dict:
        if item['przeplyw'] >= item['przepustowosc']/m:
            return False
    return False

def T(N, edges_dict, m):
    G = sum(sum(i for i in row) for row in N)
    suma = 0
    for e in edges_dict:
        a = e['przeplyw']
        c = e['przepustowosc']
        if (c/m - a) <= 0:
            raise ValueError
        suma += (a / (c/m - a))
    return (suma / G)

def main():
    # dane
    graph, edges_dict = generujGraf(20, 30)     # ilość krawędzi
    N = generujMacierzNatezen(graph,5)          # maksumalne natężenie
    generujPrzepustowosci(edges_dict, 1000)     # średnie przepustowości
    p = 0.15                                    # prawdopodobieństwo zawodu krawędzi
    T_max = 0.01

    # program
    okCounter = 0
    allCounter = 0
    for sek in range(1000):
        allCounter += 1
        e_dict = edges_dict.copy()
        newGraph = usunZawodzaceKrawedzie(graph,p)
        m = generujPrzeplywy(newGraph, e_dict, N)
        try:
            if not overflow(e_dict, m):
                t = T(N, e_dict, m)
                if t < T_max:
                    okCounter += 1
                print(sek,'\t',t)
        except ValueError:
            pass
    print(f'Prawdopodobienstwo P(T < {T_max}) wynosi {int(100*okCounter/allCounter)}%')

def testNatezenia(values):
    # dane
    graph, edges_dict = generujGraf(20, 30)
    generujPrzepustowosci(edges_dict, 1000)
    srednia_przepustowosc = sum(i['przepustowosc'] for i in edges_dict) / len(edges_dict)
    p = 0.15
    T_max = 0.01
    for intensity in values:
        N = generujMacierzNatezen(graph, intensity)
        suma_natezen = sum(sum(i for i in line) for line in N)
        okCounter = 0
        allCounter = 0
        for sek in range(1000):
            allCounter += 1
            e_dict = edges_dict.copy()
            newGraph = usunZawodzaceKrawedzie(graph,p)
            m = generujPrzeplywy(newGraph, e_dict, N)
            try:
                if not overflow(e_dict, m):
                    t = T(N, e_dict, m)
                    if t < T_max:
                        okCounter += 1
            except ValueError:
                pass
        print(f'nateżenie {intensity},\t niezalezność: {int(100*okCounter/allCounter)}%, \tstosunek {suma_natezen/srednia_przepustowosc}')

def testPrzepustowosci(values):
    # dane
    graph, edges_dict = generujGraf(20, 30)
    N = generujMacierzNatezen(graph, 5)
    suma_natezen = sum(sum(i for i in line) for line in N)
    p = 0.15
    T_max = 0.01
    for capacity in values:
        generujPrzepustowosci(edges_dict, capacity)
        srednia_przepustowosc = sum(i['przepustowosc'] for i in edges_dict) / len(edges_dict)
        okCounter = 0
        allCounter = 0
        for sek in range(1000):
            allCounter += 1
            e_dict = edges_dict.copy()
            newGraph = usunZawodzaceKrawedzie(graph,p)
            m = generujPrzeplywy(newGraph, e_dict, N)
            try:
                if not overflow(e_dict, m):
                    t = T(N, e_dict, m)
                    if t < T_max:
                        okCounter += 1
            except ValueError:
                pass
        print(f'przepustowość {capacity},\t niezalezność: {int(100*okCounter/allCounter)}%, \tstosunek {suma_natezen/srednia_przepustowosc}')

def testKrawedzi(values):
    # dane
    p = 0.15
    T_max = 0.0035
    for edges in values:
        graph, edges_dict = generujGraf(20, edges)
        N = generujMacierzNatezen(graph, 5)
        generujPrzepustowosci(edges_dict, 1000)
        okCounter = 0
        allCounter = 0
        for sek in range(1000):
            allCounter += 1
            e_dict = edges_dict.copy()
            newGraph = usunZawodzaceKrawedzie(graph,p)
            m = generujPrzeplywy(newGraph, e_dict, N)
            try:
                if not overflow(e_dict, m):
                    t = T(N, e_dict, m)
                    if t < T_max:
                        okCounter += 1
            except ValueError:
                pass
        print(f'{edges} krawędzi,\t niezalezność: {int(100*okCounter/allCounter)}%')

if __name__ == '__main__':
    main()
#     testNatezenia([2, 3, 4, 5, 6, 7, 8, 9, 10])
#     testPrzepustowosci([500, 750, 1000, 1250, 1500, 1750, 2000])
#     testKrawedzi([30, 50, 70, 90, 110, 130, 150, 170, 190])
