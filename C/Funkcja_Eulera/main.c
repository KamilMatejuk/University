/* Kamil Matejuk */
#include <stdio.h>
#include "funs.h"

/* Napisz funkcję int phi(long int n) obliczającą funkcję Eulera φ(n) równą
liczbie liczb z zakresu od 1 do n, które są względnie pierwsze z n (jedynym
wspólnym dzielnikiem każdej z nich z n jest 1).
Dopisz funkcję int main() umożliwiającą testowanie funkcji phi. */

int main()
{
    long int n;
    printf("Podaj n: ");
    scanf("%ld",&n);
    printf("%d\n", phi(n));
    return 0;
}
