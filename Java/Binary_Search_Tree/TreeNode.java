/* Kamil Matejuk */
import com.sun.source.tree.Tree;
import java.io.Serializable;

public class TreeNode<T> implements Comparable<T>, Serializable {

    T value;
    TreeNode<T> leftChild = null;
    TreeNode<T> rightChild = null;

    TreeNode(T k){
        this.value = k;
    }

    /**
     * settery dla parwego ilewwego dzieckaa
     * @param l węzeł
     */
    void setLeftChild(TreeNode l){
        this.leftChild = l;
    }
    void setRightChild(TreeNode r){
        this.rightChild = r;
    }

    /**
     * usuniecie danego węzła
     * @param value wartosc
     * @param parent węzeł-rodzic
     * @return prawda gdy usunieto, fałsz gdy nie
     */
    public boolean remove(T value, TreeNode parent) {
        if (this.compareTo(value)>0) {
            if (leftChild != null) {
                return leftChild.remove(value, this);
            } else {
                return false;
            }
        } else if (this.compareTo(value)<0) {
            if (rightChild != null) {
                return rightChild.remove(value, this);
            } else {
                return false;
            }
        } else {
            if (leftChild != null && rightChild != null) {
                this.value = rightChild.minValue();
                rightChild.remove(this.value, this);
            } else if (parent.leftChild == this) {
                parent.leftChild = (leftChild != null) ? leftChild : rightChild;
            } else if (parent.rightChild == this) {
                parent.rightChild = (leftChild != null) ? leftChild : rightChild;
            }
            return true;
        }
    }

    /**
     * minimalna wartość w poddrzewie
     * @return wartośc węzła
     */
    public T minValue() {
        if (this.leftChild == null) {
            return this.value;
        } else {
            return this.leftChild.minValue();
        }
    }

    /**
     * porównanie wartości węzłów na podstawie ich typów
     * @param that wartośc do której porownujemy
     * @return 1 jezeli po prawej, -1 jezeli po lewej
     */
    @Override
    public int compareTo(T that) {
        if(value.getClass() == Integer.class){
            if((int) this.value < (int) that){
                return -1;
            } else {
                return 1;
            }
        }
        else if(value.getClass() == Double.class){
            if((double) this.value < (double) that){
                return -1;
            } else {
                return 1;
            }
        }
        else if(value.getClass() == String.class){
            String a = this.value.toString();
            String b = that.toString();
            int length = Math.min(a.length(), b.length());
            for(int i=0; i<length; i++){
                int aASCII = (int) a.charAt(i);
                int bASCII = (int) b.charAt(i);
                if(aASCII > bASCII){
                    return 1;
                } else if (aASCII < bASCII){
                    return -1;
                }
            }
            return 1;
        }
        return 0;
    }

    /**
     * wyswietlanie węzłów
     * @param prefix poprzedni string
     * @param isTail czy ma dzieci
     * @param sb aktualny string
     * @return wyglad jako string
     */
    public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if(rightChild!=null) {
            rightChild.toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(value.toString()).append("\n");
        if(leftChild!=null) {
            leftChild.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }
        return sb;
    }

    /**
     * konwwertuje stringbuilder do stringa
     * @return wyglad węzła w stringu
     */
    @Override
    public String toString() {
        return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
    }
}
