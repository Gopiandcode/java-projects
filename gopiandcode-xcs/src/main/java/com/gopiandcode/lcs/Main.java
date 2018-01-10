package com.gopiandcode.lcs;

import com.gopiandcode.lcs.dataset.BinaryClassifierDataset;
import com.gopiandcode.lcs.dataset.LocalBinaryClassifierDataset;
import com.gopiandcode.lcs.graphing.GraphRenderer;
import com.gopiandcode.lcs.graphing.GraphingLogger;
import com.gopiandcode.lcs.problem.BinaryClassifier;
import com.gopiandcode.lcs.rcs.RCSBinaryClassifier;
import com.gopiandcode.lcs.xcs.XCSBinaryClassifier;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
       BinaryClassifier xcs = new RCSBinaryClassifier();

       GraphingLogger logger = new GraphingLogger("RCSBinaryClassifier Accuracy over Training Iterations");
//        setXCSParameters(xcs);
        BinaryClassifierDataset testDataset = LocalBinaryClassifierDataset.loadFromFile("testData20.txt");
        BinaryClassifierDataset trainDataset = LocalBinaryClassifierDataset.loadFromFile("trainData20.txt");

        BinaryClassifierTestRunner runner = new BinaryClassifierTestRunner(trainDataset, testDataset, xcs);

        runner.setLogger(logger, 100);
        runner.setShouldReset(true);

//        runner.runTrainIterations(50000);
        runner.runTrainIterations(50000);


        System.out.println("Final Accuracy: " + runner.runTestIterations(1000));
        GraphRenderer renderer = new GraphRenderer(logger, 1280, 720,20);
        renderer.save("result20_rcs_1.png");




    }

    private static void setXCSParameters(XCSBinaryClassifier xcs) {
//        xcs.setMew(0.001);
//        xcs.setTheta_GA(10000);
//        xcs.setN(20000);
        xcs.setDoGASubsumption(false);
        xcs.setDoActionSetSubsumption(false);
    }
}
