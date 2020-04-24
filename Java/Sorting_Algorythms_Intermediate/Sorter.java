/* Kamil Matejuk */
public abstract class Sorter {

    int[] array;
    String comparator;
    long compareCounter;
    long moveCounter;
    long timeMillis;
    long memoryUsage;

    Sorter(String comp){
        this.comparator = comp;
    }

    void sort(int[] arr) {
        this.array = arr;
        compareCounter = 0;
        moveCounter = 0;
        // memmory start
        long beforeUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        // time start
        long startTime = System.nanoTime();
        // sorting
        costomSort();
        // time end
        timeMillis = System.nanoTime()-startTime;
        // memmory end
        long afterUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        memoryUsage = afterUsedMemory - beforeUsedMemory;
    }

    abstract void costomSort();

    /** funkcja porównująca elementy a i b na podstawie zadanej relacji */
    boolean compare(int a, int b) {
        if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Porównanie %d z %d", a, b));
        compareCounter++;
        if(comparator.equals(">=")){
            return (a >= b);
        } else {
            return (a <= b);
        }
    }
    /** funkcja analogiczna do compare,
     * ale na potrzeby asserta nie wliczająca się do liczby porównań */
    boolean compareAssert(int a, int b) {
        if(comparator.equals(">=")){
            return (a >= b);
        } else {
            return (a <= b);
        }
    }

    /** funckja wypisująca dane (ilość przestawień, ilośc porównań, czas)
     * na standardowe wyjście błedu pod koniec sortowania */
    long[] getStats() {
        return new long[]{compareCounter, moveCounter, timeMillis, memoryUsage};
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

    int[] getResults(){
        return array;
    }

}
