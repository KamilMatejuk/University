#include "funs.h"
#include <stdio.h>
#include <string.h>

char* usun_pierwszy_znak(char* str){
    int dlugosc = strlen(str);
    for(int i=0; i<dlugosc; i++){
        str[i]=str[i+1];
    }
    return str;
}
