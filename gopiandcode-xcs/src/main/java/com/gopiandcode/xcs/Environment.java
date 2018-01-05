package com.gopiandcode.xcs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Environment {
    private List<Problem> problems = new ArrayList<>();
    private int problemIndex = 0;
    private int stepIndex = 0;

    @NotNull
    @Override
    public String toString() {
        return "Environment{" +
                "problems=" + problems +
                ", problemIndex=" + problemIndex +
                ", stepIndex=" + stepIndex +
                '}';
    }

    public Environment(int problemCount) {
        for(int i = 0; i < problemCount; i++) {
            problems.add(new Problem(Situation.createRandom(6)));
        }
    }

    public Environment(List<Problem> problems) {
        this.problems = problems;
    }

    public Environment(@NotNull String... problems) {
        this.problems = Arrays.stream(problems).map(s -> new Problem(new Situation(s))).collect(Collectors.toList());
    }

    public boolean isEndOfProblem() {
        if (problemIndex < this.problems.size()) {
            Problem problem = this.problems.get(problemIndex);
            return stepIndex >= problem.getSteps();
        } else {
            throw new RuntimeException("Index out of bounds");
        }
    }

    public Problem getLastProblem() {
        if (isEndOfProblem()) {
            return this.problems.get(problemIndex);
        } else {
            throw new RuntimeException("Index out of bounds");
        }
    }

    public Optional<Situation> getSituation(long timestep) {
        if (problemIndex < this.problems.size()) {
            Problem problem = this.problems.get(problemIndex);
            if (stepIndex < problem.getSteps()) {
                return Optional.of(problem.getSituationAt(stepIndex++));
            } else {
                stepIndex = 0;
                problemIndex++;
                return getSituation(timestep);
            }
        } else {
            return Optional.empty();
        }
    }

    public int getProblemSize() {
        return 6;
    }
}
