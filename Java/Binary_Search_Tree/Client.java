/* Kamil Matejuk */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends Frame implements ActionListener {

    static Client frame;
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;
    static String line = "";
    static ObjectInputStream obj_in = null;
    //static ObjectOutputStream obj_out = null;
    static BinarySearchTree recievedTree = null;

    JRadioButton rb_type_int;
    JRadioButton rb_type_double;
    JRadioButton rb_type_string;
    JButton btn_insert;
    JButton btn_delete;
    JButton btn_search;
    JButton btn_draw;

    /**
     * konstrukrot z twozrrniem gui
     */
    Client(){
        JLabel txt_choose_type = new JLabel("Wybierz typ drzewa");
        rb_type_int = new JRadioButton("Integer", false);
        rb_type_double = new JRadioButton("Double", false);
        rb_type_string = new JRadioButton("String", false);
        ButtonGroup rb_group = new ButtonGroup();
        rb_type_int.addActionListener(this);
        rb_type_double.addActionListener(this);
        rb_type_string.addActionListener(this);
        rb_group.add(rb_type_int);
        rb_group.add(rb_type_double);
        rb_group.add(rb_type_string);
        btn_insert = new JButton("Dodaj");
        btn_delete = new JButton("Usuń");
        btn_search = new JButton("Wyszukaj");
        btn_draw = new JButton("Wyświetl drzewo");
        btn_insert.addActionListener(this);
        btn_delete.addActionListener(this);
        btn_search.addActionListener(this);
        btn_draw.addActionListener(this);

        JPanel radio_panel = new JPanel();
        radio_panel.setLayout(new GridLayout(1,3));
        radio_panel.add(rb_type_int);
        radio_panel.add(rb_type_double);
        radio_panel.add(rb_type_string);

        setLayout(new GridLayout(6,1));
        add(txt_choose_type);
        add(radio_panel);
        add(btn_insert);
        add(btn_delete);
        add(btn_search);
        add(btn_draw);

        btn_insert.setEnabled(false);
        btn_delete.setEnabled(false);
        btn_search.setEnabled(false);
        btn_draw.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //try {
            if (e.getSource() == rb_type_int) {
                //wysle na serwer zmienna typ, ktora utworzy drzewo, nic nie zwraca
                out.println("Integer");
                //obj_out.writeObject("Integer");
                System.out.println("Sent request to create tree of Integer");
                btn_insert.setEnabled(true);
                btn_delete.setEnabled(false);
                btn_search.setEnabled(false);
                btn_draw.setEnabled(false);
            } else if (e.getSource() == rb_type_double) {
                out.println("Double");
                //obj_out.writeObject("Double");
                System.out.println("Sent request to create tree of Double");
                btn_insert.setEnabled(true);
                btn_delete.setEnabled(false);
                btn_search.setEnabled(false);
                btn_draw.setEnabled(false);
            } else if (e.getSource() == rb_type_string) {
                out.println("String");
                //obj_out.writeObject("String");
                System.out.println("Sent request to create tree of String");
                btn_insert.setEnabled(true);
                btn_delete.setEnabled(false);
                btn_search.setEnabled(false);
                btn_draw.setEnabled(false);
            } else if (e.getSource() == btn_insert) {
                String element = JOptionPane.showInputDialog(frame, "Jaki element chcesz dodać?");
                if (element != null) {
                    out.println("100" + element);
                    //obj_out.writeObject("100"+element);
                    btn_delete.setEnabled(true);
                    btn_search.setEnabled(true);
                    btn_draw.setEnabled(true);
                }
            } else if (e.getSource() == btn_delete) {
                String element = JOptionPane.showInputDialog(frame, "Jaki element chcesz usunąć?");
                if (element != null) {
                    out.println("200" + element);
                    //obj_out.writeObject("200"+element);
                }
            } else if (e.getSource() == btn_search) {
                String element = JOptionPane.showInputDialog(frame, "Jaki element chcesz wyszukać?");
                if (element != null) {
                    out.println("300" + element);
                    //obj_out.writeObject("300"+element);
                }
            } else if (e.getSource() == btn_draw) {
                out.println("400");
                //obj_out.writeObject("400");
            }
        /*} catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }

    public static void main(String[] args) {
        frame = new Client();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        center(frame);
        frame.setVisible(true);

        try {
            socket = new Socket("localhost", 4444);
            System.out.println("Client active");
            // Polaczenie z socketem
            // Wysylanie do serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // Odbieranie z serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //odbieranie obiektów
            obj_in = new ObjectInputStream(socket.getInputStream());
            //obj_out = new ObjectOutputStream(socket.getOutputStream());

            while (true){ //line != null
                line = in.readLine();
                //line = (String) obj_in.readObject();
                if(line.substring(0,3).equals("101")){ //insert positive
                    //recievedTree = (BinarySearchTree) obj_in.readObject();
                    //draw(recievedTree.root);
                } else if(line.substring(0,3).equals("201")){ //delete positive
                    JOptionPane.showMessageDialog(frame,"Element \"" + line.substring(3) + "\" was deleted succesfully");
                    //recievedTree = (BinarySearchTree) obj_in.readObject();
                    //draw(recievedTree.root);
                } else if(line.substring(0,3).equals("202")){ //delete negative
                    JOptionPane.showMessageDialog(frame,"Cannot delete element \"" + line.substring(3) + "\"");
                } else if(line.substring(0,3).equals("301")){ //search positive
                    JOptionPane.showMessageDialog(frame,"Element \"" + line.substring(3) + "\" was found succesfully");
                } else if(line.substring(0,3).equals("302")){ //search negative
                    JOptionPane.showMessageDialog(frame,"Cannot find element \"" + line.substring(3) + "\"");
                } else if(line.substring(0,3).equals("401")){ //get tree
                    recievedTree = (BinarySearchTree) obj_in.readObject();
                    draw(recievedTree.root);
                } else if(line.substring(0,3).equals("500")){ //error
                    JOptionPane.showMessageDialog(frame, "Podaj poprawne dane");
                }
            }
        }
        catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost"); System.exit(1);
        } catch (StreamCorruptedException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No I/O"); System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void draw(TreeNode root) {
        System.out.println(recievedTree.root.toString());
        //JOptionPane.showMessageDialog(frame, recievedTree.root.toString());

    }

    private static void center(Client frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()*3) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

}
