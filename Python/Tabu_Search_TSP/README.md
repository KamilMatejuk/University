# Polecenie
Napisz program, który dla przedstawionej instancji problemu TSP znajdzie, z wykorzystaniem Tabu Search, 
cykl o możliwie najmniejszym koszcie, rozpoczynając z pierwszego miasta.

## In: 
Dane wejściowe składać się będą z n+ 1 linii. W pierwszej linii będą umieszczone, oddzielone spacją liczby całkowite
 t i n, gdzie t jest limitem czasu, natomiast n liczbą miast do odwiedzenia. W kolejnych n liniach będą znajdowały się 
 odległości pomiędzy miastami oddzielone co najmniej jedną spacją. Uwaga, odległość z miasta i do miasta i zawsze wynosi 0, 
 natomiast nie należy zakładać, że mamy zadany problem Metric TSP, ani że odległości między wybranymi dwoma miastami są 
 takie same w obu kierunkach.</br>
Wczytywane poprzez ./main <input1 | <input2

## Out: 
Na standardowym wyjściu znaleźć się powinien jedynie koszt pokonania cyklu. Ostatnią linią na standardowym wyjściu błędów powinien być (n + 1)-elementowy ciąg liczb całkowitych oddzielonych spacjami, oznaczający kolejno odwiedzane miasta, np. 1 5 4 3 2 6 1