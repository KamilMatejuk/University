import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasa odpowiadajaca za obiekt na którym bedzie sie rysować.
 * @author Kamil Matejuk
 * @version 1.0
 */

public class Draw extends JComponent{

    static int type = 0; //1-circle, 2-rectangle, 3-polygon, 4-zaznaczenie, 5-zmiana rozmiaru, 6-pzresuniecie, 7-usuń wszystko, 8-wgraj
    static boolean checked_bool = false;
    static ArrayList<Shape> figury = new ArrayList<>();
    static ArrayList<Color> kolory = new ArrayList<>();
    //static ArrayList<Point> poligon_pts = new ArrayList<>();
    static ArrayList<ArrayList<Point>> poligon_pts = new ArrayList<ArrayList<Point>>();
    static ArrayList<Point> current_poligon_pts = new ArrayList<>();
    private Point start_point, end_point;
    private Color default_color = Color.RED;
    private Shape checked_shape = null;
    private int index_of_checked = -1;

    private JButton btn_draw_circ;
    private JButton btn_draw_rect;
    private JButton btn_draw_poly;
    private JButton btn_check;
    private JLabel label_size_title;
    private JButton btn_resize;
    private JLabel label_width_show;
    private JLabel label_heigth_show;
    private JLabel label_coordinates_title;
    private JButton btn_move;
    private JLabel label_coordinates_show;
    private JButton btn_info;
    private JButton btn_save;
    private JButton btn_clear;
    private JButton btn_upload;

