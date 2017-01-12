/**
 * Created by gopia on 12/01/2017.
 */
public abstract class PrimeGen {
    protected static long gcd(long a, long b) {
        if(b == 0) return a;
        else return gcd(b, a%b);
    }
    public abstract boolean testPrime(long input);

    public void printPrimes(){
        for(long i = 0; i<Long.MAX_VALUE; i++) {
            if(testPrime(i)) System.out.println(i);
        }
    }
}
