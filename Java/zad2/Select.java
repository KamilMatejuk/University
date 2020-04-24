import java.util.Arrays;

public class Select extends SelectAlgorythm {

    // https://brilliant.org/wiki/median-finding-algorithm/

    Select(int[] array){
        super(array);
    }

    @Override
    int run(int k){

        this.compare = 0;
        this.move = 0;

        int kth = select(array, 0, array.length-1, k);

        // stats
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba porównań: %d", compare));
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba przestawień: %d", move));

        return kth;
    }

    private int medianOfMedian(int[] array) {
        if(array.length == 1){
            return array[0];
        }

        int[] medians = new int[(int) Math.ceil(array.length/5.0)];
        for(int i = 0; i<array.length; i += 5){
            int[] temparray = Arrays.copyOfRange(array, i, Math.min(i+5, array.length));

            // insertion sort
            for (int j = 1; j < temparray.length; ++j) {
                int key = temparray[j];
                int k = j - 1;
                while (k >= 0 && temparray[k] > key) {
                    temparray[k + 1] = temparray[k];
                    k--;

                    if(zad2.GENERATE_STATS) System.err.println(String.format("Porównanie %d z %d", temparray[k+1], key));
                    compare++;
                    if(zad2.GENERATE_STATS) System.err.println(String.format("Przestawienie %d z %d", temparray[k+1], key));
                    move++;
                }
                temparray[k + 1] = key;
            }
            if(temparray.length % 2 == 0){
                //parzyste
                medians[i/5] = Math.max(temparray[temparray.length/2 - 1], temparray[temparray.length/2]);
            } else {
                //nieparzyste
                medians[i/5] = temparray[temparray.length/2];
            }
        }
        return medianOfMedian(medians);
    }

    int select(int[] array, int begin, int end, int k){
        if(begin >= end){
            return array[begin];
        }
        int mm = medianOfMedian(Arrays.copyOfRange(array, begin, end));
        int pivot = partition(array, begin, end, mm);
        int i = pivot - begin + 1;
        if(k <= i){
            return select(array, begin, pivot, k);
        } else {
            return select(array, pivot+1, end, k-i);
        }
    }

    int partition(int[] array, int begin, int end, int x){
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
                int swapTemp = array[i];
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
