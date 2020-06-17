/* Kamil Matejuk */
#include <stdio.h>
#include <climits>


int main() {
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
