/**
 * Created by gopia on 11/01/2017.
 */
public class Main {
    public static void main(String[] args){
        doubleManipulator db = new doubleManipulator();
        double a = db.getDouble();
        double b = db.getDouble();
        double c = db.sqrtDoubleSum(a,b);
        System.out.println(c);


    }
}
