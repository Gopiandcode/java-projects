package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class AssignStm extends Stm {
    public int maxargs() {
        return exp.maxargs();
    }
    public String id; public Exp exp;
    public AssignStm(String i, Exp e) {id = i; exp = e;}
}
