/* Kamil Matejuk */
#include <stdio.h>

/* Niech τ (n) = |{(a, b) ∈ {1, ... , n}^2: NWD(a, b) = 1}| będzie liczbą par liczb
względnie pierwszych. Napisz w języku C program drukujący w kolejnych wierszach po dwie
oddzielone średnikiem liczby n i (τ(n) / n^2), dla n = 1, . . . , 1000 */

int main(){
    int n=2;
    float liczbapar=1;
    printf("%d;%f\n",1,1.0); //dla pierwszej pary (1,1)
    while(n<=1000){
        for(int a=1; a<=n-1; a++){
            if(NWD(a,n)==1){liczbapar++;}}
        for(int b=1; b<=n-1; b++){
            if(NWD(n,b)==1){liczbapar++;}}
        float ulamek = liczbapar/(n*n);
        printf("%d;%f\n",n,ulamek);
        n++;
    }
    return 0;
}

int NWD(int pierwsza, int druga){
    while(pierwsza!=druga){
       if(pierwsza>druga)
           pierwsza = pierwsza - druga;
       else
           druga = druga - pierwsza;
    }
    return pierwsza;
}
