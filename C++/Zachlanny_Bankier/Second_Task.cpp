/* Kamil Matejuk */
#include <iostream>
using namespace std;

/* Napisz program, który dla danej kwoty wypisze nominały banknotów, których 
użyje Bajtazar do jej wypłacenia; wypłacenie podanej kwoty zawsze będzie możliwe.

wejście:
W pierwszym wierszu wejścia dane są nominały funkcjonujące w Megabajtolandii;
pierwsza liczba (nie przekracza ona 100) oznacza liczbę nominałów, następnie 
podane są wartości nominałów w kolejności malejącej. W drugim wierszu wejścia 
dana jest jedna nieujemna liczba całkowita nie większa od dwóch tysięcy – kwota, 
którą musi wypłacić Bajtazar.

wyjście:
Na wyjściu należy wypisać nominały kolejnych banknotów, których użyje Bajtazar 
do wypłacenia żądanej kwoty; kolejność wypisywanych nominałów musi być taka sama, 
jak kolejność użyta przez Bajtazara. */

int main()
{
    int ilosc_nominalow;
    cin >> ilosc_nominalow;
    int nominaly[ilosc_nominalow];
    for(int i=0; i<ilosc_nominalow; i++){
        cin >> nominaly[i];
    }
    int kwota;
    cin >> kwota;
    int zostalo = kwota;
    for(int i=0; i<ilosc_nominalow; i++){
        while(zostalo-nominaly[i]>=0){
            cout << nominaly[i] << " ";
            zostalo -= nominaly[i];
        }
    }
    return 0;
}
