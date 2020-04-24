# Polecenie
Napisz program, który gra w grę Mastermind.</br>
W grze łamie się ukryty kod złożony z sekwencji czterech kolorów wybranych spośród sześciu (kolory mogą powtarzać się w sekwencji).
Gra dwóch graczy. Jeden układa kod z czterech kolorów (nazywać będziemy go koderem) a drugi stara się go odgadnąć (nazywać będziemy go dekoderem).
Dekoder podaje sekwencję czterech kolorów i dostaje od kodera w odpowiedzi informację ile kolorów jest poprawnych
i na swoich miejscach a ile poprawnych ale na złych miejscach.
Liczbę (ale nie pozycje) kolorów poprawnych i na swoim miejscu koder oznacza w odpowiedzi kołeczkami w kolorze czarnym, 
natomiast liczbę (ale nie pozycje) kolorów poprawnych ale nie na swoim miejscu kołeczkami w kolorze białym.
Celem naszym jest napisanie programu, który będzie łamał ukryty kod (grał jako dekoder).
Zamiast kolorów używać będziemy cyfr od 1 do 6. 
Zakładamy, że osoba uruchamiająca program jest koderem i zapisała sobie na kartce kod złożony z czterech cyfr.
Program cyklicznie drukuje swoją propozycję kodu (cztery cyfry z zakresu od 1 do 6) i czeka na wprowadzenie 
przez kodera liczbę białych i liczbę czarnych kołeczków.
