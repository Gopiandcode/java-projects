package com.gopiandcode.lcs.graphing;

import com.gopiandcode.lcs.problem.Action;
import com.gopiandcode.lcs.problem.Situation;
import com.gopiandcode.lcs.rcs.RCSBinaryClassifier;
import com.gopiandcode.lcs.rcs.RCSClassifier;
import com.gopiandcode.lcs.util.Either;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

public class RCSGraphGenerator {
    private final RCSBinaryClassifier classifier;
    private Graph graph;


    public RCSGraphGenerator(RCSBinaryClassifier classifier) {
        this.classifier = classifier;
        graph = generateGraph();
    }

    public void writeToPNG(String outpath) {
        try {
            Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(outpath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occured!");
        }
    }

    public void writeToJSON(String outpath) {
        try {
            Graphviz.fromGraph(graph).width(1080).render(Format.JSON).toFile(new File(outpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Graph generateGraph() {
        Graph graph = graph("RCS Classifier Graph").directed();
        HashMap<RCSClassifier, Node> nodeMap = new HashMap<>();
        List<RCSClassifier> population = classifier.getPopulation();
        int i = 0;
        for (RCSClassifier rcsClassifier : population) {
            System.out.println("Generating Graph - " + (100.0 * ((double)i++ / (double)population.size())) + "% complete");
            Node node;
            if (rcsClassifier.getOutput().isLeft()) {
                node = node(rcsClassifier.toString()).with(Style.RADIAL);
            } else {
                node = node(rcsClassifier.toString());
            }
            graph = graph.with(node);
            nodeMap.put(rcsClassifier, node);
        }

        for (RCSClassifier rcsClassifier : population) {
            Node currentElement = nodeMap.get(rcsClassifier);
            Either<Action, Situation> output = rcsClassifier.getOutput();
            if (output.isRight()) {
                Situation intermediateOutput = output.right();
                for (RCSClassifier classifier1 : population) {
                    if (classifier1.matches(intermediateOutput)) {
                        Node otherElement = nodeMap.get(classifier1);
                        currentElement = currentElement.link(otherElement);
                        nodeMap.put(rcsClassifier, currentElement);
                        graph = graph.with(currentElement);
                    }
                }
            }
        }
        System.out.println("Completed graph: " + graph.toString());
        return graph;
    }

    public static void main(String[] args) {
        Graph g = graph("example1").directed().with(node("a").link(node("b")));
        Node node = node("3");
        g = g.with(node);
        node = node.link("a");
        node = node.link("b");
        g = g.with(node);
        try {
            Graphviz.fromGraph(g).width(200).render(Format.PNG).toFile(new File("./ex1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
