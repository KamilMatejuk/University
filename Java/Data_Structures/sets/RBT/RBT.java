package sets.RBT;

import sets.CustomPrinter;
import sets.DataSet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Klasa odpowiadająca za strukturę drzewa binarnego Czarno - Czerwonego, oraz jej funckje */

public class RBT extends DataSet {
    
    RBTnode root;

    @Override
    public void insert(String s) {
        // pozbyć się znaków z poza a-zA-Z
        s = s.replaceAll("[^a-zA-Z]+", "");
        if(!s.equals("")) {
            //wstawić
            RBTnode node = root;
            RBTnode prevRBTnode = root;
            RBTnode newRBTnode = new RBTnode(s);
            if (root == null) {
                root = newRBTnode;
                newRBTnode.setColor(Color.BLACK);
                return;
            }
            while (node != null) {
                if (node.isGreaterThen(s)) {
                    prevRBTnode = node;
                    node = node.getLeft();
                } else {
                    prevRBTnode = node;
                    node = node.getRight();
                }
            }
            if (prevRBTnode.isGreaterThen(s)) {
                prevRBTnode.setLeft(newRBTnode);
                newRBTnode.setParent(prevRBTnode);
            } else {
                prevRBTnode.setRight(newRBTnode);
                newRBTnode.setParent(prevRBTnode);
            }
            if(newRBTnode.getParent().getParent() != null){
                fixInsert(newRBTnode);
            }
            numberOfElements++;
            if (numberOfElements > maxNumberOfElements) {
                maxNumberOfElements = numberOfElements;
            }
            insertCounter++;

        }
    }

    @Override
    public void delete(String s) {
        RBTnode node = getNode(s);
        if(node == null){
            // nie ma takiego elementu w drzewie
            return;
        }
        RBTnode x, y;
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
        numberOfElements--;
        deleteCounter++;
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
        findCounter++;
    }

    @Override
    public void min() {
        if(this.root == null){
            // puste drzewo
            System.out.println("");
            return;
        }
        RBTnode node = this.root;
        while(node.getLeft() != null){
            node = node.getLeft();
        }
        System.out.println(node.getValue());
        minCounter++;
    }

    @Override
    public void max() {
        if(this.root == null){
            // puste drzewo
            System.out.println("");
            return;
        }
        RBTnode node = this.root;
        while(node.getRight() != null){
            node = node.getRight();
        }
        System.out.println(node.getValue());
        maxCounter++;
    }

    @Override
    public void succesor(String s) {
        RBTnode node = getNode(s);
        // nie ma takiego elementu w drzewie albo nie ma następnika
        if (node == null || getNext(node) == null) {
            System.out.println("");
        } else {
            System.out.println(getNext(node).getValue());
        }
        succesorCounter++;
    }

    @Override
    public void inorder() {
        showSubtree(this.root);
        System.out.print("\n");
        insertCounter++;
    }


    /* funkcje pomocnicze */
    public void showSubtree(RBTnode node){
        if(node == null){
            System.out.print("");
        } else {
            if(node.getLeft() != null) showSubtree(node.getLeft());
            System.out.print(node.getValue() + " ");
            if(node.getRight() != null) showSubtree(node.getRight());
        }
    }

    RBTnode getNode(String s){
        RBTnode node = this.root;
        while (node != null && !node.getValue().equals(s)) {
            if (node.isGreaterThen(s)) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node;
    }

    RBTnode getNext(RBTnode node){
        // ma prawe dziecko - następnik
        if(node.getRight() != null){
            RBTnode n = node.getRight();
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

    void fixInsert(RBTnode node) {
        // dopóki rodzic jest czerwony
        while (this.root != node && node.getParent().getColor() == Color.RED) {
            if(node.getGrandparent() != null) {
                // rodzic jest lewym dzieckiem dziadka
                if (node.getParent() == node.getGrandparent().getLeft()) {
                    // wujek
                    RBTnode uncle = node.getGrandparent().getRight();
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
                    RBTnode uncle = node.getGrandparent().getLeft();
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

    void fixDelete(RBTnode node){
        while(node != null && this.root != node && node.getColor() == Color.BLACK){
            if(node == node.getParent().getLeft()){
                RBTnode w = node.getParent().getRight();
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
                RBTnode w = node.getParent().getLeft();
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

    void leftRotate(RBTnode node) {
        RBTnode rightChild = node.getRight();
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

    void rightRotate(RBTnode node) {
        RBTnode leftChild = node.getLeft();
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

    // wyświetlanie
    @Override
    public void tempPrint(){
        CustomPrinter.printNode(this.root);
    }
}
