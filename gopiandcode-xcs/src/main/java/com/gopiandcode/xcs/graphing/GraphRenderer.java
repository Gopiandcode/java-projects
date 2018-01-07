package com.gopiandcode.xcs.graphing;

import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraphRenderer extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final JFreeChart chart;

    public GraphRenderer(GraphingLogger log, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        chart = log.constructGraph();

        ChartPanel panel = new ChartPanel(decorateChart());
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.add(panel);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       EventQueue.invokeLater(() -> this.setVisible(true));
    }

    public GraphRenderer(GraphingLogger log) {
        this(log, 560, 367);
    }

    public void save(String filename, int width, int height) {
       File outputfile = new File(filename);
        try {
            ChartUtilities.saveChartAsPNG(outputfile, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(String filename) {
       save(filename, WIDTH, HEIGHT);
    }

    @NotNull
    private JFreeChart decorateChart() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        chart.getXYPlot().setRenderer(renderer);
        return chart;
    }

}

/*

    private String title;

    public GraphRenderer(String title) {
        this.title = title;
        this.add(createDemoPanel());
        this.pack();
    }

    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("IPhone 5s", new Double(20));
        dataset.setValue("Samsung Grand", new Double(20));
        dataset.setValue("MotoG", new Double(40));
        dataset.setValue("Nokia Lumia", new Double(10));
        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Mobile Sales",
                dataset,
                true,
                true,
                false
        );

        return chart;
    }


    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        GraphRenderer renderer = new GraphRenderer("Awesome");

        EventQueue.invokeLater(() -> {
           renderer.setVisible(true);
        });

    }

 */
