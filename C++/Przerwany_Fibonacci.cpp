/* Kamil Matejuk */
#include <stdio.h>


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
