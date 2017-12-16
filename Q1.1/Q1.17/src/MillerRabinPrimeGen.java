import java.util.Random;

/**
 * Created by gopia on 12/01/2017.
 */
public class MillerRabinPrimeGen extends PrimeGen{
    private long sample_size;

    public MillerRabinPrimeGen(long sample_size) {
        this.sample_size = sample_size;
    }

    private boolean check(long input, long a, long d, long s) {
        long result;
        for(long i = 0; i<s; i++) {
            result = (long) Math.pow(a, (Math.pow(2, i)*d)) % input;
            if(result == 1 || result == input-1) return true;
        }
        return false;
    }



    @Override
    public boolean testPrime(long input) {
        if(input % 2 == 0) return false;
        Random gen = new Random();
        long binLength =(long)Math.log((double) input);
        long s = 0, d = 0, a;

        for(long i = 0; i<binLength; i++) {
            if(input % Math.pow(2, i) == 0) {
                s = i;
                d = (long) (input/Math.pow(2,i));
                break;
            }
        }
        for(long i = 0; i<sample_size; i++) {
            a = gen.nextLong();
            while (a > input - 1 || a < 1) a = gen.nextLong();
            if(!check(input, a, d, s)) return false;
        }
        return true;
    }
}
