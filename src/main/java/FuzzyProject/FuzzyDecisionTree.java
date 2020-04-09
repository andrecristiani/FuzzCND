package FuzzyProject;

import FuzzyProject.FuzzyDT.Fuzzy.CombinatoricException;
import FuzzyProject.FuzzyDT.Models.ComiteArvores;
import FuzzyProject.FuzzyND.FaseOffline;
import FuzzyProject.FuzzyND.FaseOnline;
import FuzzyProject.FuzzyND.Models.Exemplo;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuzzyDecisionTree {
    public static void main(String[] args) throws IOException, CombinatoricException, Exception {
        int numCjtos = 7;
        String dataset = "iris";
        String taxaPoda = "25";
        String caminho = "";
        String current = (new File(".")).getCanonicalPath();
        caminho = current + "/" + dataset + "/";
        int tComite = 8;
        int tChunk = 50;
        int K = 50;

        FaseOffline faseOffline = new FaseOffline();
        ComiteArvores comite = faseOffline.inicializar(dataset, caminho, taxaPoda, numCjtos, tComite, tChunk, K);

//        FaseOnline faseOnline = new FaseOnline(2,2, K, 2, 2, 1,1, 5, 2000);
//        faseOnline.inicializar(caminho, dataset, comite);

        ConverterUtils.DataSource source;
        Instances data;
        try {
            source = new ConverterUtils.DataSource(caminho + dataset + "-test.arff");
            data = source.getDataSet();
            List<Exemplo> exemplosRotulados = new ArrayList<>();
            for(int i=0; i<data.size(); i++) {
                Instance ins = data.get(i);
                Exemplo exemplo = new Exemplo(ins.toDoubleArray(), true);
                exemplosRotulados.add(exemplo);
            }
            comite.treinaNovaArvore(exemplosRotulados, tChunk, K);
            System.out.println("Terminou");
        } catch (Exception ex) {
            System.err.println("Erro:" + ex);
        }
    }
}



