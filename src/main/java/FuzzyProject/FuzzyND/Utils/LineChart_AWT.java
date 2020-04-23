package FuzzyProject.FuzzyND.Utils;

import FuzzyProject.FuzzyND.Models.MedidasClassicas;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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
}