package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class PairExpList extends ExpList {
    public int maxargs() {
        return 1 + tail.maxargs();
    }
    public Exp head; public ExpList tail;
    public PairExpList(Exp h, ExpList t) {head = h; tail = t;}
}
