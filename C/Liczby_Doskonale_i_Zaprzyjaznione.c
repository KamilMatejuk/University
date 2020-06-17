/* Kamil Matejuk */
#include <stdio.h>


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
