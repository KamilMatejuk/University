package sets.HMAP;

import sets.Node;

import java.awt.*;

public class RBThnode extends Node {

    int hash;
    String value;
    Color color;
    RBThnode left;
    RBThnode right;
    RBThnode parent;

    RBThnode(String v, int h){
        this.value = v;
        this.hash = h;
    }

    // getters
    int getHash(){
        return this.hash;
    }
    public String getValue(){
        return this.value;
    }
    public Color getColor(){
        return this.color;
    }
    public RBThnode getLeft(){
        return this.left;
    }
    public RBThnode getRight(){
        return this.right;
    }
    public RBThnode getParent(){
        return this.parent;
    }
    public RBThnode getGrandparent(){
        return (this.parent != null ? this.parent.parent : null);
    }


    // setters
    void setHash(int h){
        this.hash = h;
    }
    public void setValue(String s){
        this.value = s;
    }
    public void setColor(Color c){
        this.color = c;
    }
    public void setLeft(RBThnode l){
        this.left = l;
    }
    public void setRight(RBThnode r){
        this.right = r;
    }
    public void setParent(RBThnode p){this.parent = p;}
    public boolean isGreaterThen(String s){
        String valueL = this.value.toLowerCase();
        String sL = s.toLowerCase();
        if(valueL.equals(sL)){
            return this.value.compareTo(s) <= 0;
        } else {
            return valueL.compareTo(sL) > 0;
        }
    }
}
