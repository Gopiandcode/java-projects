package com.gopiandcode.xcs;

import org.jetbrains.annotations.NotNull;

public class ReinforcementProgram {
    @NotNull
    private SystemScorer scorer = new SystemScorer();
    private double positiveReward;
    private double incorrectPunishment;

    ReinforcementProgram(double positiveReward, double incorrectPunishment) {
        this.positiveReward = positiveReward;
        this.incorrectPunishment = incorrectPunishment;
    }

    public double getRewardForAction(@NotNull Situation situation, @NotNull Action action) {
        if(isCorrect(situation, action)) {
            scorer.recordCorrect();
            return positiveReward;
        } else {
            scorer.recordIncorrect();
            return incorrectPunishment;
        }
    }

    public static boolean isCorrect(@NotNull Situation situation, @NotNull Action action) {
        BinaryAlphabet[] situationValues = situation.getValues();
        int index;
        if(situationValues[0] == BinaryAlphabet.ZERO && situationValues[1] == BinaryAlphabet.ZERO) {
            index = 0;
        } else if(situationValues[0] == BinaryAlphabet.ZERO && situationValues[1] == BinaryAlphabet.ONE) {
            index = 1;
        } else if(situationValues[0] == BinaryAlphabet.ONE && situationValues[1] == BinaryAlphabet.ZERO) {
            index = 2;
        } else if(situationValues[0] == BinaryAlphabet.ONE && situationValues[1] == BinaryAlphabet.ONE) {
            index = 3;
        } else {
            throw new RuntimeException("Invalid configuration of situation values - " + situationValues[0] + ", " + situationValues[1]);
        }

        index = index + 2;
//        System.out.println("RP[" + situation + "->" + situationValues[index] + "] ? " + action + "  =============== " + (action.getClassification() == situationValues[index]));
        return situationValues[index] == action.getClassification();
    }

    @NotNull
    public SystemScorer getSystemScorer() {
        return scorer;
    }
}
