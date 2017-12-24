/**
 * Created by gopia on 11/01/2017.
 */
public class Main {
    public static void main(String[] args){
        DoubleParser reader = new DoubleParser(10);
        reader.getInput();
        System.out.println("The Average is: " + reader.getAvg());
    }
}
