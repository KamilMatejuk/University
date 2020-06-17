## Contents
* [Zachlanny_Bankier](#Zachlanny_Bankier)
* [Ksiazki_Na_Polce](#Ksiazki_Na_Polce)
* [Ksiazki_Na_Polce_Odwrotnie](#Ksiazki_Na_Polce_Odwrotnie)
* [Maly_Symbol_Newtona](#Maly_Symbol_Newtona)
* [Maskowanie](#Maskowanie)
* [Pole_i_Obwod_Figury](#Pole_i_Obwod_Figury)
* [Przerwany_Fibonacci](#Przerwany_Fibonacci)
* [Wiersz_Trojkata_Pascala](#Wiersz_Trojkata_Pascala)

## Topics
### Zachlanny_Bankier
More info in [folder](Zachlanny_Bankier).

### Ksiazki_Na_Polce
Na półce Jasia znajduje się wiele książek. Każda z nich ma pewną wartość – Jaś odczuwa do każdej z nich wielki sentyment i wycenia je zgodnie z wartością swoich przeżyć z nią związanych. Ponieważ w ciągu wielu lat zebrało się na półce bardzo wiele książek, Jasio dla swojej estetycznej satysfakcji postanowił umieścić między książkami  przegródki. Jednak Jasio jest miłośnikiem harmonii – chciałby ustawić przegródki tak, że znajdować się będą pomiędzy kolejnymi książkami na półce (np. pierwsza przegródka pomiędzy trzecią i czwartą książką, druga pomiędzy ósmą i dziewiątą książką i podobnie dalej) oraz tak, aby największa suma wysokości książek w jednej przegródce była jak najmniejsza. Pomóż mu!<br/>
**wejście:** <br/>
Pierwsza linia wejścia zawiera dwie liczby całkowite:<br/>
n – liczbę książek oraz k – liczbę przegródek (1 ≤ n, k ≤ 100000).
W kolejnej linii znajduje się n liczb h0, h1, ..., hn-1 – są to wysokości kolejnych książek na półce (1 ≤ hi ≤ 1000).<br/>
**wyjście:** <br/>
Wyjście powinno składać się z jednej liczby – minimalnej wartości, jaką może otrzymać Jasio, jeśli poprzekłada książki przegródkami i wybierze spośród oddzielonych części tę o maksymalnej sumie wysokości książek. Traktuj początek i koniec półki jako przegródki (tj. część od pierwszej książki do pierwszej przegródki też wliczamy do rozwiązania, podobnie od ostatniej przegródki do ostatniej książki). Nie musisz wykorzystywać wszystkich przegródek!

### Ksiazki_Na_Polce_Odwrotnie
Na półce Jasia znajduje się wiele książek. Każda z nich ma pewną  wartość – Jasiu odczuwa do każdej z nich wielki sentyment i wycenia je zgodnie z wartością swoich przeżyć z nią związanych. Ponieważ w ciągu wielu lat zebrało się na półce bardzo wiele książek, Jasio dla swojej estetycznej satysfakcji postanowił umieścić między książkami przegródki. Jednak Jasio jest miłośnikiem harmonii – chciałby ustawić przegródki tak, że znajdować się będą pomiędzy kolejnymi książkami na półce (np. pierwsza przegródka pomiędzy trzecią i czwartą książką, druga pomiędzy ósmą i dziewiątą książką i podobnie dalej) oraz tak, aby w jednej przegródce sumaryczna wysokość książek nie była większa niż k. <br/>
**wejście:** <br/>
Pierwsza linia wejścia zawiera dwie liczby całkowite:<br/>
n – liczbę książek oraz k – maksymalna suma wyskości między dwoma przegródkami (1 ≤ n, k ≤ 100000). W kolejnej linii znajduje się n liczb h0, h1, ..., hn-1 – są to wysokości kolejnych książek na półce (1 ≤ hi ≤ 1000).<br/>
**wyjście:** <br/>
Wyjście powinno składać się z jednej liczby – minimalnej liczby przegródek, tak, aby w jednej przegródce sumaryczna suma wysokości książek była nie większa niż k. Traktuj początek i koniec półki jako przegródki (tj. część od pierwszej książki do pierwszej przegródki też wliczamy do rozwiązania, podobnie od ostatniej 
przegródki do ostatniej książki).

### Maly_Symbol_Newtona
Małym Symbolem Newtona nazywamy (autorzy zadania - to nie jest określenie uznane w świecie matematyki) 
symbol Newtona ("n po k") dla n i k z przedziału od 0 do 10. 
Napisz program obliczjący MSN.

### Maskowanie
Druga wojna światowa. Amerykańska armia dała ci niezwykle odpowiedzialne zadanie: masz zamaskować hangary wojskowe na Pearl Harbour. Wygląda to tak, że wzdłuż lotniska, po jego jednej stronie, znajduje się n pagórków (dla uproszczenia, reprezentowane jako punkty całkowite na osi liczbowej), z których niektóre z nich zostały zamienione na hangary. Maskowanie polega na przykrywaniu ich płatami sztucznych krzaczorów (ABC, Artificial Bush Carpet), przy czym, ze względów logistycznych, można na półwysep dostarczyć zaledwie m trawiastych dywanów, i to do tego tej samej wielkości. Twoim zadaniem jest wyznaczenie, jakiej co najmniej szerokości muszą być dywany, aby przykryć wszystkie hangary. Oczywiście, płaty trawy mogą się nakrywać i wystawać (byleby nie spadały na płytę lotniska). <br/>
**wejście:** <br/>
Na wejściu pojawi się liczba n (1 ≤ n ≤ 1000), a następnie fragment mapy terenu przedstawiający okolice pasa głównego lotniska. Znak '+' oznacza że w danym miejscu znajduje się hangar, znak '.' oznacza, że nic tam nie ma i nie trzeba tego miejsca maskować. Następnie znajduje się liczba m ≤ n. <br/>
**wyjście:** <br/>
Wypisz jedną liczbę całkowitą k oznaczających minimalną szerokość dywanu konieczną do zamaskowania hangarów. Szerokość licz w całkowitych krotnościach szerokości hangaru.

### Pole_i_Obwod_Figury
Program powinien liczyć pole i obwód następujących figur  geometrycznych: okrąg, kwadrat, prostokąt, romb, pięciokąt foremny, sześciokąt foremny. <br/>
Do tego celu należy stworzyć hierarchię obsaugujących poszczególne rodzaje figur. Korzeniem tej hierarchii powinna być abstrakcyjna klasa Figura, zawierająca abstrakcyjne metody do obliczania obwodu oraz pola danej figury. Po klasie Figura powinna dziedziczyć abstrakcyjna klasa Czworokat oraz klasy: Okrag, Pieciokat, Szesciokat. Po klasie Czworokat klasy: Kwadrat, Prostokat, Romb.
Stwórz odpowiednie metody w klasach potomnych, które będą obliczały obwód i pole w sposób specyczny dla danej figury. W lini polece« można podać następujące rodzaje figur geometrycznych (o - okrąg, c-czworokąt, p-pięciokąt, s-sześciokąt) oraz ich parametry, przy czym: okrąg posiada jeden parametr: promień, czworokąt posiada pięć parametrów: bok1, bok2, bok3, bok4, kąt, pięciokąt i sze±ciokąt foremny: bok. Program powinien stworzyć obiekty dla tych figur, zapisać te obiekty w jednej tablicy, a następnie wypisać dla
poszczególnych obiektów pole i obwód figury. <br/>
Należy zadbać o odpowiednią obsługę błędów uwzględniając brakujące parametry, nieprawidłowe wartości kątów, itd.

### Przerwany_Fibonacci
Dany jest program który liczy n-ty wyraz ciągu Fibonacciego rekurencyjnie. Wykonuje się on korzystając z zależności `f(n) = f(n-1) + f(n-2) dla n > 1 oraz f(1) = 1 i f(0) = 0`. Funkcje są wywoływane dokładnie w tej kolejności; funkcja aby podać wynik dla n najpierw oblicza wynik dla n-1, a później dla n-2. Wypisz k pierwszych wartości, dla których tak funkcja będzie wywołana (chyba, ze się zakończy w mniejszej ilości wywołań).<br/>
**wejście:** <br/>
Na wejściu dane są dwie liczby, n < 100000 oraz k < 1000000.<br/>
**wyjście:** <br/>
Należy wypisać k linii, w i-tej argument i-tego wywołania funkcji f

### Wiersz_Trojkata_Pascala
Stwórz publiczną klasę WierszTrojkataPascala posiadającą jeden konstruktor WierszTrojkataPascala(int n), który tworzy tablicę liczb odpowiedniego rozmiaru i oblicza w niej n-ty wiersz trójkąta Pascala. 
Następnie zaimplementuj publiczną metodę wspolczynnik(int m) zwracającą wartość m-tego elementu wiersza trójkąta Pascala. Funkcja ta powinna prawidaowo dziaaa¢ dla liczb od 0 do n.<br/>
Dodaj odpowiednie własne wyjątki dla tej klasy, np. w przypadku użycia liczb ujemnych czy większych od argumentu konstruktora.