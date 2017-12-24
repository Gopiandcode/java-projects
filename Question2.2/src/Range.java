import java.util.ArrayList;

/**
 * Created by gopia on 23/01/2017.
 */
public class Range {
    private int low, high;
    private ArrayList<Integer> sequence = new ArrayList<Integer>();
    public Range(int lower, int upper) {
        if(upper > lower) throw new IllegalArgumentException("First arg must be less than second.\n");
        this.low = lower;
        this.high = upper;
        while(lower <= upper) {
            sequence.add(lower);
            lower++;
        }
    }
    public int getLower() {
        return this.low;
    }
    public int getUpper() {
        return this.high;
    }

    public boolean contains(int n) {
        return n <= high && n >= low;
    }

    public ArrayList<Integer> getValues() {
        return new ArrayList<Integer>(sequence);
    }
}
