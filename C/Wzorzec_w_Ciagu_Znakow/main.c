/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>
#include <string.h>

/* Napisz funkcję match(char* wzorzec, char* łańcuch), która ustala zgodność wzorca z łańcuchem.
Znak ? oznacza zgodność z dowolnym innym znakiem. 
Znak * oznacza zgodność z dowolnym, również pustym, ciągiem znaków w łańcuchu.
Znak różny od ? i * oznacza zgodność tylko z samym sobą.
Na przykład:
    • match(”*.doc”,s) ma zwracać true wtedy i tylko wtedy, gdy napis s jest ciągiem dowolnych znaków z czterema ostatnimi znakami ’.doc’
    • match(”a???”,s) ma zwracać true wtedy i tylko wtedy, gdy s ma długość 4 i zaczyna się od litery ’a’
    • match("a*b*b*c", s) ma zwracać true wtedy i tylko wtedy, gdy napis s zaczyna się od litery ’a’ i kończy się literą ’c’ 
    a między nimi znajdują się przynajmniej dwie litery ’b’ (niekoniecznie obok siebie) */

int main()
{
    char wzorzec[1000], lancuch[1000];
    printf("Podaj wzor (min 1 max 100 znaków): ");
    scanf("%s",wzorzec);
    printf("Podaj łańcuch znaków do sprawdzenia (min 1 max 100 znaków): ");
    scanf("%s",lancuch);
    char wyrazy[1000];
    struct wynik_match wm;
    wm.tf=true;
    wm.lancuch2="";
    int dlugosc = strlen(wzorzec);
    for(int i=0; i<dlugosc; i++){
        if(wzorzec[i]!='*'){
            wyrazy[i] = wzorzec[i];
        } else {
            wm = match(wyrazy,lancuch);
            strcpy(lancuch, wm.lancuch2);
            if(!wm.tf){
                printf("Lancuch nie zgadza sie ze wzorem\n");
                break;
            }
        }
    }
    if(wm.tf){
        printf("Lancuch zgadza sie ze wzorem\n");
    }
    return 0;
}
