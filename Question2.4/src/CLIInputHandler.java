import java.util.Scanner;

/**
 * Created by gopia on 28/01/2017.
 */
public class CLIInputHandler implements InputHandler {
    private Scanner sc;

    public CLIInputHandler() {
        sc = new Scanner(System.in);
    }
    public String getName() {
        System.out.println("Enter a player name:");
        return sc.next();
    }

    public int numberOfNamedPlayers() {
        System.out.println("Enter the number of named players:");
        return sc.nextInt();
    }

    public int getStartInput() {
        System.out.println("Enter the number of players:");
        return sc.nextInt();
    }


    public void playerTurn(String name) {
        System.out.printf("%s rolls the dice\n", name);
    }

    public void showBust(int score, String name) {
        System.out.printf("Sorry %s, you have been bust, you're score is still %d.\n", name, score);
    }

    public void canRoll(boolean rollable) {
        if(rollable) System.out.println("You can still roll.");
        else System.out.println("You can not roll any more.");
    }

    public boolean playerChoice() {
        System.out.println("Do you want to continue?(Y\\N)");
        char response = sc.next().charAt(0);
        if(response == 'Y' || response == 'y') return true;
        else return false;
    }

    public void displayDice(int[] values) {
        System.out.println("The dice values are:");
        for(int i = 0; i<values.length; i++) {
            System.out.printf("%2d ", values[i]);
        }
        System.out.println("");
    }

    public void showPlayerScore(int score, String name) {
        System.out.printf("%s, Your potential score is %d\n", name, score);
    }

    public void showEndGame(int score, String name) {
        System.out.printf("The winning player is %s with a score of %d\nWell Done!\n", name, score);
    }
}
