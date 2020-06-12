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
        String dataset = "moa";
        String current = (new File(".")).getCanonicalPath();
        String caminhoMeu = current + "/datasets-j48/" + dataset + "/"+ dataset + "-meu-predctions.txt";
        String caminhoHat = current + "/datasets-j48/" + dataset + "/"+ dataset + "-hat-predctions.txt";
        String caminhoArf = current + "/datasets-j48/" + dataset + "/"+ dataset + "-arf-predctions.txt";
        String caminhoJ48 = current + "/datasets-j48/" + dataset + "/"+ dataset + "-j48-predctions.txt";
        String caminhoEnsemble = current + "/datasets-j48/" + dataset + "/"+ dataset + "-ensemblej48-predctions.txt";

        List<List<AcuraciaMedidas>> acuraciasDosClassificadores = new ArrayList<>();
        List<String> rotuloAlgoritmo = new ArrayList<>();

        //moa
        int numAnalises = 90;

        //rbf
//        int numAnalises = 47;

        //forest
//        int numAnalises = 570;
        List<AcuraciaMedidas> acuraciaMeu = ManipulaArquivos.carregaAcuracias(caminhoMeu, numAnalises);
        acuraciasDosClassificadores.add(acuraciaMeu);
        rotuloAlgoritmo.add("FuzzCND");

        List<AcuraciaMedidas> acuraciaVFDT = ManipulaArquivos.carregaAcuracias(caminhoHat, numAnalises);
        acuraciasDosClassificadores.add(acuraciaVFDT);
        rotuloAlgoritmo.add("HAT");

        List<AcuraciaMedidas> acuraciaARF = ManipulaArquivos.carregaAcuracias(caminhoArf, numAnalises);
        acuraciasDosClassificadores.add(acuraciaVFDT);
        rotuloAlgoritmo.add("ARF");

        List<AcuraciaMedidas> acuraciaEnsembleJ48 = ManipulaArquivos.carregaAcuracias(caminhoEnsemble, numAnalises);
        acuraciasDosClassificadores.add(acuraciaEnsembleJ48);
        rotuloAlgoritmo.add("ADTE");

        List<AcuraciaMedidas> acuraciaJ48 = ManipulaArquivos.carregaAcuracias(caminhoJ48, numAnalises);
        acuraciasDosClassificadores.add(acuraciaJ48);
        rotuloAlgoritmo.add("C4.5");

        LineChart_AWT chart = new LineChart_AWT(
        "" ,
        "", acuraciasDosClassificadores, rotuloAlgoritmo);

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}
