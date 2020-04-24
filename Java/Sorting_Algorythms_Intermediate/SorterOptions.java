/* Kamil Matejuk */
import java.util.ArrayList;
import java.util.List;

public enum SorterOptions {
    insert("InsertionSort"),
    merge("MergeSort"),
    quick("QuickSort"),
    radix("RadixSort");

    String sorter;

    private SorterOptions(String s) {
        this.sorter = s;
    }

    public static List<String> getValues(){
        List<String> v = new ArrayList<>();
        SorterOptions arr[] = SorterOptions.values();
        for(SorterOptions s : arr){
            v.add(s.toString());
        }
        return v;
    }
}
