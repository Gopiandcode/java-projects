/**
 * XCS Implementation - Based on An Algorithmic Description of XCS - Martin V. Butz and Stewart W.Wilson
 */
package com.gopiandcode.xcs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.concurrent.ThreadLocalRandom.current;

enum BinaryAlphabet {
    ONE, ZERO;
    public String toString() {
        switch(this) {
            case ONE:
                return "1";
            case ZERO:
                return "0";
        }
        return null;
    }

}

enum TernaryAlphabet {
    ONE, ZERO, HASH;

    public String toString() {
      switch(this) {
          case ONE:
              return "1";
          case ZERO:
              return "0";
          case HASH:
              return "#";
      }
      return null;
    }
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

class Condition {
    private final TernaryAlphabet[] values;

    public TernaryAlphabet[] getValues() {
        return values;
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TernaryAlphabet value : values) {
           sb.append(value.toString());
        }
        return "Condition(" +
                sb.toString() +
                ')';
    }

    Condition(TernaryAlphabet[] values) {
        this.values = values;
    }

    Condition(@NotNull String representation) {
        values = new TernaryAlphabet[representation.length()];
        for (int i = 0; i < representation.length(); i++) {
            switch (representation.charAt(i)) {
                case '0':
                    values[i] = TernaryAlphabet.ZERO;
                    break;
                case '1':
                    values[i] = TernaryAlphabet.ONE;
                    break;
                case '#':
                    values[i] = TernaryAlphabet.HASH;
                    break;
                default:
                    throw new RuntimeException("Unknown character when constructing condition" + representation.charAt(i));
            }
        }
    }

    public boolean matches(@NotNull Situation situation) {
        BinaryAlphabet[] situationValues = situation.getValues();
        if (situationValues.length != this.values.length) {
            throw new RuntimeException("Situation does not match the size of the Condition");
        }
        for (int i = 0; i < situationValues.length; i++) {
            TernaryAlphabet value = this.values[i];
            if (value != TernaryAlphabet.HASH) {
                switch (situationValues[i]) {
                    case ONE:
                        if (value != TernaryAlphabet.ONE) return false;
                        break;
                    case ZERO:
                        if (value != TernaryAlphabet.ZERO) return false;
                        break;
                }
            }
        }

        return true;
    }

    public long hashCount() {
        return Arrays.stream(values).filter(ternaryAlphabet -> ternaryAlphabet == TernaryAlphabet.HASH).map(ternaryAlphabet -> 1).reduce(Integer::sum).orElse(0);
    }

    public static Condition createRandom(int size) {
        TernaryAlphabet[] ternaryAlphabets = new TernaryAlphabet[size];
        for (int i = 0; i < size; i++) {

            TernaryAlphabet value;
            switch (current().nextInt(0, 3)) {
                case 0:
                    value = TernaryAlphabet.ZERO;
                    break;
                case 1:
                    value = TernaryAlphabet.ONE;
                    break;
                case 2:
                    value = TernaryAlphabet.HASH;
                    break;
                default:
                    throw new RuntimeException("Unknown random value recieved in creating Condition");
            }
            ternaryAlphabets[i] = value;
        }
        return new Condition(ternaryAlphabets);
    }

    public static Condition createRandom(int size, double P_hash) {
        TernaryAlphabet[] ternaryAlphabets = new TernaryAlphabet[size];
        for (int i = 0; i < size; i++) {

            TernaryAlphabet value = TernaryAlphabet.HASH;
            if (current().nextDouble() > P_hash) {
                switch (current().nextInt(0, 2)) {
                    case 0:
                        value = TernaryAlphabet.ZERO;
                        break;
                    case 1:
                        value = TernaryAlphabet.ONE;
                        break;
                    default:
                        throw new RuntimeException("Unknown random value recieved in creating Condition");
                }
            }
            ternaryAlphabets[i] = value;
        }
        return new Condition(ternaryAlphabets);
    }

    public static Condition createCovering(@NotNull Situation sigma, double p_hash) {
        BinaryAlphabet[] sigmaValues = sigma.getValues();
        TernaryAlphabet[] values = new TernaryAlphabet[sigmaValues.length];

        for (int i = 0; i < values.length; i++) {
            if (current().nextDouble() < p_hash) {
                // x <- #
                values[i] = TernaryAlphabet.HASH;
            } else {
                // x <- the corresponding value in sigmah
                switch (sigmaValues[i]) {
                    case ONE:
                        values[i] = TernaryAlphabet.ONE;
                        break;
                    case ZERO:
                        values[i] = TernaryAlphabet.ZERO;
                        break;
                }
            }
        }
        return new Condition(values);
    }

