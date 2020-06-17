public class Item {

    private int value;
    private int priority;
    private Item parent, sibling, child;

    public Item(int v, int p){
        this.value = v;
        this.priority = p;
        this.parent = null;
        this.sibling = null;
        this.child = null;
    }

    // getters
    public int getValue() {
        return value;
    }
    public int getPriority() {
        return priority;
    }
    public Item getSibling() {
        return sibling;
    }
    public Item getChild() {
        return child;
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
