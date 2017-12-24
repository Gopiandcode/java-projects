import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the messge to embed:");
        String message = sc.next();
        String encoded = stringSpacer.spaceOut(message, 80);
        System.out.println(encoded);
        return;
    }
}
