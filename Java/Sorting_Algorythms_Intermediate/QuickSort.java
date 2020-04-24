/* Kamil Matejuk */
public class QuickSort extends Sorter {

    QuickSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort() {
        quickSort(array, 0, array.length-1);
    }

    void quickSort(int[] array, int begin, int end) {
        if (begin < end) {
            int pivot = partition(array, begin, end);

            quickSort(array, begin, pivot - 1);
            quickSort(array, pivot + 1, end);
        }
    }

    int partition(int array[], int begin, int end) {
        int pivot = array[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (compare(array[j],pivot)){
                i++;

                if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Zamiana miejscami %d z %d", array[i], array[j]));
                moveCounter++;
                int swapTemp = array[i];
                array[i] = array[j];
                array[j] = swapTemp;
            }
        }

        if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Zamiana miejscami %d z %d", array[i+1], array[end]));
        moveCounter++;
        int swapTemp = array[i+1];
        array[i+1] = array[end];
        array[end] = swapTemp;

        return i+1;
    }
}
