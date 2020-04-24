/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>
#include <string.h>

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
