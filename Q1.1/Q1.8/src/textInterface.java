import java.util.Scanner;

/**
 * Created by gopia on 11/01/2017.
 */
public class textInterface {
    Scanner sc;
    int state = 0; //0 for selection, 1 for milis, 2 for meters, 3 for km, 4 for exit
    // run returns -1 for exit condition

    public textInterface() {
        sc = new Scanner(System.in);
    }


    public int run() {
        switch(state) {
            case 0:
                selectionScreen();
                break;
            case 1:
                millisConversion();
                break;
            case 2:
                metersConversion();
                break;
            case 3:
                kmConversion();
                break;
            default:
                return -1;
        }
        return 0;
    }

    public void selectionScreen() {

        System.out.println("1. Convert millimeters to feet.");
        System.out.println("2. Convert meters to inches..");
        System.out.println("1. Convert kilometers to yards..");
        System.out.println("4. Quit");
        this.state = sc.nextInt();
    }

    public void millisConversion() {
        System.out.println("Enter a value in millimeters to be converted to feet:");
        double inp = sc.nextDouble();
        System.out.format("%f millimeters in feet is %f\n", inp, unitConverter.milliToFeet(inp));
        state = 0;
    }

    public void metersConversion() {
        System.out.println("Enter a value in meters to be converted to inches:");
        double inp = sc.nextDouble();
        System.out.format("%f meters in inches is %f\n", inp, unitConverter.metersToInches(inp));
        state = 0;
    }


    public void kmConversion() {
        System.out.println("Enter a value in kilometers to be converted to yards:");
        double inp = sc.nextDouble();
        System.out.format("%f meters in inches is %f\n", inp, unitConverter.kilometersToYards(inp));
        state = 0;
    }

}
