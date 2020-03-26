package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Models.ComiteArvores;

public class FaseOffline {

    public ComiteArvores inicializar(String dataset, String caminho, String taxaPoda, int numCjtos, int tComite) throws Exception {
        ComiteArvores comite = new ComiteArvores(tComite);
        comite.treinaComiteInicial(dataset, caminho, taxaPoda, numCjtos);
        return comite;
//        mA1.apagaArqsTemporarios(dataset + numClassificador, caminho);
    }
}
