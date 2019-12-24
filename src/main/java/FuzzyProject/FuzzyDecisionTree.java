package main.java.FuzzyProject;

import main.java.FuzzyProject.FuzzyDT.Fuzzy.CombinatoricException;
import main.java.FuzzyProject.FuzzyDT.Models.DecisionTree;
import main.java.FuzzyProject.FuzzyDT.Models.FDT;
import main.java.FuzzyProject.FuzzyDT.Utils.ConverteArquivos;
import main.java.FuzzyProject.FuzzyDT.Utils.gera10Folds;
import main.java.FuzzyProject.FuzzyDT.Utils.manipulaArquivos;

import java.io.File;
import java.io.IOException;

public class FuzzyDecisionTree {
    public static void main(String[] args) throws IOException, CombinatoricException, Exception {
        DecisionTree dt = new DecisionTree();
        int numCjtos = 7;
        String dataset = "iris";
        String metodoGerarParticao = "wm";
        String taxaPoda = "25";
        String caminho = "";
        int numClassificador = 0;

        String current = (new File(".")).getCanonicalPath();
        FDT fdt;
        gera10Folds GF;
        String alg = "j48";

        ConverteArquivos ca = new ConverteArquivos();
        fdt = new FDT();
        GF = new gera10Folds();
        manipulaArquivos mA1 = new manipulaArquivos();
        numClassificador = 0;
        ca.main(dataset, numClassificador);
        caminho = current + "/" + dataset + "/";
        System.out.println("Database: " + dataset + numClassificador);
        GF.geraNFolds(dataset + numClassificador, caminho, 10);
        fdt.geraFuzzyDecisionTree(dataset + numClassificador, taxaPoda, numCjtos, caminho);
        System.out.println("\nRules \t SD \tConjuntions \tSD");
        mA1.regras(dataset + numClassificador, caminho, alg);
        mA1.apagaArqsTemporarios(dataset + numClassificador, caminho);

        FDT fdt1 = new FDT();
        gera10Folds GF1 = new gera10Folds();
        manipulaArquivos mA = new manipulaArquivos();
        numClassificador = 1;
        ca.main(dataset, numClassificador);
        caminho = current + "/" + dataset + "/";
        System.out.println("Database: " + dataset + numClassificador);
        GF1.geraNFolds(dataset + numClassificador, caminho, 10);
        fdt1.geraFuzzyDecisionTree(dataset + numClassificador, taxaPoda, numCjtos, caminho);
        System.out.println("\nRules \t SD \tConjuntions \tSD");
        mA.regras(dataset + numClassificador, caminho, alg);
        mA.apagaArqsTemporarios(dataset + numClassificador, caminho);

        FDT fdt2 = new FDT();
        gera10Folds GF2 = new gera10Folds();
        manipulaArquivos mA2 = new manipulaArquivos();
        numClassificador = 2;
        ca.main(dataset, numClassificador);
        caminho = current + "/" + dataset + "/";
        System.out.println("Database: " + dataset + numClassificador);
        GF2.geraNFolds(dataset + numClassificador, caminho, 10);
        fdt2.geraFuzzyDecisionTree(dataset + numClassificador, taxaPoda, numCjtos, caminho);
        System.out.println("\nRules \t SD \tConjuntions \tSD");
        mA2.regras(dataset + numClassificador, caminho, alg);
        mA2.apagaArqsTemporarios(dataset + numClassificador, caminho);

        FDT fdt3 = new FDT();
        gera10Folds GF3 = new gera10Folds();
        manipulaArquivos mA3 = new manipulaArquivos();
        numClassificador = 3;
        ca.main(dataset, numClassificador);
        caminho = current + "/" + dataset + "/";
        System.out.println("Database: " + dataset + numClassificador);
        GF3.geraNFolds(dataset + numClassificador, caminho, 10);
        fdt3.geraFuzzyDecisionTree(dataset + numClassificador, taxaPoda, numCjtos, caminho);
        System.out.println("\nRules \t SD \tConjuntions \tSD");
        mA3.regras(dataset + numClassificador, caminho, alg);
        mA3.apagaArqsTemporarios(dataset + numClassificador, caminho);
    }
}



