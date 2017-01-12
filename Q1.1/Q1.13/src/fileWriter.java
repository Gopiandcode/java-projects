import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by gopia on 12/01/2017.
 */
public class fileWriter {
    private String filename;
    private File file;
    private PrintWriter pr;

    public fileWriter(String filename) {
        this.filename = filename;
        file = new File(filename);
        try {
            pr = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
    }

    public void writeLine(String input) {
        pr.println(input);
        return;
    }

    public void writeData(String data) {
        pr.print(data);
        return;
    }

    public void closeFile() {
        pr.close();
        return;
    }


}
