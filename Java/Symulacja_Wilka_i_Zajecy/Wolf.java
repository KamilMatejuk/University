import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa tworzaca wilka, jako wątek.
 * @author Kamil Matejuk
 * @version 1.0
 */
public class Wolf extends Thread{

    public int cord_x; //wspolrzedna X lewego-górnego rogu
    public int cord_y; //wspolrzedna Y lewego-górnego rogu
    public int size;
    public int time;
    public Pole pole;
    public JFrame frame;
    public Image img;

    /**
     * Konstruktor wybierający losowy czas trwania cyklu wątku i ładujący obraz.
     * @param x współrzedna X
     * @param y współrzedna Y
     * @param s rozmiar
     * @param p pole na którym znajdują sie króliki
     * @param fr okno w którym znajduje sie wilk i całe pole do rysowania
     */
    public Wolf(int x, int y, int s, Pole p, JFrame fr){
        this.cord_x = x;
        this.cord_y = y;
        this.size = s;
        this.pole = p;
        this.frame = fr;
        this.time = pole.random.nextInt(zad1.thread_time) + zad1.thread_time/3;
        try {
            Image image = ImageIO.read(new File("wolf.png"));
            img = image.getScaledInstance(size, size, Image.SCALE_FAST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rysowanie królika jako zielonego kwadratu i zdjęcia
     * @param g grafika
     */
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(cord_x, cord_y, size, size);
        g.drawImage(img, cord_x, cord_y, null);
    }

    //getters
    /**
     * Zwraca wartosc położenia X
     * @return X
     */
    public int getX(){ return cord_x; }
    /**
     * Zwraca wartosc położenia Y
     * @return Y
     */
    public int getY(){ return cord_y; }

    /**
     * Poruszanie sie, najpierw znajdując najbliższego królika, potem przyblizając się, oraz obsługa zabijania/jedzenia.
     */
    public void moveToNearestRabbit(){
        Rabbit rabbit = null;
        int distance = Integer.MAX_VALUE;
        CopyOnWriteArrayList<Rabbit> temp = Pole.Rabbits;
        for(Rabbit r : temp){
            int new_distance = Math.max(Math.abs(cord_x-r.getX()), Math.abs(cord_y-r.getY()));
            if(new_distance < distance){
                distance = new_distance;
                rabbit = r;
            }
        }
        if(pole.Rabbits.size()>0) { //są jeszcze jakieś króliki
            if (rabbit.getX() > cord_x) { //po prawej
                cord_x += size;
            } else if (rabbit.getX() < cord_x) { //po lewej
                cord_x -= size;
            }
            if (rabbit.getY() > cord_y) { //na dole
                cord_y += size;
            } else if (rabbit.getY() < cord_y) { //na górze
                cord_y -= size;
            }
            if (cord_x == rabbit.getX() && cord_y == rabbit.getY()) {
                try {
                    Pole.Rabbits.remove(rabbit);
                    rabbit.stopThread();
                    Thread.sleep(4 * time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pole.repaint();
        } else { //end of rabbits
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Akcje wykonywne podczas działania wątku, zapętlone wykonywanie ruchu i czekanie.
     */
    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep(time);
                moveToNearestRabbit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
