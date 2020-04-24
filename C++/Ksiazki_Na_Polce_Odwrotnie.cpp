/* Kamil Matejuk */
#include <stdio.h>
using namespace std;

/* Na półce Jasia znajduje się wiele książek. Każda z nich ma pewną wartość – Jasiu odczuwa do każdej z nich wielki 
sentyment i wycenia je zgodnie z wartością swoich przeżyć z nią związanych. Ponieważ w ciągu wielu lat zebrało się 
na półce bardzo wiele książek, Jasio dla swojej estetycznej satysfakcji postanowił umieścić między książkami przegródki. 
Jednak Jasio jest miłośnikiem harmonii – chciałby ustawić przegródki tak, że znajdować się będą pomiędzy kolejnymi 
książkami na półce (np. pierwsza przegródka pomiędzy trzecią i czwartą książką, druga pomiędzy ósmą i dziewiątą 
książką i podobnie dalej) oraz tak, aby w jednej przegródce sumaryczna wysokość książek nie była większa niż k. 
Pomóż mu! 

wejście:
Pierwsza linia wejścia zawiera dwie liczby całkowite n – liczbę książek oraz k – maksymalna suma wyskości 
między dwoma przegródkami (1 ≤ n, k ≤ 100000). W kolejnej linii znajduje się n liczb h0, h1, ..., hn-1 – są to 
wysokości kolejnych książek na półce (1 ≤ hi ≤ 1000).

wyjście:
Wyjście powinno składać się z jednej liczby – minimalnej liczby przegródek, tak, aby w jednej przegródce 
sumaryczna suma wysokości książek była nie większa niż k. Traktuj początek i koniec półki jako przegródki 
(tj. część od pierwszej książki do pierwszej przegródki też wliczamy do rozwiązania, podobnie od ostatniej 
przegródki do ostatniej książki).*/

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
