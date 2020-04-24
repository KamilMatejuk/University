/* Kamil Matejuk */
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa tworzaca króliki, jako wątki.
 * @author Kamil Matejuk
 * @version 1.0
 */
public class Rabbit extends Thread{
    public int cord_x; //wspolrzedna X lewego-górnego rogu
    public int cord_y; //wspolrzedna Y lewego-górnego rogu
    public int size;
    public int time;
    public Pole pole;
    public Image img;
    public boolean alive = true;

    /**
     * Konstruktor wybierający losowy czas trwania cyklu wątku i ładujący obraz.
     * @param x współrzedna X
     * @param y współrzedna Y
     * @param s rozmiar
     * @param p pole na którym znajdują sie króliki
     */
    public Rabbit(int x, int y, int s, Pole p){
        this.cord_x = x;
        this.cord_y = y;
        this.size = s;
        this.pole = p;
        this.time = pole.random.nextInt(zad1.thread_time) + zad1.thread_time/2;
        /*try {
            Image image = ImageIO.read(new File("rabbit.png"));
            img = image.getScaledInstance(size, size, Image.SCALE_FAST);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Rysowanie królika jako zielonego kwadratu, lub zdjęcia
     * @param g grafika
     */
    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(cord_x, cord_y, size, size);
        //g.drawImage(img, cord_x, cord_y, null);
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
     * Poruszanie sie, najpierw wybierajac miejsca gdzie nikt nie stoi, potem eliminując te które przyblizają królika do wilka, a na końcu losując z pozostałych.
     */
    public void move(){
        //find all places
        ArrayList<Point> places = new ArrayList<>();
        ArrayList<Point> taken_places = new ArrayList<>();
        places.add(new Point(cord_x-size, cord_y-size)); //Left Up
        places.add(new Point(cord_x, cord_y-size)); //Up
        places.add(new Point(cord_x+size, cord_y-size)); //Right Up
        places.add(new Point(cord_x+size, cord_y)); //Right
        places.add(new Point(cord_x+size, cord_y+size)); //Right Down
        places.add(new Point(cord_x, cord_y+size)); //Down
        places.add(new Point(cord_x-size, cord_y+size)); //Left Down
        places.add(new Point(cord_x-size, cord_y)); //Left
        //check taken places
        for(Point pt : places){
            if(pt.x<0 || pt.x>=pole.width*size || pt.y<0 || pt.y>=pole.height*size){ taken_places.add(pt); } //poza krawedziami
            else if(pt.x==pole.wolf.getX() && pt.y==pole.wolf.getY()){ taken_places.add(pt); } //wilk
            else if(Math.max(pole.wolf.getX(), cord_x) > pt.x && pt.x > Math.min(pole.wolf.getX(), cord_x)){ taken_places.add(pt); } //w kierunku wilka
            else if(Math.max(pole.wolf.getY(), cord_y) > pt.y && pt.y > Math.min(pole.wolf.getY(), cord_y)){ taken_places.add(pt); }
            else { //inne zające
                try {
                    CopyOnWriteArrayList<Rabbit> temp = Pole.Rabbits;
                    for (Rabbit rb : temp) {
                        if (rb != this) {
                            if (pt.x == rb.getX() && pt.y == rb.getY()) {
                                taken_places.add(pt);
                            }
                        }
                    }
                } catch (ConcurrentModificationException ex){
                    ex.printStackTrace();
                }
            }
        }
        //remove taken places
        for(Point pt : taken_places){
            places.remove(pt);
        }
        //chose one by random
        if(places.size()>0) {
            int i = pole.random.nextInt(places.size());
            cord_x = places.get(i).x;
            cord_y = places.get(i).y;
            pole.repaint();
        }
    }

    /**
     * Akcje wykonywne podczas działania wątku, zapętlone wykonywanie ruchu i czekanie.
     */
    @Override
    public void run(){
        while (alive) {
            try {
                Thread.sleep(time);
                move();
            } catch (InterruptedException | ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopThread(){
        alive = false;
    }
}
