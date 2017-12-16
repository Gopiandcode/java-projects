package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class CompoundStm extends Stm {
    public int maxargs() {
        return Math.max(stm1.maxargs(), stm2.maxargs());
    }
    public Stm stm1, stm2;
    public CompoundStm(Stm s1, Stm s2) {stm1 = s1; stm2 = s2;}
}
