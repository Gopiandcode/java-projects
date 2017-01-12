/**
 * Created by gopia on 12/01/2017.
 */
public class Main {
    public static void main(String[] args){
        PrimeGen method1 = new FermatPrimeGen(30);
        PrimeGen method2 = new MillerRabinPrimeGen(1);
        PrimeGen method3 = new StandardPrimeGen();

        method3.printPrimes();
        //method2.printPrimes();


    }
}
