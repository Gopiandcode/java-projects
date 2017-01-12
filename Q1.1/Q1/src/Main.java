/**
 * Created by gopia on 11/01/2017.
 */
public class Main {
    public static void main(String[] args){
        StringSequence reader = new StringSequence("stop");
        reader.getInput();
        String[] array = reader.getSequence();
        for(String s : array) {
            System.out.print(s + ",");
        }
    }
}
