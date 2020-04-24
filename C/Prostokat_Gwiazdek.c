/* Kamil Matejuk */
#include <stdio.h>

/* Napisz program, który czyta liczbę całkowitą n (można założyć, że będzie ona
z zakresu od 1 do 20) a następnie drukuje gwiazdkami prostokąt złożony z n
wierszy i 2n kolumn. */

int main()
{
    int n;
    scanf("%d", &n);
    for(int i=0; i<n; i++){
        for(int j=0; j<2*n; j++){
            printf("*");
        }
        printf("\n");
    }
    return 0;
}
