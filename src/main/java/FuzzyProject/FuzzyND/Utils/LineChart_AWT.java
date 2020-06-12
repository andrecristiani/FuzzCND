package FuzzyProject.FuzzyND.Utils;

import FuzzyProject.FuzzyND.Models.Avaliacao.AcuraciaMedidas;
import FuzzyProject.FuzzyND.Models.MedidasClassicas;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.List;

public class LineChart_AWT extends ApplicationFrame {

    public LineChart_AWT(String applicationTitle , String chartTitle, List<MedidasClassicas> medidasClassicas, String campo) {
        super(applicationTitle);
        XYDataset dataset;
        String label = "";
        if(campo.equals("mnew")) {
            dataset = createDatasetMnew(medidasClassicas);
            label = "Mnew";
        } else if (campo.equals("fnew")) {
            dataset = createDatasetFnew(medidasClassicas);
            label = "Fnew";
        } else {
            dataset = createDatasetErr(medidasClassicas);
            label = "Err";
        }
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "",
                "Momentos de avaliação (em milhares)",label,
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    public LineChart_AWT(String applicationTitle , String chartTitle, List<List<AcuraciaMedidas>> acuracias, List<String> rotuloClassificadores) {
        super(applicationTitle);
        XYDataset dataset;
        dataset = createDatasetAcuracia(acuracias, rotuloClassificadores);
        String label = "Accuracy";
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "",
                "Evaluation moments",label,
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);


        XYPlot xyplot = (XYPlot) lineChart.getPlot();

        ValueAxis rangeAxis = xyplot.getRangeAxis();
        rangeAxis.setRange(0.0, 100);

//        final NumberAxis rangeAxis2 = (NumberAxis) xyplot.getRangeAxis();
//        rangeAxis2.setTickUnit(new NumberTickUnit(10));
//
//        final NumberAxis domainAxis = (NumberAxis) xyplot.getDomainAxis();
//        //domainAxis.setTickUnit(new NumberTickUnit(100));
//        domainAxis.setTickUnit(new NumberTickUnit(5));

//        final XYPlot plot = lineChart.getXYPlot();
//        ValueMarker marker;
//        marker = new ValueMarker(10000);
//        marker.setPaint(Color.gray);
//        marker.setStroke(new BasicStroke(1.0f));
//        plot.addDomainMarker(marker);


        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);

        XYItemRenderer r = xyplot.getRenderer();
        r.setSeriesStroke(0, new BasicStroke(3.0f));
        r.setSeriesStroke(1, new BasicStroke(3.0f ));
        r.setSeriesStroke(2, new BasicStroke(3.0f ));
        r.setSeriesStroke(3, new BasicStroke(3.0f ));
        r.setSeriesStroke(4, new BasicStroke(3.0f ));

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    public LineChart_AWT(String applicationTitle , List<List<Double>> unkR, List<String> rotuloClassificadores) {
        super(applicationTitle);
        XYDataset dataset;
        dataset = createDataseUnkR(unkR, rotuloClassificadores);
        String label = "UnkR";
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "",
                "Evaluation moments",label,
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);


        XYPlot xyplot = (XYPlot) lineChart.getPlot();

        ValueAxis rangeAxis = xyplot.getRangeAxis();
        rangeAxis.setRange(0.0, 40);

        final NumberAxis rangeAxis2 = (NumberAxis) xyplot.getRangeAxis();
        rangeAxis2.setTickUnit(new NumberTickUnit(10));

        final NumberAxis domainAxis = (NumberAxis) xyplot.getDomainAxis();
        domainAxis.setTickUnit(new NumberTickUnit(100));
        domainAxis.setTickUnit(new NumberTickUnit(5));

