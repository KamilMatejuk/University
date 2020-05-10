# Polecenie
Napisz program, który symuluje działanie wybranych struktur danych przechowujących ciągi znaków.
Program powinien przyjmowac jako parametr wejściowy typ struktury:
* --type bst drzewo BST,
* --type rbt drzewo czerwono–czarne,
* --type hmap tablice hashujące z metodą łancuchów, a dla przechowywanych w jednej komórce danych długosci mniejszej 
niż n_t oraz z wykorzystaniem samoorganizujących się drzew binarnych (np. drzew czerwono–czarnych) dla przechowywanych 
w jednej komórce danych o długosci większej niż n_t.

## Funkcjonalności
Kazda ze struktur powinna udostępniać przynajmniej poniższe funkcjonalności:
* insert s - wstaw do struktury ciąg s (jesli na początku lub końcu ciagu znajduje się znak spoza klasy [a-zA-Z] to znak
ten jest usuwany)
* load f - dla każdego, oddzielonego białym znakiem, wyrazu z pliku f wykonaj operację insert,
lub zwróc informację o nieistniejącym pliku
* delete s - jesli struktura nie jest pusta i dana wartość s istnieje, to usun element s
* find s - sprawdź czy w strukturze przechowywana jest wartość s (jesli tak to wypisz 1, w p. p. wypisz 0)
* min - wypisz najmniejszy element znajdujący się w strukturze lub, dla struktur pustych oraz
nie zachowujących porządku (np. hmap), pustą linię
* max - wypisz największy element znajdujący się w strukturze lub, dla struktur pustych oraz
nie zachowujących porządku (np. hmap), pustą linię
* successor k - wypisz następnik elementu k lub, jesli on nie istnieje (np. struktura nie zawiera
k, k nie ma następników, struktura nie zachowuje porządku), pusta linię
* inorder - wypisz elementy drzewa w posortowanej kolejnosci (od elementu najmniejszego do
największego) lub, dla struktur pustych oraz nie zachowujących porządku (np. hmap), pustą linię

## Wejście
Wejscie składa się z n+1 linii. W pierwszej, znajduje się liczba n okreslająca liczbę wykonywanych operacji, 
w liniach 2 do (n+1) znajdują się kolejne operacje zgodnie z ich specyfikacją.
Długość pojedynczego ciągu znaków nie przekracza 100, natomiast n + 1 nie przekracza zakresu Integera.

## Wyjście
Wyjscie składa się z k ≤ n linii, będących wynikami kolejnych operacji podanych na wejsciu.
Wynik powinien byc wypisywany na standardowe wyjście, a na standardowym wyjściu błędów powinny byc 
wypisywane w kolejności: czas działania całego programu, liczba operacji każdego typu,
maksymalna liczba elementów (maksymalne zapełnienie struktury w czasie działania programu),
koncowa liczba elementów w strukturze.

## Przykładowe wywołanie
`./main --type rbt <./input >out.res`</br>
input</br>
`17\
max  
insert aaa
insert a
insert b
insert ab
inorder
delete a
delete b
max
load sample.txt
find three
delete three
find three
find Three
delete Three
find Three
min`

out.res
`a aaa ab b
ab
1
1
1
0
aaa`
