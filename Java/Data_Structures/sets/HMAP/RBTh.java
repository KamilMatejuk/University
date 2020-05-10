package sets.HMAP;

import sets.CustomPrinter;

import java.awt.*;
import java.util.ArrayList;

/** Klasa odpowiadająca za strukturę drzewa Czerowno - Czarnego przystosowanego pod Mape Hashującą, oraz jej funckje */

public class RBTh extends InternalStructure {

    RBThnode root;

    @Override
    public void insert(String s, int h) {
        RBThnode node = root;
        RBThnode prevRBThnode = root;
        RBThnode newRBThnode = new RBThnode(s, h);
        if (root == null) {
            root = newRBThnode;
            newRBThnode.setColor(Color.BLACK);
            return;
        }
        while (node != null) {
            if (node.isGreaterThen(s)) {
                prevRBThnode = node;
                node = node.getLeft();
            } else {
                prevRBThnode = node;
                node = node.getRight();
            }
        }
        if (prevRBThnode.isGreaterThen(s)) {
            prevRBThnode.setLeft(newRBThnode);
            newRBThnode.setParent(prevRBThnode);
        } else {
            prevRBThnode.setRight(newRBThnode);
            newRBThnode.setParent(prevRBThnode);
        }
        if(newRBThnode.getParent().getParent() != null){
            fixInsert(newRBThnode);
        }
        size++;
    }

    @Override
    public void delete(String s) {
        RBThnode node = getNode(s);
        if(node == null){
            // nie ma takiego elementu w drzewie
            return;
        }
        RBThnode x, y;
        if(node.getLeft() == null || node.getRight() == null) {
            y = node;
        } else {
            y = getNext(node);
        }
        if(y.getLeft() != null){
            x = y.getLeft();
        } else {
            x = y.getRight();
        }
        if(x != null) {
            x.setParent(y.getParent());
        }
        if(y.getParent() == null){
            this.root = x;
        } else {
            if(y == y.getParent().getLeft()){
                y.getParent().setLeft(x);
            } else {
                y.getParent().setRight(x);
            }
        }
        if(y != node){
            node.setValue(y.getValue());
        }
        if(y.getColor() == Color.BLACK){
            fixDelete(x);
        }
        size--;
    }

    @Override
    public void find(String s) {
        if(getNode(s) != null){
            // znalezino
            System.out.println("1");
        } else {
            // nie ma takiego elementu w drzewie
            System.out.println("0");
        }
    }

    @Override
    public void show() {
        CustomPrinter.printNode(this.root);
    }

    @Override
    public ArrayList<String> iterate() {
        return getSubelemnts(this.root);
    }

    private ArrayList<String> getSubelemnts(RBThnode node){
        if(node == null){
            return new ArrayList<>();
        } else {
            ArrayList<String> val = new ArrayList<>();
            val.addAll(getSubelemnts(node.getLeft()));
            val.add(node.getValue());
            val.addAll(getSubelemnts(node.getRight()));
            return val;
        }
    }


    void fixInsert(RBThnode node) {
        // dopóki rodzic jest czerwony
        while (this.root != node && node.getParent().getColor() == Color.RED) {
            if(node.getGrandparent() != null) {
                // rodzic jest lewym dzieckiem dziadka
                if (node.getParent() == node.getGrandparent().getLeft()) {
                    // wujek
                    RBThnode uncle = node.getGrandparent().getRight();
                    // wujek jest czerowny - przekolorować
                    if (uncle != null && uncle.getColor() == Color.RED) {
                        uncle.setColor(Color.BLACK);
                        node.getParent().setColor(Color.BLACK);
                        node.getGrandparent().setColor(Color.RED);
                        node = node.getGrandparent();
                    } else {
                        if (node == node.getParent().getRight()) {
                            node = node.getParent();
                            leftRotate(node);
                        }
                        node.getParent().setColor(Color.BLACK);
                        node.getGrandparent().setColor(Color.RED);
                        rightRotate(node.getGrandparent());
                    }
                    // rodzic jest prawym dzieckiem dziadka
                } else {
                    // wujek
                    RBThnode uncle = node.getGrandparent().getLeft();
                    // wujek jest czerowny - przekolorować
                    if (uncle != null && uncle.getColor() == Color.RED) {
                        uncle.setColor(Color.BLACK);
                        node.getParent().setColor(Color.BLACK);
                        node.getGrandparent().setColor(Color.RED);
                        node = node.getGrandparent();
                    } else {
                        if (node == node.getParent().getLeft()) {
                            node = node.getParent();
                            rightRotate(node);
                        }
                        node.getParent().setColor(Color.BLACK);
                        node.getGrandparent().setColor(Color.RED);
                        leftRotate(node.getGrandparent());
                    }
                }
            }
        }
        this.root.setColor(Color.BLACK);
    }

