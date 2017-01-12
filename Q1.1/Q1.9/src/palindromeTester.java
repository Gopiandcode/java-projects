/**
 * Created by gopia on 12/01/2017.
 */
public class palindromeTester {
    public static boolean testLongInt(long val){
        long rev = 0, temp = val;
        while(temp != 0) {
            rev += temp%10;
            temp /= 10;
            if(temp != 0) {
                rev *= 10;
            }
        }
        System.out.println(val + " reversed is " + rev);

        return (rev == val);
    }
}
