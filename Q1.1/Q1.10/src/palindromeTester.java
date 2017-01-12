import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gopia on 12/01/2017.
 */
public class palindromeTester {
    private static String sanitizeString(String input) {
        StringBuilder sb = new StringBuilder();
        char a;
        for(int i = 0; i< input.length(); i++) {
            a = input.charAt(i);
            if(Character.isLetter(a)) sb.append(Character.toLowerCase(a));
        }
        input =  sb.toString();
        return input;
    }

    public static boolean testPalindrome(String input) {
        String reverse;
        input = sanitizeString(input);
        StringBuilder sb = new StringBuilder();
        sb.append(input);
        sb.reverse();
        reverse = sb.toString();
        return (reverse.equals(input));
    }


}
