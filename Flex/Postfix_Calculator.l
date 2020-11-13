/* Kamil Matejuk, 250135 */

%{
    #include <stdio.h>
    #include <cmath>
    #define stack_size 10
    double stack[stack_size];
    int stack_index;
    int number_count = 0;
    int operands_count = 0;

    void push(double x){
        if(stack_index < stack_size){
            stack[++stack_index] = x;
        }
    }
    double pop(){
        if(stack_index >= 0){
            double temp = stack[stack_index--];
            stack[stack_index + 1] = 0;
            return temp;
        }
    }
%}

int     [-]?[0-9]+
real    ([0-9]+[.][0-9]*)|([0-9]*[.][0-9]+)
exp    [-]?({int}|{real})[eE][-]?{int}
numbers ({int}|{real}|{exp})

%%
{numbers}   {
        push(atof(yytext));
        number_count++;
    }
"+" {
        push(pop() + pop());
        operands_count++;
    }
"-" {
        double temp = pop(); 
        push(pop() - temp);
        operands_count++;
    }
"*" {
        push(pop() * pop());
        operands_count++;
    }
"/" {
        double temp = pop();
        push(int(pop() / temp));
        operands_count++;
    }
"^" {
        double temp = pop();
        push(pow(pop(), temp));
        operands_count++;
}
"%" {
        double temp = pop();
        push(fmod(pop(), temp));
        operands_count++;
}
[\n]    {
    if(number_count == operands_count + 1){
        printf ("%.3f \n", pop());
    } else if(number_count > operands_count + 1){
        printf("ERROR: too many numbers\n");
    } else if(number_count < operands_count + 1){
        printf("ERROR: too many operands\n");
    }
    number_count = 0;
    operands_count = 0;
    }

[^-0-9+*/^%.;eE \n]+.*\n     { 
    printf("ERROR: Invalid Character - %c\n", yytext[0]);
    number_count = 0;
    operands_count = 0;
    }

%%

int main(int argc, char** argv){
    if(argc > 1){
        yyin = fopen(argv[1],"r");
        return yylex();
    }
    return yylex();
}
int yywrap(){
    return 1;
}
