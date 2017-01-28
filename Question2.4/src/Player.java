/**
 * Created by gopia on 28/01/2017.
 */
public class Player {
    private static int instanceCount = 0;
    private int currentInstance = instanceCount++;
    public enum PlayerState {
        BUST, PLAYING, WIN
    }
    private PlayerState state;
    private int score;
    private int turn;
    private String name = "";


    public Player() {
        this.state = PlayerState.PLAYING;
        this.turn = 0;
        this.score = 0;
    }
    public String getName() {
        if(this.name == "") return String.format("Player %d", currentInstance);
        else return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBust() {
        this.state = PlayerState.BUST;
    }

    public void notBust() {
        if(this.state == PlayerState.BUST) this.state = PlayerState.PLAYING;
    }

    public boolean canPlay() {
        return this.state == PlayerState.PLAYING;
    }

    public int getScore() {
        return score;
    }

    public int getTurn() {
        return turn;
    }


    public void addScore(int score) {
        assert this.state == PlayerState.PLAYING;
        this.score += score;
        if(this.score >= 5000) {
            this.state = PlayerState.WIN;
        }
    }

    public boolean hasWon() {
        if(this.state == PlayerState.WIN) return true;
        return false;
    }

    public void nextTurn() {
        this.turn+=1;
    }


}
