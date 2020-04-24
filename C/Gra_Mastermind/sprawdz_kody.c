/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>

int * sprawdz_kody(int mozliowsci[], struct BlackWhite bw_podane){
	for(int i=0; i<mozliowsci[1296]; i++){
		int podane[4] = {bw_podane.podany_kod/1000, (bw_podane.podany_kod%1000)/100, (bw_podane.podany_kod%100)/10, (bw_podane.podany_kod%10)};
		int wygenerowane[4] = {mozliowsci[i]/1000, (mozliowsci[i]%1000)/100, (mozliowsci[i]%100)/10, (mozliowsci[i]%10)};

		int white=0, black=0;
		for(int i=0; i<4; i++){
			if(podane[i]==wygenerowane[i]){
				black++;
			}
			else if((podane[i]==wygenerowane[0]) || (podane[i]==wygenerowane[1]) || (podane[i]==wygenerowane[2]) || (podane[i]==wygenerowane[3])){
				white++;
			}
		}
		if((black != bw_podane.black) || (white != bw_podane.white)){
			for(int j=i; j<mozliowsci[1296]-1; j++){
				mozliowsci[j] = mozliowsci[j+1];
			}
			mozliowsci[1296]--;
			i--;
		}
	}
	return mozliowsci;
}
