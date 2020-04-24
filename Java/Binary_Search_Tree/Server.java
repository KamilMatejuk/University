import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    /** KODY
     * create tree: request - "Integer", "Double", "String"
     * add element: request - 100, positive - 101
     * delete elemnt: request - 200, posityve - 201, negative - 202
     * search elemnt: request - 300, posityve - 301, negative - 302
     * get tree: request - 400, back - 401
     * convert: error - 500
     */

    static ServerSocket server = null;
    static Socket client = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static String line = "";
    static String typeT;
    //static ObjectInputStream obj_in = null;
    static ObjectOutputStream obj_out = null;

    private static BinarySearchTree tree;

    /**
     * kontruktor inicjuje socket
     */
    Server() {
        try {
            server = new ServerSocket(4444);
            System.out.println("Server active");
        } catch (IOException e) {
            System.out.println("Could not listen on port 4444"); System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        try {
            client = server.accept();
            System.out.println("Client connected");
            // Odbieranie od socketa
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Wysylanie do socketa
            out = new PrintWriter(client.getOutputStream(), true);
            //wysyłanie obiektów
            //obj_in = new ObjectInputStream(client.getInputStream());
            obj_out = new ObjectOutputStream(client.getOutputStream());
        }
        catch (IOException e) {
            System.out.println("Accept failed: 4444"); System.exit(-1);
        }
        while(line != null) {
            try {
                // Odbieranie od socketa
                line = in.readLine();
                //line = (String) obj_in.readObject();
                if(line.equals("Integer")){
                    tree = new BinarySearchTree<Integer>();
                    System.out.println("Tree of Integer created");
                    typeT = "Integer";
                }
                else if(line.equals("Double")){
                    tree = new BinarySearchTree<Double>();
                    System.out.println("Tree of Double created");
                    typeT = "Double";
                }
                else if(line.equals("String")){
                    tree = new BinarySearchTree<String>();
                    System.out.println("Tree of String created");
                    typeT = "String";
                } else if(line.substring(0,3).equals("100")){
                    String el = line.substring(3);
                    tree.insertTreeNode(convert(el));
                    out.println("101");
                } else if(line.substring(0,3).equals("200")){
                    String el = line.substring(3);
                    if(tree.deleteTreeNode(convert(el))){
                        out.println("201"+el);
                        //obj_out.writeObject("201");
                        //obj_out.writeObject(tree);
                    } else {
                        out.println("202"+el);
                        //obj_out.writeObject("202");
                    }
                } else if(line.substring(0,3).equals("300")){
                    String el = line.substring(3);
                    if(tree.searchTreeNode(tree.root, convert(el))){
                        out.println("301"+el);
                        //obj_out.writeObject("301"+el);
                    } else {
                        out.println("302"+el);
                        //obj_out.writeObject("302"+el);
                    }
                } else if(line.equals("400")){
                    out.println("401");
                    //obj_out.writeObject("401");
                    obj_out.writeObject(tree);
                }
            }
            /*catch (ClassNotFoundException e){
                e.printStackTrace();
            }*/
            catch (IOException e) {
                System.out.println("Read failed"); System.exit(-1);
            }
        }
    }

    protected void finalize() {
        try {
            in.close();
            out.close();
            //obj_in.close();
            obj_out.close();
            client.close();
            server.close();
        }
        catch (IOException e) {
            System.out.println("Could not close."); System.exit(-1);
        }
    }

    /**
     * zamiana na odpowoedni typ dla drzewa
     * @param el element do  zaminay
     * @return element o odpowiedim typie
     */
    private static Object convert(String el) {
        try {
            if (typeT.equals("Integer")) {
                return Integer.parseInt(el);
            } else if(typeT.equals("Double")){
                return Double.parseDouble(el);
            } else if(typeT.equals("String")){
                return el;
            }
        } catch (NumberFormatException ex){
            //zwróć error do klienta że podał złe dane
            out.println("500");
            /*try {
                obj_out.writeObject("500");
            } catch (IOException e){
                e.printStackTrace();
            }*/
        }
        return null;
    }
}
