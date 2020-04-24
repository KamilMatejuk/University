/* Kamil Matejuk */
#include "agents.h"
#include <stdlib.h>

struct agent newagent(int x, int y)
{
    struct agent *newagent = malloc(sizeof(struct agent));
    newagent->x=x;
    newagent->y=y;
    return *newagent;
}
