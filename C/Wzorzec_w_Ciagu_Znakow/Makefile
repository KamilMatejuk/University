# Makefile

all: main

main: main.o match.o usun_pierwszy.o
	clang -o main main.o match.o usun_pierwszy.o

main.o: main.c
	clang -c main.c
match.o: match.c
	clang -c match.c
usun_pierwszy.o: usun_pierwszy.c
	clang -c usun_pierwszy.c

clean: 
	rm -f main *.o *~