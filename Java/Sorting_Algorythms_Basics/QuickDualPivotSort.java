/* Kamil Matejuk */
public class QuickDualPivotSort extends Sorter {

    QuickDualPivotSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort() {
        quickSort(array, 0, array.length-1);
    }

    void quickSort(Comparable[] array, int begin, int end) {
        if (begin < end) {
            int[] pivots = partition(array, begin, end);
            if (pivots[0] > pivots[1]){
                int temp = pivots[0];
                pivots[0] = pivots[1];
                pivots[1] = pivots[0];
            }
            int p = pivots[0];
            int q = pivots[1];

            quickSort(array, begin, p - 1);
            quickSort(array, p + 1, q - 1);
            quickSort(array, q + 1, end);
        }
    }

    int[] partition(Comparable array[], int begin, int end) {

        if (!compare(array[begin], array[end])) swap(array, begin, end);
        Comparable p = array[begin];
        Comparable q = array[end];

        int i = begin + 1;
        int k = begin + 1;
        int g = end - 1;

        while (k <= g){
            if(compare(array[k], p) && !equal(array[k], p)){
                swap(array, k, i);
                i++;
            } else if(!compare(array[k], q)){
                while(!compare(array[g],q) && k < g){
                    g--;
                }
                swap(array, k, g);
                g--;
                if(compare(array[k],p)){
                    swap(array, k, i);
                    i++;
                }
            }
            k++;
        }
        i--;
        g++;
        swap(array, begin, i);
        swap(array, end, g);
        return new int[]{i, g};
    }
}

