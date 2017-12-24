package com.gopiandcode.xcs;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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

    public static Action createRandom() {
        if(ThreadLocalRandom.current().nextInt(0,1) == 0) {
           return getZeroClassification();
        } else {
            return getOneClassification();
        }
    }
}

class Condition {
    private final TernaryAlphabet[] values;

    public TernaryAlphabet[] getValues() {
        return values;
    }

    Condition(TernaryAlphabet[] values) {
        this.values = values;
    }

    Condition(String representation) {
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

    public boolean matches(Situation situation) {
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

    public static Condition createRandom(int size) {
        TernaryAlphabet[] ternaryAlphabets = new TernaryAlphabet[size];
        for (int i = 0; i < size; i++) {

            TernaryAlphabet value = TernaryAlphabet.ONE;
            switch (ThreadLocalRandom.current().nextInt(0, 3)) {
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
            if (ThreadLocalRandom.current().nextDouble() > P_hash) {
                switch (ThreadLocalRandom.current().nextInt(0, 2)) {
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
            switch (ThreadLocalRandom.current().nextInt(0, 2)) {
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
                return getSituation();
            }
        } else {
            return Optional.empty();
        }
    }

    public int getProblemSize() {
        return 6;
    }
}

class ReinforcementProgram {
    public double getRewardForAction(Situation situation, Action action) {

        return 0;
    }
}

class Classifier {
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

    public Classifier(Condition condition, Action action, double p, double e, double f, double exp, long ts, double as, double n) {
        this.condition = condition;
        this.action = action;
        this.p = p;
        this.e = e;
        this.f = f;
        this.exp = exp;
        this.ts = ts;
        this.as = as;
        this.n = n;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public double getAs() {
        return as;
    }

    public void setAs(double as) {
        this.as = as;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }
}

public class XCS {
    /**
     * Maximum size of population
     * <p>
     * should be large enough that covering only occurs at the beginning of a run
     */
    private long N;
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
     * <p>
     * should be 1% of the maximum valu eof p
     */
    private double epsilon_nought = 10; // assuming maximum is 1000
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
    private double p_explr = 0.5;
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
    private boolean doGASubsumption = true;
    /**
     * whether action sets are to be tested for subsuming
     * <p>
     * stronger than GA subsumption and condenses population more
     */
    private boolean doActionSetSubsumption = true;

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
    private List<Classifier> P = new ArrayList<>();
    /**
     * match set formed from P - includes all classifiers that match the current situation
     */
    private List<Classifier> M = new ArrayList<>();
    /**
     * action set formed from M - includes all classifiers of M which propose executed action
     */
    private List<Classifier> A = new ArrayList<>();
    /**
     * previous action set
     */
    private List<Classifier> A_1 = new ArrayList<>();

    private Environment env;
    private ReinforcementProgram rp;

    public XCS(Environment env, ReinforcementProgram rp) {
        this.env = env;
        this.rp = rp;
    }

    public void initializeXCS() {
        for(int i = 0; i < this.N; i++) {
            this.P.add(new Classifier(
                    Condition.createRandom(env.getProblemSize(), this.P_sharp),
                    Action.createRandom(),
                    this.p_I,
                    this.e_I,
                    this.F_I,
                    0,
                    0,
                    0,
                    1));
        }
    }

    public boolean runSingleTrainIteration() {
            Optional<Situation> nextSituation = env.getSituation(this.t);
            if(!nextSituation.isPresent()) {
                return false;
            }
            Situation sigma = nextSituation.get();
            this.generateMatchSet(sigma);
            PredictionArray PA = this.generatePredictionArray();
            Action act = this.selectActionUsing(PA);
            this.generateActionSet(act);
            double p = rp.getRewardForAction(sigma, act);
            if(this.A_1.size() > 0) {
                double Ptemp = this.p_1 + this.gamma * PA.findMax();
                this.updateLastActionSet(Ptemp);
                this.runGAOnLastActionSet(this.sigma_1);
            }
            if(env.isEndOfProblem()) {
                double Ptemp = p;
                this.updateCurrentActionSet(Ptemp);
                this.runGAOnCurrentActionSet(sigma);
                this.A_1.clear();
            } else {
                this.A_1.addAll(this.A);
                this.p_1 = p;
                this.sigma_1 = sigma;
            }

            return true;
    }

    private void runGAOnLastActionSet(Situation sigma_1) {
        this.runGAOnActionSet(this.A_1, sigma_1);
    }

    private void runGAOnCurrentActionSet(Situation sigma) {
        this.runGAOnActionSet(this.A, sigma);
    }

    private void runGAOnActionSet(List<Classifier> a, Situation sigma_1) {
        // TODO: RUN GA in A considering sigma_1 inserting and possibly deleting in [P]

    }
    private void updateCurrentActionSet(double p) {
        this.updateActionSet(this.A, p);
    }

    private void updateLastActionSet(double p) {
        this.updateActionSet(this.A_1, p);
    }

    private void updateActionSet(List<Classifier> a, double p) {
        // TODO: UPDATE SET A using p possibly deleting in [P]
    }

    private void generateActionSet(Action act) {
        // TODO: GENERATE ACTION SET out of [M] according to act
    }

    private Action selectActionUsing(PredictionArray PA) {
        // TODO: SELECT ACTION according to PA
        return null;
    }

    private PredictionArray generatePredictionArray() {
        // TODO: PA <- generate prediction array out of [M]
        return null;
    }

    private void generateMatchSet(Situation sigma) {
        // TODO: [M] <- Generate Match set out of [P] using Sigma
    }
}
