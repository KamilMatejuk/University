/* Kamil Matejuk */
#include <stdio.h>

/* Korzystając z funkcji printf wydrukuj następujący „magiczny” trójkąt. Np dla słowa ABCD:
A   B   C   D
  A   B   C
    A   B
      A
*/

int main()
{
    char wyraz[] = {"ABRAKADABRA"};
    for(int i=0; i<sizeof(wyraz)-1; i++){
        for(int k=0; k<i; k++){
            printf(" ");
        }
        for(int j=sizeof(wyraz)-1; j>i; j--){
            printf("%c ", wyraz[sizeof(wyraz)-1-j]);
        }
        printf("\n");
    }
    return 0;
}
