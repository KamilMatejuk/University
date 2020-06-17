# Polecenie
Korzystając ze struktury zaimplementowanej w `Priority_Queue_using_Heap` zaimplementuj program realizujący algorytm Djikstry, dla podanego grafu skierowanegoo `G=(V, E)`, znajdujący najkrótsze ścieżki z wybranego wierzchołka `v ∈ V` do każdego `v ∈ V`.

## Wejście
Program nie powinien wymagaćzadnych parametrów uruchomienia. Po uruchomieniu programu, na standardowym wejsciu, podajemy definicję grafu `G` oraz wierzchołek startowy `v`. Kolejno wczytywane sąa:
* liczba wierzchołków `n=|V|` (przyjmujmy, ze wierzchołki są etykietowane kolejnymi liczbami naturalnymi {1, . . . , n})
* liczba krawędzi `m = |E|` (krawędzie są postaci `(u, v, w)`, gdzie `u` to źródło krawędzi, `v` jest wierzchołkiem docelowym a `w` wagą krawędzi – zakładamy, ze wagi są nieujemnymi liczbami rzeczywistymi, ale niekoniecznie spełniona jest nierówność trójkąta, ponadto przyjmujemy że ścieżka z `u` do `u` zawsze istnieje i ma koszt 0)
* kolejno `m` definicji krawędzi w postaci `u v w`
* etykieta wierzchołka startowego

## Wyjście
Na standardowym wyjsciu powinno zostać wyświetlone `n` linii, w formacie `id_celu waga_drogi`, natomiast na standardowym wyjsciu błędów powinny być wypisane dokładne ścieżki (tzn. wierzchołki pośrednie i wagi) do kazdego z wierzchołków docelowych oraz czas działania programu w milisekundach. Drogi wypisywane są w postaci: `v_1 (w_1) v_2 (w_2) ... (w_n-1) v_n`, gdzie `v_i` oznacza wierzchołek, a `w_i` wagę krawędzi.

## Przykładowe wywołanie
```
java main <source >out.res 2>err
```
**source**
```
8
15
5 6 0.35
6 5 0.35
5 8 0.37
6 8 0.28
8 6 0.28
6 2 0.32
1 5 0.38
1 3 0.26
8 4 0.39
2 4 0.29
3 8 0.34
7 3 0.40
4 7 0.52
7 1 0.58
7 5 0.93
3
```

**out.res**
```
1 1.83000
2 0.94000
3 0.00000
4 0.73000
5 0.97000
6 0.62000
7 1.25000
8 0.34000
```

**err**
```
3 (0.34) 8 (0.39) 4 (0.52) 7 (0.58) 1 
3 (0.34) 8 (0.28) 6 (0.32) 2 
3 
3 (0.34) 8 (0.39) 4 
3 (0.34) 8 (0.28) 6 (0.35) 5 
3 (0.34) 8 (0.28) 6 
3 (0.34) 8 (0.39) 4 (0.52) 7 
3 (0.34) 8 
Czas działania programu 41.055496 ms
```