    void fixDelete(RBThnode node){
        while(node != null && this.root != node && node.getColor() == Color.BLACK){
            if(node == node.getParent().getLeft()){
                RBThnode w = node.getParent().getRight();
                if(w.getColor() == Color.RED){
                    w.setColor(Color.BLACK);
                    node.getParent().setColor(Color.RED);
                    leftRotate(node.getParent());
                    w = node.getParent().getRight();
                }
                if(w.getLeft().getColor() == Color.BLACK && w.getRight().getColor() == Color.BLACK){
                    w.setColor(Color.RED);
                    node = node.getParent();
                } else {
                    if(w.getRight().getColor() == Color.BLACK){
                        w.getLeft().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        rightRotate(w);
                        w = node.getParent().getRight();
                    }
                    w.setColor(node.getParent().getColor());
                    node.getParent().setColor(Color.BLACK);
                    w.getRight().setColor(Color.BLACK);
                    leftRotate(node.getParent());
                    node = this.root;
                }
            } else {
                RBThnode w = node.getParent().getLeft();
                if(w.getColor() == Color.RED){
                    w.setColor(Color.BLACK);
                    node.getParent().setColor(Color.RED);
                    rightRotate(node.getParent());
                    w = node.getParent().getLeft();
                }
                if(w.getRight().getColor() == Color.BLACK && w.getLeft().getColor() == Color.BLACK){
                    w.setColor(Color.RED);
                    node = node.getParent();
                } else {
                    if(w.getLeft().getColor() == Color.BLACK){
                        w.getRight().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        leftRotate(w);
                        w = node.getParent().getLeft();
                    }
                    w.setColor(node.getParent().getColor());
                    node.getParent().setColor(Color.BLACK);
                    w.getLeft().setColor(Color.BLACK);
                    rightRotate(node.getParent());
                    node = this.root;
                }
            }
        }
        if(node != null) {
            node.setColor(Color.BLACK);
        }
    }

    void leftRotate(RBThnode node) {
        RBThnode rightChild = node.getRight();
        if(rightChild != null) {
            node.setRight(rightChild.getLeft());
            if (rightChild.getLeft() != null) {
                rightChild.getLeft().setParent(node);
            }
            rightChild.setParent(node.getParent());
            if (node.getParent() == null) {
                this.root = rightChild;
            } else if (node == node.getParent().getLeft()) {
                node.getParent().setLeft(rightChild);
            } else {
                node.getParent().setRight(rightChild);
            }
            rightChild.setLeft(node);
            node.setParent(rightChild);
        }
    }

    void rightRotate(RBThnode node) {
        RBThnode leftChild = node.getLeft();
        if(leftChild != null) {
            node.setLeft(leftChild.getRight());
            if (leftChild.getRight() != null) {
                leftChild.getRight().setParent(node);
            }
            leftChild.setParent(node.getParent());
            if (node.getParent() == null) {
                this.root = leftChild;
            } else if (node == node.getParent().getRight()) {
                node.getParent().setRight(leftChild);
            } else {
                node.getParent().setLeft(leftChild);
            }
            leftChild.setRight(node);
            node.setParent(leftChild);
        }
    }

    RBThnode getNode(String s){
        RBThnode node = this.root;
        while (node != null && !node.getValue().equals(s)) {
            if (node.isGreaterThen(s)) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node;
    }

    RBThnode getNext(RBThnode node){
        // ma prawe dziecko - następnik
        if(node.getRight() != null){
            RBThnode n = node.getRight();
            while(n.getLeft() != null){
                n = n.getLeft();
            }
            return n;
        }
        // nie ma prawego dziecka - trzeba iść w górę
        while(node.getParent() != null && node.getParent().getLeft() != node){
            node = node.getParent();
        }
        node = node.getParent();
        return node;
    }

}
