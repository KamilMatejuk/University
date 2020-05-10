package sets.HMAP;

public class LSTnode {

    String value;
    int hash;
    LSTnode next;

    LSTnode(String v, int h){
        this.value = v;
        this.hash = h;
    }

    // getters
    String getValue(){
        return this.value;
    }
    int getHash(){
        return this.hash;
    }
    LSTnode getNext(){
        return this.next;
    }

    // setters
    void setValue(String s){
        this.value = s;
    }
    void setHash(int h){
        this.hash = h;
    }
    void setNext(LSTnode n){
        this.next = n;
    }

}
