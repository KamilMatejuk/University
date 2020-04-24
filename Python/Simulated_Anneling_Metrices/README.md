# Polecenie
Napisz program, który dla przedstawionej macierzy liczb całkowitych (zakres wartości między 0 a 255) M, znajdzie
najbliższą macierz M0, spełniającą poniższe ograniczenia:
* wykorzystujemy co najwyżej 8 różnych wartości liczbowych: 0, 32, 64, 128, 160, 192, 223, 255,
* macierz wynikowa M0 składa się z bloków a × b, takich że a < k i b < k, wewnątrz jednego bloku wszystkie liczby mają równą wartość,
* odległość między macierzami M i M0, rozmiaru n×m, liczymy jako [dM](https://math.stackexchange.com/questions/507742/distance-similarity-between-two-matrices)

## In: 
Dane wejściowe składać się będą z n+ 1 linii. W pierwszej linii będą umieszczone, oddzielone spacją liczby całkowite t, n, m, k, gdzie t jest limitem czasu, natomiast n i m oznaczają wymiary macierzy początkowej M, a k jest parametrem odpowiadającym za rozmiar bloków w macierzy wynikowej M0. W kolejnych n liniach będą znajdowały się wartości kolejnego wiersza macierzy</br>
Wczytywane poprzez: `./main <infile1 | <infile2`

## Out: 
Na standardowym wyjściu znaleźć się powinna jedynie odległość pomiędzy M a M0. Na standardowym wyjściu błędów powinna być jedynie macierz
M0, przedstawiona jako n wierszy, w każdym dokładnie m liczb całkowitych z określonego przedziału, oddzielonych spacją.
