package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public class PrintStm extends Stm {
    public int maxargs(){
        return exps.maxargs();
    }
    public ExpList exps;
    public PrintStm(ExpList e) {exps = e;}
}
