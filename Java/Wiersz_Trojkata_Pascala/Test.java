/* Kamil Matejuk */
import java.util.*;

/* Stwórz publiczną klasę WierszTrojkataPascala posiadającą jeden konstruktor
WierszTrojkataPascala(int n), który tworzy tablicę liczb odpowiedniego rozmiaru i oblicza
w niej n-ty wiersz trójkąta Pascala.
Następnie zaimplementuj publiczną metodę wspolczynnik(int m) zwracającą wartość m-tego
elementu wiersza trójkąta Pascala. Funkcja ta powinna prawidaowo dziaaa¢ dla liczb od 0 do n.

Dodaj odpowiednie własne wyjątki dla tej klasy, np. w przypadku użycia liczb ujemnych czy
większych od argumentu konstruktora.

Stwórz klasę Test której metoda statyczna main dla pierwszego argumentu wywoaania stworzy
wiersz trójkąta Pascala a następnie dla kolejnych argumentów wypisze warto±ci tego wiersza.
W przypadku błędnego pierwszego argumentu program wypisze komunikat błędu i skończy pracę.
W przypadku błednych pozostaaych argumentów wypisze, zamiast wartości, informację o tym błędziee
 i przejdzie do obliczeń dla następnego argumentu.*/

public class Test {
    public static WierszTrojkataPascala W;

    public static void main(String[] args) {
        //sprawdzenie czy nr wiersza jest prawidłowy (liczba naturalna)
        try {
            int n = Integer.parseInt(args[0]);
            if(n >= 0){
                W = new WierszTrojkataPascala(n);
            } else {
                System.out.println(n + " - nieprawidłowy nr wiersza");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println(args[0] + " - nieprawidłowy nr wiersza");
            return;
        }
        //sprawdzenie czy dane są liczbami
        for(int i=1; i<args.length; i++){
            try {
                int n = Integer.parseInt(args[i]);
                W.wspolczynnik(n);
            } catch (NumberFormatException ex) {
                System.out.println(args[i] + " - nieprawidlowa dana");
            }
        }
    }
}