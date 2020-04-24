/* Kamil Matejuk */
#include <stdio.h>
#include "agents.h"

/* Agent porusza się na płaszczyźnie po kracie punktów o współrzędnych całkowitych.
Agent znajdujący się w miejscu (x, y) może wykonać krok na północ i przejdzie na miejsce (x, y + 1).
Agent znajdujący się w miejscu (x, y) może wykonać krok na południe i przejdzie na miejsce (x, y − 1).
Agent znajdujący się w miejscu (x, y) może wykonać krok na wschód i przejdzie na miejsce (x + 1, y).
Agent znajdujący się w miejscu (x, y) może wykonać krok na zachód i przejdzie na miejsce (x − 1, y). 

Zdefiniuj strukturę Agent oraz funkcje north, south, east, west */

int main()
{
    struct agent Bob = newagent(0,0);
    struct agent Alice = newagent(3,3);
    north(&Bob);
    south(&Alice);
    west(&Alice);
    north(&Bob);
    east(&Bob);
    south(&Alice);
    printf("odjeglosc = %f\n",distance(Bob,Alice));

    return 0;
}
