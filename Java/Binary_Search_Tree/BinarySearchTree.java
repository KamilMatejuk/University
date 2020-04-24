/* Kamil Matejuk */
import java.io.Serializable;

public class BinarySearchTree<T> implements Serializable {
    
    TreeNode root;

    /**
     * zerowanie aktualnego drzewa
     */
    public BinarySearchTree(){
        root = null;
    }

    /**
     * Wstawienie do drzewa nowego węzła o wartości value
     * @param value
     */
    public void insertTreeNode(T value) {
        if (value != null) {
            TreeNode node = new TreeNode(value);
            if (root == null) {
                root = node;
                System.out.println("Element \"" + value + "\" was added succesfully");
            } else {
                TreeNode nd = root;
                TreeNode parent;
                while (true) {
                    parent = nd;
                    if(node.compareTo(nd.value)>0){
                        nd = nd.rightChild;
                        if (nd == null) {
                            parent.setRightChild(node);
                            System.out.println("Element \"" + value + "\" was added succesfully");
                            return;
                        }
                    } else {
                        nd = nd.leftChild;
                        if (nd == null) {
                            parent.setLeftChild(node);
                            System.out.println("Element \"" + value + "\" was added succesfully");
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * usunęcie węzła o wartości value
     * @param value
     * @return prawda gdy usunięto, fałsz gdy nie
     */
    public boolean deleteTreeNode(T value) {
        //TODO nie działa, cały czas zwraca false
        //http://www.algolist.net/Data_structures/Binary_search_tree/Removal
        if (root == null || value == null) {
            return false;
        } else {
            if (root.value.equals(value)){
                TreeNode auxRoot = new TreeNode(0);
                auxRoot.setLeftChild(root);
                boolean result = root.remove(value, auxRoot);
                root = auxRoot.leftChild;
                return result;
            } else {
                return root.remove(value, null);
            }
        }
    }

    /**
     * wyszukanie rekurencyjnie węzła o warośi value
     * @param node wezeł który rozpatrujemy
     * @param value wartość której szukamy
     * @return prawda gdy znaleziono, fałsz gdy nie
     */
    public boolean searchTreeNode(TreeNode node, T value){
        if(node == null || value == null){
            return false;
        } else if(node.value.equals(value)){
            return true;
        } else if(node.compareTo(value)<0){
            return searchTreeNode(node.rightChild, value);
        } else {
            return searchTreeNode(node.leftChild, value);
        }
    }

}
