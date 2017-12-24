package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class LastExpList extends ExpList {
    public int maxargs() {return 1;}
    public Exp head;
    public LastExpList(Exp h) {head = h;}
}
