/* Kamil Matejuk */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener wyświetlający informacje o programie.
 * @author Kamil Matejuk
 * @version 1.0
 */

public class InfoActionListener implements ActionListener {

    private JFrame frame;

    /**
     * Konstruktor przypisujący zmiennej globalnej JFrame wartosc.
     * @param frame Okno główne, nad którym ma sie pojawić komunikat
     */
    public InfoActionListener(JFrame frame){
        this.frame = frame;
    }

    /**
     * ActionListener wyświetlający okno dialogowe
     * @param e akcja kliknięcia
     * @see java.awt.Dialog
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame,
                "Program: zad1\nautor: Kamil Matejuk\ndata powstania: 15.05.2019r\n\nProgram podobny do Paint'a, pozwalajcy rysować koła, prostokąty oraz wielokąty,\nrozszerzający działanie o zmianę położenia i rozmiaru figury",
                "Informacje",
                JOptionPane.INFORMATION_MESSAGE);

    }
}
