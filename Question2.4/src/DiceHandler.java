import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gopia on 28/01/2017.
 */
public class DiceHandler {
    private Dice[] dice = new Dice[5];
    private Boolean[] scoringDice = new Boolean[5];
    {for(int i = 0; i<5; i++) {
            this.dice[i] = new Dice();
            this.scoringDice[i] = false;
    }}

    public DiceHandler() {}

    public int[] getDice() {
        int[] output = new int[5];
        for(int i = 0; i<5; i++) {
            output[i] = this.dice[i].getVal();
        }
        return output;
    }

    public boolean isScoring(int index) {
        return this.scoringDice[index];
    }

    public void removeDice(int value, int count) {
        int[] diceValues = getDice();
        for(int i = 0; i<5 && count > 0; i++) {if(diceValues[i] == value) {count--;scoringDice[i] = true;}}
        return;
    }

    public void rollDice() {
        for(int i = 0; i<5; i++) {
            if(!scoringDice[i])
            dice[i].rollDice();
        }
    }

    public boolean canRoll() {
        for(int i = 0; i<5; i++) {
            if(scoringDice[i] == false) return true;
        }
        return false;
    }

    public int getScore() {
        int score = 0;
        HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
        int[] diceValues = getDice();
        for(int i = 0; i< 5; i++) {
            if(!this.isScoring(i))
            counter.put(diceValues[i], counter.getOrDefault(diceValues[i],0)+1);
        }
        for(Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            if(entry.getValue()>=3) {
                if(entry.getKey() == 1) {
                    score += 1000;
                    removeDice(1, 3);
                } else {
                    score += 100 * entry.getKey();
                    removeDice(1, 3);
                }
            }
        }

        for(int i = 0; i<5; i++) {
            if(!this.scoringDice[i]) {
                if(diceValues[i] == 1) {score += 100; scoringDice[i] = true;}
                if(diceValues[i] == 1) {score += 50; scoringDice[i] = true;}
            }
        }

        return score;
    }



}
