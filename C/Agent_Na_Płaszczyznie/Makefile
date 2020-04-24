# Makefile

all: main

main: main.o newagent.o distance.o north.o south.o east.o west.o
	clang -o main main.o newagent.o distance.o north.o south.o east.o west.o -lm

main.o: main.c 
	clang -c main.c
newagent.o: newagent.c
	clang -c newagent.c
distance.o: distance.c
	clang -c distance.c
north.o: north.c
	clang -c north.c
south.o: south.c
	clang -c south.c
east.o: east.c
	clang -c east.c
west.o: west.c
	clang -c west.c

clean: 
	rm -f main *.o *~