/* Kamil Matejuk */
#include <stdio.h>

/* Napisz program, który czyta liczbę całkowitą n i drukuje gwiazdkami równora-
mienny trójkąt złożony z n wierszy. */

int main()
{
    int n;
    scanf("%d", &n);
    for (int i=0; i<n; i++){
        for (int j=n-i; j>0; j--){
            printf(" ");
        }
        for (int k=0; k<(2*i+1); k++){
            printf("*");
        }
        printf("\n");
    }
    return 0;
}
