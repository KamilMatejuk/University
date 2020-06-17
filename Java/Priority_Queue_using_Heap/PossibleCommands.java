import java.util.ArrayList;
import java.util.List;

/** Enum odpowiadający za przekształcanie wpisanej komendy na wywoływaną metodę
 **/

public enum PossibleCommands {
    insert("insert", 2),
    empty("checkIfEmpty", 0),
    top("maxPriority", 0),
    pop("pop", 0),
    priority("changePriority", 2),
    print("show", 0);


    public String command;
    int arguments;

    PossibleCommands(String s, int n){
        this.command = s;
        this.arguments = n;
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

    // zwraca listę komend które do wywołania potrzebują 'number' argumentów
    public static List<String> getCommandsWithArg(int number){
        List<String> v = new ArrayList<>();
        PossibleCommands[] arr = PossibleCommands.values();
        for(PossibleCommands c : arr){
            if(c.arguments == number) {
                v.add(c.toString());
            }
        }
        return v;
    }

}
