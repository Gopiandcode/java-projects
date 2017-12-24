import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class doubleManipulator {

    public double getDouble(){
        Scanner sc = new Scanner(System.in);
        return sc.nextDouble();
    }

    public double sqrtDoubleSum(double a, double b) {
        double sum = a + b;
        return Math.sqrt(sum);
    }

}
