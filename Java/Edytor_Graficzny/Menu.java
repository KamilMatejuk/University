import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * Klasa odpowiadająca za wyświetlenie menu przy kliknięciu prawym przyciskiem w figurę.
 * @author Kamil Matejuk
 * @version 1.0
 */

public class Menu extends JPopupMenu {

    static Color new_color = Color.RED;
    static int index;
    static Draw draw;

    /**
     * Konstruktor tworzący Menu z 8 opcjami domyślnymi kolorów i jedną do wyboru.
     * @param index index zaznaczonej figury
     * @param d komponent w którym mieszczą sie figury
     * @see JPopupMenu
     */
    public Menu(int index, Draw d) {
        this.index = index;
        this.draw = d;
        JMenuItem color1 = new JMenuItem(new AbstractAction("Czarny") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.BLACK;
                changeColor();
            }
        });
        JMenuItem color2 = new JMenuItem(new AbstractAction("Fioletowy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.MAGENTA;
                changeColor();
            }
        });
        JMenuItem color3 = new JMenuItem(new AbstractAction("Niebieski") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.BLUE;
                changeColor();
            }
        });
        JMenuItem color4 = new JMenuItem(new AbstractAction("Zielony") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.GREEN;
                changeColor();
            }
        });
        JMenuItem color5 = new JMenuItem(new AbstractAction("Zółty") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.YELLOW;
                changeColor();
            }
        });
        JMenuItem color6 = new JMenuItem(new AbstractAction("Pomarańczowy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.ORANGE;
                changeColor();
            }
        });
        JMenuItem color7 = new JMenuItem(new AbstractAction("Czerwony") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.RED;
                changeColor();
            }
        });
        JMenuItem color8 = new JMenuItem(new AbstractAction("Biały") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new_color = Color.WHITE;
                changeColor();
            }
        });
        JMenuItem custom_color = new JMenuItem(new AbstractAction("Inny kolor") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("MyColorChooser");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JComponent newContentPane = new MyColorChooser(frame);
                newContentPane.setOpaque(true);
                frame.setContentPane(newContentPane);
                frame.pack();
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
                frame.setLocation(x, y);
                frame.setVisible(true);
            }
        });
        add(color1);
        add(color2);
        add(color3);
        add(color4);
        add(color5);
        add(color6);
        add(color7);
        add(color8);
        addSeparator();
        add(custom_color);
    }

    /**
     * Zmiana koloru w ArrayLiscie i odświerzenie okna.
     */
    public static void changeColor(){
        Draw.kolory.set(index, new_color);
        draw.repaint();
    }
}

/**
 * Klasa tworząca okno z paletą kolorów do wyboru.
 * @author Kamil Matejuk
 * @version 1.0
 */
class MyColorChooser extends JPanel {
    
    protected JColorChooser color_chooser;
    private JFrame frame;

    /**
     * Konstruktor tworzacy panel kolorów, wraz z obsługą kliknięcia w kolor.
     * @param fr okno wyboru kolorów
     * @see JColorChooser
     */
    public MyColorChooser(JFrame fr) {
        this.frame = fr;
        color_chooser = new JColorChooser();
        color_chooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Menu.new_color = color_chooser.getColor();
                Menu.changeColor();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        add(color_chooser);
    }

}
