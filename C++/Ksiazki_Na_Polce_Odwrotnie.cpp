/* Kamil Matejuk */
#include <stdio.h>
using namespace std;

int main()
{
    int n,k; //n-liczba ksia¿ek, k-max suma w przegrodkach
    scanf("%d %d",&n,&k);
    int ksiazki[n];
    int ilosc_przegrodek=0,suma=0;
    for(int i=0; i<n; i++){
        scanf("%d",&ksiazki[i]);
    }
    for(int i=0; i<n; i++){
        suma += ksiazki[i];
        if(suma>k){
            ilosc_przegrodek++;
            suma = ksiazki[i];
        }
    }
    printf("%d",ilosc_przegrodek);
    return 0;
}
