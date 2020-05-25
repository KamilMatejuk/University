# Polecenie
Napisz program, który za pomocą wybranego algorytmu populacyjnego znajdzie minimum funkcji [X.S.Yang](http://benchmarkfcns.xyz/benchmarkfcns/xinsheyangn1fcn.html)

## In:
11 liczb (6 całkowitych i 5 typu double) t x1 x2 x3 x4 x5 ε1 ε2 ε3 ε4 ε5 oddzielonych spacją.
t – maksymalna liczba sekund, którą może wykonywać się program w tym uruchomieniu,
x = (x1, x2, x3, x4, x5) – rozwiązanie początkowe
εi są współczynnikami funkcji reprezentowanymi przez liczby typu double
Uwaga:
* Dla każdego i: |xi| <= 5.
* Dla każdego i: εi - niezależna zmienna losowa z rozkładu jednostajnego na [0, 1]</br>
Wczytywane poprzez `./main <infile`

## Out: 
6 liczb typu double oddzielonych spacją, z czego pięć pierwsze to x, natomiast szósta to wartość odpowiedniej funkcji w punkcie x.
