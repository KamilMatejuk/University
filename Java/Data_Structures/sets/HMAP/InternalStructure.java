package sets.HMAP;

import java.util.ArrayList;

/** Klasa używana do przechowywania wartości w Hash-Mapie - tj. albo lista albo drzewo */

public abstract class InternalStructure {

    public int size;

    InternalStructure(){
        this.size = 0;
    }

    public abstract void insert(String s, int hash);
    public abstract void delete(String s);
    public abstract void find(String s);
    public abstract void show();
    public abstract ArrayList<String> iterate();
}
