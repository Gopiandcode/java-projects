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
//        xcs.setMew(0.001);
//        xcs.setTheta_GA(10000);
//        xcs.setN(300);
        xcs.setDoGASubsumption(true);
//        xcs.setTheta_mna(20);
        xcs.setDoActionSetSubsumption(true);

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

                lastNScores.clear();
            }
        }

    }
}
