/* Kamil Matejuk, 250135 */

%{
    #include<stdio.h>
%}

%%
#.* {};     // usunięcie zwykłych komentarzy
%%

int main(int argc, char** argv){
    if(argc > 1){
        yyin = fopen(argv[1],"r");
        yylex();
    }
}
int yywrap(){
    return 1;
}
