/* Kamil Matejuk */
#include <stdio.h>

/* Dla jakich liczb naturalnych n prawdziwa jest nierówność:
1 + (1/2) + (1/3) + ... + (1/n) > 10
Napisz program w C wyznaczający najmniejszą z takich liczb n. */

int main()
{
    float n=2;
    float suma=1;
    while(suma<=10){
        suma = suma + (1/n);
        n=n+1;
    }
    printf("najmniejsze n rowne %f \n", n-1);
    return 0;
}
