/* Kamil Matejuk */
#include <stdio.h>

/* Napisz program, który czyta liczbę całkowitą n a następnie wczytuje n liczb rzeczywistych 
x1, x2, . . . , xn. Na koniec drukuje średnią arytmetyczną wczytanych wartości rzeczywistych. */

int main()
{
    int n;
    float x, suma=0, sr;
    printf("podaj n: ");
    scanf("%d", &n);
    printf("podaj %d liczb: ", n);
    for(int i=0; i<n; i++){
        scanf("%f", &x);
        suma = suma + x;
    }
    sr = suma/n;
    printf("%f\n", sr);
    return 0;
}
