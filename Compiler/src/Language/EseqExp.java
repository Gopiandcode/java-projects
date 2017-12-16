package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class EseqExp extends Exp {
    public int maxargs() {
        return Math.max(stm.maxargs(), exp.maxargs());
    }
    public Stm stm; public Exp exp;
    public EseqExp(Stm s, Exp e) {stm = s; exp = e;}
}
