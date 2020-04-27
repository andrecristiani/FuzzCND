package FuzzyProject;

import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Avaliacao.AcuraciaMedidas;
import FuzzyProject.FuzzyND.Utils.LineChart_AWT;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerarGraficosAcuracia {
    public static void main(String[] args) throws IOException {
        String dataset = "rbf";
        String current = (new File(".")).getCanonicalPath();
        String caminho = current + "/datasets-j48/" + dataset + "/"+ dataset + "-meu-predctions.txt";
        String caminho2 = current + "/datasets-j48/" + dataset + "/"+ dataset + "-vfdt-predctions.txt";
        String caminho3 = current + "/datasets-j48/" + dataset + "/"+ dataset + "-j48-predctions.txt";
        String caminho4 = current + "/datasets-j48/" + dataset + "/"+ dataset + "-ensemblej48-predctions.txt";

        List<List<AcuraciaMedidas>> acuraciasDosClassificadores = new ArrayList<>();
        List<String> rotuloAlgoritmo = new ArrayList<>();

        //moa
//        int numAnalises = 90;

        //rbf
        int numAnalises = 47;
        List<AcuraciaMedidas> acuraciaMeu = ManipulaArquivos.carregaAcuracias(caminho, numAnalises);
        acuraciasDosClassificadores.add(acuraciaMeu);
        rotuloAlgoritmo.add("Meu");

        List<AcuraciaMedidas> acuraciaVFDT = ManipulaArquivos.carregaAcuracias(caminho2, numAnalises);
        acuraciasDosClassificadores.add(acuraciaVFDT);
        rotuloAlgoritmo.add("Hoeffding Adaptive Tree");

        List<AcuraciaMedidas> acuraciaJ48 = ManipulaArquivos.carregaAcuracias(caminho3, numAnalises);
        acuraciasDosClassificadores.add(acuraciaJ48);
        rotuloAlgoritmo.add("C4.5");

        List<AcuraciaMedidas> acuraciaEnsembleJ48 = ManipulaArquivos.carregaAcuracias(caminho4, numAnalises);
        acuraciasDosClassificadores.add(acuraciaEnsembleJ48);
        rotuloAlgoritmo.add("Ensemble de árvores C4.5");

        LineChart_AWT chart = new LineChart_AWT(
        "" ,
        "", acuraciasDosClassificadores, rotuloAlgoritmo);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}
