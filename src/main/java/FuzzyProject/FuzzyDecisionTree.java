package FuzzyProject;

import FuzzyProject.FuzzyDT.Fuzzy.CombinatoricException;
import FuzzyProject.FuzzyDT.Models.ComiteArvores;
import FuzzyProject.FuzzyND.FaseOffline;
import FuzzyProject.FuzzyND.FaseOnline;

import java.io.File;
import java.io.IOException;

public class FuzzyDecisionTree {
    public static void main(String[] args) throws IOException, CombinatoricException, Exception {
        int numCjtos = 6;
        String dataset = "forest";
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

        //MOA
//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("moa", caminho, taxaPoda, 6, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/moa/", "moa", comite, 10000, 40, 0.3, 0.95, 0.26);

//        RBF
//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("rbf", current + "/rbf/", taxaPoda, 6, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/rbf/", "rbf", comite, 100, 40, 0.2, 0.72, 0.04);

//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("rbf", current + "/rbf/", taxaPoda, 3, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/rbf/", "rbf", comite, 50, 40, 0.1, 0.90, 0.05);

        //forest
        FaseOffline faseOffline = new FaseOffline();
        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("forest", current + "/forest/", taxaPoda, 3, 6, 2000, 12, 2, 1, 1);
        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
        faseOnline.inicializarFuzzyCMeans(current + "/forest/", "forest", comite, 100, 40, 0.1, 0.70, 0.05);
    }
}



