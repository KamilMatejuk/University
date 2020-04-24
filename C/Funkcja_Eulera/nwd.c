#include "funs.h"

long int nwd(long int i, long int n)
{
    int a;
    while(i!=n){
        if(i<n)
            //n = n%i;
            n = n-i;
        else
            //i = i%n;
            i = i-n;
    }
    return i;
}
