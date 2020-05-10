package sets.BST;

import sets.Node;

public class BSTnode extends Node {

    String value;
    BSTnode left;
    BSTnode right;
    BSTnode parent;

    BSTnode(String v){
        this.value = v;
    }

    // getters
    public String getValue(){
        return this.value;
    }
    public BSTnode getLeft(){
        return this.left;
    }
    public BSTnode getRight(){
        return this.right;
    }
    public BSTnode getParent(){
        return this.parent;
    }

    // setters
    void setLeft(BSTnode l){
        this.left = l;
    }
    void setRight(BSTnode r){
        this.right = r;
    }
    void setParent(BSTnode p){
        this.parent = p;
    }

    // functions
    boolean isGreaterThen(String s){
        String valueL = this.value.toLowerCase();
        String sL = s.toLowerCase();
        if(valueL.equals(sL)){
            return this.value.compareTo(s) <= 0;
        } else {
            return valueL.compareTo(sL) > 0;
        }
    }
    int childrenNumber(){
        int n = 0;
        if(left != null) n++;
        if(right != null) n++;
        return n;
    }
}