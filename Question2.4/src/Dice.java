import java.util.Random;

/**
 * Created by gopia on 28/01/2017.
 */
public class Dice {
    private static Random gen;
    private int val = 0;

    static {
        gen = new Random();
    }


    public Dice() {
        this.val = gen.nextInt(6);
        assert val >= 0;
        assert val <= 6;
    }

    public int getVal() {
        return this.val;
    }

    public void rollDice(){
        this.val = gen.nextInt(6);
        assert val >= 0;
        assert val <= 6;
        return;
    }



}
