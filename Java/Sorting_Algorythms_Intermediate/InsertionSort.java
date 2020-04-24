public class InsertionSort extends Sorter {

    InsertionSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort(){
        for (int i = 1; i < array.length; ++i) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && !compare(array[j],key)) {
                if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d na miejsce %d", array[j], array[j + 1]));
                moveCounter++;
                array[j + 1] = array[j];
                j--;
            }
            if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d na miejsce %d", key, array[j + 1]));
            moveCounter++;
            array[j + 1] = key;
        }
    }

}
