/* Kamil Matejuk */
#include <stdio.h>


int main(int argc, char* argv[]){
	for(int i=0; i<256; i++){
		printf("\033[38;5;%dm%s\033[0m\n", i, "Hello, World!");
	}
	return 0;
}

/*
"\033" - select graphics rendetion
"[...m" - set graphics mode
"38" - set Foreground Color
"5" - 8 bit colors
"%d" - color number [0-255]
*/
