import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a filename (with extension):");
        String input = sc.next();

        fileReader reader = new fileReader(input);

        String data = reader.getData();
        characterCounter counter = new characterCounter();

        counter.count(data);
        System.out.println(counter);
    }
}
