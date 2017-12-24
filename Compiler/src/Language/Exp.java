package Language;

/**
 * Created by gopia on 05/09/2017.
 */
public abstract class Exp {

    public abstract int maxargs();

    public static int maxargs(Stm stm){
        return stm.maxargs();
    }


    public static int maxargs(ExpList exp){
        return exp.maxargs();
    }
}
