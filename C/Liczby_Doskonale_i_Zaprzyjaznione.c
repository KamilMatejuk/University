/* Kamil Matejuk */
#include <stdio.h>

/* Niech σ(n) oznacza sumę wszystkich dzielników liczby naturalnej n mniejszych
od liczby n (na przykład σ(5) = 1 oraz σ(6) = 1+2+3 = 6). Liczbę n nazywamy
doskonałą jeśli σ(n) = n. Parę liczb (n, m) nazywamy zaprzyjaźnioną, jeśli
σ(n) = m oraz σ(m) = n.
Napisz w C program znajdujący wszystkie liczby doskonałe mniejsze od 1000
oraz wyznaczający wszystkie zaprzyjaźnione pary liczb mniejszych niż 1000. */

int main()
{
    int dzielniki_tab[1000];
    for(int n=1; n<1000; n++){
        int suma_dzielnikow = 0;
        for(int i=1; i<n; i++){
            if(n%i==0){suma_dzielnikow=suma_dzielnikow+i;}
        }
        dzielniki_tab[n-1] = suma_dzielnikow;
    }
    printf("lliczby doskonale:\n");
    for(int i=1; i<=1000; i++){
        if(dzielniki_tab[i-1]==i){
            printf("%d\n",i);
        }
    }

    printf("zaprzyjaznione pary:\n");
    for(int i=1; i<1000; i++){
        for(int j=1; j<1000; j++){
            if(i!=j){
                if((dzielniki_tab[i-1]==j) && (dzielniki_tab[j-1]==i)){
                    printf("%d,%d\n",i,j);
                }
            }
        }
    }

    return 0;
}
