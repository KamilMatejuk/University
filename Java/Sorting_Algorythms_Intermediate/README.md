# Polecenie
Zaimplementuj następujące algorytmy sortowania: Insertion Sort, Merge Sort, Quick Sort oraz Radix Sort.</br>
Wybór typu sortowania odbywa się flagą --type, natomomiast porównnia (rosnąco/ malejąco) flagą --comp. Flaga --stat
pozwala na wygenerowanie pliku ze statystyką czasu, ilości porównań, ilości zamian oraz wykorzystanej pamięci dla k prób.

### kompilacja:
    javac *.java
### uruchamianie:
    java zad --type insert|merge|quick|radix --comp '<='|'>=' --stat filename.txt k
###uwaga: 
* sortowanie działa tylko dla liczb całkowitych.
* ze względu na działanie Garbage Collectora pomiary zużytej pamięci są niedokładne, a czasem mogą wyjść ujemne.
