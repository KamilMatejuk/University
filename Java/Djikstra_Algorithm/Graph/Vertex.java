package Graph;

import java.util.*;

public class Vertex implements Comparable<Vertex> {

    int number;
    double distance;
    List<Vertex> shortestPath;
    List<Double> shortestPathEdges;
    Map<Vertex, Double> adjacentNodes;


    public Vertex(int i){
        this.number = i;
        this.distance = Integer.MAX_VALUE;
        this.shortestPath = new ArrayList<>();
        this.shortestPath.add(this);
        this.shortestPathEdges = new ArrayList<>();
        this.adjacentNodes = new HashMap<>();
    }

    // getters
    public int getNumber() {
        return this.number;
    }
    public double getDistance() {
        return distance;
    }
    public List<Vertex> getShortestPath() {
        return shortestPath;
    }
    public List<Double> getShortestPathEdges() {
        return shortestPathEdges;
    }
    public Map<Vertex, Double> getAdjacentNodes() {
        return adjacentNodes;
    }

    // setters
    public void setDistance(double distance) {
        this.distance = distance;
    }


    public void addAdjecent(Vertex end, double weight){
        this.adjacentNodes.put(end, weight);
    }
    public void addToPath(Vertex v, double weight){
        this.shortestPath = new ArrayList<>();
        this.shortestPath.addAll(v.getShortestPath());
        this.shortestPath.add(this);

        this.shortestPathEdges = new ArrayList<>();
        this.shortestPathEdges.addAll(v.getShortestPathEdges());
        this.shortestPathEdges.add(weight);
    }

    public String getPath(){
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < this.shortestPath.size(); i++) {
            Vertex v = this.shortestPath.get(i);
            path.append(v.getNumber()).append(" ");
            if(i < this.shortestPathEdges.size()){
                double edge = this.shortestPathEdges.get(i);
                path.append(String.format("(%.2f) ", edge));
            }
        }
        return path.toString();
    }

    @Override
    public int compareTo(Vertex vertex) {
        return Integer.compare(this.number, vertex.number);
    }
}
