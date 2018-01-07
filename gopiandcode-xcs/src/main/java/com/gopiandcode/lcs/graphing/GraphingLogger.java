package com.gopiandcode.lcs.graphing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphingLogger {
    private XYSeries dataset;
    private String title;
    private int maxIteration = 0;
    public GraphingLogger(String title) {
       dataset = new XYSeries("accuracy");
       this.title = title;
    }

    public void recordAccuracyAtIteration(double accuracy, int iteration) {
       dataset.add(iteration, accuracy);
       maxIteration = iteration;
    }

    public JFreeChart constructGraph(int ticks) {
       JFreeChart chart = ChartFactory.createXYLineChart(
               title,
               "Iteration (count)",
               "Accuracy (%)",
               new XYSeriesCollection(dataset),
               PlotOrientation.VERTICAL,
               false,
               false,
              false
               );
        NumberAxis domainAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
        domainAxis.setTickUnit(new NumberTickUnit(Math.max(Math.floor((double)maxIteration/ticks), 1)));
        return chart;
    }


}
