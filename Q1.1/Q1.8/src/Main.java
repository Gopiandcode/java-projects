/**
 * Created by gopia on 11/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        textInterface main = new textInterface();
        int returnCode;
        do{
            returnCode = main.run();
        } while(returnCode != -1);
    }
}
