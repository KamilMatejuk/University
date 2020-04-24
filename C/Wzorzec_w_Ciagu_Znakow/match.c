/* Kamil Matejuk */
#include "funs.h"
#include <stdio.h>
#include <string.h>

struct wynik_match match(char* wyrazy, char* lancuch)
{
    struct wynik_match wm;
    wm.tf=true;
    wm.lancuch2="";
    int dlugosc_wzoru = strlen(wyrazy);
    int dlugosc_lancucha = strlen(lancuch);

    while(dlugosc_wzoru!=0){
        if(wyrazy[0]=='?'){
            usun_pierwszy_znak(wyrazy);
			usun_pierwszy_znak(lancuch);
        }
        else if(wyrazy[0]==lancuch[0]){
            usun_pierwszy_znak(lancuch);
            usun_pierwszy_znak(wyrazy);
        } else {
            wm.tf=false;
		    return wm;
            break;
        }
		dlugosc_wzoru = strlen(wyrazy);
    }
    if(lancuch!=NULL){
        strcpy(wm.lancuch2,lancuch);
    }
    return wm;
}
