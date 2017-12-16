import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class StringManipulator {
    private ArrayList<String> store;

    public StringManipulator() {
        this.store = new ArrayList<String>();
    }

    public void getInput(int values) {
        Scanner sc = new Scanner(System.in);
        String read_in;
        for(int i = 0; i<values; i++) {
            read_in = sc.next();
            store.add(read_in);
        }
        return;
    }

    private Comparator<String> reverseCompareString = new Comparator<String>() {
        public int compare(String a, String b) {
            return b.compareTo(a);
        }
    };

    public String toString() {
        StringBuilder sb = new StringBuilder();
        store.sort(reverseCompareString);
        String[] modified = store.toArray(new String[store.size()]);
        for(String s : modified) {sb.append(s); sb.append(", ");}
        return sb.toString();
    }

}
