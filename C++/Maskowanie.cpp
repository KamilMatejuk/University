/* Kamil Matejuk */
#include <stdio.h>

/* Druga wojna światowa. Amerykańska armia dała ci niezwykle odpowiedzialne zadanie: 
masz zamaskować hangary wojskowe na Pearl Harbour. Wygląda to tak, że wzdłuż lotniska, 
po jego jednej stronie, znajduje się n pagórków (dla uproszczenia, reprezentowane jako 
punkty całkowite na osi liczbowej), z których niektóre z nich zostały zamienione na hangary.
Maskowanie polega na przykrywaniu ich płatami sztucznych krzaczorów (ABC, Artificial Bush Carpet), 
przy czym, ze względów logistycznych, można na półwysep dostarczyć zaledwie m trawiastych dywanów, 
i to do tego tej samej wielkości. Twoim zadaniem jest wyznaczenie, jakiej co najmniej szerokości 
muszą być dywany, aby przykryć wszystkie hangary. Oczywiście, płaty trawy mogą się nakrywać i 
wystawać (byleby nie spadały na płytę lotniska) 

wejście:
Na wejściu pojawi się liczba n (1 ≤ n ≤ 1000) , a następnie fragment mapy terenu przedstawiający 
okolice pasa głównego lotniska. Znak '+' oznacza że w danym miejscu znajduje się hangar, 
znak '.' oznacza, że nic tam nie ma i nie trzeba tego miejsca maskować. 
Następnie znajduje się liczba m ≤ n.

wyjście:
Wypisz jedną liczbę całkowitą k oznaczających minimalną szerokość dywanu konieczną do zamaskowania hangarów. 
Szerokość licz w całkowitych krotnościach szerokości hangaru.*/

int main()
{
    int n,m; //m-ilosc dywan�w
    scanf("%d",&n);
    int mapa[n];
    for(int i=0; i<=n; i++){
        char z;
        scanf("%c",&z);
        if(z=='+')mapa[i-1]=1;
        else mapa[i-1]=0;
    }
    scanf("%d",&m);
    int mini=1, maxi=(n/m)+1,a=0,ilosc_uzytych=0;
    for(int j=mini; j<=maxi; j++){
        ilosc_uzytych=0;
        int i=0;
        while(i<n){
            if(mapa[i]==1){
                ilosc_uzytych++;
                i=i+j;
            }
            else i++;
        }
        if(ilosc_uzytych==m){
            a = j;
            break;
        }
    }
    printf("%d",a);

    return 0;
}
