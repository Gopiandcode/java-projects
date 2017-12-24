import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gopia on 12/01/2017.
 */
public class characterCounter {
    private Map<Character, Long> counter;

    public characterCounter() {
        counter = new HashMap<Character, Long>();
    }

    public void clearCounter() {
        counter.clear();
    }

    public void count(String input) {
        for(int i = 0; i<input.length(); i++) {
            char a = input.charAt(i);
            if(Character.isLetter(a)) {
                a = Character.toLowerCase(a);
                Long count = counter.containsKey(a) ? counter.get(a) : 0l;
                counter.put(a, count + 1);
            }
        }
        return;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Character: ");
        counter.forEach((key, value) -> sb.append(String.format("%4c", key)));
        sb.append("\n");
        sb.append("Count    : ");
        counter.forEach((key, value) -> sb.append(String.format("%4d", Math.toIntExact(value))));
        return sb.toString();
    }
}
