package Heap;

import Graph.Vertex;

public class Item {

    private Vertex value;
    private int priority;
    private Item parent, sibling, child;

    public Item(Vertex v, int p){
        this.value = v;
        this.priority = p;
        this.parent = null;
        this.sibling = null;
        this.child = null;
    }

    // getters
    public Vertex getValue() {
        return this.value;
    }
    public int getPriority() {
        return this.priority;
    }
    public Item getSibling() {
        return this.sibling;
    }
    public Item getChild() {
        return this.child;
    }

    // setters
    public void setParent(Item parent) {
        this.parent = parent;
    }
    public void setSibling(Item sibling) {
        this.sibling = sibling;
    }
    public void setChild(Item child) {
        this.child = child;
    }

}
