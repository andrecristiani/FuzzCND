package main.java.FuzzyProject;

import main.java.FuzzyProject.FuzzyDT.Fuzzy.CombinatoricException;
import main.java.FuzzyProject.FuzzyDT.Models.DecisionTree;
import main.java.FuzzyProject.FuzzyDT.Models.FDT;
import main.java.FuzzyProject.FuzzyDT.Utils.ConverteArquivos;
import main.java.FuzzyProject.FuzzyDT.Utils.gera10Folds;
import main.java.FuzzyProject.FuzzyDT.Utils.manipulaArquivos;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class FuzzyDecisionTree {
    public static void main(String[] args) throws IOException, CombinatoricException, Exception {
        int numCjtos = 7;
        String dataset = "iris";
        String taxaPoda = "25";
        String caminho = "";
        int numClassificador = 0;

        String current = (new File(".")).getCanonicalPath();
        FDT fdt;
        gera10Folds GF;
        String alg = "j48";

        caminho = current + "/" + dataset + "/";
        numClassificador = 0;

        DecisionTree dt = new DecisionTree(caminho, dataset, numClassificador = 0, taxaPoda);

        ConverteArquivos ca = new ConverteArquivos();
        fdt = new FDT();
        GF = new gera10Folds();
        manipulaArquivos mA1 = new manipulaArquivos();
        ca.main(dataset, numClassificador, dt);
        System.out.println("Database: " + dataset + numClassificador);
        GF.geraNFolds(dataset + numClassificador, caminho, 10);
        fdt.geraFuzzyDecisionTree(dataset + numClassificador, taxaPoda, numCjtos, caminho, dt);
        System.out.println("\nRules \t SD \tConjuntions \tSD");
        mA1.regras(dataset + numClassificador, caminho, alg);
        fdt.criaGruposEmNosFolhas(dataset+numClassificador, caminho, dt);
//        Vector vector = new Vector();
//        vector.add(5.0);
//        vector.add(2.0);
//        vector.add(3.5);
//        vector.add(1.0);
//        System.out.println(fdt.classificaExemplo(dt, vector));
//        System.out.println(fdt.classificaExemplo(dt, vector));
//        System.out.println(fdt.classificaExemplo(dt, vector));
//        System.out.println(fdt.classificaExemplo(dt, vector));
//        System.out.println(fdt.classificaExemplo(dt, vector));
        for(int i=0; i<dt.numRegrasAD; i++) {
//            System.out.println("Regra " + i + ": " + dt.numClassificadosPorRegraClassificacao[i]);
        }
        mA1.apagaArqsTemporarios(dataset + numClassificador, caminho);
    }
}



