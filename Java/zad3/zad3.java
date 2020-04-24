import java.io.IOException;
import java.util.*;

public class zad3 {

    public static boolean GENERATE_STATS = true;
    static ArrayList<Long> comparisonsOfN = new ArrayList<>();
    static ArrayList<Long> timesOfN = new ArrayList<>();
    static int compare;

    /** main - wywolanie odpowiedniego rodzaju sortowania */
    public static void main(String[] args) throws IOException {

        // wykonanie statystyk
        for(int i = 0; i<1000; i++){
            int n = 1000*(i+1);
            int v = new Random().nextInt(n);
            int[] array = generateRandomList(n);
            compare = 0;
            long startTime = System.nanoTime();
            checkIfExists(array, 0, array.length-1, v);
            timesOfN.add(System.nanoTime() - startTime);
            comparisonsOfN.add((long) compare);

           // System.out.println(String.format("%d\t%d\t%d", n, comparisonsOfN.get(i), timesOfN.get(i)));
        }

        // sprawdzenie master theorem
        if(checkMasterTheorem(comparisonsOfN)){
            System.out.println("Master Theorem zachodzi dla ilości porównań");
        } else {
            System.out.println("Master Theorem nie zachodzi dla ilości porównań");
        }
        if(checkMasterTheorem(timesOfN)){
            System.out.println("Master Theorem zachodzi dla czasu działania");
        } else {
            System.out.println("Master Theorem nie zachodzi dla czasu działania");
        }

        // wyliczenie O(1)
        System.out.println("Wyliczona wartość czynnika O(1) dla ilości porównań wynosi około " + countBigO(comparisonsOfN));
        System.out.println("Wyliczona wartość czynnika O(1) dla czasu działania wynosi około " + countBigO(timesOfN) + "ns");
    }


    /** funckja generująca posortowaną rosnącą listę o rozmiarze n */
    private static int[] generateRandomList(int n) {
        Random random = new Random();
        int[] array = new int[n];
        array[0] = random.nextInt(10);
        for(int i = 1; i<array.length; i++){
            array[i] = array[i-1] + random.nextInt(10);
        }
        return array;
    }

    private static int checkIfExists(int[] array, int begin, int end, int value){
        int mid = begin + (end-begin)/2;
        if(begin >= end){
            return 0;
        }
        if(array[mid] == value){
            compare++;
            return 1;
        }
        if(array[mid] > value){
            compare++;
            return checkIfExists(array, begin, mid-1, value);
        } else {
            return checkIfExists(array, mid+1, end, value);
        }
    }

    private static boolean checkMasterTheorem(ArrayList<Long> array) {
        /**
         * zgodnie z Masters Theorem
         * T(n) = a * T(n/b) + f(n),
         * gdzie: a = 1, b = 2, f(n) = O(1)
         * czyli:
         * T(n) = T(n/2) + O(1)
         *
         * c = log_b(a) = log_2(1) = 0
         * f(n) = O(n^d) => d = 0
         *
         * c = d, więc zgodnie z Masters Theorem:
         * T(n) = O(n^c log(n)) = O(log(n))
         */

        // sinteje takie k, że dla każdego n:
        // k * log(n) > wartości z Array

        long max = 0;
        long min = array.get(0);
        for(long l : array){
            max = Math.max(max, l);
            min = Math.min(min, l);
        }
        long nmax = 1000 * (1 + array.get(array.indexOf(max)));
        long nmin = 1000 * (1 + array.get(array.indexOf(min)));
        int k = 1;
        while((k * Math.log(nmin) <= max || k * Math.log(nmax) <= max) && k < Math.pow(2, 30)){ k *= 10; }
        for(int i=array.size()-1; i>=0; i--){
            if(k * Math.log(array.get(i)) <= array.get(i)){
                return false;
            }
        }
        return true;
    }

    private static float countBigO(ArrayList<Long> array) {
        /**
         * T(n) = T(n/2) + O(1)
         * rozpisując otrzymam:
         * T(n) = O(1) + ( O(1) + ( O(1) + ( O(1) + ( .. ) ) ) )
         * T(n) = O(1) * log(n)
         */
        int sum = 0;
        for(int i=0; i<array.size(); i++){
            int n = 1000 * (1 + i);
            int bigO = (int) (array.get(i) / Math.log(n));
            sum += bigO;
        }
        return ((float)sum) / array.size();
    }

}
