package sets.RBT;

import sets.Node;

import java.awt.*;

public class RBTnode extends Node {

    String value;
    Color color;
    RBTnode left;
    RBTnode right;
    RBTnode parent;

    public RBTnode(String v) {
        this.value = v;
        this.color = Color.RED;
    }

    // getters
    public String getValue(){
        return this.value;
    }
    public Color getColor(){
        return this.color;
    }
    public RBTnode getLeft(){
        return this.left;
    }
    public RBTnode getRight(){
        return this.right;
    }
    public RBTnode getParent(){
        return this.parent;
    }
    public RBTnode getGrandparent(){
        return (this.parent != null ? this.parent.parent : null);
    }

    // setters
     void setValue(String s){
        this.value = s;
    }
     void setColor(Color c){
        this.color = c;
    }
     void setLeft(RBTnode l){
        this.left = l;
    }
     void setRight(RBTnode r){
        this.right = r;
    }
     void setParent(RBTnode p){this.parent = p;}
     boolean isGreaterThen(String s){
         String valueL = this.value.toLowerCase();
         String sL = s.toLowerCase();
         if(valueL.equals(sL)){
             return this.value.compareTo(s) <= 0;
         } else {
             return valueL.compareTo(sL) > 0;
         }
    }

}
