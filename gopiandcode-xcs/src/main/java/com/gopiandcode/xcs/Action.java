package com.gopiandcode.xcs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.concurrent.ThreadLocalRandom.current;

public class Action {
    private final BinaryAlphabet classification;

    private Action(BinaryAlphabet classification) {
        this.classification = classification;
    }

    public BinaryAlphabet getClassification() {
        return classification;
    }

    public static Action getOneClassification() {
        return new Action(BinaryAlphabet.ONE);
    }

    public static Action getZeroClassification() {
        return new Action(BinaryAlphabet.ZERO);
    }

    @NotNull
    @Override
    public String toString() {
        return "Action(" +
                 classification +
                ')';
    }

    @NotNull
    public static Action createRandom() {
        if (current().nextInt(0, 1) == 0) {
            return getZeroClassification();
        } else {
            return getOneClassification();
        }
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        return classification == action.classification;
    }

    @Override
    public int hashCode() {
        return classification.hashCode();
    }


    public static Action copy(@NotNull Action action) {
        return new Action(action.classification);
    }
}
