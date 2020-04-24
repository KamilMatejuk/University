# Polecenie
Zaimplementuj podane algorytmy: RANDOMIZED SELECT, SELECT.
Program przyjmuje jeden z dwóch parametrów wejsciowych:
* wywołanie ./main -r oznacza operowanie na danych losowych długości n,
* wywołanie ./main -p oznacza operowanie na losowej permutacji zbioru {1, 2, ... , n}.
#####
Po uruchomieniu, program wczytuje ze standardowego wejscia dwie liczby całkowite:
* n — długość danych
* 1 ≤ k ≤ n — numer szukanej statystyki pozycyjnej, a następnie generuje tablicę danych (zaleznie od parametru uruchomienia) i sekwencyjnie
uruchamia zaimplementowane algorytmy na wygenerowanych danych. W czasie wykonywania algorytmów SELECT, RANDOMIZED SELECT, na standardowym 
wyjsciu błędów, powinien być wypisywany log, tak by można było działanie algorytmu odtworzyć. W szczególności powinien zawierać
on tablicę danych, k, kolejno wybierane pivoty, wykonywane porównania i przestawienia oraz podsumowanie zawierające
liczbę porównań oraz przestawień elementów.
#####
Wynikiem, wyświetlanym na standardowym wyjściu, działania algorytmu jest tablica z zaznaczoną k-tą statystyką pozycyjną np.:</br>
1 2 [3] 4 6 8 7 5

### kompilacja:
	javac *.java
### uruchamianie:
	java zad -r | -p | -check
gdzie dla -r, -p zgodnie z poleceniem następuje wczytanie danych n,k z klawiatury. Natomiast po uruchomieniu z flagą -check wykonywany jest test, powtarzający wywołanie algorytmu Select 100 razy dla tych samych danych wejsciowych (n=1000, 1<=k<=n), by wyliczyć minimalną i maksymalną liczby porównań dla obu algorytmów, oraz średnią i odchylenie standardowe.

