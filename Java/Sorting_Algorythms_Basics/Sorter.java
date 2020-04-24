/* Kamil Matejuk */
import java.util.concurrent.TimeUnit;

public abstract class Sorter {

    Comparable[] array;
    String comparator;
    int compareCounter;
    int moveCounter;
    long timeMillis;

    Sorter(String comp){
        this.comparator = comp;
    }

    void sort(Comparable[] arr) {
        this.array = arr;
        compareCounter = 0;
        moveCounter = 0;
        long startTime = System.nanoTime();
        costomSort();
        timeMillis = System.nanoTime()-startTime;
    }

    abstract void costomSort();

    /** funkcja porównująca elementy a i b na podstawie zadanej relacji */
    boolean compare(Comparable a, Comparable b){
        if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Porównanie %s z %s", a, b));
        compareCounter++;
        return compareAssert(a, b);
    }

    /** funkcja analogiczna do compare,
     * ale na potrzeby asserta nie wliczająca się do liczby porównań */
    boolean compareAssert(Comparable a, Comparable b){
        if(comparator.equals(">=")){
//            return (a >= b);
            return a.compareTo(b) >= 0;
        } else {
//            return (a <= b);
            return a.compareTo(b) <= 0;
        }
    }

    boolean equal(Comparable a, Comparable b){
//    	return a == b;
        return a.compareTo(b) == 0;
    }

    /** funkcja zamieniająca miejscami dwa elementy */
    void swap(Comparable[] array, int a, int b){
    	if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Zamiana miejscami %s z %s", array[a], array[b]));
        moveCounter++;
        Comparable temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /** funckja wypisująca dane (ilość przestawień, ilośc porównań, czas)
     * na standardowe wyjście błedu pod koniec sortowania */
    long[] getStats() {
        return new long[]{compareCounter, moveCounter, timeMillis};
    }

    /** funkcja sprawdzająca czy tabela jest posortowana w zadanejj relacji */
    void assertRight(){
        boolean right = true;
        for(int i=1; i<array.length; i++){
            if(compareAssert(array[i-1], array[i])){
                right = false;
            }
        }
        assert(right);
    }

    Comparable[] getResults(){
        return array;
    }

}
