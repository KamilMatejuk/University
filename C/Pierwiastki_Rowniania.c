/* Kamil Matejuk */
#include <stdio.h>
#include <math.h>

/* Napisz program, który czyta trzy liczby rzeczywiste a, b i c a następnie rozwiązuje równanie kwadratowe:
a · x^2 + b · x + c = 0.
Program powinien drukować albo informację, że nie ma w zbiorze liczb rzeczywistych rozwiązania tego równania,
albo przedstawiać rozwiązanie pojedyncze, gdy delta jest równa 0 albo podwójne, gdy delta jest dodatnia.
*/

int main()
{
    float a, b, c, delta, sqrtdelta, x1, x2;
    scanf("%f", &a);
    scanf("%f", &b);
    scanf("%f", &c);
    delta = (b*b)-(4*a*c);
    printf("Rownanie postaci %fx^2 + %fx + %f = 0 \n", a, b, c);
    if(delta<0){
        printf("Brak rozwiazan w liczbach rzeczywistych");
    }
    else if(delta == 0){
        x1 = -b/(2*a);
        printf("Istnieje jedno rozwiazanie: %f", x1);
    }
    else{
        sqrtdelta = sqrt(delta);
        x1 = (-b-sqrtdelta)/(2*a);
        x2 = (-b+sqrtdelta)/(2*a);
        printf("Istnieja 2 rozwiazania: %f i %f", x1, x2);
    }
    return 0;
}
