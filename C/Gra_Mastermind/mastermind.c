/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>
#include <string.h>

/* Napisz program, który gra w grę Mastermind.
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
przez kodera liczbę białych i liczbę czarnych kołeczków. */

int main()
{
	printf("Niech zacznie się gra:\nDostepnych jest 6 wariości, oznaczanych cyframi 0-5\nPo każdej próbie podaj ilość bialych i czarnych\nBiałe - poprawnie ale na złym miejscu\nCzarne - poprawne na swoim miejscu\n\n");
	struct BlackWhite bw;
	int wszystkie_mozliwosci[1297];

	//wpisanie wartości do tabeli mozliwości
	wszystkie_mozliwosci[1296] = 1296; //ilosc opcji
	for(int i=0; i<6; i++){
		for(int j=0; j<6; j++){
			for(int k=0; k<6; k++){
				for(int l=0; l<6; l++){
					int x = 216*i + 36*j + 6*k + l;
					wszystkie_mozliwosci[x] = 1000*i + 100*j + 10*k + l;
				}
			}
		}
	}

	for(int runda = 1; runda<=10; runda++){
		bw = zapytaj(wszystkie_mozliwosci);
		if(bw.finish){
			break;
		}
		memcpy(wszystkie_mozliwosci, sprawdz_kody(wszystkie_mozliwosci,bw), sizeof(wszystkie_mozliwosci));
	}

    return 0;
}
