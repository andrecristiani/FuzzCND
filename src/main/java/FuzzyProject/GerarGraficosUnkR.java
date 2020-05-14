package FuzzyProject;

import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Utils.LineChart_AWT;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerarGraficosUnkR {
    public static void main(String[] args) throws IOException {
        String dataset = "moa";
        String current = (new File(".")).getCanonicalPath();
        String caminho = current + "/Experimentos/Desconhecidos/" + dataset + "/" + dataset + "-fuzzcnd.txt";
        String caminho2 = current + "/Experimentos/Desconhecidos/" + dataset + "/" + dataset + "-pfuzznd40.txt";
        String caminho3 = current + "/Experimentos/Desconhecidos/" + dataset + "/" + dataset + "-minas40.txt";

        List<List<Double>> unkRClassificadores = new ArrayList<>();
        List<String> rotuloAlgoritmo = new ArrayList<>();

        //moa
        int numAnalises = 90; //25

        //rbf
//    int numAnalises = 11; //7

        //forest
//        int numAnalises = 570;
        List<Double> unkRFuzzCND = ManipulaArquivos.carregaUnkR(caminho, numAnalises);
        unkRClassificadores.add(unkRFuzzCND);
        rotuloAlgoritmo.add("FuzzCND");

        List<Double> unkRPFuzzND = ManipulaArquivos.carregaUnkR(caminho2, numAnalises);
        unkRClassificadores.add(unkRPFuzzND);
        rotuloAlgoritmo.add("PFuzzND");

        List<Double> unkRMinas = ManipulaArquivos.carregaUnkR(caminho3, numAnalises);
        unkRClassificadores.add(unkRMinas);
        rotuloAlgoritmo.add("MINAS");

        LineChart_AWT chart = new LineChart_AWT("", unkRClassificadores, rotuloAlgoritmo);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
