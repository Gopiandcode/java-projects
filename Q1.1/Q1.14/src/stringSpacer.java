/**
 * Created by gopia on 12/01/2017.
 */
public class stringSpacer {
    public static String spaceOut(String message, int length) {
        int start =  (length - message.length())/2;
        int remaining = length - start - message.length();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<start; i++) {sb.append(" ");}
        sb.append(message);
        for(int i =0; i<remaining; i++) {sb.append(" ");}

        return sb.toString();
    }
}
