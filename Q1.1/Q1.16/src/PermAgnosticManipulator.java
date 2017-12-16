import java.util.HashSet;
import java.util.Set;

/**
 * Created by gopia on 12/01/2017.
 */
public class PermAgnosticManipulator {
    public static boolean compareCharray(char[] a, char[] b) {
        if(a.length != b.length) return false;
        Set<Character> aSet = new HashSet<Character>(), bSet = new HashSet<>();
        for(char c: a) aSet.add(c);
        for(char c: b) bSet.add(c);

        return bSet.equals(aSet);
    }

}
