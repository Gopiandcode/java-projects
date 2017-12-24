package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class OpExp extends Exp {
    public int maxargs() {
        return Math.max(left.maxargs(), right.maxargs());
    }
    public Exp left, right; public int oper;
    final public static int PLUS=1,MINUS=2,TIMES=3,DIV=4;
    public OpExp(Exp l, int o, Exp r) {left = l; right = r; oper = o;}
}
