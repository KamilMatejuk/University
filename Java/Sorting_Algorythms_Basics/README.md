# Polecenie
Zaimplementuj następujące algorytmy sortowania: Insertion Sort, Merge Sort, Quick Sort, Dual Pivot Quick Sort oraz
stwórz własne połączenie dowolnych z nich by otrzymać najszybszy algorytm. Wybór typu sortowania odbywa się flagą --type,
natomomiast porównnia (rosnąco/ malejąco) flagą --comp. Flaga --stat pozwala na wygenerowanie pliku ze statystyką czasu,
ilości porównań oraz ilości zamian dla k prób.

### kompilacja:
	javac *.java
### uruchamianie:
	java zad4 --type insert|merge|quick|dualpivot|hybrid --comp '<='|'>=' --stat|--smallstat|--stringstat|--stringsmallstat filename.txt k
gdzie: </br>
	--stat generuje statystyki dla liczb całkowitych i n: {100, 200, ... 1000} </br>
	--smallstat generuje statystyki dla liczb całkowitych i n: {2, 3, 4, ... 100} </br>
	--stat generuje statystyki dla ciagów alfanumerycznych i n: {100, 200, ... 1000} </br>
	--smallstat generuje statystyki dla ciagów alfanumerycznych i n: {2, 3, 4, ... 100} </br>
