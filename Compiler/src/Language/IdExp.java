package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class IdExp extends Exp {
    public int maxargs(){
        return 0;
    }
    public String id;
    public IdExp(String i) {id = i;}
}
