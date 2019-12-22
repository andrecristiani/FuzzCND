package main.java.FuzzyProject;

import main.java.FuzzyProject.FuzzyDT.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FuzzyDecisionTree {
    public static void main(String[] args) throws IOException, CombinatoricException, Exception {
        DecisionTree dt = new DecisionTree();
        manipulaArquivos mA = new manipulaArquivos();
        boolean linhadecomando = false;
        int numCjtos = 3;
        String dataset = "iris";
        String metodoGerarParticao = "wm";
        String taxaPoda = "25";
        if (linhadecomando) {
            dataset = args[0];
            metodoGerarParticao = args[1];
            taxaPoda = args[2];
            if (metodoGerarParticao.compareTo("fixed") == 0) {
                numCjtos = Integer.parseInt(args[3]);
            }
        }

        String current = (new File(".")).getCanonicalPath();
        ConverteArquivos ca = new ConverteArquivos();
        ArrayList<Exemplo> exemplos = new ArrayList<>();
        ca.main(dataset);
        String caminho = current + "/" + dataset + "/";
        FDT fdt;
        gera10Folds GF;
        String alg;
        if (metodoGerarParticao.compareTo("infogain") == 0 || metodoGerarParticao.compareTo("wm") == 0 || metodoGerarParticao.compareTo("rf") == 0 || metodoGerarParticao.compareTo("predefined") == 0) {
            System.out.println("0");
            fdt = new FDT();
            GF = new gera10Folds();
            GF.geraNFolds(dataset, caminho, 10);
            fdt.geraFuzzyDecisionTree(dataset, metodoGerarParticao, taxaPoda, numCjtos, caminho);
            alg = "j48";
            System.out.println("\nRules \t SD \tConjuntions \tSD");
            mA.regras(dataset, caminho, alg);
            mA.apagaArqsTemporarios(dataset, caminho);
        }

        if (metodoGerarParticao.compareTo("fixed") == 0) {
            System.out.println("1");
            fdt = new FDT();
            GF = new gera10Folds();
            GF.geraNFolds(dataset, caminho, 10);
            fdt.geraFuzzyDecisionTree(dataset, metodoGerarParticao, taxaPoda, numCjtos, caminho);
            alg = "j48";
            System.out.println("\nRules \t SD \tConjuntions \tSD");
            mA.regras(dataset, caminho, alg);
            mA.apagaArqsTemporarios(dataset, caminho);
        }

        if (metodoGerarParticao.compareTo("infogain") != 0 && metodoGerarParticao.compareTo("wm") != 0 && metodoGerarParticao.compareTo("rf") != 0 && metodoGerarParticao.compareTo("predefined") != 0 && metodoGerarParticao.compareTo("fixed") != 0) {
            System.out.println("Fuzzy Data Base generation option invalid...");
        }
    }
}



