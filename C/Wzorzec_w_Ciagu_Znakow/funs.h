//funs.h
#include <stdbool.h>

struct wynik_match {
    bool tf;
    char* lancuch2;
    char* wzor2;
};

struct wynik_match match(char* wyrazy, char* lancuch);
char* usun_pierwszy_znak(char* string);
