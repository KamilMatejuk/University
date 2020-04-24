/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>
#include <string.h>

int main()
{
    char wzorzec[1000], lancuch[1000];
    printf("Podaj wzor (min 1 max 100 znaków): ");
    scanf("%s",wzorzec);
    printf("Podaj łańcuch znaków do sprawdzenia (min 1 max 100 znaków): ");
    scanf("%s",lancuch);
    char wyrazy[1000];
    struct wynik_match wm;
    wm.tf=true;
    wm.lancuch2="";
    int dlugosc = strlen(wzorzec);
    for(int i=0; i<dlugosc; i++){
        if(wzorzec[i]!='*'){
            wyrazy[i] = wzorzec[i];
        } else {
            wm = match(wyrazy,lancuch);
            strcpy(lancuch, wm.lancuch2);
            if(!wm.tf){
                printf("Lancuch nie zgadza sie ze wzorem\n");
                break;
            }
        }
    }
    if(wm.tf){
        printf("Lancuch zgadza sie ze wzorem\n");
    }
    return 0;
}
