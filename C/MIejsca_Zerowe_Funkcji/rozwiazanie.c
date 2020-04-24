#include "funs.h"
#include <stdio.h>

long double rozwiazanie(long double a, long double b, long double eps){
    for(long double i=a; i<b; i=i+eps){
        if(f(i)*f(i+eps)<=0){
            return i;
        }
    }
    return 0;
}
