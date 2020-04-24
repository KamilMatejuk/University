/* Kamil Matejuk */
import java.util.Arrays;
import java.util.Random;

public class RandomSelect extends SelectAlgorythm {

    RandomSelect(int[] array){
        super(array);
    }

    @Override
    int run(int k){
        this.compare = 0;
        this.move = 0;

        int kth = randomizeSelect(array, 0, array.length-1, k);

        // stats
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba porównań: %d", compare));
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba przestawień: %d", move));

        return kth;
    }

    int randomizeSelect(int[] array, int begin, int end, int k){
        if(begin == end){
            return array[begin];
        }
        int pivot = randomizedPartition(array, begin, end);
        int i = pivot - begin + 1;
        if(k <= i){
            return randomizeSelect(array, begin, pivot, k);
        } else {
            return randomizeSelect(array, pivot+1, end, k-i);
        }
    }

    int randomizedPartition(int[] array, int begin, int end){
        int index = new Random().nextInt((end - begin) + 1) + begin;
        int swapTemp = array[index];
        array[index] = array[begin];
        array[begin] = swapTemp;

        if(zad2.GENERATE_STATS) System.err.println(String.format("Przestawienie %d z %d", array[begin], array[index]));
        move++;

        int x = array[begin];
        int i = begin - 1;
        int j = end + 1;

        while(true) {
            do {
                j--;

                if(zad2.GENERATE_STATS) System.err.println(String.format("Porównanie %d z %d", array[j], x));
                compare++;
            } while (j > 0 && array[j] > x);
            do {
                i++;

                if(zad2.GENERATE_STATS) System.err.println(String.format("Porównanie %d z %d", array[i], x));
                compare++;
            } while (i < array.length && array[i] < x);
            if (i < j) {
                swapTemp = array[i];
                array[i] = array[j];
                array[j] = swapTemp;

                if(zad2.GENERATE_STATS) System.err.println(String.format("Przestawienie %d z %d", array[i], array[j]));
                move++;
            } else {
                return j;
            }
        }
    }
}
