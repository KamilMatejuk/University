/* Kamil Matejuk, 250135 */

%{
    #include<stdio.h>
    int doc = 0;
%}

 // wszystkie znaki dozwolone poza zakończeniem komentarza */
chars [a-z]|[A-Z]|[0-9]|" "|\n|\t|\*|\/" "|\/\*|\/\/|\/\*\*
 // stany
%s IN_STRING
%s IN_COMMENT_MULTILINE
%s IN_COMMENT_SINGLELINE
%s IN_DOCUMENTATION_MULTILINE
%s IN_DOCUMENTATION_SINGLELINE

%%
<INITIAL>{
    "/**"|"/*!"     if(doc == 1){ECHO;} BEGIN(IN_DOCUMENTATION_MULTILINE);
    "///"|"//!"     if(doc == 1){ECHO;} BEGIN(IN_DOCUMENTATION_SINGLELINE);
    "//"            BEGIN(IN_COMMENT_SINGLELINE);
    "/*"            BEGIN(IN_COMMENT_MULTILINE);
    \"              BEGIN(IN_STRING);
}
<IN_STRING>{
    \\\"    ;
    \"      BEGIN(INITIAL);
}
<IN_COMMENT_MULTILINE>{
    .       ;
    \*\/    BEGIN(INITIAL);
}
<IN_COMMENT_SINGLELINE>{
    .       ;
    \\\n    BEGIN(IN_COMMENT_SINGLELINE);
    \n      ECHO; BEGIN(INITIAL);
}
<IN_DOCUMENTATION_MULTILINE>{
    .       if(doc == 1){ECHO;}
    \*\/    if(doc == 1){ECHO;} BEGIN(INITIAL);
}
<IN_DOCUMENTATION_SINGLELINE>{
    .       if(doc == 1){ECHO;}
    \\\n    if(doc == 1){ECHO;} BEGIN(IN_DOCUMENTATION_SINGLELINE);
    \n      ECHO; BEGIN(INITIAL);
}
%%

int main(int argc, char** argv){
    if(argc > 1){
        yyin = fopen(argv[1],"r");
        if(argc > 2 && strcmp(argv[2], "--doc") == 0){
            doc = 1;
        }
        return yylex();
    }
}
int yywrap(){
    return 1;
}
