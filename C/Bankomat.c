/* Kamil Matejuk */
#include <stdio.h>

/* Napisz w C program, który czyta kwotę podaną w postaci całkowitej liczby
złoty i całkowitej liczby groszy a następnie drukuje w jaki sposób wypłacić ją jak
najmniejszą liczbą banknotów i monet. */

int main()
{
    int zl, gr, x;
    printf("podaj liczbe zloty: ");
    scanf("%d", &zl);
    printf("podaj liczbe groszy: ");
    scanf("%d", &gr);

    //banknoty
    printf("banknoty:\n");
    int wartzl1[] = {200,100,50,20,10};
    for(int i=0; i<5; i++){
        if(zl>=wartzl1[i]){
            x = zl / wartzl1[i];
            zl = zl - wartzl1[i]*x;
            printf("  %d x %d zl\n", x, wartzl1[i]);
        }
    }

    //monety w zl
    printf("monety:\n");
    int wartzl2[] = {5,2,1};
    for(int i=0; i<3; i++){
        if(zl>=wartzl2[i]){
            x = zl / wartzl2[i];
            zl = zl - wartzl2[i]*x;
            printf("  %d x %d zl\n", x, wartzl2[i]);
        }
    }

    //monety w gr
    int wartgr[] = {50,20,10,5,2,1};
    for(int i=0; i<6; i++){
        if(gr>=wartgr[i]){
            x = gr / wartgr[i];
            gr = gr - wartgr[i]*x;
            printf("  %d x %d gr\n", x, wartgr[i]);
        }
    }
    return 0;
}
