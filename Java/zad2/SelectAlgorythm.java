/* Kamil Matejuk */
public abstract class SelectAlgorythm {

    int[] array;
    int compare;
    int move;

    SelectAlgorythm(int[] array){
        this.array = array;
    }

    abstract int run(int k);
}
