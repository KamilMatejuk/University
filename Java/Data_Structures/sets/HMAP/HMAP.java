package sets.HMAP;

import sets.DataSet;

import java.util.ArrayList;

import static java.lang.Math.abs;

/** Klasa odpowiadająca za strukturę mapy hashującej, oraz jej funckje */

public class HMAP extends DataSet {

    // poniżej (nt) dane w komórkach przechowywane są jako lista jednokierunkwa,
    // natomiast przy przekroczeniu (nt) zamienia się w drzewo Czerwono - Czarne

    int m = 16;     // rozmiar tabeli
    int nt = 8;     // rozmiar przy którym zmienia się listę na drzewo binarne
    ArrayList<InternalStructure> array = new ArrayList<>(m);

    public HMAP(){
        super();
        for(int i = 0; i < m; i++) {
            array.add(new LST());
        }
    }

    @Override
    public void insert(String s) {
        // pozbyć się znaków z poza a-zA-Z
        s = s.replaceAll("[^a-zA-Z]+", "");
        if(!s.equals("")) {
            int pos = hash(s) % m;
            InternalStructure is = array.get(pos);
            is.insert(s, hash(s));

            if(is instanceof LST && is.size > nt){
                RBTh newRBTh = convertListToTree((LST) is);
                array.set(pos, newRBTh);
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
        int pos = hash(s) % m;
        InternalStructure is = array.get(pos);
        is.delete(s);

        if(is instanceof RBTh && is.size <= nt){
            LST newLST = convertTreeToList((RBTh) is);
            array.set(pos, newLST);
        }
        numberOfElements--;
        deleteCounter++;
    }

    @Override
    public void find(String s) {
        int pos = hash(s) % m;
        InternalStructure is = array.get(pos);
        is.find(s);
        findCounter++;
    }

    @Override
    public void tempPrint(){
        for(InternalStructure is : array){
            is.show();
        }
    }


    private int hash(String s){
        // postać s[0]*31^(n-1) + s[1]*31^(n-2) + … + s[n-1]
        int h = 1;
        int n = s.length();
        for(int i = 0; i < n; i++){
            h = h * 31 + s.charAt(i);
        }
        return abs(h);
    }

    private RBTh convertListToTree(LST lst) {
        RBTh newRBTh = new RBTh();
        for(String s : lst.iterate()){
            newRBTh.insert(s, hash(s));
        }
        return newRBTh;
    }

    private LST convertTreeToList(RBTh tree) {
        LST newLST = new LST();
        for(String s : tree.iterate()){
            newLST.insert(s, hash(s));
        }
        return newLST;
    }
}
