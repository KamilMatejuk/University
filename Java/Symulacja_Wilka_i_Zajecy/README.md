# Polecenie
Napisz korzystając z wątków następującą symulację:
* Plansza do symulacji jest prostokątem n na m punktów. Na planszy jeden wilk poluje na pewną liczbę zajecy. Jeęli na planszy jest tylko wilk to aplikacja kończy działanie.
* Zając jest wątkiem który w jednym cyklu może się przesuną¢ o jedno pole w pionie, poziomie lub na skos. Zając zawsze ucieka w kierunku przeciwnym do wilka. Jeżli pole na które chciaaby przejść jest zajęte to nie rusza
    się, a jeżeli dochodzi do ściany to wybiera z rozkładem jednostajnym jeden z pięciu dozwolonych kierunków ruchu
    (dla rogu jeden z trzech). Jeżli warunki ruchu spełnia więcej niż jedno pole to losuje następne pole z rozkładem
    jednostajnym.
* Wilk jest wątkiem który w jednym cyklu może przesunąć się o jedno pole w pionie, poziomie lub na skos. Wilk
    zawsze biegnie w stronę najbliższego zająca. Jeżli warunki ruchu spełnia więcej niż jedno pole to losuje następne
    pole z rozkaadem jednostajnym. Jeżeli złapie zająca (wskoczy na to pole na którym jest zając) to go zabija i
    odpoczywa przez 5 cykli.
* Czas dziaaania jednego cyklu wątku jest równy w przybliżeniu losowo wybranej liczbie milisekund
    z przedziału [0.5k, 1.5k], gdzie k jest parametrem programu.
#####
Pisząc symulację zapewnij takie opóźnienia ruchów (parametr k), aby ruch zwierząt był w miarę płynny. Zapewnij też
łatwe rozróżnienie między wilkiem a zającami na planszy. Liczba zajęcy, opóźnienie oraz rozmiar planszy powinny być
także podane jako parametry.</br>
Początkowe położenie zwierząt powinno być losowe. 
