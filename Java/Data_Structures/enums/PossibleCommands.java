package enums;

import java.util.ArrayList;
import java.util.List;


/** Enum odpowiadający za przekształcanie wpisanych koemnd na wykonywane funkcje.
 * W przypadku dodania funckji wystarczy dodać opcję do enuma, zamiast nadpisywać klasę main
 **/

public enum PossibleCommands {
    insert("insert", true),
    load("load", true),
    delete("delete", true),
    find("find", true),
    min("min", false),
    max("max", false),
    succesor("succesor", true),
    inorder("inorder", false),
    show("tempPrint", false),
    exit("printStats", false);

    public String command;
    boolean arguments;

    PossibleCommands(String s, boolean b){
        this.command = s;
        this.arguments = b;
    }

    // zwraca listę wszystkich komend
    public static List<String> getCommands(){
        List<String> v = new ArrayList<>();
        PossibleCommands[] arr = PossibleCommands.values();
        for(PossibleCommands c : arr){
            v.add(c.toString());
        }
        return v;
    }

    // zwraca listę komend które do wywołania potrzebują argumentów
    public static List<String> getCommandsWithArgs(){
        List<String> v = new ArrayList<>();
        PossibleCommands[] arr = PossibleCommands.values();
        for(PossibleCommands c : arr){
            if(c.arguments) {
                v.add(c.toString());
            }
        }
        return v;
    }

    // zwraca listę komend które można wywołać baz argumentów
    public static List<String> getCommandsWithoutArgs(){
        List<String> v = new ArrayList<>();
        PossibleCommands[] arr = PossibleCommands.values();
        for(PossibleCommands c : arr){
            if(!c.arguments) {
                v.add(c.toString());
            }
        }
        return v;
    }

}
