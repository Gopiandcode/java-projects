package com.gopiandcode.xcs;

import java.util.*;
import java.util.stream.Collectors;

enum BinaryAlphabet {
    ONE, ZERO
}

enum TernaryAlphabet {
    ONE, ZERO, HASH
}

class Action {
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
}

class Situation {
    private final BinaryAlphabet[] values;
    public BinaryAlphabet[] getValues() {
        return values;
    }
    Situation(BinaryAlphabet[] values) {
        this.values = values;
    }

    Situation(String representation) {
        values = new BinaryAlphabet[representation.length()];
        for(int i = 0; i < representation.length(); i++) {
            switch(representation.charAt(i)) {
                case '0':
                    values[i] = BinaryAlphabet.ZERO;
                    break;
                case '1':
                    values[i] = BinaryAlphabet.ONE;
                    break;
                default:
                    throw new  RuntimeException("Unknown character when constructing situation" + representation.charAt(i));
            }
        }
    }
}

class Problem {
    private final List<Situation> steps;
    public Problem(List<Situation> environment) {
        this.steps = environment;
    }
    public Problem(Situation... steps) {
        this.steps = new ArrayList<>();
        Collections.addAll(this.steps, steps);
    }

    public Situation getSituationAt(int index) {
       return this.steps.get(index);
    }

    public int getSteps() {
        return this.steps.size();
    }
}

class Environment {
    private List<Problem> problems = new ArrayList<>();
    private int problemIndex = 0;
    private int stepIndex = 0;

    public Environment(List<Problem> problems) {
        this.problems = problems;
    }
    public Environment(String... problems) {
        this.problems =  Arrays.stream(problems).map(s -> new Problem(new Situation(s))).collect(Collectors.toList());
    }
    public boolean isEndOfProblem() {
         if(problemIndex < this.problems.size()) {
            Problem problem = this.problems.get(problemIndex);
             return stepIndex >= problem.getSteps();
        } else {
             throw new RuntimeException("Index out of bounds");
        }
    }
    public Problem getLastProblem() {
        if(isEndOfProblem()) {
            return this.problems.get(problemIndex);
        } else {
            throw new RuntimeException("Index out of bounds");
        }
    }

    public Optional<Situation> getSituation() {
        if(problemIndex < this.problems.size()) {
            Problem problem = this.problems.get(problemIndex);
            if(stepIndex < problem.getSteps()) {
                return Optional.of(problem.getSituationAt(stepIndex++));
            } else {
                stepIndex = 0;
                problemIndex++;
                return getSituation();
            }
        } else {
            return Optional.empty();
        }
    }

}

class ReinforcementProgram {
    public double getRewardForAction(Problem situation, Action action) {
        return 0;
    }
}

class Condition {

}

public class XCS {
    /**
     * Maximum size of population
     */
    private long N;
    /**
     * Learning rate for p, e, f, and as
     */
    private double beta;
    /**
     * Parameter used in calculating the fitness of a classifier
     */
    private double alpha;
    /**
     * Parameter used in calculating the fitness of a classifier
     */
    private double epsilon_nought;
    /**
     * Parameter used in calculating the fitness of a classifier
     */
    private double v;

    /**
     * discount factor used in multistep problems to update classifier predictions
     */
    private double gamma;
    /**
     * GA threshold - GA is applied when average time since last GA is greater than this value
     */
    private double theta_GA;
    /**
     * probability of applying crossover
     */
    private double x;
    /**
     * probability of mutating an allele in the offspring
     */
    private double mew;
    /**
     * deletion threshold below which classifier considered for deletion
     */
    private double theta_del;
    /**
     * fraction of mean fitness below which a classifier's fitness is then used for considering it's deletion
     */
    private double delta;
    /**
     * subsumption threshold above which classifiers are elligeable for subsuming others
     */
    private double theta_sub;
    /**
     * probability of using a # in an attribute in C
     */
    private double P_sharp;
    /**
     * initial value for prediction
     */
    private double p_I;
    /**
     * initial value of prediction error
     */
    private double e_I;
    /**
     * initial value of fitness
     */
    private double F_I;
    /**
     * specifies probability during action selection of choosing the value uniform randomly
     */
    private double p_explr;
    /**
     * minimal number of actions required for no covering to happen
     */
    private double theta_mna;
    /**
     * whether offspring are tested for subsumption by parents
     */
    private boolean doGASubsumption;
    /**
     * whether action sets are to be tested for subsuming
     */
    private double doActionSetSubsumption;

}