    public static Condition copy(@NotNull Condition condition) {
       return new Condition(Arrays.copyOf(condition.getValues(), condition.getValues().length));
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Condition condition = (Condition) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if(values.length != condition.values.length)
            return false;
        for(int i = 0; i < values.length; i++) {
            if(values[i] != condition.values[i])
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    public void setValues(int i, TernaryAlphabet value) {
       values[i] = value;
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

    Situation(@NotNull String representation) {
        values = new BinaryAlphabet[representation.length()];
        for (int i = 0; i < representation.length(); i++) {
            switch (representation.charAt(i)) {
                case '0':
                    values[i] = BinaryAlphabet.ZERO;
                    break;
                case '1':
                    values[i] = BinaryAlphabet.ONE;
                    break;
                default:
                    throw new RuntimeException("Unknown character when constructing situation" + representation.charAt(i));
            }
        }
    }

    public static Situation createRandom(int size) {
        BinaryAlphabet[] binaryAlphabets = new BinaryAlphabet[size];
        for (int i = 0; i < size; i++) {
            BinaryAlphabet value;
            switch (current().nextInt(0, 2)) {
                case 0:
                    value = BinaryAlphabet.ZERO;
                    break;
                case 1:
                    value = BinaryAlphabet.ONE;
                    break;
                default:
                    throw new RuntimeException("Unknown random value recieved in creating Condition");
            }
            binaryAlphabets[i] = value;
        }
        return new Situation(binaryAlphabets);
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BinaryAlphabet situationValue : values) {
            sb.append(situationValue.toString());
        }

        return "Situation(" +
                  sb.toString() +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Situation situation = (Situation) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(values, situation.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
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

    @NotNull
    @Override
    public String toString() {
        return "Problem(" +
                steps +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem = (Problem) o;

        return steps != null ? steps.equals(problem.steps) : problem.steps == null;
    }

    @Override
    public int hashCode() {
        return steps != null ? steps.hashCode() : 0;
    }
}

class Environment {
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

class SystemScorer {
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
class ReinforcementProgram {
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

class Classifier {

    public static Situation GenerateMatching(Classifier cl) {
        TernaryAlphabet[] values = cl.condition.getValues();
        BinaryAlphabet[] result = new BinaryAlphabet[values.length];
        for (int i = 0; i < values.length; i++) {
           switch(values[i]) {
               case ONE:
                   result[i] =  BinaryAlphabet.ONE;
                   break;
               case ZERO:
                   result[i] =  BinaryAlphabet.ZERO;
                   break;
               case HASH:
                   if(ThreadLocalRandom.current().nextDouble() > 0.5) {
                       result[i] =  BinaryAlphabet.ZERO;
                   } else {
                       result[i] =  BinaryAlphabet.ONE;
                   }
                   break;
           }
        }

        return new Situation(result);
    }
    @NotNull
    @Override
    public String toString() {
        return "Classifier(" +
                 condition +
                " -> " + action +
                ")(" + n + ")";
    }

    private Condition condition;
    private Action action;
    /**
     * acerage payoff expected if classifier matches and action used
     */
    private double p;
    /**
     * estimates errors in prediction value
     */
    private double e;
    private double f;
    /**
     * number of times since creationg classifier has belonged to an action set
     */
    private double exp;
    /**
     * the time step the last of a GA in which the classifier belonged
     */
    private long ts;
    /**
     * estimate of the average size of the action set the classifier belongs to
     */
    private double as;
    /**
     * the number of microclassifiers this classifier represents
     */
    private double n;
    private boolean initialized;

    public Classifier(Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
        this.p = 0;
        this.e = 0;
        this.f = 0;
        this.exp = 0;
        this.ts = 0;
        this.as = 0;
        this.n = 0;
        initialized = false;
    }

    private void checkInitialization() {
        if (!initialized)
            throw new RuntimeException("Classifier used before initialization");
    }

    public Condition getCondition() {
        checkInitialization();
        return condition;
    }

    public void setCondition(Condition condition) {
        checkInitialization();
        this.condition = condition;
    }

    public Action getAction() {
        checkInitialization();
        return action;
    }

    public void setAction(Action action) {
        checkInitialization();
        this.action = action;
    }

    public double getP() {
        checkInitialization();
        return p;
    }

    public void setP(double p) {
        checkInitialization();
        this.p = p;
    }

    public double getE() {
        checkInitialization();
        return e;
    }

    public void setE(double e) {
        checkInitialization();
        this.e = e;
    }

    public double getExp() {
        checkInitialization();
        return exp;
    }

    public void setExp(double exp) {
        checkInitialization();
        this.exp = exp;
    }

    public long getTs() {
        checkInitialization();
        return ts;
    }

    public void setTs(long ts) {
        checkInitialization();
        this.ts = ts;
    }

    public double getAs() {
        checkInitialization();
        return as;
    }

    public void setAs(double as) {
        checkInitialization();
        this.as = as;
    }

    public double getN() {
        checkInitialization();
        return n;
    }

    public void setN(double n) {
        checkInitialization();
        this.n = n;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public boolean matches(@NotNull Situation sigma) {
        checkInitialization();
        return this.condition.matches(sigma);
    }

    public static Classifier coverSituation(@NotNull Situation sigma, Action action, double P_hash) {
        return new Classifier(Condition.createCovering(sigma, P_hash), action);
    }

    public void initialize(double p_I, double e_I, double F_I, long t) {
        this.p = p_I;
        this.e = e_I;
        this.f = F_I;
        this.exp = 0;
        this.ts = t;
        this.n = 1;
        this.as = 1;
        this.initialized = true;
    }

    @NotNull
    public static Classifier copy(@NotNull Classifier original) {
       Classifier copy = new Classifier(Condition.copy(original.getCondition()), Action.copy(original.getAction()));
       copy.initialize(
               original.getP(),
               original.getE(),
               original.getF(),
               original.getTs()
       );
       copy.setExp(original.getExp());
       copy.setN(original.getN());
       copy.setAs(original.getAs());
       return copy;
    }

    public static void applyCrossover(@NotNull Classifier child1, @NotNull Classifier child2) {
        double x = ThreadLocalRandom.current().nextDouble() * (child1.getCondition().getValues().length + 1);
        double y = ThreadLocalRandom.current().nextDouble() * (child1.getCondition().getValues().length + 1);
        if(x > y) {
            double temp = x;
            x = y;
            y = temp;
        }

        int i = 0;
        do {
            if(x <= i && i < y) {
                TernaryAlphabet temp = child1.getCondition().getValues()[i];
                child1.getCondition().getValues()[i] = child2.getCondition().getValues()[i];
                child2.getCondition().getValues()[i] = temp;
            }
            i++;
        } while(i < y && i < child1.getCondition().getValues().length);

        child1.initialized = false;
        child2.initialized = false;

    }

    public void mutate(@NotNull Situation sigma, double mew) {
        int i = 0;
        do {
            if(ThreadLocalRandom.current().nextDouble() < mew) {
               if(condition.getValues()[i] == TernaryAlphabet.HASH) {
                   switch(sigma.getValues()[i]) {
                       case ONE:
                           condition.setValues(i, TernaryAlphabet.ONE);
                           break;
                       case ZERO:
                           condition.setValues(i, TernaryAlphabet.ZERO);
                           break;
                   }
               } else {
                   condition.setValues(i, TernaryAlphabet.HASH);
               }
            }
            i++;
        } while(i < condition.getValues().length);
    }

    public boolean isSame(@NotNull Classifier cl) {
        return cl.condition.equals(condition) && cl.action.equals(action);
    }
    public static boolean couldSubsume(@NotNull Classifier cl, double theta_sub, double epsilon_nought) {
        if(cl.getExp() > theta_sub) {
            if(cl.getE() < epsilon_nought) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMoreGeneral(@NotNull Classifier general, @NotNull Classifier specific) {
        if(general.getCondition().hashCount() <= specific.getCondition().hashCount()) return false;
        int i = 0;
        TernaryAlphabet[] generalValues = general.getCondition().getValues();
        TernaryAlphabet[] specificValues = specific.getCondition().getValues();
        do {
            TernaryAlphabet genI = generalValues[i];
            TernaryAlphabet specI = specificValues[i];
            if(genI != TernaryAlphabet.HASH && genI != specI)
                return false;
            i++;
        } while(i < generalValues.length);
        return true;
    }

    public static boolean doesSubsume(@NotNull Classifier sub, @NotNull Classifier tos, double theta_sub, double epsilon_nought) {
        if(sub.getAction() == tos.getAction())
            if(Classifier.couldSubsume(sub, theta_sub, epsilon_nought))
                if(Classifier.isMoreGeneral(sub, tos))
                    return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classifier that = (Classifier) o;

        if (Double.compare(that.p, p) != 0) return false;
        if (Double.compare(that.e, e) != 0) return false;
        if (Double.compare(that.f, f) != 0) return false;
        if (Double.compare(that.exp, exp) != 0) return false;
        if (ts != that.ts) return false;
        if (Double.compare(that.as, as) != 0) return false;
        if (Double.compare(that.n, n) != 0) return false;
        if (initialized != that.initialized) return false;
        if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;
        return action != null ? action.equals(that.action) : that.action == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = condition != null ? condition.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        temp = Double.doubleToLongBits(p);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(e);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(f);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(exp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (ts ^ (ts >>> 32));
        temp = Double.doubleToLongBits(as);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(n);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (initialized ? 1 : 0);
        return result;
    }
}

class PredictionArray {
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

public class XCS {
    /**
     * Maximum size of population
     * <p>
     * should be large enough that covering only occurs at the beginning of a run
     */
    private long N = 10000;
    /**
     * Learning rate for p, e, f, and as
     * <p>
     * should be in the range 0.1 - 0.2
     */
    private double beta = 0.15;
    /**
     * Parameter used in calculating the fitness of a classifier
     * <p>
     * should be in the range 0.1
     */
    private double alpha = 0.1;
    /**
     * Parameter used in calculating the fitness of a classifier
     * should be 1% of the maximum value of p
     */
    private double epsilon_nought = 1; // assuming maximum is 100
    /**
     * Parameter used in calculating the fitness of a classifier
     * <p>
     * typically 5
     */
    private double v = 5;

    /**
     * discount factor used in multistep problems to update classifier predictions
     * <p>
     * typically 0.71
     */
    private double gamma = 0.71;
    /**
     * GA threshold - GA is applied when average time since last GA is greater than this value
     * <p>
     * often in the range 25-50
     */
    private double theta_GA = 37.5;
    /**
     * probability of applying crossover
     * <p>
     * often in the range 0.5-1.0
     */
    private double x = 0.725;
    /**
     * probability of mutating an allele in the offspring
     * <p>
     * often in the range 0.01-0.05
     */
    private double mew = 0.025;
    /**
     * deletion threshold below which classifier considered for deletion
     * <p>
     * could be about 20
     */
    private double theta_del = 20;
    /**
     * fraction of mean fitness below which a classifier's fitness is then used for considering it's deletion
     * <p>
     * often 0.1
     */
    private double delta = 0.1;
    /**
     * subsumption threshold above which classifiers are elligeable for subsuming others
     * <p>
     * around 20, although in some problems larger values work better
     */
    private double theta_sub = 20;
    /**
     * probability of using a # in an attribute in C
     * <p>
     * could be around 0.33
     */
    private double P_sharp = 0.33;
    /**
     * initial value for prediction
     * <p>
     * very small - essentially 0
     */
    private double p_I = 0.0001;
    /**
     * initial value of prediction error
     * <p>
     * very small - essentially 0
     */
    private double e_I = 0.0001;
    /**
     * initial value of fitness
     * <p>
     * very small - essentially 0
     */
    private double F_I = 0.0001;
    /**
     * specifies probability during action selection of choosing the value uniform randomly
     * <p>
     * could be 0.5 - depends on type of experiment
     */
    private double p_explr = 0.01;
    /**
     * minimal number of actions required for no covering to happen
     * <p>
     * to achieve maximum covering set this to the number of actions
     */
    private double theta_mna = 2;
    /**
     * whether offspring are tested for subsumption by parents
     * <p>
     * usefull in problems with well defined target functions - i.e multiplexer
     */
    private boolean doGASubsumption = false;
    /**
     * whether action sets are to be tested for subsuming
     * <p>
     * stronger than GA subsumption and condenses population more
     */
    private boolean doActionSetSubsumption = false;

    /**
     * timestep
     */
    private long t = 0;
    /**
     * Last reward
     */
    private double p_1;
    /**
     * Last situation
     */
    private Situation sigma_1;
    /**
     * population - all classifiers that exist at time t
     */
    @NotNull
    private List<Classifier> P = new ArrayList<>();
    /**
     * match set formed from P - includes all classifiers that match the current situation
     */
    @NotNull
    private List<Classifier> M = new ArrayList<>();
    /**
     * action set formed from M - includes all classifiers of M which propose executed action
     */
    @NotNull
    private List<Classifier> A = new ArrayList<>();
    /**
     * previous action set
     */
    @NotNull
    private List<Classifier> A_1 = new ArrayList<>();

    private Environment env;
    private ReinforcementProgram rp;

    public XCS(Environment env, ReinforcementProgram rp) {
        this.env = env;
        this.rp = rp;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public void initializeXCS() {
        for (int i = 0; i < this.N; i++) {
            Classifier child = new Classifier(
                    Condition.createRandom(env.getProblemSize(), this.P_sharp),
                    Action.createRandom());
            child.initialize(p_I, e_I, F_I, t);
            this.insertIntoPopulation(child
            );
        }
    }

    public boolean runSingleTrainIteration() {
        Optional<Situation> nextSituation = env.getSituation(this.t);
        if (!nextSituation.isPresent()) {
            return false;
        }
        Situation sigma = nextSituation.get();
        this.generateMatchSet(sigma);
        PredictionArray PA = this.generatePredictionArray();
        Action act = this.selectActionUsing(PA);
        this.generateActionSet(act);
        double p = rp.getRewardForAction(sigma, act);
        if (this.A_1.size() > 0) {
            assert false;
            double Ptemp = this.p_1 + this.gamma * PA.findMax();
            this.updateLastActionSet(Ptemp);
            this.runGAOnLastActionSet(this.sigma_1);
        }
        if (env.isEndOfProblem()) {
            double Ptemp = p;
            this.updateCurrentActionSet(Ptemp);
            this.runGAOnCurrentActionSet(sigma);
            this.A_1.clear();
        } else {
            assert false;
            this.A_1.addAll(this.A);
            this.p_1 = p;
            this.sigma_1 = sigma;
        }
        t++;
        return true;
    }

    private void runGAOnLastActionSet(@NotNull Situation sigma_1) {
        this.runGAOnActionSet(this.A_1, sigma_1);
    }

    private void runGAOnCurrentActionSet(@NotNull Situation sigma) {
        this.runGAOnActionSet(this.A, sigma);
    }

    private void updateCurrentActionSet(double p) {
        this.updateActionSet(this.A, p);
    }

    private void updateLastActionSet(double p) {
        this.updateActionSet(this.A_1, p);
    }

    private void generateActionSet(Action act) {
        //  GENERATE ACTION SET out of [M] according to act
        this.A.clear();
        for (Classifier cl : this.M) {
            if (cl.getAction().equals(act)) {
                this.A.add(cl);
            }
        }
    }

    private Action selectActionUsing(@NotNull PredictionArray PA) {
        if (current().nextDouble() < this.p_explr) {
            return PA.selectRandomAction();
        } else {
            return PA.selectBestAction();
        }
    }

    private PredictionArray generatePredictionArray() {
        return PredictionArray.generatePredictionArray(this.M);
    }

    private void generateMatchSet(@NotNull Situation sigma) {
        // [M] <- Generate Match set out of [P] using Sigma
        this.M.clear();
        while (this.M.isEmpty()) {
            for (Classifier cl : this.P) {
                if (cl.matches(sigma)) {
                    this.M.add(cl);
                }
            }

            Set<BinaryAlphabet> actions = new HashSet<>();
            for (Classifier classifier : this.M) {
               actions.add(classifier.getAction().getClassification());
            }
            if (actions.size() < this.theta_mna) {
                Classifier cl_c = this.generateCoveringClassifier(sigma);
                this.insertIntoPopulation(cl_c);
                this.deleteFromPopulation();
                this.M.clear();
            }
        }
    }

    @NotNull
    private Classifier generateCoveringClassifier(@NotNull Situation sigma) {
        Classifier classifer = Classifier.coverSituation(sigma, this.selectActionFromMatchSet(), this.P_sharp);
        assert classifer.matches(sigma);
        classifer.initialize(this.p_I, this.e_I, this.F_I, this.t);
        return classifer;
    }

    @NotNull
    private Action selectActionFromMatchSet() {
        Set<Action> actionsInM = new HashSet<>();
        for (Classifier cl : this.M) {
            actionsInM.add(cl.getAction());
        }

        Action oneClassification = Action.getOneClassification();
        Action zeroClassification = Action.getZeroClassification();
        if (actionsInM.contains(oneClassification) && !actionsInM.contains(zeroClassification)) {
            return zeroClassification;
        } else if (actionsInM.contains(zeroClassification) && !actionsInM.contains(oneClassification)) {
            return oneClassification;
        } else {
            return Action.createRandom();
        }
    }

    private void deleteFromPopulation() {
        long populationSize = this.getPopulationSize();
        if (populationSize <= this.N) {
            return;
        }
        double averagePopulationFitness = getAveragePopulationFitness(populationSize);
        double votesum = 0.0;
        for (Classifier cl : this.P) {
            double deletionVoteFor = this.getDeletionVoteFor(cl, averagePopulationFitness);
            votesum += deletionVoteFor;
        }
        double choicePoint = ThreadLocalRandom.current().nextDouble() * votesum;
        votesum = 0;
        for (Classifier cl : this.P) {
            votesum += this.getDeletionVoteFor(cl, averagePopulationFitness);
            if (votesum > choicePoint) {
                if (cl.getN() > 1) {
                    cl.setN(cl.getN() - 1);
                } else {
                    this.P.remove(cl);
                }
                return;
            }
        }
    }

    private double getDeletionVoteFor(@NotNull Classifier cl, double averagePopulationFitness) {
        double vote = cl.getAs() * cl.getN();
        if (cl.getExp() > this.theta_del && cl.getF() / cl.getN() < this.delta * averagePopulationFitness) {
            vote *= averagePopulationFitness / (cl.getF() / cl.getN());
        }
        return vote;
    }

    private double getAveragePopulationFitness() {
        return getAveragePopulationFitness(getPopulationSize());
    }

    private double getAveragePopulationFitness(long populationSize) {
        double totalFitness = 0;
        for (Classifier cl : this.P) {
            totalFitness += cl.getF();
        }
        return totalFitness / populationSize;
    }

    private long getPopulationSize() {
        long size = 0;
        for (Classifier cl : this.P) {
            size += cl.getN();
        }
        return size;
    }


    private void updateActionSet(@NotNull List<Classifier> a, double P) {
        // TODO: UPDATE SET A using p possibly deleting in [P]
        Double actionSetSize = a.stream().map(Classifier::getN).reduce(Double::sum).orElse(0.0);
        for (Classifier cl : a) {
            // for each classifer cl in [a]

            cl.setExp(cl.getExp() + 1);
            // cl.exp++

            if (cl.getExp() < 1 / this.beta) {
                // if cl.exp < 1/beta
                cl.setP(cl.getP() + (P - cl.getP()) / cl.getExp());
                // cp.p <- cp.p + (P - cl.p) / cl.exp
            } else {
                cl.setP(cl.getP() + this.beta * (P - cl.getP()));
                //cl.p <- cl.p + beta * (P - cl.p)
            }

            if (cl.getExp() < 1 / this.beta) {
                // if cl.exp < 1/beta
                cl.setE(cl.getE() + (Math.abs(P - cl.getP()) - cl.getE()) / cl.getExp());
                // cl.e <- cl.e + (|P - cl.p| - cl.e)/cl.exp
            } else {
                cl.setE(cl.getE() + this.beta * (Math.abs(P - cl.getP()) - cl.getE()));
                // cl.e <- cl.e + this.beta * (|P - cl.p| - cl.e)
            }

            if (cl.getExp() < 1 / this.beta) {
                cl.setAs(cl.getAs() + (actionSetSize - cl.getAs()) / cl.getExp());
                // cl.as <- cl.as + (sum c.n for c in [A] - cl.as)/cl.exp
            } else {
                cl.setAs(cl.getAs() + this.beta * (actionSetSize - cl.getAs()));
                // cl.as <- cl.as + beta * (sum c.n for c in [a] - cl.as)
            }
        }

        this.updateFitnessActionSet(a);
        if (this.doActionSetSubsumption) {
            this.performActionSetSubsumption(a);
        }
    }

    private void updateFitnessActionSet(@NotNull List<Classifier> a) {
        // UPDATE fitness in set [A]
        double accuracySum = 0;
        List<Double> k = new ArrayList<>();
        for (Classifier cl : a) {
            double value;
            if (cl.getE() < this.epsilon_nought) {
                value = 1.0;
            } else {
                value = this.alpha * Math.pow(cl.getE() / this.epsilon_nought, -1 * this.v);
            }
            k.add(value);
            accuracySum += value * cl.getN();
        }

        for (int i = 0; i < a.size(); i++) {
            Classifier cl = a.get(i);
            cl.setF(cl.getF() + this.beta * (k.get(i) * cl.getN() / accuracySum - cl.getF()));
        }

    }
public List<Classifier> getTopN(long n){

       return this.P.stream().sorted((o1, o2) -> -1 * Double.compare(o1.getF(), o2.getF())).limit(n).collect(Collectors.toList());
}
    private void runGAOnActionSet(@NotNull List<Classifier> a, @NotNull Situation sigma) {
        // TODO: RUN GA in A considering sigma_1 inserting and possibly deleting in [P]

        Double actionSetSize = a.stream().map(Classifier::getN).reduce(Double::sum).orElse(0.0);
        Double averageActionSetTime = a.stream()
                .map(classifier -> classifier.getTs() * classifier.getN())
                .reduce(Double::sum)
                .orElse(0.0) / actionSetSize;
        if (this.t - averageActionSetTime > this.theta_GA) {
            for (Classifier cl : a) {
                cl.setTs(t);
            }
            Classifier parent1 = this.selectOffspring(a);
            Classifier parent2 = this.selectOffspring(a);

            Classifier child1 = Classifier.copy(parent1);
            Classifier child2 = Classifier.copy(parent2);

            child1.setN(1);
            child2.setN(1);

            if (ThreadLocalRandom.current().nextDouble() < this.x) {
                Classifier.applyCrossover(child1, child2);
                child1.initialize(
                        (parent1.getP() + parent2.getP()) / 2,
                        (parent1.getE() + parent2.getE()) / 2,
                        (parent1.getF() + parent2.getF()) / 2, t);
                child2.initialize(
                        (parent1.getP() + parent2.getP()) / 2,
                        (parent1.getE() + parent2.getE()) / 2,
                        (parent1.getF() + parent2.getF()) / 2, t);
            }
            child1.setF(child1.getF() * 0.1);
            child2.setF(child2.getF() * 0.1);

            for(Classifier child : Arrays.asList(child1, child2)) {
                child.mutate(sigma, mew);
                if(this.doGASubsumption) {
                    assert false;
                    if(Classifier.doesSubsume(parent1, child, this.epsilon_nought, this.theta_sub)) {
                        parent1.setN(parent1.getN() + 1);
                    } else if(Classifier.doesSubsume(parent2, child, this.epsilon_nought, this.theta_sub)) {
                        parent2.setN(parent2.getN() + 1);
                    } else {
                        this.insertIntoPopulation(child);
                    }
                } else {
                    this.insertIntoPopulation(child);
                }
                this.deleteFromPopulation();
            }
        }
    }

    private void insertIntoPopulation(@NotNull Classifier child) {
        // INSERT child into [P]
        for(Classifier cl : this.P) {
            if(cl.isSame(child)) {
                cl.setN(cl.getN() + 1);
                return;
            }
        }
        this.P.add(child);
    }

    @NotNull
    private Classifier selectOffspring(@NotNull List<Classifier> a) {
        // TODO : SELECT OFFSPRING in [A]
        double fitnessSum = 0.0;
        for(Classifier cl : a) {
            fitnessSum += cl.getF();
        }
        double choicePoint = ThreadLocalRandom.current().nextDouble() * fitnessSum;
        fitnessSum = 0.0;
        for(Classifier cl : a) {
            fitnessSum += cl.getF();
            if(fitnessSum > choicePoint) {
                return cl;
            }
        }
        throw new RuntimeException("Reached end of select offspring method without selecting offspring");
    }

    private void performActionSetSubsumption(@NotNull List<Classifier> a) {
    assert false;
        // DO ACTION SET SUBSUMPTION in [A] updating [P]
        Optional<Classifier> cl = Optional.empty();

        for(Classifier c : a) {
           if(Classifier.couldSubsume(c, this.theta_sub, this.epsilon_nought)) {
               if (!cl.isPresent() || c.getCondition().hashCount() > cl.get().getCondition().hashCount() ||
                       (c.getCondition().hashCount() == cl.get().getCondition().hashCount() && ThreadLocalRandom.current().nextDouble() < 0.5)
                       ) {

                   cl = Optional.of(c);
               }
           }
        }

        cl.ifPresent(classifier -> {
            Iterator<Classifier> iterator = a.iterator();
            while (iterator.hasNext()) {
                Classifier c = iterator.next();

                if (Classifier.isMoreGeneral(classifier, c)) {
                    classifier.setN(classifier.getN() + c.getN());
                    iterator.remove();
                    this.P.remove(c);
                }
            }
        });
    }

    public long getN() {
        return N;
    }

    public void setN(long n) {
        N = n;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getEpsilon_nought() {
        return epsilon_nought;
    }

    public void setEpsilon_nought(double epsilon_nought) {
        this.epsilon_nought = epsilon_nought;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getTheta_GA() {
        return theta_GA;
    }

    public void setTheta_GA(double theta_GA) {
        this.theta_GA = theta_GA;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getMew() {
        return mew;
    }

    public void setMew(double mew) {
        this.mew = mew;
    }

    public double getTheta_del() {
        return theta_del;
    }

    public void setTheta_del(double theta_del) {
        this.theta_del = theta_del;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getTheta_sub() {
        return theta_sub;
    }

    public void setTheta_sub(double theta_sub) {
        this.theta_sub = theta_sub;
    }

    public double getP_sharp() {
        return P_sharp;
    }

    public void setP_sharp(double p_sharp) {
        P_sharp = p_sharp;
    }

    public double getP_I() {
        return p_I;
    }

    public void setP_I(double p_I) {
        this.p_I = p_I;
    }

    public double getE_I() {
        return e_I;
    }

    public void setE_I(double e_I) {
        this.e_I = e_I;
    }

    public double getF_I() {
        return F_I;
    }

    public void setF_I(double f_I) {
        F_I = f_I;
    }

    public double getP_explr() {
        return p_explr;
    }

    public void setP_explr(double p_explr) {
        this.p_explr = p_explr;
    }

    public double getTheta_mna() {
        return theta_mna;
    }

    public void setTheta_mna(double theta_mna) {
        this.theta_mna = theta_mna;
    }

    public boolean isDoGASubsumption() {
        return doGASubsumption;
    }

    public void setDoGASubsumption(boolean doGASubsumption) {
        this.doGASubsumption = doGASubsumption;
    }

    public boolean isDoActionSetSubsumption() {
        return doActionSetSubsumption;
    }

    public void setDoActionSetSubsumption(boolean doActionSetSubsumption) {
        this.doActionSetSubsumption = doActionSetSubsumption;
    }

    public static void main(String[] args) {
        List<Double> lastNScores = new ArrayList<>();
        int n = 200;
        int problemCount = 500;
        ReinforcementProgram rp = new ReinforcementProgram(1000.0, 0.0);
        Environment env = new Environment(problemCount);
        XCS xcs = new XCS(env, rp);
        xcs.setMew(0.001);
        xcs.setTheta_GA(10000);
        int n1 = 1000;
        xcs.setN(n1);
//        xcs.setN(300);
        xcs.setDoGASubsumption(false);
//        xcs.setTheta_mna(20);
        xcs.setDoActionSetSubsumption(false);

        int iterationCount = 0;

        for(int i = 0; i < 1000000; i++) {
            while (xcs.runSingleTrainIteration()) {
                iterationCount++;
            }
//            System.out.println("[" + iterationCount + "]: " + rp.getSystemScorer().getAccuracy() * 100 + "%");
            lastNScores.add(rp.getSystemScorer().getAccuracy()*100);
            if(lastNScores.size() > n) {
                lastNScores.remove(0);
            }
            iterationCount = 0;
            xcs.setEnv(new Environment(problemCount));
            rp.getSystemScorer().reset();
            if(i%Math.max(30,n) == 0 && lastNScores.size() > 0) {
                double sx = lastNScores.stream().reduce(Double::sum).orElse(0.0);
                double sx2 = lastNScores.stream().map(x -> x * x).reduce(Double::sum).orElse(0.0);

                double xbar = sx / lastNScores.size();
                double x2bar = sx2 / lastNScores.size();

                double var = x2bar - xbar * xbar;
                double sd = Math.sqrt(var);

                System.out.println("[" + iterationCount + "]: Average of last " + n + ": " + xbar + ", sd: " + sd);
                n1 += 100;
                xcs.setN(n1++);
                List<Classifier> correct = xcs.getTopN(100).stream().filter(classifier -> ReinforcementProgram.isCorrect(Classifier.GenerateMatching(classifier), classifier.getAction())).collect(Collectors.toList());
                List<Classifier> incorrect = xcs.getTopN(100).stream().filter(classifier -> !ReinforcementProgram.isCorrect(Classifier.GenerateMatching(classifier), classifier.getAction())).collect(Collectors.toList());

                System.out.println("Correct("+correct.size() +"): "+ correct);
                System.out.println("Incorrect(" + incorrect.size() + "): "+ incorrect);
                lastNScores.clear();
            }
        }

    }
}
