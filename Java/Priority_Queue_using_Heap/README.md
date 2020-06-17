# Polecenie
Napisz program, który symuluje działanie kolejki priorytetowej. Struktura powinna umozliwiać przechowywanie wartości typu int wraz z ich priorytetem, będącym nieujemną liczbą całkowitą, przy czym najwyższym priorytetem jest wartość 0. Program nie powinien wymagać żadnych parametrów uruchomienia.

## Operacje
Struktura powinna udostępniać przynajmniej poniższe funkcjonalności:
* `insert x p` - wstaw do struktury wartość `x` o priorytecie `p`
* `empty` - wypisz wartość 1 dla pustej struktury, w przeciwnym przypadku 0
* `top` - wypisz wartość o najwyższym priorytecie lub pustą linię w przypadku braku elementów w strukturze
* `pop` - wypisz wartość o najwyższym priorytecie a następnie usuń ją ze struktury (wypisz pustą linię w przypadku braku elementów w strukturze)
* `priority x p` - dla kazdego elementu o wartości `x` obecnego w strukturze ustawia priorytet `p` jesli jest on wyższy od aktualnego priorytetu danego elementu ˙
* `print` - wypisuje w jednej linii zawartość struktury w postaci  `(xi, pi)`, gdzie `xi` to kolejne wartosci przechowywane w kolejce, a `pi` odpowiadające im priorytety.

## Wejście
Wejscie składa się z n+1 linii. W pierwszej, znajduje się liczba n okreslająca liczbę wykonywanych operacji, 
w liniach 2 do (n+1) znajdują się kolejne operacje zgodnie z ich specyfikacją.
Długość pojedynczego ciągu znaków nie przekracza 100, natomiast n + 1 nie przekracza zakresu Integera.

## Wyjście
Wyjscie składa się z k ≤ n linii, będących wynikami kolejnych operacji podanych na wejsciu.

## Przykładowe wywołanie
```
java main
```