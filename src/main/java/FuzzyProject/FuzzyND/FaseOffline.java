package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Models.ComiteArvores;

public class FaseOffline {

    public ComiteArvores inicializarKMeans(String dataset, String caminho, String taxaPoda, int numCjtos, int tComite, int tChunk, int K, double fuzzificacao) throws Exception {
        ComiteArvores comite = new ComiteArvores(dataset, caminho, taxaPoda, numCjtos, tComite);
        comite.treinaComiteInicial(tChunk, K, fuzzificacao);
        return comite;
    }

    public ComiteArvores inicializarFuzzyCMeans(String dataset, String caminho, String taxaPoda, int numCjtos, int tComite, int tChunk, int K, double fuzzificacao) throws Exception {
        ComiteArvores comite = new ComiteArvores(dataset, caminho, taxaPoda, numCjtos, tComite);
        comite.treinaComiteInicial(tChunk, K, fuzzificacao);
        return comite;
    }
}
