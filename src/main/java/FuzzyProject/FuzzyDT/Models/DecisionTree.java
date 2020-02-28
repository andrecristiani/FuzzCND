package main.java.FuzzyProject.FuzzyDT.Models;

import main.java.FuzzyProject.FuzzyDT.Utils.manipulaArquivos;
import weka.core.FastVector;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DecisionTree {
    public int inicializada;
    public int numObjetos;
    public int numAtributos;
    public int numConjuntos;
    public int numRegrasAD;
    public int nVE;
    public String caminho;
    public String dataset;
    public int numClassificador;
    public String taxaPoda;
    public String arvoreJ48;
    public String[][] particao;
    public String[][] regrasAD;
    public ArrayList<String> rotulos;
    public ArrayList<String> atributos;
    public FastVector atributosParaWeka;
    Instances datasetParaWeka;
    public List<List<Vector>> numClassificadosPorRegraClassificacao;
    public Integer[] numClassificadosPorRegraTreinamento;

    public DecisionTree(String caminho, String dataset, int numClassificador, String taxaPoda) {
        this.caminho = caminho;
        this.dataset = dataset;
        this.numClassificador = numClassificador;
        this.taxaPoda = taxaPoda;
        rotulos = new ArrayList<String>();
        atributos = new ArrayList<String>();
        this.inicializada = 0;
    }

    public void inicializaValores() {
        this.inicializada = 1;
        manipulaArquivos mA = new manipulaArquivos();
        mA.carregaParticao(this.particao, (this.caminho + "particao" + this.dataset + numClassificador  + ".txt"), this.numAtributos, this.numConjuntos);
        this.numRegrasAD = mA.getNumRegrasAD(this.caminho + "RegrasFC45-" + this.dataset + numClassificador + this.taxaPoda + ".txt");
        this.numClassificadosPorRegraTreinamento = new Integer[this.numRegrasAD];
        this.numClassificadosPorRegraClassificacao = new ArrayList<>();
        for(int i=0; i<=numRegrasAD; i++) {
            numClassificadosPorRegraClassificacao.add(new ArrayList<Vector>());
        }
        this.regrasAD = mA.carregaRegrasAD(this.caminho + "RegrasFC45-" + this.dataset + numClassificador  + this.taxaPoda + ".txt", this.numAtributos, this.numRegrasAD);
    }

    public void inicializaValoresParaClassificacaoWeka(FastVector atributos, Instances instancias) {
        this.atributosParaWeka = atributos;
        this.datasetParaWeka = instancias;
    }
}
