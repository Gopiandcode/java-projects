package com.gopiandcode.xcs.graphing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;

public class GraphingLogger {
    private XYSeries dataset;
    private String title;
    public GraphingLogger(String title) {
       dataset = new XYSeries("accuracy");
       this.title = title;
    }

    public void recordAccuracyAtIteration(double accuracy, int iteration) {
       dataset.add(iteration, accuracy);
    }

    public JFreeChart constructGraph() {
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
        domainAxis.setTickUnit(new NumberTickUnit(1000));
        return chart;
    }


}
