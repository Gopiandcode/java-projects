package com.gopiandcode.lcs.graphing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import java.awt.*;
import java.awt.geom.Ellipse2D;

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
//       JFreeChart chart = ChartFactory.createXYLineChart(
//               title,
//               "Iteration (count)",
//               "Accuracy (%)",
//               new XYSeriesCollection(dataset),
//               PlotOrientation.VERTICAL,
//               false,
//               false,
//              false
//               );
//        XYPlot xyPlot = chart.getXYPlot();
//        XYSplineRenderer renderer = new XYSplineRenderer();
//        renderer.setSeriesShapesVisible(0, false);
//        xyPlot.setRenderer(renderer);
//
//
//        NumberAxis domainAxis = (NumberAxis) xyPlot.getDomainAxis();
//        domainAxis.setTickUnit(new NumberTickUnit(Math.max(Math.floor((double)maxIteration/ticks), 1)));
//        return chart;


        XYSplineRenderer renderer = new XYSplineRenderer();
        NumberAxis xAxis = new NumberAxis("Iteration Count");
        xAxis.setTickUnit(new NumberTickUnit(Math.max(Math.floor((double)maxIteration/ticks), 1)));
        NumberAxis yAxis = new NumberAxis("Accuracy (%)");
        yAxis.setRange(0, 100);

        XYPlot plot = new XYPlot(new XYSeriesCollection(dataset), xAxis, yAxis, renderer);
        plot.getRenderer().setBaseShape(new Ellipse2D.Double(0,0,1,1));

        JFreeChart jFreeChart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        return jFreeChart;
    }


}
