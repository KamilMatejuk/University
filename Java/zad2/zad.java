/* Kamil Matejuk */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class zad {

    public static boolean GENERATE_STATS = true;

    /** main - wywolanie odpowiedniego rodzaju sortowania */
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Wrong number of parameters\n");
            return;
        }
        if(!args[0].equals("-r") && !args[0].equals("-p") && !args[0].equals("-check")){
            System.out.println("Unknown parameters\n");
            return;
        }
        if (args[0].equals("-check")){
            checkStats();
            return;
        }
        GENERATE_STATS = true;
        int[] data = readData();
        int n = data[0]; // długość danych
        int k = data[1]; // numer szukanej statystyki pozycyjnej (ma zwracać k-ty najmniejszy element)
        int[] array = new int[0];
        if (args[0].equals("-r")){
            array = generateRandomList(n);
        } else if (args[0].equals("-p")){
            array = generateRandomPermutation(n);
        }

        if(zad2.GENERATE_STATS) System.err.println(Arrays.toString(array));
        if(zad2.GENERATE_STATS) System.err.println("k=" + k);

        int kMinimalElement;
        if(k == 1){
            kMinimalElement = minimum(array); // zlożoność liniowa
            showResult(array, kMinimalElement);
        } else if(k == n){
            kMinimalElement = maximum(array); // zlożoność liniowa
            showResult(array, kMinimalElement);
        } else {
            //normalne algorytmy select i randomselect
            // stats
            if(zad2.GENERATE_STATS) System.err.println("Log algorytmu SELECT");
            kMinimalElement = new Select(array.clone()).run(k);
            showResult(array, kMinimalElement);

            // stats
            if(zad2.GENERATE_STATS) System.err.println("Log algorytmu RANDOM SELECT");
            kMinimalElement = new RandomSelect(array.clone()).run(k);
            showResult(array, kMinimalElement);
        }


    }

    private static void checkStats() {
        /** dla tych samych danych wejsciowych, by wyciągnąć wnioski na temat minimalnej i maksymalnej liczby porównań
         dla obu algorytmów, policz również srednią i odchylenie standardowe dla zebranych  statystyk.*/

        GENERATE_STATS = false;
        int n = 1000;
        int k = new Random().nextInt(n-2)+2;
        try {
            check(n, k, "Select");
            check(n, k, "RandomSelect");
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static void check(int n, int k, String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int[] porownania = new int[100];
        int sumaPorownan = 0;
        for(int i=0; i< 100; i++){
            int[] array = generateRandomPermutation(n);

            Class className = Class.forName(name);
            Class[] constructorArguments = new Class[1];
            constructorArguments[0] = int[].class;
            SelectAlgorythm algorythm = (SelectAlgorythm) className.getDeclaredConstructor(constructorArguments).newInstance(array);

            algorythm.run(k);
            porownania[i] = algorythm.compare;
            sumaPorownan += algorythm.compare;
        }
        double srednia = sumaPorownan / 100.0;
        int minLiczbaPorownan = (int) Math.pow(2,30);
        int maxLiczbaPorownan = 0;
        int sumaWariancji = 0;
        for(int p : porownania){
            sumaWariancji += Math.pow((p - srednia), 2);
            minLiczbaPorownan = Math.min(minLiczbaPorownan, p);
            maxLiczbaPorownan = Math.max(maxLiczbaPorownan, p);
        }
        System.out.println("Liczba porównań algorytmu " + name + " dla n=1000:");
        System.out.println("Minimalna: " + minLiczbaPorownan);
        System.out.println("Maksymalna: " + maxLiczbaPorownan);
        System.out.println("Średnia: " + srednia);
        System.out.println("Odchylenie standardowe: " + Math.sqrt(sumaWariancji / 100.0));
    }

    /** wyszukiwanie dla k = 1 */
    private static int minimum(int[] array) {
        // stats
        if(zad2.GENERATE_STATS) System.err.println("Log algorytmu liniowego minimum dla k=1");
        int min = array[0];
        for(int a : array){
            min = Math.min(min,a);
            if(zad2.GENERATE_STATS) System.err.println(String.format("Porównanie %d z %d", min, a));
        }
        // stats
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba porównań: %d", array.length-1));
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba przestawień: %d", 0));
        return min;
    }

    /** wyszukiwanie dla k = n */
    private static int maximum(int[] array) {
        // stats
        if(zad2.GENERATE_STATS) System.err.println("Log algorytmu liniowego maximum dla k=n");
        int max = array[0];
        for(int a : array){
            max = Math.max(max,a);
            if(zad2.GENERATE_STATS) System.err.println(String.format("Porównanie %d z %d", max, a));
        }
        // stats
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba porównań: %d", array.length-1));
        if(zad2.GENERATE_STATS) System.err.println(String.format("Liczba przestawień: %d", 0));
        return max;
    }

    /** funckja generująca listę o rozmiarze n */
    private static int[] generateRandomList(int n) {
        Random random = new Random();
        int[] array = new int[n];
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt();
        }
        return array;
    }

    /** funckja generująca permutację n */
    private static int[] generateRandomPermutation(int n) {
        Random random = new Random();
        int[] array = new int[n];
        Arrays.fill(array, 0);
        ArrayList<Integer> unusedPositions = new ArrayList<>();
        for(int i=0; i<n; i++){
            unusedPositions.add(i);
        }
        for(int i=1; i<=n; i++){
            int pos = unusedPositions.get(random.nextInt(unusedPositions.size()));
            unusedPositions.remove(unusedPositions.indexOf(pos));
            array[pos] = i;
        }
        return array;
    }

    /** funkcja wczytująca tabelę do posortowania */
    private static int[] readData() {
        // [n,k]
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[2];
        for (int i = 0; i < 2; i++) {
            try {
                array[i] = Integer.parseInt(scanner.next());
            } catch (NumberFormatException nfe){
                array[i] = 0;
            }
        }
        if(Arrays.asList(array).contains(0) || array[0]<1 || array[1]<1 || array[1]>array[0]){
            System.out.println("n,k should be integers such as: 1 <= n and 1 <= k <= n");
            System.exit(0);
        }
        return array;
    }

    /** wyświetlenie znalezionego elementu w odpowiedni sposób */
    private static void showResult(int[] array, int kMinimalElement) {
        for(int i : array){
            if(i == kMinimalElement){
                System.out.print("["+kMinimalElement+"] ");
            } else {
                System.out.print(i+" ");
            }
        }
        System.out.print("\n");
    }

}
