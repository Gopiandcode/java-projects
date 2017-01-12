import java.util.Random;

/**
 * Created by gopia on 11/01/2017.
 */
public class RandomStatGen {
    double min, max, sample_min, sample_max, average, scale;
    int sample_size;

    public RandomStatGen(double min, double max, int sample){
        this.min = min;
        this.max = max;
        this.scale = max-min;
        this.sample_size = sample;
    }
    public void altGenStats() {
        Random generator = new Random();
        double sum = 0;
        double readIn;
        sample_max = min;
        sample_min = max;
        for(int i = 0; i<sample_size; i++) {
            readIn = generator.nextDouble();
            if(generator.nextBoolean()) readIn *= -1;
            sum += readIn;
            if(sample_min > readIn) {
                sample_min = readIn;
            }
            if(sample_max < readIn) {
                sample_max = readIn;
            }
        }
        this.average = sum/sample_size;

    }
    public void genStats() {
        Random generator = new Random();
        double sum = 0;
        double readIn;
        sample_max = min;
        sample_min = max;
        for(int i = 0; i<sample_size; i++) {
            readIn = generator.nextDouble();
            readIn = readIn*scale + min;
            sum += readIn;
            if(sample_min > readIn) {
                sample_min = readIn;
            }
            if(sample_max < readIn) {
                sample_max = readIn;
            }
        }
        this.average = sum / sample_size;
    }

    public String toString() {
        return "Results[Avg=" + average + ",min=" + sample_min + ",max=" + sample_max + "]";
    }
}
