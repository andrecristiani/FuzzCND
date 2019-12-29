package main.java.FuzzyProject.FuzzyDT.Models;

import java.util.ArrayList;

public class DecisionTree {
    public int numObjetos;
    public int numAtributos;
    public int numConjuntos;
    public int numRegras;
    public String arvoreJ48;
    public String[][] particao;
    public String[][] regras;
    public ArrayList<String> rotulos;
    public ArrayList<Metadata> atributos;

    public DecisionTree() {
        rotulos = new ArrayList<String>();
        atributos = new ArrayList<Metadata>();
    }

    public void inicializaParticao(int numAtributos, int numConjuntos) {
        particao = new String[numConjuntos + 1][numAtributos + 2];
    }
}
