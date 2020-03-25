package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Models.DecisionTree;
import FuzzyProject.FuzzyDT.Models.FDT;
import FuzzyProject.FuzzyDT.Utils.ConverteArquivos;
import FuzzyProject.FuzzyDT.Utils.gera10Folds;
import FuzzyProject.FuzzyDT.Utils.manipulaArquivos;

import java.io.File;

public class OfflinePhase {

    public void treinaComiteDeArvoresFuzzy(String dataset, String caminho, String taxaPoda, int numCjtos) throws Exception {
        int numClassificador = 0;
        DecisionTree dt = new DecisionTree(caminho, dataset, numClassificador = 0, taxaPoda);
        FDT fdt;
        gera10Folds GF;
        String alg = "j48";
        ConverteArquivos ca = new ConverteArquivos();
        fdt = new FDT();
        GF = new gera10Folds();
        manipulaArquivos mA1 = new manipulaArquivos();
        ca.main(dataset, numClassificador, dt);
        System.out.println("Database: " + dataset + numClassificador);
        GF.geraNFolds(dataset + numClassificador, caminho, 10);
        fdt.geraFuzzyDT(dataset + numClassificador, taxaPoda, numCjtos, caminho, dt);
        System.out.println("\nRules \t SD \tConjuntions \tSD");
        mA1.regras(dataset + numClassificador, caminho, alg);
        fdt.criaGruposEmNosFolhas(dataset+numClassificador, caminho, dt);
        float[] f = new float[4];
        f[0] = (float) 5.7;
        f[1] = (float) 4.4;
        f[2] = (float) 1.5;
        f[3] = (float) 0.4;
        float[] f2 = new float[4];
        f2[0] = (float) 7.9;
        f2[1] = (float) 1.5;
        f2[2] = (float) 7.9;
        f2[3] = (float) 1.9;
        System.out.println(dt.microGruposPorRegra.get(0).get(0).verificaSeExemploPertenceAoGrupo(f));
        System.out.println(dt.microGruposPorRegra.get(0).get(1).verificaSeExemploPertenceAoGrupo(f));
        System.out.println(dt.microGruposPorRegra.get(0).get(0).verificaSeExemploPertenceAoGrupo(f2));
        System.out.println(dt.microGruposPorRegra.get(0).get(1).verificaSeExemploPertenceAoGrupo(f2));
        mA1.apagaArqsTemporarios(dataset + numClassificador, caminho);
    }
}