    /**
     * Konstruktor klasy z kontenerem którego elementy edytuje i wyświetla.
     * @param c Container z głównego okna
     */
    //konstruktor
    Draw(Container c){
        this.btn_draw_circ = (JButton) c.getComponent(0);
        this.btn_draw_rect = (JButton) c.getComponent(1);
        this.btn_draw_poly = (JButton) c.getComponent(2);
        this.btn_check = (JButton) c.getComponent(3);
        this.label_size_title = (JLabel) c.getComponent(4);
        this.btn_resize = (JButton) c.getComponent(5);
        this.label_width_show = (JLabel) c.getComponent(6);
        this.label_heigth_show = (JLabel) c.getComponent(7);
        this.label_coordinates_title = (JLabel) c.getComponent(8);
        this.btn_move = (JButton) c.getComponent(9);
        this.label_coordinates_show = (JLabel) c.getComponent(10);
        this.btn_info = (JButton) c.getComponent(11);
        this.btn_save = (JButton) c.getComponent(12);
        this.btn_clear = (JButton) c.getComponent(13);
        this.btn_upload = (JButton) c.getComponent(14);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                start_point = new Point(e.getX(), e.getY());
                end_point = null;
                switch (type){
                    case 1:
                    case 2:
                        checked_shape = null;
                        show_data(false, 0, 0, 0, 0);
                        break;
                    case 3:
                        checked_shape = null;
                        //poligon_pts.add(start_point);
                        current_poligon_pts.add(start_point);
                        show_data(false, 0, 0, 0, 0);
                        break;
                    case 4:
                    case 5:
                    case 6:
                        int i=0;
                        for(Shape sh : figury){
                            if(sh.contains(e.getX(), e.getY())){
                                checked_shape = sh;
                                checked_bool = true;
                                index_of_checked = figury.indexOf(sh);
                                show_data(true, sh.getBounds().x, sh.getBounds().y, sh.getBounds().width, sh.getBounds().height);
                            } else { i++;}
                        }
                        if(i == figury.size()){
                            show_data(false, 0, 0, 0, 0);
                            checked_bool = false;
                        }
                        else if(e.getButton()==MouseEvent.BUTTON3){
                            new Menu(index_of_checked, Draw.this).show(e.getComponent(), e.getX(), e.getY());
                        }
                        break;
                }
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Shape sh;
                switch (type){
                    case 1:
                        sh = drawCircle(start_point.x, start_point.y, e.getX(), e.getY());
                        figury.add(sh);
                        kolory.add(default_color);
                        break;
                    case 2:
                        sh = drawRect(start_point.x, start_point.y, e.getX(), e.getY());
                        figury.add(sh);
                        kolory.add(default_color);
                        break;
                }
                start_point = null;
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                end_point = new Point(e.getX(), e.getY());
                switch (type){
                    case 4:
                    case 5:
                    case 6: show_data(true, checked_shape.getBounds().x, checked_shape.getBounds().y, checked_shape.getBounds().width, checked_shape.getBounds().height);
                }
                repaint();
            }
        });
    }

    /**
     * Rysowanie na powierzchni obiektu, zaleznie od zmiennej 'type' która odpowiada za rodzaj akcji.
     * @param g grafika
     * @see Graphics
     * @see Graphics2D
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);

        //usunac puste arraylisty
        ArrayList<Integer> indexs = new ArrayList<>();
        for(ArrayList<Point> pts : poligon_pts){
            if(pts.size()==0){
                indexs.add(poligon_pts.indexOf(pts));
            }
        }
        int max = poligon_pts.size()-1;
        for(Integer i : indexs){
            poligon_pts.remove(max-i);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Iterator<Color> kolor_iterator;

        switch (type){
            case 1: //rysowanie koła
                kolor_iterator = kolory.iterator();
                for(Shape sh : figury){
                    g2.setPaint(kolor_iterator.next());
                    g2.fill(sh);
                }
                if (start_point != null && end_point != null){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
                    g2.setPaint(Color.LIGHT_GRAY);
                    Shape hint = drawCircle(start_point.x, start_point.y, end_point.x, end_point.y);
                    g2.fill(hint);
                }
                //poligon_pts.clear();
                if(current_poligon_pts.size()>0){
                    poligon_pts.add(current_poligon_pts);
                    current_poligon_pts.clear();
                }
                break;
            case 2: //rysowanie prostokata
                kolor_iterator = kolory.iterator();
                for(Shape sh : figury){
                    g2.setPaint(kolor_iterator.next());
                    g2.fill(sh);
                }
                if (start_point != null && end_point != null){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f));
                    g2.setPaint(Color.LIGHT_GRAY);
                    Shape hint = drawRect(start_point.x, start_point.y, end_point.x, end_point.y);
                    g2.fill(hint);
                }
                //poligon_pts.clear();
                if(current_poligon_pts.size()>0){
                    poligon_pts.add(current_poligon_pts);
                    current_poligon_pts.clear();
                }
                break;
            case 3: //rysowanie wielokąta
                Shape poligon;
                if(current_poligon_pts.size()== 1){
                    poligon = drawPoligon(current_poligon_pts);
                    figury.add(poligon);
                    kolory.add(default_color);
                    index_of_checked = figury.indexOf(poligon);
                } else {
                    poligon = drawPoligon(current_poligon_pts);
                    figury.set(index_of_checked, poligon);
                }
                kolor_iterator = kolory.iterator();
                for(Shape sh : figury){
                    g2.setPaint(kolor_iterator.next());
                    g2.fill(sh);
                    g2.draw(sh);
                }
                break;
            case 4: //rysowanie obwódki przy zaznaczej figurze
                kolor_iterator = kolory.iterator();
                for(Shape sh : figury){
                    if(sh == checked_shape){
                        g2.setPaint(Color.BLACK);
                        Shape sh_dotted = new Rectangle2D.Float(sh.getBounds().x, sh.getBounds().y, sh.getBounds().width, sh.getBounds().height);
                        g2.draw(sh_dotted);
                    }
                    g2.setPaint(kolor_iterator.next());
                    g2.fill(sh);
                }
                if(current_poligon_pts.size()>0){
                    poligon_pts.add(current_poligon_pts);
                    current_poligon_pts.clear();
                }
                break;
            case 5: //zmiana rozmiaru figury (zmienia współrzedne prawego-dolnego rogu)
                if (start_point != null && end_point != null && end_point.x > checked_shape.getBounds().x+10 && end_point.y > checked_shape.getBounds().y+10) {
                    kolor_iterator = kolory.iterator();
                    for (Shape sh : figury) {
                        if (sh == checked_shape) {
                            if (sh.getClass() == Rectangle2D.Float.class) {
                                checked_shape = drawRect(checked_shape.getBounds().x,checked_shape.getBounds().y,end_point.x,end_point.y);
                            } else if (sh.getClass() == Ellipse2D.Float.class) {
                                checked_shape = drawCircle(checked_shape.getBounds().x,checked_shape.getBounds().y,end_point.x,end_point.y);
                            }
                            figury.set(figury.indexOf(sh), checked_shape);
                            g2.setPaint(Color.BLACK);
                            Shape sh_dotted = new Rectangle2D.Float(sh.getBounds().x, sh.getBounds().y, sh.getBounds().width, sh.getBounds().height);
                            g2.draw(sh_dotted);
                        }
                        g2.setPaint(kolor_iterator.next());
                        g2.fill(sh);
                    }
                } else {
                    kolor_iterator = kolory.iterator();
                    for(Shape sh : figury){
                        if(sh == checked_shape && checked_bool){
                            g2.setPaint(Color.BLACK);
                            Shape sh_dotted = new Rectangle2D.Float(sh.getBounds().x, sh.getBounds().y, sh.getBounds().width, sh.getBounds().height);
                            g2.draw(sh_dotted);
                        }
                        g2.setPaint(kolor_iterator.next());
                        g2.fill(sh);
                    }
                }
                break;
            case 6: //zmiana położenia figury (zmienia współrzedne środka)
                if (start_point != null && end_point != null) {
                    kolor_iterator = kolory.iterator();
                    for (Shape sh : figury) {
                        if (sh == checked_shape) {
                            if (sh.getClass() == Rectangle2D.Float.class) {
                                checked_shape = drawRect(end_point.x - checked_shape.getBounds().width / 2,
                                        end_point.y - checked_shape.getBounds().height / 2,
                                        end_point.x + checked_shape.getBounds().width / 2,
                                        end_point.y + checked_shape.getBounds().height / 2);
                            } else if (sh.getClass() == Ellipse2D.Float.class) {
                                checked_shape = drawCircle(end_point.x - checked_shape.getBounds().width / 2,
                                        end_point.y - checked_shape.getBounds().height / 2,
                                        end_point.x + checked_shape.getBounds().width / 2,
                                        end_point.y + checked_shape.getBounds().height / 2);
                            } else if (sh.getClass() == GeneralPath.class){
                                //TODO zrobic zeby to działało
                                /*ArrayList<Point> pts;
                                for(ArrayList<Point> ap : poligon_pts){
                                    if(checked_shape.getBounds().contains(ap.get(0))){
                                        pts = ap;
                                        for(Point p : pts){
                                            p.x += end_point.x;
                                            p.y += end_point.y;
                                        }
                                        checked_shape = drawPoligon(pts);
                                    }
                                }*/
                                /*AffineTransform tr = new AffineTransform();
                                tr.translate(end_point.x+checked_shape.getBounds().getMinX()-checked_shape.getBounds().width/2, end_point.y+checked_shape.getBounds().getMinY()-checked_shape.getBounds().height/2);
                                checked_shape = tr.createTransformedShape(sh);*/
                            }
                            figury.set(figury.indexOf(sh), checked_shape);
                            g2.setPaint(Color.BLACK);
                            Shape sh_dotted = new Rectangle2D.Float(sh.getBounds().x, sh.getBounds().y, sh.getBounds().width, sh.getBounds().height);
                            g2.draw(sh_dotted);
                        }
                        g2.setPaint(kolor_iterator.next());
                        g2.fill(sh);
                    }
                } else {
                    kolor_iterator = kolory.iterator();
                    for(Shape sh : figury){
                        if(sh == checked_shape && checked_bool){
                            g2.setPaint(Color.BLACK);
                            Shape sh_dotted = new Rectangle2D.Float(sh.getBounds().x, sh.getBounds().y, sh.getBounds().width, sh.getBounds().height);
                            g2.draw(sh_dotted);
                        }
                        g2.setPaint(kolor_iterator.next());
                        g2.fill(sh);
                    }
                }
                break;
            case 7: break; //wyczyszczenie pola do rysowania
            case 8: //wgranie nowych figur z pliku
                kolor_iterator = kolory.iterator();
                for(Shape sh : figury){
                    g2.setPaint(kolor_iterator.next());
                    g2.fill(sh);
                }
        }
    }

    /**
     * Tworzenie prostokata o podanych wspołrzednych pkt przeciwnych.
     * @param x1 wspołrzedna X jednego z rogów
     * @param y1 wspołrzedna Y jednego z rogów
     * @param x2 wspołrzedna X drugiego rogu
     * @param y2 wspołrzedna Y drugiego rogu
     * @return prostokąt Rectangle2D
     */
    private Shape drawRect(int x1, int y1, int x2, int y2){
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);

        return new Rectangle2D.Float(x, y, width, height);
    }
    /**
     * Tworzenie elipsy o podanych wspołrzednych dwóch pkt.
     * @param x1 wspołrzedna X jednego z pkt
     * @param y1 wspołrzedna Y jednego z pkt
     * @param x2 wspołrzedna X drugiego pkt
     * @param y2 wspołrzedna Y drugiego pkt
     * @return elipsa Ellipse2D
     */
    private Shape drawCircle(int x1, int y1, int x2, int y2){
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);

        return new Ellipse2D.Float(x, y, width, height);
    }

    /**
     * Tworzenie wielokąta łączącego podane punkty.
     * @param p lista punktów
     * @return wielokąt narysowany w GeneralPath
     * @see GeneralPath
     */
    private Shape drawPoligon(ArrayList<Point> p){
        ArrayList<Point> points = p;
        GeneralPath gp = new GeneralPath();
        gp.moveTo(points.get(0).x, points.get(0).y);
        for(int i=1; i<points.size(); i++){
            gp.lineTo(points.get(i).x, points.get(i).y);
        }
        return gp;
    }

    /**
     * Pokazywanie i ukrywanie pól pokazujących położenie/wielkość figury oraz aktywowanie/dezaktywowanie przycisków.
     * @param checked zmienna sterująca czy nalezy pokazać/ukryć
     * @param x współrzedna X połozenia figury
     * @param y współrzedna Y połozenia figury
     * @param width szerokość figury
     * @param heigth wysokość figury
     */
    private void show_data(boolean checked, int x, int y, int width, int heigth) {
        btn_draw_circ.setEnabled(!checked);
        btn_draw_rect.setEnabled(!checked);
        btn_draw_poly.setEnabled(!checked);
        btn_check.setEnabled(!checked);
        btn_info.setEnabled(!checked);
        btn_save.setEnabled(!checked);
        btn_clear.setEnabled(!checked);
        btn_upload.setEnabled(!checked);
        label_size_title.setVisible(checked);
        btn_resize.setVisible(checked);
        label_width_show.setVisible(checked);
        label_heigth_show.setVisible(checked);
        label_coordinates_title.setVisible(checked);
        btn_move.setVisible(checked);
        label_coordinates_show.setVisible(checked);
        label_width_show.setText("szerokość: " + width);
        label_heigth_show.setText("wysokość: " + heigth);
        label_coordinates_show.setText("X: " + x + ", Y: " + y);
    }
}