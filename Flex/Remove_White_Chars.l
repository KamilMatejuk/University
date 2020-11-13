/* Kamil Matejuk, 250135 */

%{
    #include<stdio.h>
    int line_count = 0;
    int word_count = 0;
%}

%%
^" "+           {};             // spacje na początku wiersza
" "+$           {};             // spacje na końcu wiersza
[" "\t]+        printf(" ");    // ciągi spacji lub tabulacji na jedną
\n+             printf("\n"); line_count++;   // liczenie linii
([a-zA-Z0-9])*  ECHO; word_count++;   // liczenie słów
%%

int main(int argc, char** argv){
    if(argc > 1){
        yyin = fopen(argv[1],"r");
        yylex();
        printf("\n%d linii\n%d słów\n", line_count, word_count);
    }
}
int yywrap(){
    return 1;
}
