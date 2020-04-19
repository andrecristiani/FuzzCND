package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Models.ComiteArvores;

public class FaseOffline {

    public ComiteArvores inicializarKMeans(String dataset, String caminho, String taxaPoda, int numCjtos, int tComite, int tChunk, int K) throws Exception {
        ComiteArvores comite = new ComiteArvores(dataset, caminho, taxaPoda, numCjtos, tComite);
        comite.treinaComiteInicialKMeans(tChunk, K);
        return comite;
    }

    public ComiteArvores inicializarFuzzyCMeans(String dataset, String caminho, String taxaPoda, int numCjtos, int tComite, int tChunk, int K, double fuzzificacao, double alpha, double betha) throws Exception {
        ComiteArvores comite = new ComiteArvores(dataset, caminho, taxaPoda, numCjtos, tComite);
        comite.treinaComiteInicialFuzzyCMeans(tChunk, K, fuzzificacao, alpha, betha);
        return comite;
    }
}
