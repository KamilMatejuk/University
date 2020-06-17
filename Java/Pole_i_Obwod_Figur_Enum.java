/* Kamil Matejuk */
import java.util.*;

/*Pierwsza klasa powinna zawierać elementy obsługujące figury z jednym parametrem
(kolo, kwadrat, pięciokąt foremny, sze±ciokąt foremny)*/
enum OneParameter{
    Kolo{
        public double ObliczObwod(int x){
            return 3.14159*2*x;
        }
        public double ObliczPole(int x){
            return 3.14159*x*x;
        }
    },
    Kwadrat{
        public double ObliczObwod(int x){
            return 4*x;
        }
        public double ObliczPole(int x){
            return x*x;
        }
    },
    Pieciokat{
        public double ObliczObwod(int x){
            return 5*x;
        }
        public double ObliczPole(int x){
            return 1.72*x*x;
        }
    },
    Szesciokat{
        public double ObliczObwod(int x){
            return 6*x;
        }
        public double ObliczPole(int x){
            return 2.59805*x*x;
        }
    };
    public abstract double ObliczObwod(int x);
    public abstract double ObliczPole(int x);
}
/*Druga klasa powinna zawiera¢ elementy obsługujące figury z dwoma parametrami
(prostokąt, romb)*/
enum TwoParameters{
    Prostokat{
        //x-bok1, y-bok2
        public double ObliczObwod(int x, int y){
            return 2*(x+y);
        }
        public double ObliczPole(int x, int y){
            return x*y;
        }
    },
    Romb{
        //x-bok, y-kat
        public double ObliczObwod(int x, int y){
            return 4*x;
        }
        public double ObliczPole(int x, int y){
            return x*x*Math.sin(Math.toRadians(y));
        }
    };
    public abstract double ObliczPole(int x, int y);
    public abstract double ObliczObwod(int x, int y);
}

public class Pole_i_Obwod_Figur_Enum {

    public static void main(String[] args) {

        //skrawdzic czy istnieje args[0]
        if(args.length == 0){
            System.out.println("Musisz podać jakieś figury");
            return;
        }
        //sprawdzenie czy wszystkie argumenty to liczby naturalne i czy jest ich dobra ilosc
        int sum = 1;
        for(int i=0; i<args[0].length(); i++){
            switch (args[0].charAt(i)) {
                case 'o': sum++; break;
                case 'c': sum += 5; break;
                case 'p': sum++; break;
                case 's': sum++; break;
                default: System.out.println("Błędny format - nie ma takiej figury jak: " + args[0].charAt(i)); return;
            }
        }
        if(args.length == sum){
            for(int i=1; i<args.length; i++){
                try {
                    int x = Integer.parseInt(args[i]);
                    if(x<=0){
                        System.out.println("Argumenty nie są liczbami naturalnymi");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Argumenty nie są liczbami naturalnymi");
                    return;
                }
            }
        } else { 
            System.out.println("Nieprawidłowa ilość argumentów");
            return;
        }

        int j=1; //polozenie argumentu
        for(int i=0; i<args[0].length(); i++){
            switch (args[0].charAt(i)) {
                case 'o': //koło
                    int r = Integer.parseInt(args[j]);
                    System.out.println("Pole .......... " + OneParameter.Kolo.ObliczPole(r));
                    System.out.println("Obwod ......... " + OneParameter.Kolo.ObliczObwod(r));
                    j++;
                    break;
                case 'c': //czworokąt
                    int b1 = Integer.parseInt(args[j]);
                    int b2 = Integer.parseInt(args[j+1]);
                    int b3 = Integer.parseInt(args[j+2]);
                    int b4 = Integer.parseInt(args[j+3]);
                    int kat = Integer.parseInt(args[j+4]);
                    if (kat == 90) {
                        if((b1==b2) && (b1==b3) && (b1==b4)){
                            System.out.println("Pole .......... " + OneParameter.Kwadrat.ObliczPole(b1));
                            System.out.println("Obwod ......... " + OneParameter.Kwadrat.ObliczObwod(b1));
                        } else {
                            if(b1 != b2){
                                System.out.println("Pole .......... " + TwoParameters.Prostokat.ObliczPole(b1,b2));
                                System.out.println("Obwod ......... " + TwoParameters.Prostokat.ObliczObwod(b1,b2));
                            }
                            else if(b1 != b3){
                                System.out.println("Pole .......... " + TwoParameters.Prostokat.ObliczPole(b1,b3));
                                System.out.println("Obwod ......... " + TwoParameters.Prostokat.ObliczObwod(b1,b3));
                            }
                            else if(b1 != b4){
                                System.out.println("Pole .......... " + TwoParameters.Prostokat.ObliczPole(b1,b4));
                                System.out.println("Obwod ......... " + TwoParameters.Prostokat.ObliczObwod(b1,b4));
                            }
                            else {
                                System.out.println("Pole .......... nie wiadomo");
                                System.out.println("Obwod ......... " + TwoParameters.Prostokat.ObliczObwod(b1,b3));
                            }
                        }
                    } else if((b1==b2) && (b1==b3) && (b1==b4)){
                        System.out.println("Pole .......... " + TwoParameters.Prostokat.ObliczPole(b1,kat));
                        System.out.println("Obwod ......... " + TwoParameters.Prostokat.ObliczObwod(b1,kat));
                    } else {
                        System.out.println("nie znam takiej figury");
                    }
                    j += 5;
                    break;
                case 'p': //pięciokąt
                    int bok = Integer.parseInt(args[j]);
                    System.out.println("Pole .......... " + OneParameter.Pieciokat.ObliczPole(bok));
                    System.out.println("Obwod ......... " + OneParameter.Pieciokat.ObliczObwod(bok));
                    j++;
                    break;
                case 's': //sześciokąt
                    bok = Integer.parseInt(args[j]);
                    System.out.println("Pole .......... " + OneParameter.Szesciokat.ObliczPole(bok));
                    System.out.println("Obwod ......... " + OneParameter.Szesciokat.ObliczObwod(bok));
                    j++;
                    break;
            }
        }
    }
}