package com.gopiandcode.xcs;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PredictionArray {
    @NotNull
    @Override
    public String toString() {
        return "PredictionArray{" +
     predictionArray +
                '}';
    }

    private final Map<Action, Double> predictionArray;

    PredictionArray(Map<Action, Double> predictionArray) {
        this.predictionArray = predictionArray;
    }


    public double findMax() {
        return predictionArray.values().stream().max(Double::compare).orElse(0.0);
    }

    public static PredictionArray generatePredictionArray(@NotNull List<Classifier> M) {
        Map<Action, Double> pa = new LinkedHashMap<>();
        Map<Action, Double> fsa = new HashMap<>();
        for (Classifier cl : M) {
            //  pa[cl.a] <- pa[cl.a] + cl.p * cl.f
            pa.put(cl.getAction(),
                    // pa[cl.a]
                    pa.getOrDefault(cl.getAction(), 0.0) +
                            // cl.p * cl.f
                            cl.getP() * cl.getF());
            // fsa[cl.a] = fsa[cl.a] + cl.f
            fsa.put(cl.getAction(),
                    // fsa[cl.a]
                    fsa.getOrDefault(cl.getAction(), 0.0) +
                            // cl.f
                            cl.getF());
        }

        for (Action a : fsa.keySet()) {
            // if fsa[a] != 0.0, pa[a] <- pa[a] / fsa[a]
            if(fsa.get(a) != 0)
                pa.put(a, pa.get(a) / fsa.get(a));
        }
        return new PredictionArray(pa);
    }

    public Action selectRandomAction() {
        List<Action> actions = new ArrayList<>(predictionArray.keySet());
        int size = predictionArray.keySet().size();
        return actions.get(ThreadLocalRandom.current().nextInt(0, size));
    }

    public Action selectBestAction() {
        Optional<Action> bestSeen = Optional.empty();
        Optional<Double> bestScore = Optional.empty();
        for (Map.Entry<Action, Double> actionDoubleEntry : predictionArray.entrySet()) {
            if (!bestScore.isPresent() || bestScore.get() < actionDoubleEntry.getValue()) {
                bestScore = Optional.of(actionDoubleEntry.getValue());
                bestSeen = Optional.of(actionDoubleEntry.getKey());
            }
        }
        return bestSeen.orElse(Action.createRandom());
    }
}
