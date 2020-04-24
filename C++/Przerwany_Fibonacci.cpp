/* Kamil Matejuk */
#include <stdio.h>

/* Dany jest program który liczy n-ty wyraz ciągu Fibonacciego rekurencyjnie.
Wykonuje się on korzystając z zależności f(n) = f(n-1) + f(n-2
dla n > 1 oraz f(1) = 1 i f(0) = 0. 
Funkcje są wywoływane dokładnie w tej kolejności; funkcja aby podać wynik dla n najpierw oblicza wynik dla n-1, a później dla n-2.
Wypisz k pierwszych wartości, dla których tak funkcja będzie wywołana (chyba, ze się zakończy w mniejszej ilości wywołań).

wejście:
Na wejściu dane są dwie liczby, n < 100000 oraz k < 1000000.

wyjście:
Należy wypisać k linii, w i-tej argument i-tego wywołania funkcji f
*/

int main()
{
    //nie wiem o co w og�le chodzi w tym zadaniu
    int n,k;
    scanf("%d %d",&n,&k);
    for(int i=n; i>0; i--){
        printf("%d\n",i);
    }
    for(int i=0; i<((k-n)/2.0); i++){
        printf("%d\n",i);
    }
    for(int i=((k-n)/2.0)-1; i>=0; i--){
        printf("%d\n",i);
    }

    return 0;
}
