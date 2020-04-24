/* Kamil Matejuk */
#include <stdio.h>
#include <climits>

/* Na półce Jasia znajduje się wiele książek. Każda z nich ma pewną wartość – Jaś odczuwa do każdej 
z nich wielki sentyment i wycenia je zgodnie z wartością swoich przeżyć z nią związanych. 
Ponieważ w ciągu wielu lat zebrało się na półce bardzo wiele książek, Jasio dla swojej estetycznej 
satysfakcji postanowił umieścić między książkami przegródki. Jednak Jasio jest miłośnikiem 
harmonii – chciałby ustawić przegródki tak, że znajdować się będą pomiędzy kolejnymi książkami na półce 
(np. pierwsza przegródka pomiędzy trzecią i czwartą książką, druga pomiędzy ósmą i dziewiątą książką i podobnie dalej) 
oraz tak, aby największa suma wysokości książek w jednej przegródce była jak najmniejsza (patrz przykład). 
Pomóż mu! 

wejście: 
Pierwsza linia wejścia zawiera dwie liczby całkowite n – liczbę książek oraz k – liczbę przegródek (1 ≤ n, k ≤ 100000).
W kolejnej linii znajduje się n liczb h0, h1, ..., hn-1 – są to wysokości kolejnych książek na półce (1 ≤ hi ≤ 1000).

wyjście:
Wyjście powinno składać się z jednej liczby – minimalnej wartości, jaką może otrzymać Jasio, jeśli poprzekłada książki 
przegródkami i wybierze spośród oddzielonych części tę o maksymalnej sumie wysokości książek. Traktuj początek i koniec 
półki jako przegródki (tj. część od pierwszej książki do pierwszej przegródki też wliczamy do rozwiązania, podobnie 
od ostatniej przegródki do ostatniej książki). Nie musisz wykorzystywać wszystkich przegródek!
*/

int main()
{
    int n,k; //n-liczba ksia¿ek, k-liczba przegródek
    scanf("%d %d",&n,&k);
    int ksiazki[n];
    int maxi=0, mini=INT_MIN, srodek=(mini+maxi)/2, suma_w_przegrodce=0, przegrodek_zostalo=k, wynik;
    for(int i=0; i<n; i++){
        scanf("%d",&ksiazki[i]);
        maxi = maxi + ksiazki[i];
        if(ksiazki[i]>mini){mini=ksiazki[i];}
    }
    while(mini<=maxi){
        srodek=(mini+maxi)/2;
        for(int i=0; i<n; i++){
            suma_w_przegrodce = suma_w_przegrodce + ksiazki[i];
            if(suma_w_przegrodce>srodek){
                suma_w_przegrodce = ksiazki[i];
                przegrodek_zostalo--;
            }
        }
        if(przegrodek_zostalo>=0){
            wynik=srodek;
            maxi=srodek-1;
            przegrodek_zostalo=k;
            suma_w_przegrodce=0;
            //printf("maxi %d\n",maxi);
        }
        else{
            wynik=srodek+1;
            mini=srodek+1;
            przegrodek_zostalo=k;
            suma_w_przegrodce=0;
            //printf("mini %d\n",mini);
        }
    }
    printf("%d",wynik);
    return 0;
}
