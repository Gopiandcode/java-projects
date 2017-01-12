import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the lower bound of the range:");
        int min = sc.nextInt();

        System.out.println("Enter the upper bound of the range:");
        int max = sc.nextInt();

        recursiveRangeProduct rangeProduct = new recursiveRangeProduct(min, max);
        System.out.format("The product of consecutive values between %d and %d is ", min, max);
        System.out.println(rangeProduct.getResult());
    }

}
