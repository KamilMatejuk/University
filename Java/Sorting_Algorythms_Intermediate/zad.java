/* Kamil Matejuk */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class zad {

    public static final boolean STANDARD_OUT_ERR = false;

    //todo dodać zmienną regulującą czy jest wypisywane na standardowe wyjście błedu czy nie

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

        String comparator = arguments[1];
        Sorter sorter = chooseSorter(arguments[0], comparator);

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
            if(arguments[2].equals("radixRandomDependency.txt")){
                generateStatsOverRandomization(sorter, fileOutputStream, k);
            } else {
                generateStats(sorter, fileOutputStream, k);
            }
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

    static Sorter chooseSorter(String arg, String comp){
        try {
            String name = SorterOptions.valueOf(arg).sorter;
            Class className = Class.forName(name);
            Class[] constructorArguments = new Class[1];
            constructorArguments[0] = String.class;
            Object obj = className.getDeclaredConstructor(constructorArguments).newInstance(comp);
            return (Sorter) obj;
        } catch (IllegalArgumentException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex){
            //domyslnie zwraca Insertion Sorta jeżeli nie znajdzie nazwy w enumie
            return new InsertionSort(comp);
        }
    }

    /** wywołanie programu z danymi losowymi i efektami wypisanymi do pliku */
    private static void generateStats(Sorter sorter, FileOutputStream fileOutputStream, int k) throws IOException {
        ArrayList<Integer> values = new ArrayList<>();
        for(int i = 10; i < 1000000; i *= 10){
            values.add(i);
            values.add(5*i);
        }
        values.add(1000000);
        for(int i : values){
            System.out.println(i);
            int[] array = generateRandomArray(i);
            for(int j = 0; j < k; j++){
                int[] a = array.clone();
                sorter.sort(a);
                long[] stats = sorter.getStats();
                // rozmiar tabeli, ilość porównań, ilość przesunięć, czas, pamięć
                String textline = String.format("%d\t%d\t%d\t%d\t%d\n", a.length, stats[0], stats[1], stats[2], stats[3]);
                fileOutputStream.write(textline.getBytes());
            }
        }
        fileOutputStream.close();
    }

    /** wywołanie programu z danymi losowymi o zmiennym zakresie losowości i efektami wypisanymi do pliku */
    private static void generateStatsOverRandomization(Sorter sorter, FileOutputStream fileOutputStream, int k) throws IOException {
        ArrayList<Integer> ranges = new ArrayList<>();
        for(int i = 10; i <= 1000000; i *= 10){
            ranges.add(i);
        }
        for(int i : ranges){
            int[] array = generateRandomArrayFromRange(10000, i);
            for(int j = 0; j < k; j++){
                int[] a = array.clone();
                sorter.sort(a);
                long[] stats = sorter.getStats();
                // zakres losowości danych, ilość porównań, ilość przesunięć, czas
                String textline = String.format("%d\t%d\n", i, stats[2]);
                fileOutputStream.write(textline.getBytes());
            }
        }
        fileOutputStream.close();
    }

    private static int[] generateRandomArray(int n) {
        Random random = new Random();
        int[] array = new int[n];
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt();
        }
        return array;
    }
    private static int[] generateRandomArrayFromRange(int n, int max){
        Random random = new Random();
        int[] array = new int[n];
        for(int i = 0; i<array.length; i++){
            array[i] = random.nextInt(max);
        }
        return array;
    }

    /** wywołane zwykłego sortowania pojedynczego, z danymi zczytanymi ze standardowego wejścia */
    private static void usualSort(Sorter sorter) {
        int[] arr = readData();
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
        String type = "none";
        int typeindex = Arrays.asList(args).indexOf("--type");
        if (typeindex + 1 < args.length && SorterOptions.getValues().contains(args[typeindex + 1])){
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
        return new String[]{type, comp, statfile, statk};
    }

    /** funkcja wczytująca tabelę do posortowania */
    private static int[] readData() {
        Scanner scanner = new Scanner(System.in);
        try {
            int n = Integer.parseInt(scanner.nextLine());
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(scanner.next());
            }
            return array;
        } catch (NumberFormatException e){
            System.out.println("Input should be numbers");
            return new int[0];
        }
    }

    /** funckja wypisująca dane (rozmiar tabeli, tabela)
     * na standardowe wyjście pod koniec sortowania */
    private static void logStatsOut(int[] array) {
        System.out.println(String.format("Liczba elementów: %d", array.length));
        System.out.println(Arrays.toString(array));
    }
}
