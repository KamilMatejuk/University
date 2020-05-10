package sets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/** Klasa abstrakcyjna będąca podstwą każdego typu struktury (bst, rbt, hmap) */

public abstract class DataSet {

    // czas działania
    protected long startTime;
    // ilości
    protected long insertCounter, deleteCounter, findCounter, minCounter, maxCounter, succesorCounter, inorderCounter;
    // maksymalna liczba elementów
    protected long maxNumberOfElements;
    // końcowa liczba elementów
    protected long numberOfElements;

    public DataSet(){
        this.startTime = System.nanoTime();
        this.insertCounter = 0;
        this.deleteCounter = 0;
        this.findCounter = 0;
        this.minCounter = 0;
        this.maxCounter = 0;
        this.succesorCounter = 0;
        this.inorderCounter = 0;
        this.maxNumberOfElements = 0;
        this.numberOfElements = 0;
    }

    // wstaw do struktury ciąg s (znaki tylko [a-zA-Z])
    public void insert(String s){
        System.out.println("");
        insertCounter++;
    }

    // dla każdego, oddzielonego białym znakiem, wyrazu z pliku f wykonaj operacj̨e insert,
    //lub zwróć informacj̨e o nieistniejącym pliku
    public void load(String f) {
        try {
            File src = new File(f);
            Scanner scanner = new Scanner(src);
            while(scanner.hasNext()) {
                insert(scanner.next());
            }
        } catch (FileNotFoundException e){
            System.out.println("File " + f + "doesn't exists");
        }
    }

    // jeśli struktura nie jest pusta i dana wartość s istnieje, to usuń element s
    public void delete(String s){
        System.out.println("");
        deleteCounter++;
    }

    // sprawdź czy w strukturze przechowywana jest wartość s (jeśli tak to wypisz 1, w p.p. 0)
    public void find(String s){
        System.out.println("");
        findCounter++;
    }

    // wypisz najmniejszy element, lub (dla struktur pustych oraz nie zachowujących porządku)
    // pustą linįe
    public void min(){
        System.out.println("");
        minCounter++;
    }

    // wypisz najmniejszy element, lub (dla struktur pustych oraz nie zachowujących porządku)
    // pustą linįe
    public void max(){
        System.out.println("");
        maxCounter++;
    }

    // wypisz nast̨epnik elementu s, lub jeśli on nie istnieje (struktura nie zawiera k,
    // k nie ma nast̨epników, struktura nie zachowuje porządku), pustą linię
    public void succesor(String s){
        System.out.println("");
        succesorCounter++;
    }

    // wypisz elementy drzewa w posortowanej kolejności (od najmniejszego do najwįekszego),
    // lub (dla struktur pustych oraz nie zachowujących porządku) pustą linię
    public void inorder(){
        System.out.println("");
        inorderCounter++;
    }

    public void printStats(){
        long timeInSec = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        System.err.println("Czas działania: \t" + timeInSec + "ms");
        System.err.println("Ilość wykonania operacji 'insert': \t" + insertCounter);
        System.err.println("Ilość wykonania operacji 'delete': \t" + deleteCounter);
        System.err.println("Ilość wykonania operacji 'find': \t" + findCounter);
        System.err.println("Ilość wykonania operacji 'min': \t" + minCounter);
        System.err.println("Ilość wykonania operacji 'max': \t" + maxCounter);
        System.err.println("Ilość wykonania operacji 'succesor': \t" + succesorCounter);
        System.err.println("Ilość wykonania operacji 'inorder': \t" + inorderCounter);
        System.err.println("Maksymalne zapełnienie struktury: \t" + maxNumberOfElements);
        System.err.println("Finalne zapełnienie struktury: \t\t" + numberOfElements);
        System.exit(0);
    }


    public abstract void tempPrint();

}
