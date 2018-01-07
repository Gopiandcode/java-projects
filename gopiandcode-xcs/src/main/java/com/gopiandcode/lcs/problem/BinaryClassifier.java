package com.gopiandcode.lcs.problem;

import com.gopiandcode.lcs.dataset.BinaryClassifierTestData;
import com.gopiandcode.lcs.internal.Action;
import com.gopiandcode.lcs.problem.Situation;

public interface BinaryClassifier {
    boolean runSingleTrainIteration(BinaryClassifierTestData data);

    Action classify(Situation sigma);
}
