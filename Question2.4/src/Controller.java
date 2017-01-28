/**
 * Created by gopia on 28/01/2017.
 */
public class Controller {
    private int playerCount;
    private InputHandler inputHandler;
    private Player[] players;
    private DiceHandler cup;
    public enum GameState {INIT, PLAYING, END}
    GameState state;
    private int maxTurn = 0;
    boolean winningSituation = false;

    public Controller() {
        state = GameState.INIT;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void run() {
        boolean running = true;
        while(running)
        switch(this.state) {
            case INIT:
                initialization();
                break;
            case PLAYING:
                playGame();
                break;
            case END:
                endGame();
                running = false;
                break;
        }
        return;
    }

    private void initializePlayers() {
        players = new Player[playerCount];
        int namedPlayers = inputHandler.numberOfNamedPlayers();
        for(int i = 0; i<playerCount; i++) {
            players[i] = new Player();
            if(namedPlayers > 0) {
                String name = inputHandler.getName();
                players[i].setName(name);
                namedPlayers--;
            }
        }
    }

    private void initialization() {
        inputHandler = new CLIInputHandler();
        this.playerCount = inputHandler.getStartInput();
        initializePlayers();
        this.state = GameState.PLAYING;
    }

    private void playerTurn(Player p) {
        int turn = 0;
        int score = 0;
        cup = new DiceHandler();
        while(cup.canRoll() && p.canPlay()) {
            p.nextTurn();
            inputHandler.playerTurn(p.getName());
            cup.rollDice();
            inputHandler.displayDice(cup.getDice());
            score = cup.getScore();
            if(turn == 0 && score < 300) {inputHandler.showBust(p.getScore(), p.getName());break;}
            inputHandler.showPlayerScore(p.getScore() + score, p.getName());
            inputHandler.canRoll(cup.canRoll());
            if(cup.canRoll()) if(!inputHandler.playerChoice()) break;
            turn++;
        }
        p.addScore(score);
    }
    private Player getWinner() {
        int maxScore = 0;
        int winningIndex = -1;
        for(int i = 0; i<playerCount; i++) {
            int score = players[i].getScore();
            if(score > maxScore) {
                maxScore = score;
                winningIndex = i;
            }
        }
        return players[winningIndex];
    }

    private void playGame() {
        boolean anyPlays = false;
        if(!winningSituation) winningSituation = checkWinners();
        for(int i = 0; i< this.playerCount; i++) {
            if(players[i].canPlay()) {
                if(winningSituation) if(players[i].getTurn() >= maxTurn) {players[i].setBust();break;}
                anyPlays = true;
                playerTurn(players[i]);
            }
        }
        if(!anyPlays) this.state = GameState.END;
    }

    private boolean checkWinners() {
        for(int i = 0; i<playerCount; i++) if(players[i].hasWon()) {maxTurn = players[i].getTurn(); return true;}
        return false;
    }

    private void endGame() {
        Player winner = getWinner();

        inputHandler.showEndGame(winner.getScore(), winner.getName());

    }

}
