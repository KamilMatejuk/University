/* Kamil Matejuk */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class zad {

    public static final boolean STANDARD_OUT_ERR = false;

    /** main - wywolanie odpowiedniego rodzaju sortowania */
    public static void main(String[] args) throws IOException {
        if(args.length != 4 && args.length != 7){
            System.out.println("Wrong number of parameters\n");
            return;
        }
        String[] arguments = chooseTypeandComp(args);
        if (arguments[0].equals("none") || arguments[1].equals("none")){
            System.out.println("Unknown parameters\n");
            return;
        }

        Sorter sorter = null;
        String comparator = arguments[1];
        if(arguments[0].equals("insert"))       sorter = new InsertionSort(comparator);
        if(arguments[0].equals("merge"))        sorter = new MergeSort(comparator);
        if(arguments[0].equals("quick"))        sorter = new QuickSort(comparator);
        if(arguments[0].equals("dualpivot"))    sorter = new QuickDualPivotSort(comparator);
        if(arguments[0].equals("hybrid"))       sorter = new HybridSort(comparator);

        try {
            int k = Integer.parseInt(arguments[3]);
            if(arguments[2].equals("none")){
                // nie zdefiniowany parametr --stat
                usualSort(sorter);
                return;
            }
            File yourFile = new File(arguments[2]);
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream fileOutputStream = new FileOutputStream(yourFile, false);
            if (Arrays.asList(args).contains("--stat"))     generateStats(sorter, fileOutputStream, k, Integer.class);
            else if (Arrays.asList(args).contains("--smallstat"))     generateSmallStats(sorter, fileOutputStream, k, Integer.class);
            else if (Arrays.asList(args).contains("--stringstat"))     generateStats(sorter, fileOutputStream, k, String.class);
            else if (Arrays.asList(args).contains("--stringsmallstat"))     generateSmallStats(sorter, fileOutputStream, k, String.class);
        } catch (NumberFormatException e){
            // zła wartość k, albo nie zdefiniowany parametr --stat
            usualSort(sorter);
        } catch (FileNotFoundException e){
            // nie da sie stworzyć pliku o takiej nazwie
            System.out.println("Cannot create file \""+arguments[2]+"\"");
            return;
        } catch (IOException e){
            // nie mozna zapisać do pliku
            System.out.println("Cannot write to file");
            return;
        }

    }

    /** wywołanie programu z danymi losowymi i efektami wypisanymi do pliku - n: 100, 200, ..., 1000 */
    private static void generateStats(Sorter sorter, FileOutputStream fileOutputStream, int k, Object typ) throws IOException {
        for(int i = 100; i <= 1000; i += 100){
            Comparable[] array = null;
            if(typ == Integer.class) array = generateRandomIntArray(i);
            if(typ == String.class) array = generateRandomStringArray(i);
            for(int j = 0; j < k; j++){
                Comparable[] a = array.clone();
                sorter.sort(a);
                long[] stats = sorter.getStats();
                // rozmiar tabeli, ilość porównań, ilość przesunięć, czas
                String textline = String.format("%d\t%d\t%d\t%d\n", a.length, stats[0], stats[1], stats[2]);
                fileOutputStream.write(textline.getBytes());
            }
        }
        fileOutputStream.close();
    }

    /** wywołanie programu z danymi losowymi i efektami wypisanymi do pliku - n: 2, 3, ..., 100 */
    private static void generateSmallStats(Sorter sorter, FileOutputStream fileOutputStream, int k, Object typ) throws IOException {
        for(int i = 2; i <= 100; i++){
            Comparable[] array = null;
            if(typ == Integer.class) array = generateRandomIntArray(i);
            if(typ == String.class) array = generateRandomStringArray(i);
            for(int j = 0; j < k; j++){
                Comparable[] a = array.clone();
                sorter.sort(a);
                long[] stats = sorter.getStats();
                // rozmiar tabeli, ilość porównań, ilość przesunięć, czas
                String textline = String.format("%d\t%d\t%d\t%d\n", a.length, stats[0], stats[1], stats[2]);
                fileOutputStream.write(textline.getBytes());
            }
        }
        fileOutputStream.close();
    }
    

    private static Comparable[] generateRandomIntArray(int n) {
        Random random = new Random();
        Comparable[] array = new Comparable[n];
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt();
        }
        return array;
    }
    private static Comparable[] generateRandomStringArray(int n) {
        Random random = new Random();
        Comparable[] array = new Comparable[n];
        for(int i = 0; i<array.length; i++){
            byte[] word = new byte[7];
            random.nextBytes(word);
            array[i] = new String(word, Charset.forName("UTF-8"));
        }
        return array;
    }

    /** wywołane zwykłego sortowania pojedynczego, z danymi zczytanymi ze standardowego wejścia */
    private static void usualSort(Sorter sorter) {
        Comparable[] arr = readData();
        sorter.sort(arr);
        long[] stats = sorter.getStats();
        System.err.println(String.format("Liczba porównań: %d", stats[0]));
        System.err.println(String.format("Liczba przestawień: %d", stats[1]));
        System.err.println(String.format("Czas działania: %d ns", stats[2]));
        sorter.assertRight();
        logStatsOut(sorter.getResults());
    }


    /** funkcja zczytująca argumenty i zwracająca rodzaj sortowania oraz porównanie */
    static String[] chooseTypeandComp(String[] args){
        // --type
        String[] possibleTypes = new String[]{"insert", "merge", "quick", "dualpivot", "hybrid"};
        String type = "none";
        int typeindex = Arrays.asList(args).indexOf("--type");
        if (typeindex + 1 < args.length && Arrays.asList(possibleTypes).contains(args[typeindex + 1])){
            type = args[typeindex + 1];
        }

        // --comp
        String[] possibleComparators = new String[]{"<=", ">="};
        String comp = "none";
        int compindex = Arrays.asList(args).indexOf("--comp");
        if (compindex + 1 < args.length && Arrays.asList(possibleComparators).contains(args[compindex + 1])){
            comp = args[compindex + 1];
        }

        // --stat
        String statfile = "none";
        String statk = "none";
        int statindex = Arrays.asList(args).indexOf("--stat");
        if (statindex + 2 < args.length && statindex != -1){
            statfile = args[statindex + 1];
            statk = args[statindex + 2];
        }
        statindex = Arrays.asList(args).indexOf("--smallstat");
        if (statindex + 2 < args.length && statindex != -1){
            statfile = args[statindex + 1];
            statk = args[statindex + 2];
        }
        statindex = Arrays.asList(args).indexOf("--stringstat");
        if (statindex + 2 < args.length && statindex != -1){
            statfile = args[statindex + 1];
            statk = args[statindex + 2];
        }
        statindex = Arrays.asList(args).indexOf("--stringsmallstat");
        if (statindex + 2 < args.length && statindex != -1){
            statfile = args[statindex + 1];
            statk = args[statindex + 2];
        }
        return new String[]{type, comp, statfile, statk};
    }

    /** funkcja wczytująca tabelę do posortowania
     * @return*/
    private static Comparable[] readData() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        Comparable[] array = new Comparable[n];
        for(int i = 0; i < n; i++){
//            array[i] = Integer.parseInt(scanner.next());
            array[i] = scanner.next();
        }
        return array;
    }

    /** funckja wypisująca dane (rozmiar tabeli, tabela)
     * na standardowe wyjście pod koniec sortowania
     * @param array*/
    private static void logStatsOut(Comparable[] array) {
        System.out.println(String.format("Liczba elementów: %d", array.length));
        System.out.println(Arrays.toString(array));
    }
}
