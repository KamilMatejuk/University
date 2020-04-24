#include "funs.h"

int phi(long int n)
{
    int p=1; //1
    for(int i=2; i<n; i++){
        if(nwd(i,n)==1){
            p++;
        }
    }
    return p;
}
