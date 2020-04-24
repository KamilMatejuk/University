/* Kamil Matejuk */
#include <iostream>
using namespace std;

/* Napisz program, który dla danej kwoty wypisze nominały banknotów, których 
użyje Bajtazar do jej wypłacenia.

wejście:
Na wejściu dana jest jedna nieujemna liczba całkowita nie większa od dwóch 
tysięcy – kwota, którą musi wypłacić Bajtazar.

wyjście:
Na wyjściu należy wypisać nominały kolejnych banknotów, których użyje Bajtazar 
do wypłacenia żądanej kwoty; kolejność wypisywanych nominałów musi być taka sama, 
jak kolejność użyta przez Bajtazara. */

int main()
{
    int nominaly[7]={100,50,20,10,5,2,1},kwota;
    cin >> kwota;
    int zostalo = kwota;
    for(int i=0; i<7; i++){
        while(zostalo-nominaly[i]>=0){
            cout << nominaly[i] << " ";
            zostalo -= nominaly[i];
        }
    }
    return 0;
}
