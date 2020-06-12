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

        //MOA
//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("moa", caminho, taxaPoda, 6, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 12, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/moa/", "moa", comite, 100000, 40, 0.3, 0.95, 0.30);

//        RBF
//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("rbf", current + "/rbf/", taxaPoda, 6, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/rbf/", "rbf", comite, 100000, 40, 0.2, 0.95, 0.34);

//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("rbf", current + "/rbf/", taxaPoda, 3, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/rbf/", "rbf", comite, 50, 40, 0.1, 0.90, 0.05);

        //SyncEDC
        FaseOffline faseOffline = new FaseOffline();
        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("synedc", current + "/synedc/", taxaPoda, 6, 6, 5000, 50, 2, 1, 1);
        FaseOnline faseOnline = new FaseOnline(2,2, 50, 2, 2, 1,1, 5, 5000);
        faseOnline.inicializarFuzzyCMeans(current + "/synedc/", "synedc", comite, 2000, 40, 0.085, 0.40, 0.14);

        //forest
//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("forest", current + "/forest/", taxaPoda, 3, 6, 2000, 24, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, 24, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/forest/", "forest", comite, 2000, 40, 0.1, 0.95, 0.50);

//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("covtype", current + "/covtype/", taxaPoda, 3, 8, 5000, 50, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, 50, 2, 2, 1,1, 5, 5000);
//        faseOnline.inicializarFuzzyCMeans(current + "/covtype/", "covtype", comite, 2000, 40, 0.1, 0.95, 0.35);

        //        KDD
//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("kdd", current + "/kdd/", taxaPoda, 6, 6, 2000, 12, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/kdd/", "kdd", comite, 2000, 40, 0.2, 0.99, 0.0);

//        FaseOffline faseOffline = new FaseOffline();
//        ComiteArvores comite = faseOffline.inicializarFuzzyCMeans("kddn", current + "/kddn/", taxaPoda, 6, 6, 2000, 24, 2, 1, 1);
//        FaseOnline faseOnline = new FaseOnline(2,2, 24, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializarFuzzyCMeans(current + "/kddn/", "kddn", comite, 2000, 40, 0.2, 0.99, 0.50);
    }
}



