import java.util.Arrays;

public class HybridSort extends QuickSort {

    private int changePoint = 40;

    HybridSort(String comp) {
        super(comp);
    }

    @Override
    void quickSort(Comparable[] array, int begin, int end) {
        if (begin < end) {
            int pivot = partition(array, begin, end);

            Comparable[] a = new Comparable[0], b = new Comparable[0];

            // podział ze względu na pkt przejścia
            if(pivot-1-begin > changePoint) quickSort(array, begin, pivot - 1);
            else {
                Sorter s = new QuickDualPivotSort(comparator);
                Comparable[] tempArr = Arrays.copyOfRange(array, begin + 1, pivot+1);
                s.sort(tempArr);
                a = s.getResults();
                compareCounter += s.getStats()[0];
                moveCounter += s.getStats()[1];
            }

            if(end-pivot-1 > changePoint)   quickSort(array, pivot + 1, end);
            else {
                Sorter s = new QuickDualPivotSort(comparator);
                Comparable[] tempArr = Arrays.copyOfRange(array, pivot, end + 1);
                s.sort(tempArr);
                b = s.getResults();
                compareCounter += s.getStats()[0];
                moveCounter += s.getStats()[1];
            }

            System.arraycopy(a, 0, array, 0, a.length);
            System.arraycopy(b, 0, array, a.length, b.length);
        }
    }
}
