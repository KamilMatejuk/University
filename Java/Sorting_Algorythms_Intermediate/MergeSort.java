public class MergeSort extends Sorter {

    MergeSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort() {
        mergeSort(array);
    }

    void mergeSort(int[] a){
        if (a.length < 2){
            return;
        }
        int middle = a.length / 2;
        int[] left = new int[middle];
        int[] right = new int[a.length - middle];

        for (int i = 0; i < middle; i++) {
            left[i] = a[i];
        }
        for (int i = middle; i < a.length; i++) {
            right[i - middle] = a[i];
        }
        mergeSort(left);
        mergeSort(right);

        merge(a, left, right);
    }

    void merge(int[] a, int[] l, int[] r){

        int i = 0, j = 0, k = 0;
        int llength = l.length, rlength = r.length;
        while (i < llength && j < rlength){
            if (compare(l[i],r[j])){
                if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d do scalonej tabeli", l[i]));
                moveCounter++;
                a[k] = l[i];
                k++;
                i++;
            } else {
                if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d do scalonej tabeli", r[j]));
                moveCounter++;
                a[k] = r[j];
                k++;
                j++;
            }
        }
        while (i < llength) {
            if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d do scalonej tabeli", l[i]));
            moveCounter++;
            a[k] = l[i];
            k++;
            i++;
        }
        while (j < rlength) {
            if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d do scalonej tabeli", r[j]));
            moveCounter++;
            a[k] = r[j];
            k++;
            j++;
        }
    }
}
