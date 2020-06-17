/* Kamil Matejuk */
import java.util.*;


interface FiguraInterface{
    void obwod();
    void pole();
}

abstract class Pole_i_Obwod_Figur implements FiguraInterface {
}

class Kolo extends Figura{
    private int promien;
    public Kolo(int r){
        this.promien = r;
    }

    public void pole(){
        System.out.format("Pole Koła: ............ %.5f\n", promien*promien*3.14159);
    }
    public void obwod(){
        System.out.format("Obwód Koła: ........... %.5f\n", 2*promien*3.14159);
    }
}
class Pieciokat extends Figura{
    private int bok;
    public Pieciokat(int b){
        this.bok = b;
    }

    public void pole(){
        System.out.format("Pole Pięciokąta: ...... %.5f\n", bok*bok*1.72);
    }
    public void obwod(){
        System.out.format("Obwód Pięciokąta: ..... %d\n", bok*5);
    }
}
class Szesciokat extends Figura{
    private int bok;
    public Szesciokat(int b){
        this.bok = b;
    }

    public void pole(){
        System.out.format("Pole Sześciokata: ..... %.5f\n", bok*bok*2.59805);
    }
    public void obwod(){
        System.out.format("Obwód Sześciokata: .... %d\n", bok*6);
    }
}
abstract class Czworokat extends Figura{}

class Kwadrat extends Czworokat{
    private int bok;
    public Kwadrat(int bok){
        this.bok = bok;
    }

    public void pole(){
        System.out.format("Pole Kwadratu: ........ %d\n", bok*bok);
    }
    public void obwod(){
        System.out.format("Obwód Kwadratu: ....... %d\n", bok*4);
    }
}
class Prostokat extends Czworokat{
    private int b1,b2;
    public Prostokat(int b1, int b2){
        this.b1 = b1;
        this.b2 = b2;
    }

    public void pole(){
        System.out.format("Pole Prostokąta: ...... %d\n", b1*b2);
    }
    public void obwod(){
        System.out.format("Obwód Prostokąta: ..... %d\n", 2*(b1+b2));
    }
}
class Romb extends Czworokat{
    private int bok,kat;
    public Romb(int bok, int kat){
        this.bok = bok;
        this.kat = kat;
    }

    public void pole(){
        System.out.format("Pole Rombu: ........... %.5f\n", bok*bok*Math.sin(Math.toRadians(kat)));
    }
    public void obwod(){
        System.out.format("Obwód Rombu:........... %d\n", 4*bok);
    }
}


public class figury {

    public static void main(String[] args) {

        //skrawdzic czy istnieje args[0]
        Figura [] tabela_figur;
        if(args.length > 0){
            tabela_figur = new Figura[args[0].length()];
        } else {
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
        int k=0; //ilosc obiektow w tabeli
        for(int i=0; i<args[0].length(); i++){
            switch (args[0].charAt(i)) {
                case 'o': //koło
                    int r = Integer.parseInt(args[j]);
                    tabela_figur[i] = new Kolo(r);
                    j++;
                    k++;
                    break;
                case 'c': //czworokąt
                    int b1 = Integer.parseInt(args[j]);
                    int b2 = Integer.parseInt(args[j+1]);
                    int b3 = Integer.parseInt(args[j+2]);
                    int b4 = Integer.parseInt(args[j+3]);
                    int kat = Integer.parseInt(args[j+4]);
                    if (kat == 90) {
                        if((b1==b2) && (b1==b3) && (b1==b4)){
                            tabela_figur[i] = new Kwadrat(b1);
                        } else {
                            if(b1 != b2){tabela_figur[i] = new Prostokat(b1,b2);}
                            else {tabela_figur[i] = new Prostokat(b1,b3);}
                        }
                    } else if((b1==b2) && (b1==b3) && (b1==b4)){
                        tabela_figur[i] = new Romb(b1,kat);
                    }
                    j += 5;
                    k++;
                    break;
                case 'p': //pięciokąt
                    int bok = Integer.parseInt(args[j]);
                    tabela_figur[i] = new Pieciokat(bok);
                    j++;
                    k++;
                    break;
                case 's': //sześciokąt
                    bok = Integer.parseInt(args[j]);
                    tabela_figur[i] = new Szesciokat(bok);
                    j++;
                    k++;
                    break;
            }
        }
        for(int a=0; a<k; a++){
            tabela_figur[a].pole();
            tabela_figur[a].obwod();
        }
    }
}