        final XYPlot plot = lineChart.getXYPlot();
        ValueMarker marker;
        //vertical
        marker = new ValueMarker(25);
        marker.setPaint(Color.gray);
        marker.setStroke(new BasicStroke(1.0f));
        plot.addDomainMarker(marker);


        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);

        XYItemRenderer r = xyplot.getRenderer();
        r.setSeriesStroke(0, new BasicStroke(3.0f));
        r.setSeriesStroke(1, new BasicStroke(3.0f ));
        r.setSeriesStroke(2, new BasicStroke(3.0f ));
        r.setSeriesStroke(3, new BasicStroke(3.0f ));
        r.setSeriesStroke(4, new BasicStroke(3.0f ));

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDatasetToErr(List<MedidasClassicas> medidasClassicas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(int i=0; i<medidasClassicas.size(); i++) {
            dataset.addValue(medidasClassicas.get(i).getErr(), "err", Integer.toString(medidasClassicas.get(i).getIndice()));
        }
        return dataset;
    }

    private DefaultCategoryDataset createDatasetToMnew(List<MedidasClassicas> medidasClassicas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(int i=0; i<medidasClassicas.size(); i++) {
            dataset.addValue(medidasClassicas.get(i).getMnew(), "mnew", Integer.toString(medidasClassicas.get(i).getIndice()));
        }
        return dataset;
    }

    private DefaultCategoryDataset createDatasetToFNew(List<MedidasClassicas> medidasClassicas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(int i=0; i<medidasClassicas.size(); i++) {
            dataset.addValue(medidasClassicas.get(i).getFnew(), "fnew", Integer.toString(medidasClassicas.get(i).getIndice()));
        }
        return dataset;
    }

    private XYDataset createDatasetErr(List<MedidasClassicas> medidasClassicas) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Err");
        for(int i=0; i<medidasClassicas.size(); i++) {
            series.add(Double.parseDouble(Integer.toString(medidasClassicas.get(i).getIndice())), medidasClassicas.get(i).getErr());
        }
        dataset.addSeries(series);
        return dataset;
    }

    private XYDataset createDatasetFnew(List<MedidasClassicas> medidasClassicas) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Fnew");
        for(int i=0; i<medidasClassicas.size(); i++) {
            series.add(Double.parseDouble(Integer.toString(medidasClassicas.get(i).getIndice())), medidasClassicas.get(i).getFnew());
        }
        dataset.addSeries(series);
        return dataset;
    }

    private XYDataset createDatasetMnew(List<MedidasClassicas> medidasClassicas) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Mnew");
        for(int i=0; i<medidasClassicas.size(); i++) {
            series.add(Double.parseDouble(Integer.toString(medidasClassicas.get(i).getIndice())), medidasClassicas.get(i).getMnew());
        }
        dataset.addSeries(series);
        return dataset;
    }

    private XYDataset createDatasetAcuracia(List<List<AcuraciaMedidas>> acuracias, List<String> rotuloClassificadores) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(rotuloClassificadores.get(0));
        XYSeries series2 = new XYSeries(rotuloClassificadores.get(1));
        XYSeries series3 = new XYSeries(rotuloClassificadores.get(2));
        XYSeries series4 = new XYSeries(rotuloClassificadores.get(3));
        XYSeries series5 = new XYSeries(rotuloClassificadores.get(4));
        for(int i=0; i<acuracias.get(0).size(); i++) {
            series1.add(Double.parseDouble(Integer.toString(acuracias.get(0).get(i).getPonto()/1000)), acuracias.get(0).get(i).getAcuracia());
            series2.add(Double.parseDouble(Integer.toString(acuracias.get(1).get(i).getPonto()/1000)), acuracias.get(1).get(i).getAcuracia());
            series3.add(Double.parseDouble(Integer.toString(acuracias.get(2).get(i).getPonto()/1000)), acuracias.get(2).get(i).getAcuracia());
            series4.add(Double.parseDouble(Integer.toString(acuracias.get(3).get(i).getPonto()/1000)), acuracias.get(3).get(i).getAcuracia());
            series5.add(Double.parseDouble(Integer.toString(acuracias.get(4).get(i).getPonto()/1000)), acuracias.get(4).get(i).getAcuracia());
        }
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);
        return dataset;
    }

    private XYDataset createDataseUnkR(List<List<Double>> acuracias, List<String> rotuloClassificadores) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(rotuloClassificadores.get(0));
        XYSeries series2 = new XYSeries(rotuloClassificadores.get(1));
        XYSeries series3 = new XYSeries(rotuloClassificadores.get(2));
        for(int i=0; i<acuracias.get(0).size(); i++) {
            double valor = acuracias.get(1).get(i)*100;
            series1.add(i, acuracias.get(0).get(i));
            series2.add(i, valor);
            series3.add(i, acuracias.get(2).get(i));
        }
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        return dataset;
    }
}