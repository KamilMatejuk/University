import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort extends Sorter {

    RadixSort(String comp) {
        super(comp);
    }

    @Override
    void costomSort(){
        int[] positive = split(array, "positive");
        int[] negative = split(array, "negative");

        int[] sortedPositive = radixSort(positive);
        int[] sortedNegative = radixSort(negative);

        //reverse negatives and multiply for (-1)
        for(int i=0; i<(sortedNegative.length/2); i++){
            int a = sortedNegative[i];
            int b = sortedNegative[sortedNegative.length-1 - i];
            sortedNegative[i] = (-1)*b;
            sortedNegative[sortedNegative.length-1-i] = (-1)*a;
            if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Zamiana miejscami %d i %d", -1*a, -1*b));
            moveCounter += 2;
        }

        //join arrays positive and negative
        System.arraycopy(sortedNegative, 0, array, 0, sortedNegative.length);
        System.arraycopy(sortedPositive, 0, array, sortedNegative.length, sortedPositive.length);

    }

    private int[] radixSort(int[] array) {
        int digits = getMaxDigit(array);
        for (int exp = 0; exp < digits; exp++) {
            countSort(array, (int) Math.pow(10,exp));
        }
        return array;
    }

    int[] split(int[] arr, String which){
        ArrayList<Integer> splitted = new ArrayList<>();
        for(int i : arr){
            if(which.equals("positive") && i >= 0) {
                splitted.add(i);
                if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d", i));
                moveCounter++;
            }
            else if(which.equals("negative") && i < 0) {
                splitted.add((-1)*i);
                if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d", i));
                moveCounter++;
            }
        }
        int[] s = new int[splitted.size()];
        for(int i=0; i<s.length; i++){
            s[i] = splitted.get(i);
        }
        return s;
    }

    static int getMaxDigit(int arr[]){
        if(arr.length == 0){
            return 0;
        }
        int maximal = arr[0];
        for (int i : arr){
            maximal = Math.max(maximal, i);
        }
        if(maximal < 0){
            return String.valueOf(maximal).length()-1;
        } else {
            return String.valueOf(maximal).length();
        }
    }


    void countSort(int arr[], int exp){

        ArrayList<ArrayList<Integer>> count = new ArrayList<>();
        for(int i=0; i<10; i++){
            count.add(new ArrayList<>());
        }
        for(int i : arr){
            int digit = (i / exp)%10;
            count.get(digit).add(i);
            if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d", i));
            moveCounter++;
        }
        ArrayList<Integer> all = new ArrayList<>();
        for(ArrayList<Integer> list : count){
            all.addAll(list);
        }
        for(int i=0; i<arr.length; i++){
            arr[i] = all.get(i);
            if(zad1.STANDARD_OUT_ERR) System.err.println(String.format("Przestawienie %d", i));
            moveCounter++;
        }
    }

}
