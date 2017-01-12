import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a un-formatted string:");
        System.out.println(palindromeTester.testPalindrome(sc.nextLine()));
    }
}
