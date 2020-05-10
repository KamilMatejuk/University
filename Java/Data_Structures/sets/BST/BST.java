package sets.BST;

import sets.CustomPrinter;
import sets.DataSet;

import java.util.*;

/** Klasa odpowiadająca za strukturę podstawowego drzewa binarnego, oraz jej funckje */

public class BST extends DataSet {

    BSTnode root = null;

    @Override
    public void insert(String s) {
        // pozbyć się znaków z poza a-zA-Z
        s = s.replaceAll("[^a-zA-Z]+", "");
        if(!s.equals("")) {
            //wstawić
            BSTnode node = this.root;
            BSTnode prevBSTnode = this.root;
            BSTnode newBSTnode = new BSTnode(s);
            if (this.root == null) {
                this.root = newBSTnode;
            } else {
                while (node != null) {
                    if (node.isGreaterThen(s)) {
                        prevBSTnode = node;
                        node = node.getLeft();
                    } else {
                        prevBSTnode = node;
                        node = node.getRight();
                    }
                }
                if (prevBSTnode.isGreaterThen(s)) {
                    prevBSTnode.setLeft(newBSTnode);
                    newBSTnode.setParent(prevBSTnode);
                } else {
                    prevBSTnode.setRight(newBSTnode);
                    newBSTnode.setParent(prevBSTnode);
                }

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
        if(getNode(s) == null){
            // nie ma takiego elementu w drzewie
            return;
        }
        BSTnode node = getNode(s);
        BSTnode parent = node.getParent();

        // node nie ma dzieci
        if(node.childrenNumber() == 0) {
            if(parent == null){
                this.root = null;
            }
            else if (parent.getLeft() == node) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
        // node ma jedno dziecko
        else if(node.childrenNumber() == 1) {
            if(node.getLeft() != null){
                if(parent == null){
                    this.root = node.getLeft();
                    if(node.getLeft() != null) node.getLeft().setParent(null);
                }
                else if(parent.getLeft() == node){
                    parent.setLeft(node.getLeft());
                    if(node.getLeft() != null) node.getLeft().setParent(parent);
                }
                else if(parent.getRight() == node){
                    parent.setRight(node.getLeft());
                    if(node.getLeft() != null) node.getLeft().setParent(parent);
                }
            }
            else if(node.getRight() != null){
                if(parent == null){
                    this.root = node.getRight();
                    if(node.getRight() != null) node.getRight().setParent(null);
                }
                else if(parent.getLeft() == node){
                    parent.setLeft(node.getRight());
                    if(node.getRight() != null) node.getRight().setParent(parent);
                }
                else if(parent.getRight() == node){
                    parent.setRight(node.getRight());
                    if(node.getRight() != null) node.getRight().setParent(parent);
                }
            }
        }
        // node ma oba dzieci
        else if(node.childrenNumber() == 2){
            // znaleźć natępnik i podmienić
            BSTnode prevBSTnode = node;
            BSTnode n = node.getRight();
            while(n.getLeft() != null){
                prevBSTnode = n;
                n = n.getLeft();
            }
            // usunąć nastepnik
            if(prevBSTnode.getLeft() == n)       prevBSTnode.setLeft(null);
            else if(prevBSTnode.getRight() == n) prevBSTnode.setRight(null);
            //podmienic znaleziony element na nastepnik
            if(parent == null){
                this.root = n;
                n.setRight(node.getRight());
                n.setLeft(node.getLeft());
                n.setParent(null);
                if(node.getRight() != null) node.getRight().setParent(n);
                if(node.getLeft() != null) node.getLeft().setParent(n);
            }
            else if(parent.getLeft() == node){
                parent.setLeft(n);
                n.setRight(node.getRight());
                n.setLeft(node.getLeft());
                n.setParent(parent);
                if(node.getRight() != null) node.getRight().setParent(n);
            }
            else if(parent.getRight() == node){
                parent.setRight(n);
                n.setRight(node.getRight());
                n.setLeft(node.getLeft());
                n.setParent(parent);
                if(node.getLeft() != null) node.getLeft().setParent(n);
            }
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
        BSTnode node = this.root;
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
        BSTnode node = this.root;
        while(node.getRight() != null){
            node = node.getRight();
        }
        System.out.println(node.getValue());
        maxCounter++;
    }

    @Override
    public void succesor(String s) {
        BSTnode node = getNode(s);
        // nie ma takiego elementu w drzewie albo nie ma następnika
        if(node == null){
            System.out.println("");
            return;
        }
        // ma prawe dziecko - następnik
        if(node.getRight() != null){
            BSTnode n = node.getRight();
            while(n.getLeft() != null){
                n = n.getLeft();
            }
            System.out.println(n.getValue());
            return;
        }
        // nie ma prawego dziecka - trzeba iść w górę
        while(node.getParent() != null && node.getParent().getLeft() != node){
            node = node.getParent();
        }
        node = node.getParent();
        if(node == null){
            // nie ma takiego elementu w drzewie albo nie ma następnika
            System.out.println("");
        } else {
            System.out.println(node.getValue());
        }
        succesorCounter++;
    }

    @Override
    public void inorder() {
        showSubtree(this.root);
        System.out.print("\n");
        inorderCounter++;
    }

    /* funkcje pomocnicze */
    public void showSubtree(BSTnode node){
        if(node == null){
            System.out.print("");
        } else {
            if(node.getLeft() != null) showSubtree(node.getLeft());
            System.out.print(node.getValue() + " ");
            if(node.getRight() != null) showSubtree(node.getRight());
        }
    }

    BSTnode getNode(String s){
        BSTnode node = this.root;
        while (node != null && !node.getValue().equals(s)) {
            if (node.isGreaterThen(s)) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node;
    }


    // wyświetlanie
    @Override
    public void tempPrint(){
        CustomPrinter.printNode(this.root);
    }
}
