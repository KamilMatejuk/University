/* Kamil Matejuk */
#include "agents.h"
#include <stdlib.h>
#include <math.h>

double distance(struct agent a1, struct agent a2)
{
    int x = abs(a1.x - a2.x);
    int y = abs(a1.y - a2.y);
    double distance = sqrt(x*x + y*y);
    return distance;
}
