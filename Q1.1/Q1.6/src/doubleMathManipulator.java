import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class doubleMathManipulator {
    private double a, b, c;
    public void getInput() {
        Scanner sc = new Scanner(System.in);
        this.a = sc.nextDouble();
        this.b = sc.nextDouble();
    }

    public double getResult() {
        this.c = Math.sqrt(a+b);
        return this.c;
    }
    public String toString() {
       return "Results[a=" + this.a + ",b=" + this.b +",c=" + this.c + "]";
    }
}
