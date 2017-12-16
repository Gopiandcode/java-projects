/**
 * Created by gopia on 28/01/2017.
 */
public interface InputHandler {
    /* Gets the data for the class to start
            - Number of players
            - Computer Opponents
            - Real Opponents
    */
    int getStartInput();

    void playerTurn(String name);

    String getName();

    int numberOfNamedPlayers();

    boolean playerChoice();

    void canRoll(boolean rollable);

    void showBust(int score,String name);

    void displayDice(int[] values);

    void showPlayerScore(int score, String name);

    void showEndGame(int score, String name);

}
