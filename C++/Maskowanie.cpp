/* Kamil Matejuk */
#include <stdio.h>


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
