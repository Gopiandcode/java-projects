import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by gopia on 12/01/2017.
 */
public class fileReader {
    private String filename;
    private File file;
    private Scanner sc;
    public fileReader(String filename) {
        this.filename = filename;
        file = new File(filename);
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.out.println("Searching in " + System.getenv());
            System.exit(1);
        }
    }

    public String nextLine() {
        return sc.nextLine();
    }

    public boolean hasNextLine() {
        return sc.hasNextLine();
    }

    public String getData() {
        StringBuilder sb = new StringBuilder();
        while(sc.hasNextLine())
            sb.append(sc.nextLine() + "\n");
        return sb.toString();
    }

    public String toString(){
        return "<fileReader Object[file=" + file.toString() + ",filename=" + filename.toString() + "]>";
    }

}
