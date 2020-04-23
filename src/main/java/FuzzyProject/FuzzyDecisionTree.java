package FuzzyProject;

import FuzzyProject.FuzzyDT.Fuzzy.CombinatoricException;
import FuzzyProject.FuzzyDT.Models.ComiteArvores;
import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.FaseOffline;
import FuzzyProject.FuzzyND.FaseOnline;

import java.io.File;
import java.io.IOException;

public class FuzzyDecisionTree {
    public static void main(String[] args) throws IOException, CombinatoricException, Exception {
        int numCjtos = 6;
        String dataset = "moa";
        String taxaPoda = "25";
        String caminho = "";
        String current = (new File(".")).getCanonicalPath();
        caminho = current + "/" + dataset + "/";
        int tComite = 6;
        int tChunk = 2000;
        int K = 12;
        int tDesconhecidos = 50;
        double phi = 0.1;
        double alpha = 1;
        double betha = 1;
        double todasTipMax = 0.95;
        double adaptadorTheta = 0.26;

        FaseOffline faseOffline = new FaseOffline();
        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans(dataset, caminho, taxaPoda, numCjtos, tComite, tChunk, K, 2, alpha, betha);

        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
        faseOnline.inicializarFuzzyCMeans(caminho, dataset, comite, tDesconhecidos, 40, phi, todasTipMax, adaptadorTheta);
    }
}



