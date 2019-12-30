package main.java.FuzzyProject.FuzzyDT.Models;

import main.java.FuzzyProject.FuzzyDT.Utils.manipulaArquivos;

import java.util.ArrayList;

public class DecisionTree {
    public int numObjetos;
    public int numAtributos;
    public int numConjuntos;
    public int numRegrasAD;
    public String caminho;
    public String dataset;
    public int numClassificador;
    public String taxaPoda;
    public String arvoreJ48;
    public String[][] particao;
    public String[][] regrasAD;
    public ArrayList<String> rotulos;
    public ArrayList<Metadata> atributos;

    public DecisionTree(String caminho, String dataset, int numClassificador, String taxaPoda) {
        this.caminho = caminho;
        this.dataset = dataset;
        this.numClassificador = numClassificador;
        this.taxaPoda = taxaPoda;
        rotulos = new ArrayList<String>();
        atributos = new ArrayList<Metadata>();
    }

    public void inicializaValores() {
        manipulaArquivos mA = new manipulaArquivos();
        mA.carregaParticao(this.particao, (this.caminho + "particao" + this.dataset + numClassificador  + ".txt"), this.numAtributos, this.numConjuntos);
        this.numRegrasAD = mA.getNumRegrasAD(this.caminho + "RegrasFC45-" + this.dataset + numClassificador + this.taxaPoda + ".txt");
        this.regrasAD = mA.carregaRegrasAD(this.caminho + "RegrasFC45-" + this.dataset + numClassificador  + this.taxaPoda + ".txt", this.numAtributos, this.numRegrasAD);
    }
}
