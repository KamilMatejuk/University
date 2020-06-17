import java.util.ArrayList;

/** Klasa implementująca kopiec dwumianowy z podstawowymi funkcjami
 **/

public class CustomHeap {

    private Item startingItem;
    ArrayList<Item> changedItems;

    public CustomHeap(){
        this.startingItem = null;
    }

    public void insert(int value, int priority) {
        if(priority < 0){
            return;
        }
        if (this.startingItem == null){
            this.startingItem = new Item(value, priority);
        } else {
            unionItems(new Item(value, priority));
        }
    }

    public void checkIfEmpty() {
        int empty = (this.startingItem == null ? 1 : 0);
        System.out.println(empty);
    }

    public void maxPriority() {
        if (this.startingItem == null){
            System.out.println("");
        } else {
            Item top = findMinItem();
            String item = "(" + top.getValue() + ", " + top.getPriority() + ")";
            System.out.println(item);
        }
    }

    public void pop() {
        if (this.startingItem == null){
            System.out.println("");
        } else {
            Item top = findMinItem();
            String item = "(" + top.getValue() + ", " + top.getPriority() + ")";
            System.out.println(item);
            removeMin();
        }
    }

    public void changePriority(int value, int priority) {
        this.changedItems = new ArrayList<>();
        findItems(value, this.startingItem);
        for(Item i : changedItems){
            remove(i);
        }
        for(Item i : changedItems){
            int p = Math.min(i.getPriority(), priority);
            insert(i.getValue(), p);
        }
    }

    public void show() {
        displayHeap(this.startingItem);
        System.out.print("\n");
    }
    
    
    /* funkcje pomocnicze */

    private void merge(Item item){
        Item temp1 = this.startingItem, temp2 = item;

        while ((temp1 != null) && (temp2 != null)){
            if (temp1.getPriority() == temp2.getPriority()){
                Item tmp = temp2;
                temp2 = temp2.getSibling();
                tmp.setSibling(temp1.getSibling());
                temp1.setSibling(tmp);
                temp1 = tmp.getSibling();

            } else if (temp1.getPriority() < temp2.getPriority()){
                if ((temp1.getSibling() == null) || (temp1.getSibling().getPriority() > temp2.getPriority())){
                    Item tmp = temp2;
                    temp2 = temp2.getSibling();
                    tmp.setSibling(temp1.getSibling());
                    temp1.setSibling(tmp);
                    temp1 = tmp.getSibling();
                } else {
                    temp1 = temp1.getSibling();
                }

            } else {
                Item tmp = temp1;
                temp1 = temp2;
                temp2 = temp2.getSibling();
                temp1.setSibling(tmp);
                if (tmp == this.startingItem) {
                    this.startingItem = temp1;
                }
            }
        }
        if (temp1 == null){
            temp1 = this.startingItem;
            while (temp1.getSibling() != null) {
                temp1 = temp1.getSibling();
            }
            temp1.setSibling(temp2);
        }
    }

    private void unionItems(Item binHeap){
        merge(binHeap);

        Item prevTemp = null, temp = this.startingItem, nextTemp = this.startingItem.getSibling();

        while (nextTemp != null) {
            if ((temp.getPriority() != nextTemp.getPriority()) || ((nextTemp.getSibling() != null)
                    && (nextTemp.getSibling().getPriority() == temp.getPriority()))) {
                prevTemp = temp;
                temp = nextTemp;
            } else {
                if (temp.getValue() <= nextTemp.getValue()) {
                    temp.setSibling(nextTemp.getSibling());
                    nextTemp.setParent(temp);
                    nextTemp.setSibling(temp.getChild());
                    temp.setChild(nextTemp);
                } else {
                    if (prevTemp == null) {
                        this.startingItem = nextTemp;
                    } else {
                        prevTemp.setSibling(nextTemp);
                    }
                    temp.setParent(nextTemp);
                    temp.setSibling(nextTemp.getChild());
                    nextTemp.setChild(temp);
                    temp = nextTemp;
                }
            }
            nextTemp = temp.getSibling();
        }
    }

    private void removeMin(){
        if (this.startingItem == null)
            return;
        Item minNode = findMinItem();
        remove(minNode);
    }

    private void remove(Item item){
        Item temp = this.startingItem, prevTemp = null;
        while (temp != null && item != null && temp.getValue() != item.getValue()){
            prevTemp = temp;
            temp = temp.getSibling();
        }

        if (prevTemp == null){
            this.startingItem = (temp != null ? temp.getSibling() : null);
        } else {
            prevTemp.setSibling((temp != null ? temp.getSibling() : null));
        }

        temp = (temp != null ? temp.getChild() : null);
        Item fakeNode = temp;

        while (temp != null){
            temp.setParent(null);
            temp = temp.getSibling();
        }

        if ((this.startingItem == null) && (fakeNode != null)){
            this.startingItem = reverse(fakeNode, null);
        }
        else if(this.startingItem != null && fakeNode != null){
            unionItems(reverse(fakeNode, null));
        }
    }

    private Item reverse(Item i1, Item i2){
        Item ret = (i1.getSibling() == null ? i1 : reverse(i1.getSibling(), i1));
        i1.setSibling(i2);
        return ret;
    }

    private Item findMinItem(){
        Item curr = this.startingItem;
        Item minItem = this.startingItem;
        while (curr != null){
            if (curr.getPriority() < minItem.getPriority()){
                minItem = curr;
            }
            curr = curr.getSibling();
        }
        return minItem;
    }

    private Item findItems(int value, Item startItem){
        Item temp = startItem, node = null;

        while (temp != null){
            if (temp.getValue() == value){
                node = temp;
                this.changedItems.add(node);
            }
            if (temp.getChild() == null) {
                temp = temp.getSibling();
            } else {
                node = findItems(value, temp.getChild());
                if (node == null) {
                    temp = temp.getSibling();
                } else {
                    break;
                }
            }
        }
        return node;
    }

    private void displayHeap(Item item){
        if (item != null) {
            displayHeap(item.getChild());
            String item_name = "(" + item.getValue() + ", " + item.getPriority() + ")";
            System.out.print(item_name + " ");
            displayHeap(item.getSibling());
        }
    }
}
