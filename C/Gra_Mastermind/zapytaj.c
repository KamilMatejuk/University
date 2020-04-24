/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct BlackWhite zapytaj(int mozliwosci[]){
	struct BlackWhite bw;
	time_t t;
	int zarodek = time(&t);
	srand(zarodek);
	if(mozliwosci[1296]==0){
		printf("Oszukujesz!\n");
		bw.finish = true;
		return bw;
	} else {
		int losowo = rand() % mozliwosci[1296];
		bw.podany_kod = mozliwosci[losowo];
		int m1 = bw.podany_kod/1000;
		int m2 = (bw.podany_kod%1000)/100;
		int m3 = (bw.podany_kod%100)/10;
		int m4 = (bw.podany_kod%10);
		printf("[%d], [%d], [%d], [%d] ?\n", m1,m2,m3,m4);
		printf("Białe: ");
		scanf("%d",&bw.white);
		if(bw.white<0 || bw.white>4){
			printf("Oszukujesz!\n");
			bw.finish = true;
			return bw;
		}
		printf("Czarne: ");
		scanf("%d",&bw.black);
		if(bw.black<0 || bw.black>4 || bw.black+bw.white>4){
			printf("Oszukujesz!\n");
			bw.finish = true;
			return bw;
		}
		else if(bw.black==4){
			printf("Wygrałem!\n");
			bw.finish = true;
			return bw;
		}
	}
	bw.finish = false;
	return bw;
}
