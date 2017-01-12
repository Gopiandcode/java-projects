import java.util.Random;

/**
 * Created by gopia on 12/01/2017.
 */
public class FermatPrimeGen extends PrimeGen {
    private int sample_size;

    public FermatPrimeGen(int samplesize){
        super();
        this.sample_size = samplesize;
    }

    @Override
    public boolean testPrime(long input) {
        if(input %2 == 0) return false;
        Random gen = new Random();
        long a;
        for(int i = 0; i< sample_size; i++) {
            a = gen.nextLong();
            if(FermatPrimeGen.gcd(input, a) != 1) {return false;}
        }
        return true;
    }
}
