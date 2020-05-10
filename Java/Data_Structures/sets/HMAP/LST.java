package sets.HMAP;

import java.util.ArrayList;

/** Klasa odpowiadająca za strukturę listy jednokierunkowej, oraz jej funckje */

public class LST extends InternalStructure {

    LSTnode root;

    @Override
    public void insert(String s, int h){
        LSTnode newNode = new LSTnode(s, h);
        if(this.root == null){
            this.root = newNode;
        } else {
            LSTnode n = this.root;
            while(n.getNext() != null){
                n = n.getNext();
            }
            n.setNext(newNode);
        }
        size++;
    }

    @Override
    public void delete(String s){
        LSTnode n = this.root;
        LSTnode prev = this.root;
        while(n != null && !n.getValue().equals(s)){
            prev = n;
            n = n.getNext();
        }
        if(n != null){
            prev.setNext(n.getNext());
        }
        size--;
    }

    @Override
    public void find(String s){
        LSTnode n = this.root;
        while(n != null && !n.getValue().equals(s)){
            n = n.getNext();
        }
        if(n != null){
            // znalezino
            System.out.println("1");
        } else {
            // nie ma takiego elementu w drzewie
            System.out.println("0");
        }
    }

    @Override
    public void show() {
        LSTnode n = this.root;
        ArrayList<String> val = new ArrayList<>();
        while(n != null){
            val.add(n.getValue());
            n = n.getNext();
        }
        System.out.println(val);
    }

    @Override
    public ArrayList<String> iterate() {
        LSTnode n = this.root;
        ArrayList<String> list = new ArrayList<>();
        while(n != null){
            list.add(n.getValue());
            n = n.getNext();
        }
        return list;
    }

}
