/**
 * Created by gopia on 11/01/2017.
 */
public class baseConverter {
    public static String toBase(int n, int b) {
        StringBuilder sb = new StringBuilder();
        while(n!=0) {
            sb.append(n%b);
            n /= b;
        }
        sb.reverse();
        return sb.toString();
    }
}
