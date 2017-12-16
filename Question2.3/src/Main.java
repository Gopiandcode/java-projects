import java.util.Scanner;

/**
 * Created by gopia on 27/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        float min, max;
        int spaces, columns;
        char initHeader, endHeader;
        System.out.println("Enter a min and max value:");
        min = sc.nextFloat(); max = sc.nextFloat();
        System.out.println("Enter the number of columns:");
        columns = sc.nextInt();
        System.out.println("Enter the number of spaces between columns:");
        spaces = sc.nextInt();
        System.out.println("Enter the column headers");
        initHeader = sc.next().charAt(0);
        endHeader = sc.next().charAt(0);

        TemperatureTableGenerator table = new TemperatureTableGenerator(min, max);
        table.setColumnHeaders(initHeader, endHeader);
        table.setColumnNumber(columns);
        table.setColumnSpaces(spaces);
        System.out.println(new TablePrinter(table.getTable()).toString());

    }
}
