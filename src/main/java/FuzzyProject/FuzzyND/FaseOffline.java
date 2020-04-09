package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Models.ComiteArvores;

public class FaseOffline {

    public ComiteArvores inicializar(String dataset, String caminho, String taxaPoda, int numCjtos, int tComite, int tChunk, int K) throws Exception {
        ComiteArvores comite = new ComiteArvores(dataset, caminho, taxaPoda, numCjtos, tComite);
        comite.treinaComiteInicial(tChunk, K);
        return comite;
    }
}
