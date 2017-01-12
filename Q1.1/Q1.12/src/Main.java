import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a filename to read from:");
        String input = sc.next();
        fileReader reader = new fileReader(input);
        System.out.println("Enter a filename to write to:");
        String output = sc.next();
        fileWriter writer = new fileWriter(output);

        StringBuilder sb = new StringBuilder();

        while(reader.hasNextLine()) {
            sb.append(reader.getData());
            System.out.println(sb.reverse().toString());
            writer.writeLine(sb.toString());
            sb.delete(0, sb.length());
        }
        writer.closeFile();
    }
}
