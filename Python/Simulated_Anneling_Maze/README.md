# Polecenie
Napisz program, który będzie symulował poruszanie się agenta po kracie (wycinek Z^2). Celem jest dotarcie agenta do wyznaczonego punktu, przy założeniach,
że w każdym kroku może poruszyć się o 1 w lewo, o 1 w prawo, o 1 w górę albo o 1 w dół. Program powinien za pomocą symulowanego wyżarzania generować kolejne sekwencje kroków, tak by dotarcie do celu zajęło jak najmniej rund. Jeśli agent dotrze do celu przed wykonaniem wszystkich kroków, liczymy liczbę wykonanych kroków, jeśli po wykonaniu całej wygenerowanej sekwencji kroków, agent nie dotarł do celu - kontynuuje z miejsca, w którym wylądował a długość wykonanej sekwencji wlicza się do bieżącego rozwiązania albo zaczyna nową iterację.

## In: 
Dane wejściowe składać się będą z n+ 1 linii. W pierwszej linii będą umieszczone, oddzielone spacją liczby całkowite t, n i m, gdzie t jest limitem
czasu, jak w zadaniu 1, natomiast n i m oznaczają wymiary kraty (odpowiednio liczba wierszy i kolumn w labiryncie, który będzie chciał opuścić agent). W kolejnych n liniach będą znajdowały się cyfry tworzące labirynt. Między cyframi nie będzie spacji. </br>
Możliwe cyfry:</br>
0 – standardowe, puste pole, po którym agent może się poruszać</br>
1 – ściana, która nie może zostać pokonana (Uwaga: ściany mogą znajdować się również wewnątrz labiryntu, nie ma wymagania by przynajmniej jednym sąsiadem 1 była 1; ściany mogą całkowicie blokować dostęp do części labiryntu, pod warunkiem, że istnieje ścieżka z pozycji startowej agenta do przynajmniej jednego wyjścia)</br>
5 – symbol agenta, oznaczający jego pozycję początkową (Uwaga: nie ma konieczności wizualizacji kolejnych kroków) </br>
8 – symbol wyjścia, oznaczający pozycję celu, na który agent powinien dotrzeć (Uwaga: w danej instancji może być więcej niż jeden symbol 8, wszystkie 8 znajdują się on na obrzeżu).</br>
Wczytywane poprzez ./main <infile1 | <infile2 | <infile3

## Out: 
Na standardowym wyjściu znaleźć się powinna jedynie łączna liczba kroków k od pozycji startowej do celu, wykonana w wybranym rozwiązaniu. Ostatnią linią na standardowym wyjściu błędów powinien być k-elementowy ciąg znaków U, D, R, L oznaczający kolejne wybrane kierunki w rozwiązaniu, gdzie litery kodują odpowiednio krok w górę, krok w dół, krok w prawo i krok w lewo.

## Uwaga:
Agent nie zna swojej pozycji początkowej, ani pozycji celu. Można założyć, że agent potrafi rozpoznać rodzaj pola (cyfrę) swoich czterech sąsiadów oraz, że zna n oraz m.