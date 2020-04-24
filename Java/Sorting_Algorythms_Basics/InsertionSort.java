public class InsertionSort extends Sorter {

    InsertionSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort(){
        for (int i = 1; i < array.length; ++i) {
            Comparable key = array[i];
            int j = i - 1;
            while (j >= 0 && compare(array[j],key)) {
                if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %s na miejsce %s", array[j], array[j + 1]));
                moveCounter++;
                array[j + 1] = array[j];
                j--;
            }
            if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %s na miejsce %s", key, array[j + 1]));
            moveCounter++;
            array[j + 1] = key;
        }
    }

}
