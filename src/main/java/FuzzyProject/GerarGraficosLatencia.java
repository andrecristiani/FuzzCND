package FuzzyProject;

import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Avaliacao.AcuraciaMedidas;
import FuzzyProject.FuzzyND.Utils.LineChart_AWT;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerarGraficosLatencia {
    public static void main(String[] args) throws IOException {
        String dataset = "rbf";
        String current = (new File(".")).getCanonicalPath();
        String caminho = current + "/Experimentos/Latencias/" + dataset + "/" + dataset + "-lat100.txt";
        String caminho2 = current + "/Experimentos/Latencias/" + dataset + "/" + dataset + "-lat1000.txt";
        String caminho3 = current + "/Experimentos/Latencias/" + dataset + "/" + dataset + "-lat2000.txt";
        String caminho4 = current + "/Experimentos/Latencias/" + dataset + "/" + dataset + "-lat5000.txt";
        String caminho5 = current + "/Experimentos/Latencias/" + dataset + "/" + dataset + "-lat10000.txt";

        List<List<AcuraciaMedidas>> acuraciasDosClassificadores = new ArrayList<>();
        List<String> rotuloAlgoritmo = new ArrayList<>();

        //moa
//        int numAnalises = 90;

        //rbf
        int numAnalises = 47;

        //forest
//        int numAnalises = 570;
        List<AcuraciaMedidas> acuracia100 = ManipulaArquivos.carregaAcuracias(caminho, numAnalises);
        acuraciasDosClassificadores.add(acuracia100);
        rotuloAlgoritmo.add("100");

        List<AcuraciaMedidas> acuracia1000 = ManipulaArquivos.carregaAcuracias(caminho2, numAnalises);
        acuraciasDosClassificadores.add(acuracia1000);
        rotuloAlgoritmo.add("1,000");

        List<AcuraciaMedidas> acuracia2000 = ManipulaArquivos.carregaAcuracias(caminho3, numAnalises);
        acuraciasDosClassificadores.add(acuracia2000);
        rotuloAlgoritmo.add("2,000");

        List<AcuraciaMedidas> acuracia5000 = ManipulaArquivos.carregaAcuracias(caminho4, numAnalises);
        acuraciasDosClassificadores.add(acuracia5000);
        rotuloAlgoritmo.add("5,000");

        List<AcuraciaMedidas> acuracia10000 = ManipulaArquivos.carregaAcuracias(caminho5, numAnalises);
        acuraciasDosClassificadores.add(acuracia10000);
        rotuloAlgoritmo.add("10,000");

        LineChart_AWT chart = new LineChart_AWT(
                "",
                "", acuraciasDosClassificadores, rotuloAlgoritmo);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
