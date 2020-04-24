import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa odpowiadająca za kontener przechowujący całą symulację, pozwalający rysować.
 * @author Kamil Matejuk
 * @version 1.0
 */

public class Pole extends JComponent {

    public int size;
    public int width;
    public int height;
    public int amount_rabbits;
    public static CopyOnWriteArrayList<Rabbit> Rabbits = new CopyOnWriteArrayList<>();
    public Wolf wolf = null;
    public JFrame frame;
    public Random random;


    /**
     * Konstruktor tworzący losowy początkowy układ zwierząt na planszy, oraz tworzy jeden ogólny generator pseudolodowy.
     * @param s rozmiar jednego pola planszy
     * @param w ilosc pól poziomo
     * @param h ilosc pól pionowo
     * @param r ilosc zajęcy
     * @param fr okno w którym to sie wyświetla
     */
    public Pole(int s, int w, int h, int r, JFrame fr){
        this.size = s;
        this.width = w;
        this.height = h;
        this.amount_rabbits = r;
        this.frame = fr;
        Rabbits.clear();
        random = new Random();

        //losowanie polozenia poczatkowego zwiarzatek
        int [] cords = random.ints(1, width*height).distinct().limit(amount_rabbits+1).toArray();
        for(int i=0; i<cords.length; i++){
            int x = (cords[i] % width) * size;
            int y = ((cords[i]-x/size)/width) * size;
            if(i==0){
                wolf = new Wolf(x, y, size, this, frame);
                wolf.start();
            } else {
                Rabbit rab = new Rabbit(x, y, size, this);
                rab.start();
                Rabbits.add(rab);
            }
        }
    }

    /**
     * Rysowaie po kolei wilka i każdego zająca z ArrayListy, oraz siatki.
     * @param g grafika
     * @see Graphics
     */
    public void paint (Graphics g){
        super.paint(g);

        //poczatkowe ustanienie
        try {
            wolf.paint(g);
            CopyOnWriteArrayList<Rabbit> temp = Rabbits;
            for (Rabbit r : temp) {
                r.paint(g);
            }
        } catch (ConcurrentModificationException ex){
            ex.printStackTrace();
        }
        //siatka
        g.setColor(Color.BLACK);
        for(int i=size; i<=getWidth(); i=i+size){
            g.drawLine(i, 0, i, getHeight());
        }
        for(int i=size; i<=getHeight(); i=i+size){
            g.drawLine(0, i, getWidth(), i);
        }
    }

}
