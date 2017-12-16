import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class StringSequence {
    private ArrayList<String> input;
    private String stop_marker = "stop";
    public StringSequence(String stop_marker) {
        input = new ArrayList<String>();
        this.stop_marker = stop_marker;
    }




    public void getInput() {
        Scanner sc = new Scanner(System.in);

        String read_in;

        do {
            read_in = sc.next();
            if(read_in.compareTo(stop_marker) != 0) input.add(read_in);
        } while(read_in.compareTo(stop_marker) != 0);
        return;
    }
    public String[] getSequence(){
        return (String[]) this.input.toArray(new String[input.size()]);
    }


}
