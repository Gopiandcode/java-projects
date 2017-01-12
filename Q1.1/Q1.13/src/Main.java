import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a location to read in from:");
        String input = sc.next();
        System.out.println("Enter a location to write to:");
        String output = sc.next();

        fileReader reader = new fileReader(input);
        fileWriter writer = new fileWriter(output);

        String read_in;

        while(reader.hasNextLine()) {
            read_in = reader.nextLine();
            if(palindromeTester.testPalindrome(read_in)) {
                writer.writeLine(read_in + " is a palindrome.");
            }
            else {
                writer.writeLine(read_in + " is not a palindrome.");
            }
        }

        writer.closeFile();


    }
}
