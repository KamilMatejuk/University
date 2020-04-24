/* Kamil Matejuk */
#include <iostream>
using namespace std;

/* Napisz program, który dla danej kwoty wypisze nominały banknotów, których
użyje Bajtazar do jej wypłacenia; może okazać się, że wypłacenie podanej kwoty
przy użyciu sposobu Bajtazara nie jest możliwe.

wejście:
W pierwszym wierszu wejścia dane są nominały funkcjonujące w Megabajtolandii; 
pierwsza liczba (nie przekracza ona 100) oznacza liczbę nominałów, następnie 
podane są wartości nominałów w kolejności malejącej. W drugim wierszu wejścia 
dana jest jedna nieujemna liczba całkowita nie większa od dwóch tysięcy – kwota, 
którą musi wypłacić Bajtazar.

wyjście:
Jeżeli wypłacenie podanej kwoty przy użyciu sposobu Bajtazara jest niemożliwe, 
należy wypisać jedynie słowo "NIE". W przeciwnym wypadku należy wypisać nominały 
kolejnych banknotów, których użyje Bajtazar do wypłacenia żądanej kwoty; kolejność 
wypisywanych nominałów musi być taka sama, jak kolejność użyta przez Bajtazara. */

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
            zostalo -= nominaly[i];
        }
    }
    if(zostalo>0){
        cout << "NIE";
    }
    else {
        zostalo=kwota;
        for(int i=0; i<ilosc_nominalow; i++){
        while(zostalo-nominaly[i]>=0){
            cout << nominaly[i] << " ";
            zostalo -= nominaly[i];
        }
    }
    }
    return 0;
}
