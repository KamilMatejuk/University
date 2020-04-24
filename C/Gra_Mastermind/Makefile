# Makefile

all: main

main: mastermind.o zapytaj.o sprawdz_kody.o
	clang -o mastermind mastermind.o zapytaj.o sprawdz_kody.o

main.o: mastermind.c
	clang -c mastrmind.c
zapytaj.o: zapytaj.c
	clang -c zapytaj.c
sprawdz_kody.o: sprawdz_kody.c
	clang -c sprawdz_kody.c

clean: 
	rm -f main *.o *~
