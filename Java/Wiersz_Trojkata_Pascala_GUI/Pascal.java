/* Kamil Matejuk */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* Napisz proste GUI dla klasy WierszTrójkataPascala. Powinno ono działać w ten sposób, że podajemy liczbę będącą rozmiarem
trójkąta Pascala i wyświetlamy cały uzyskany trójkąt. Zadbaj o pełną obsługę okna aplikacji, odpowiednią kolorystykę i
rozmiar czcionki, oraz pełną obsaugę wszystkich wyjątków. */

public class Pascal {

    public static void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    public static void showGUI(){
        JFrame frame = new JFrame("Pascal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JLabel polecenie = new JLabel(" Podaj rozmiar Trójkąta Pascala ");
        JTextField dane = new JTextField("", 5);
        JButton btn = new JButton("Zatwierdz");
        JLabel error = new JLabel(" ", JLabel.CENTER);
        JLabel trojkat = new JLabel("<html> </html>", SwingConstants.CENTER);
        polecenie.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        error.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        trojkat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        dane.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(polecenie);
        contentPane.add(dane);
        contentPane.add(btn);
        contentPane.add(error);
        contentPane.add(trojkat);

        layout.putConstraint(SpringLayout.WEST, polecenie, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, polecenie, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, dane, 5, SpringLayout.EAST, polecenie);
        layout.putConstraint(SpringLayout.NORTH, dane, 0, SpringLayout.NORTH, polecenie);
        layout.putConstraint(SpringLayout.SOUTH, dane, 0, SpringLayout.SOUTH, polecenie);
        layout.putConstraint(SpringLayout.WEST, btn, 5, SpringLayout.EAST, dane);
        layout.putConstraint(SpringLayout.NORTH, btn, 0, SpringLayout.NORTH, polecenie);
        layout.putConstraint(SpringLayout.SOUTH, btn, 0, SpringLayout.SOUTH, polecenie);
        layout.putConstraint(SpringLayout.WEST, error, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, error, 5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, error, 15, SpringLayout.SOUTH, polecenie);
        layout.putConstraint(SpringLayout.WEST, trojkat, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, trojkat, 5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, trojkat, 10, SpringLayout.SOUTH, error);
        layout.putConstraint(SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, btn);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, trojkat);

        btn.setEnabled(false);
        frame.pack();
        centerWindow(frame);
        frame.setVisible(true);

        dane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int n = Integer.parseInt(dane.getText());
                    if((n >= 0)&&(n<30)){
                        error.setText(" ");
                        btn.setEnabled(true);
                    } else {
                        error.setText("Podaj liczbę naturalną");
                        btn.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    error.setText("Podaj liczbę");
                    btn.setEnabled(false);
                }
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(dane.getText());
                trojkat.setText("");
                for(int i=0; i<=n; i++) {
                    WierszTrojkataPascala W = new WierszTrojkataPascala(i);
                    for (int j = 0; j <= i; j++) {
                        trojkat.setText(trojkat.getText() + W.wiersz[j] + " ");
                    }
                    trojkat.setText(trojkat.getText() + "\n");
                }
                trojkat.setText("<html><body style='text-align: center'>" + trojkat.getText()
                        .replaceAll("<","&lt;")
                        .replaceAll(">", "&gt;")
                        .replaceAll("\n", "<br/>") + "</body></html>");

                frame.setSize(frame.getWidth()>trojkat.getPreferredSize().getWidth() ? frame.getWidth() : (int) ((int) trojkat.getPreferredSize().getWidth() * 1.2), 130+(int) trojkat.getPreferredSize().getHeight());
                frame.setVisible(true);
                centerWindow(frame);
            }
        });
    }

    public static void main(String[] args) {
        showGUI();
    }
}
