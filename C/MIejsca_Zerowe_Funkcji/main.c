/* Kamil Matejuk */
#include <stdio.h>
#include <math.h>
#include "funs.h"

/* Załóżmy, że funkcja double f(double x) oblicza wartość ciągłej funkcji f(x).
Napisz funkcję double rozwiazanie(double a, double b, double eps), która dla
zadanych końców przedziału [a, b], gdzie a < b, o tej własności, że funkcja f(x)
ma na końcach tego przedziału wartości przeciwnego znaku (tzn. f(a)∗f(b) < 0)
znajduje miejsce zerowe funkcji będące rozwiązaniem równania f(x) = 0, dla
a < x < b, z zadaną dokładnością ε > 0 (tzn. znaleziona wartość nie różni się
od faktycznej o więcej niż ε). */

int main(int argc, char **argv)
{
    long double a,b,e;
    FILE *fp;
    if(argc < 2){
        printf("Musisz podac jakis plik\n");
        return 0;
    } else {
        fp = fopen(argv[1],"r");
        if(fp==NULL){
            printf("Plik nie istnieje\n");
            return 0;
        }
        fscanf(fp,"%Lf",&a);
        fscanf(fp,"%Lf",&b);
        fscanf(fp,"%Lf",&e);
    }
    printf("%.8Lf\n",rozwiazanie(a,b,e));
    return 0;
}
