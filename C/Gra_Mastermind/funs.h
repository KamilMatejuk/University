//funs.h

/* Kamil Matejuk */
#include <stdbool.h>

struct BlackWhite{
	int white;
	int black;
	bool finish;
	int podany_kod;
};

struct BlackWhite zapytaj(int mozliwosci[]);
int * sprawdz_kody(int mozliowsci[], struct BlackWhite bw);
