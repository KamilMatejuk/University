import Graph.Graph;

import java.util.*;

public class main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        createGraph();
        float time = (float) ((System.nanoTime() - startTime) / 1000000.0);
        System.out.println(String.format("Czas działania programu %f ms", time));
    }

    public static void createGraph(){
        try {
            Scanner scanner = new Scanner(System.in);
            int V_num = scanner.nextInt();
            int E_num = scanner.nextInt();
            Graph graph = new Graph(V_num, E_num);
            for (int i = 0; i < E_num; i++) {
                int startV = scanner.nextInt();
                int endV = scanner.nextInt();
                double weight = scanner.nextDouble();
                 graph.addEdge(startV, endV, weight);
            }
            // graph.showAdjecent();
            int v = scanner.nextInt();
            graph.DijkstraAlgorithm(v);

        } catch (NumberFormatException | IndexOutOfBoundsException ex){
            System.out.println("Wrong data format");
            ex.printStackTrace();
            System.exit(1);
        } catch (NoSuchElementException ex){
            System.out.println("To few elements given");
            ex.printStackTrace();
            System.exit(1);
        }
    }

}
