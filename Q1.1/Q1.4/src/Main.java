/**
 * Created by gopia on 11/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        RandomStatGen reader = new RandomStatGen(-1.0, 1.0, 100000);
        reader.genStats();
        System.out.println(reader);
        reader = new RandomStatGen(-1.0, 1.0, 100000);
        reader.altGenStats();
        System.out.println(reader);

    }
}
