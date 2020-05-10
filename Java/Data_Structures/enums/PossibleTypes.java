package enums;

import java.util.ArrayList;
import java.util.List;

/** Enum odpowiadający za przekształcanie wpisanej flagi --type na rodzaj struktury.
 * W przypadku dodania struktury wystarczy dodać opcję do enuma, zamiast nadpisywać klasę main
 **/

public enum PossibleTypes {
    bst("sets.BST.BST"),
    rbt("sets.RBT.RBT"),
    hmap("sets.HMAP.HMAP");

    public String set;

    PossibleTypes(String s) {
        this.set = s;
    }

    // zwraca listę wszystkich struktur
    public static List<String> getValues(){
        List<String> v = new ArrayList<>();
        PossibleTypes[] arr = PossibleTypes.values();
        for(PossibleTypes s : arr){
            v.add(s.toString());
        }
        return v;
    }
}
