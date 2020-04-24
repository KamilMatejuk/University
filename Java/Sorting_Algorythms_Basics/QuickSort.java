/* Kamil Matejuk */
public class QuickSort extends Sorter {

    QuickSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort() {
        quickSort(array, 0, array.length-1);
    }

    void quickSort(Comparable[] array, int begin, int end) {
        if (begin < end) {
            int pivot = partition(array, begin, end);

            quickSort(array, begin, pivot - 1);
            quickSort(array, pivot + 1, end);
        }
    }

    int partition(Comparable array[], int begin, int end) {
        Comparable pivot = array[end];
        int i = begin;

        for (int j = begin; j < end; j++) {
            if (compare(array[j],pivot)){
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, end);
        
        return i;
    }
}
