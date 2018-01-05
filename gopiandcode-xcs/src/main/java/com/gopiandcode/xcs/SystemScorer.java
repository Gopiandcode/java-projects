package com.gopiandcode.xcs;

public class SystemScorer {
    private int correct = 0;
    private int total = 0;
    public void recordCorrect() {
        correct++;
        total++;
    }
    public void recordIncorrect() {
        total++;
    }

    public double getAccuracy() {
        if(total != 0)
            return (double) correct/(double)total;
        return 0;
    }

    public void reset() {
        correct = 0;
        total = 0;
    }
}
