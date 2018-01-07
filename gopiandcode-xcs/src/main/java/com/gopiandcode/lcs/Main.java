package com.gopiandcode.lcs;

import com.gopiandcode.lcs.dataset.BinaryClassifierDataset;
import com.gopiandcode.lcs.dataset.LocalBinaryClassifierDataset;
import com.gopiandcode.lcs.graphing.GraphRenderer;
import com.gopiandcode.lcs.graphing.GraphingLogger;
import com.gopiandcode.lcs.xcs.XCSBinaryClassifier;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
       XCSBinaryClassifier xcs = new XCSBinaryClassifier();

       GraphingLogger logger = new GraphingLogger("XCSBinaryClassifier Accuracy over Training Iterations");
        setXCSParameters(xcs);
        BinaryClassifierDataset testDataset = LocalBinaryClassifierDataset.loadFromFile("testData20.txt");
        BinaryClassifierDataset trainDataset = LocalBinaryClassifierDataset.loadFromFile("trainData20.txt");

        BinaryClassifierTestRunner runner = new BinaryClassifierTestRunner(trainDataset, testDataset, xcs);

        runner.setLogger(logger, 20);
        runner.setShouldReset(true);

        runner.runTrainIterations(1000);


        GraphRenderer renderer = new GraphRenderer(logger, 1280, 720, 20);
        renderer.save("result20.png");



        System.out.println("Final Accuracy: " + runner.runTestIterations(100));

    }

    public static void setXCSParameters(XCSBinaryClassifier xcs) {
        xcs.setMew(0.001);
//        lcs.setTheta_GA(10000);
//        lcs.setN(300);
        xcs.setDoGASubsumption(false);
//        lcs.setTheta_mna(20);
        xcs.setDoActionSetSubsumption(false);
    }
}
