import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class DoubleParser {
    private int len;
    private double[] store;
    public DoubleParser (int len) {
        this.len = len;
        this.store = new double[len];
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);
        double read_in;
        for(int i = 0; i<len; i++) {
            read_in = sc.nextDouble();
            store[i] = read_in;
        }
        return;
    }

    public double getAvg() {
        double sum = 0D;
        for(double d : this.store) sum += d;
        return sum/len;
    }

}
