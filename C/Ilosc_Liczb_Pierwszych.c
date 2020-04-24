/* Kamil Matejuk */
#include <stdio.h>
#include <math.h>

/* Niech π(x) = |{n ≤ x : Prime(n)}| będzie liczbą liczb pierwszych nieprzekraczających wartości x.
Napisz w języku C program drukujący w kolejnych wierszach po dwie oddzielone średnikiem liczby n i (π(n) / (n/ln n))
dla n = 2, . . . , 105 */

int main()
{
    int liczba=2, iloscpierwszych=0;
    while(liczba <= 100000){
        double ln = log(liczba);
	int iloscdzielnikow=0;
	for(int i=1; i<liczba; i++){
	    if(liczba%i==0){iloscdzielnikow++;}
        }
        if(iloscdzielnikow==1){iloscpierwszych++;}
	int pi = iloscpierwszych;

        double ulamek = pi/(liczba/ln);
        //printf("sprawdzenie n=%d, pi=%d, ln=%f, ulamek=%f\n", liczba, pi, ln, ulamek);
        printf("%d; %f\n", liczba, ulamek);
        liczba++;
    }
    return 0;
}
