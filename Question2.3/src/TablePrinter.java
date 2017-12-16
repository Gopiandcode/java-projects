import java.util.ArrayList;

/**
 * Created by gopia on 27/01/2017.
 */
public class TablePrinter {
    private ArrayList<ArrayList<String>> table;
    public TablePrinter(ArrayList<ArrayList<String>> table) {
        this.table = table;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(ArrayList<String> entry : this.table) {
            for(String s : entry) {
                sb.append(s);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
