import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(), b = sc.nextInt();

        System.out.format("%d in base %d is %s", a, b, baseConverter.toBase(a,b));

    }
}
