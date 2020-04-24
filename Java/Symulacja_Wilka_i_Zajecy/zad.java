/* Kamil Matejuk */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Główna klasa.
 * @author Kamil Matejuk
 * @version 1.0
 */
public class zad {

    public static int amount_width, amount_heigth, amount_rabbits, thread_time;
    private static final int MAX_SIZE = 100, MAX_TIME = 2000, MIN_TIME = 50;
    public static void main(String[] args) {
        createGUI();
    }

    /**
     * Ustawienie okna na środku ekranu.
     * @param frame okno które chce sie ustawić
     */
    private static void center(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
     * Stworzenie GUI, oraz dodanie ActionListenera.
     * @see ActionListener
     */
    private static void createGUI() {
        JFrame frame = new JFrame("Wpisz dane");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //stworzenie layoutów
        SpringLayout layout = new SpringLayout();
        Container container = frame.getContentPane();
        container.setLayout(layout);
        //dodanie elementów
        JLabel lbl_wymiary_planszy = new JLabel("Podaj wymiary planszy:");
        JTextField txt_w_planszy = new JTextField(3);
        JTextField txt_h_planszy = new JTextField(3);
        JLabel lbl_ilosc_zajecy = new JLabel("Podaj ilosc zajęcy:");
        JTextField txt_ilosc_zajecy = new JTextField(5);
        JLabel lbl_czas_watku = new JLabel("Podaj przyblizony czas wątku:");
        JTextField txt_czas_watku = new JTextField(5);
        JButton btn_start = new JButton("Zacznij symulację");
        container.add(lbl_wymiary_planszy);
        container.add(txt_w_planszy);
        container.add(txt_h_planszy);
        container.add(lbl_ilosc_zajecy);
        container.add(txt_ilosc_zajecy);
        container.add(lbl_czas_watku);
        container.add(txt_czas_watku);
        container.add(btn_start);
        //ustawienie komponentow
        layout.putConstraint(SpringLayout.NORTH, lbl_wymiary_planszy, 20, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.NORTH, txt_w_planszy, 19, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.NORTH, txt_h_planszy, 19, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.NORTH, lbl_ilosc_zajecy, 10, SpringLayout.SOUTH, lbl_wymiary_planszy);
        layout.putConstraint(SpringLayout.NORTH, txt_ilosc_zajecy, 6, SpringLayout.SOUTH, txt_w_planszy);
        layout.putConstraint(SpringLayout.NORTH, lbl_czas_watku, 10, SpringLayout.SOUTH, lbl_ilosc_zajecy);
        layout.putConstraint(SpringLayout.NORTH, txt_czas_watku, 7, SpringLayout.SOUTH, txt_ilosc_zajecy);
        layout.putConstraint(SpringLayout.NORTH, btn_start, 30, SpringLayout.SOUTH, lbl_czas_watku);
        layout.putConstraint(SpringLayout.SOUTH, container, 20, SpringLayout.SOUTH, btn_start);
        layout.putConstraint(SpringLayout.WEST, lbl_wymiary_planszy, 20, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.WEST, lbl_ilosc_zajecy, 20, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.WEST, lbl_czas_watku, 20, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.WEST, btn_start, 50, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.EAST, txt_w_planszy, -5, SpringLayout.WEST, txt_h_planszy);
        layout.putConstraint(SpringLayout.EAST, txt_h_planszy, 0, SpringLayout.EAST, txt_czas_watku);
        layout.putConstraint(SpringLayout.WEST, txt_ilosc_zajecy, 0, SpringLayout.WEST, txt_czas_watku);
        layout.putConstraint(SpringLayout.WEST, txt_czas_watku, 10, SpringLayout.EAST, lbl_czas_watku);
        layout.putConstraint(SpringLayout.EAST, container, 20, SpringLayout.EAST, txt_czas_watku);
        layout.putConstraint(SpringLayout.EAST, btn_start, -50, SpringLayout.EAST, container);

        frame.pack();
        center(frame);
        frame.setVisible(true);
        
        btn_start.addActionListener(new ActionListener() {

            /**
             * Wyświetlenie okna dialogowego z podanym tekstem.
             * @param s tekst błędu
             */
            void showError(String s){
                JOptionPane.showMessageDialog(frame, s,"Błędne Dane", JOptionPane.ERROR_MESSAGE);
            }

            /**
             * Sprawdzenie czy pola tekstowe zawierają w sobie jakis tekst.
             * @return true - gdy wszytskie zawierają, false gdy  chociaz jedno jest puste
             */
            boolean containsText(){
                if(txt_w_planszy.getText().isEmpty() ||
                        txt_h_planszy.getText().isEmpty() ||
                        txt_ilosc_zajecy.getText().isEmpty() ||
                        txt_czas_watku.getText().isEmpty()){
                    return false;
                } else {
                    return true;
                }
            }

            /**
             * ActionListener sprawdzający poprawnosc danych, a nastepnie tworzący nowe okno z symulacją.
             * @param e akcja kliknięcia w przycisk
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(containsText()) {
                    try {
                        amount_rabbits = Integer.parseInt(txt_ilosc_zajecy.getText());
                        thread_time = Integer.parseInt(txt_czas_watku.getText());
                        int dim1 = Integer.parseInt(txt_w_planszy.getText());
                        int dim2 = Integer.parseInt(txt_h_planszy.getText());
                        amount_heigth = Math.min(dim1, dim2);
                        amount_width = Math.max(dim1, dim2);
                        if(amount_rabbits<=0 || amount_width<=0 || amount_heigth<=0){
                            showError("Podaj dodatnie wartości");
                        } else if(amount_width>MAX_SIZE || amount_heigth>MAX_SIZE){
                            showError("Podaj mniejsze wymiary");
                        } else if(amount_rabbits > 0.70*amount_width*amount_heigth){
                            showError("Wprowadzono za dużo królików dla podanych wymiarów planszy");
                        } else if(thread_time<MIN_TIME){
                            showError("Wprowadzono za mały okres wątku");
                        } else if(thread_time>MAX_TIME){
                            showError("Wprowadzono za duży okres wątku");
                        } else {
                            //większy wymiar zawsze jest stała i wynosi 700, reszta jest dopasowana aby zachować pola kwadratowe
                            JFrame fr = new JFrame("Symulacja");
                            int size = 700/amount_width;
                            fr.setSize(size*amount_width+14, size*amount_heigth+68); //nwm czemu tak musi być, ale musi żeby było widac wszystkie pola
                            center(fr);
                            fr.add(new Pole(size, amount_width, amount_heigth, amount_rabbits, fr), BorderLayout.CENTER);
                            JLabel label = new JLabel("Czas: 0:00");
                            label.setPreferredSize(new Dimension(400, 30));
                            label.setHorizontalAlignment(JLabel.CENTER);
                            fr.add(label, BorderLayout.SOUTH);
                            fr.setResizable(false);
                            fr.setVisible(true);
                            //update label
                            TimerTask task = new TimerTask(){
                                int sek = 0;
                                @Override
                                public void run(){
                                    sek++;
                                    label.setText("Czas: " + sek + "s");
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 0, 1000);
                        }
                    } catch (NumberFormatException ex) {
                        showError("Wprowadż poprawne dane (liczby całkowite)");
                    }
                } else {
                    showError("Wprowadż wszystkie dane");
                }
            }
        });
    }
}
