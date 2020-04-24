/* Kamil Matejuk */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Główna klasa.
 * @author Kamil Matejuk
 * @version 1.0
 */

public class zad {

    public static void main(String[] args) {
        createGUI();
    }

    /**
     * Ustawienie okna na środku ekranu.
     * @param frame okno które chce sie ustawić
     */
    public static void center(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
     * Stworzenie GUI, oraz dodanie ActionListener'ów.
     * @see ActionListener
     */
    public static void createGUI() {
        JFrame frame = new JFrame("Draw");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // stworzenie layoutu
        SpringLayout layout = new SpringLayout();
        Container container = frame.getContentPane();
        container.setLayout(layout);
        //dodanie komponentów
        JButton btn_draw_circ = new JButton("Koło");
        JButton btn_draw_rect = new JButton("Prostokąt");
        JButton btn_draw_poly = new JButton("Wielokat");
        JButton btn_check = new JButton("Zaznacz");
        JLabel label_size_title = new JLabel("Rozmiar");
        JButton btn_resize = new JButton("zmien");
        JLabel label_width_show = new JLabel("szer: XXXX.XX");
        JLabel label_heigth_show = new JLabel("wys: XXXX.XX");
        JLabel label_coordinates_title = new JLabel("Wspólrzędne");
        JButton btn_move = new JButton("zmien");
        JLabel label_coordinates_show = new JLabel("X: 1234, Y:5678");
        JButton btn_info = new JButton("INFO");
        JButton btn_save = new JButton("ZAPISZ");
        JButton btn_clear = new JButton("usuń wszystko");
        JButton btn_upload = new JButton("wgraj z pliku");
        container.add(btn_draw_circ);
        container.add(btn_draw_rect);
        container.add(btn_draw_poly);
        container.add(btn_check);
        container.add(label_size_title);
        container.add(btn_resize);
        container.add(label_width_show);
        container.add(label_heigth_show);
        container.add(label_coordinates_title);
        container.add(btn_move);
        container.add(label_coordinates_show);
        container.add(btn_info);
        container.add(btn_save);
        container.add(btn_clear);
        container.add(btn_upload);
        Draw drawing_panel = new Draw(container);
        container.add(drawing_panel);

        //ustawienie relatywne komponentów
        layout.putConstraint(SpringLayout.WEST, drawing_panel, 15, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, drawing_panel, 15, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.SOUTH, container, 15, SpringLayout.SOUTH, drawing_panel);
        layout.putConstraint(SpringLayout.EAST, container, 10, SpringLayout.EAST, btn_move);
        layout.putConstraint(SpringLayout.NORTH, btn_draw_circ, 15, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.NORTH, btn_draw_rect, 5, SpringLayout.SOUTH, btn_draw_circ);
        layout.putConstraint(SpringLayout.NORTH, btn_draw_poly, 5, SpringLayout.SOUTH, btn_draw_rect);
        layout.putConstraint(SpringLayout.NORTH, btn_check, 15, SpringLayout.SOUTH, btn_draw_poly);
        layout.putConstraint(SpringLayout.NORTH, label_size_title, 15, SpringLayout.SOUTH, btn_check);
        layout.putConstraint(SpringLayout.NORTH, btn_resize, 0, SpringLayout.NORTH, label_size_title);
        layout.putConstraint(SpringLayout.SOUTH, btn_resize, 0, SpringLayout.SOUTH, label_size_title);
        layout.putConstraint(SpringLayout.NORTH, label_heigth_show, 5, SpringLayout.SOUTH, label_size_title);
        layout.putConstraint(SpringLayout.NORTH, label_width_show, 5, SpringLayout.SOUTH, label_heigth_show);
        layout.putConstraint(SpringLayout.NORTH, label_coordinates_title, 15, SpringLayout.SOUTH, label_width_show);
        layout.putConstraint(SpringLayout.NORTH, btn_move, 0, SpringLayout.NORTH, label_coordinates_title);
        layout.putConstraint(SpringLayout.SOUTH, btn_move, 0, SpringLayout.SOUTH, label_coordinates_title);
        layout.putConstraint(SpringLayout.NORTH, label_coordinates_show, 5, SpringLayout.SOUTH, label_coordinates_title);
        layout.putConstraint(SpringLayout.SOUTH, btn_info, -15, SpringLayout.SOUTH, container);
        layout.putConstraint(SpringLayout.SOUTH, btn_save, -15, SpringLayout.SOUTH, container);
        layout.putConstraint(SpringLayout.SOUTH, btn_upload, -5, SpringLayout.NORTH, btn_info);
        layout.putConstraint(SpringLayout.SOUTH, btn_clear, -5, SpringLayout.NORTH, btn_upload);
        layout.putConstraint(SpringLayout.WEST, btn_draw_circ, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, btn_draw_rect, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, btn_draw_poly, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, btn_check, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.EAST, btn_draw_circ, -10, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.EAST, btn_draw_rect, -10, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.EAST, btn_draw_poly, -10, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.EAST, btn_check, -10, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.WEST, label_size_title, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.EAST, btn_resize, 0, SpringLayout.EAST, btn_check);
        layout.putConstraint(SpringLayout.WEST, label_heigth_show, 25, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, label_width_show, 25, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, label_coordinates_title, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, btn_move, 10, SpringLayout.EAST, label_coordinates_title);
        layout.putConstraint(SpringLayout.WEST, label_coordinates_show, 25, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, btn_info, 15, SpringLayout.EAST, drawing_panel);
        layout.putConstraint(SpringLayout.WEST, btn_save, 5, SpringLayout.EAST, btn_info);
        layout.putConstraint(SpringLayout.WEST, btn_clear, 0, SpringLayout.WEST, btn_info);
        layout.putConstraint(SpringLayout.EAST, btn_clear, 0, SpringLayout.EAST, btn_save);
        layout.putConstraint(SpringLayout.WEST, btn_upload, 0, SpringLayout.WEST, btn_info);
        layout.putConstraint(SpringLayout.EAST, btn_upload, 0, SpringLayout.EAST, btn_save);
        //ustawienie fontów i kolorów
        Font font_btn = new Font(Font.DIALOG, Font.BOLD, 12);
        Font font_btn_edit = new Font(Font.DIALOG, Font.PLAIN, 10);
        Font font_title = new Font(Font.DIALOG, Font.BOLD, 14);
        Font font_data = new Font(Font.DIALOG, Font.PLAIN, 11);
        btn_draw_circ.setFont(font_btn);
        btn_draw_rect.setFont(font_btn);
        btn_draw_poly.setFont(font_btn);
        btn_check.setFont(font_btn);
        btn_clear.setFont(font_btn);
        btn_upload.setFont(font_btn);
        label_size_title.setFont(font_title);
        label_coordinates_title.setFont(font_title);
        btn_info.setFont(font_btn);
        btn_save.setFont(font_btn);
        label_width_show.setFont(font_data);
        label_heigth_show.setFont(font_data);
        label_coordinates_show.setFont(font_data);
        btn_move.setFont(font_btn_edit);
        btn_resize.setFont(font_btn_edit);
        label_size_title.setVisible(false);
        btn_resize.setVisible(false);
        label_coordinates_title.setVisible(false);
        btn_move.setVisible(false);
        label_width_show.setVisible(false);
        label_heigth_show.setVisible(false);
        label_coordinates_show.setVisible(false);

        //wyświetlenie framea
        frame.setSize(700, 450);
        frame.setMinimumSize(new Dimension(670, 485));
        center(frame);
        frame.setVisible(true);

        //dodanie ActionListenerów
        ActionListener info_al = new InfoActionListener(frame);
        btn_info.addActionListener(info_al);
        btn_draw_circ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 1;
            }
        });
        btn_draw_rect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 2;
            }
        });
        btn_draw_poly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 3;
            }
        });
        btn_check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 4;
            }
        });
        btn_resize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 5;
            }
        });
        btn_move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 6;
            }
        });
        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.kolory.clear();
                Draw.figury.clear();
                Draw.type = 7;
                drawing_panel.repaint();
            }
        });
        btn_upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.type = 8;
                try
                {
                    ArrayList<Object> arr = new ArrayList<>();
                    FileInputStream fis = new FileInputStream("myfile");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    arr = (ArrayList<Object>) ois.readObject();
                    ois.close();
                    fis.close();
                    Draw.figury = (ArrayList<Shape>) arr.get(0);
                    Draw.kolory = (ArrayList<Color>) arr.get(1);
                    drawing_panel.repaint();
                }catch(IOException ioe){
                    JOptionPane.showMessageDialog(frame,
                            "Nie masz żadnego zapisanego pliku",
                            "Błąd Odczytu",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }catch(ClassNotFoundException c){
                    return;
                }
            }
        });
        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(Draw.figury);
                    arr.add(Draw.kolory);

                    FileOutputStream fos= new FileOutputStream("myfile");
                    ObjectOutputStream oos= new ObjectOutputStream(fos);
                    oos.writeObject(arr);
                    oos.close();
                    fos.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        });
    }

}
