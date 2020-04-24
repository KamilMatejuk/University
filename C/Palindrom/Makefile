# Makefile

all: main

main: main.o palindrom.o wczytanie.o 
	clang -o main main.o palindrom.o wczytanie.o

main.o: main.c
	clang -c main.c
palindrom.o: palindrom.c
	clang -c palindrom.c
wczytanie.o: wczytanie.c
	clang -c wczytanie.c

clean: 
	rm -f main *.o *~