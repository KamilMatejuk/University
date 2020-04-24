/* Kamil Matejuk */
public class MergeSort extends Sorter {

    MergeSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort() {
        mergeSort(array);
    }

    void mergeSort(Comparable[] a){
        if (a.length < 2){
            return;
        }
        int middle = a.length / 2;
        Comparable[] left = new Comparable[middle];
        Comparable[] right = new Comparable[a.length - middle];

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

    void merge(Comparable[] a, Comparable[] l, Comparable[] r){

        int i = 0, j = 0, k = 0;
        int llength = l.length, rlength = r.length;
        while (i < llength && j < rlength){
            if (compare(l[i],r[j])){
                if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %s do scalonej tabeli", l[i]));
                moveCounter++;
                a[k] = l[i];
                k++;
                i++;
            } else {
                if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %s do scalonej tabeli", r[j]));
                moveCounter++;
                a[k] = r[j];
                k++;
                j++;
            }
        }
        while (i < llength) {
            if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %s do scalonej tabeli", l[i]));
            moveCounter++;
            a[k] = l[i];
            k++;
            i++;
        }
        while (j < rlength) {
            if(zad4.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %s do scalonej tabeli", r[j]));
            moveCounter++;
            a[k] = r[j];
            k++;
            j++;
        }
    }
}
