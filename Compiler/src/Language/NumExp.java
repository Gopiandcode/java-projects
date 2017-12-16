package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class NumExp extends Exp {
    public int maxargs() {
        return 0;
    }
    public int num;
    public NumExp(int n) {num = n;}
}
