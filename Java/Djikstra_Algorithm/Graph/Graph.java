package Graph;

import Heap.CustomHeap;

import java.util.*;

public class Graph {

    int numberOfVertices;
    int numberOfEdges;
    ArrayList<Vertex> vertices;
    Set<Vertex> settledVertices;

    public Graph(int v, int e){
        this.numberOfVertices = v;
        this.numberOfEdges = e;
        this.vertices = new ArrayList<>();
        for (int i = 1; i <= v; i++) {
            vertices.add(new Vertex(i));
        }
    }

    public void addEdge(int s, int e, double w){
        Vertex start = null, end = null;
        for(Vertex v : vertices) {
            if(v.getNumber() == s) {
                start = v;
            } else if(v.getNumber() == e){
                end = v;
            }
        }
        if(start != null && end != null){
            start.addAdjecent(end, w);
        }
    }

    public void showAdjecent(){
        for(Vertex v : this.vertices){
            System.out.print(v.getNumber() + ": { ");
            for(Map.Entry<Vertex, Double> neighbour : v.getAdjacentNodes().entrySet()){
                System.out.print(
                        neighbour.getKey().getNumber() + " (" + neighbour.getValue() + "), ");
            }
            System.out.print("} \n");
        }
    }

    public void DijkstraAlgorithm(int v){
        // find v
        Vertex source = null;
        for(Vertex ver : vertices) {
            if(ver.getNumber() == v) {
                source = ver;
            }
        }
        if(source == null){
            return;
        }

        // queue
        CustomHeap queue = new CustomHeap();
        queue.insert(source, 0);

        // algorithm
        source.setDistance(0);
        this.settledVertices = new HashSet<Vertex>();
        while(!queue.isEmpty()){
            Vertex curr = queue.pop();
            checkNeighbours(curr, queue);
            this.settledVertices.add(curr);
        }

        // result
        for(Vertex ver : this.vertices){
            // id_celu waga_drogi
            System.out.println(String.format("%d %.5f", ver.getNumber(), ver.getDistance()));
            // trasa z wagami i wierzchołkami
            System.err.println(ver.getPath());
        }
    }

    private void checkNeighbours(Vertex v, CustomHeap queue) {
        for(Map.Entry<Vertex, Double> neighbour : v.getAdjacentNodes().entrySet()){
            Vertex vertex = neighbour.getKey();
            double weight = neighbour.getValue();
            if(!this.settledVertices.contains(vertex)){
                double newDistance = v.getDistance() + weight;
                if(newDistance < vertex.getDistance()){
                    vertex.setDistance(newDistance);
                    vertex.addToPath(v, weight);
                }
                queue.insert(vertex, 0);
            }
        }
    }

}
