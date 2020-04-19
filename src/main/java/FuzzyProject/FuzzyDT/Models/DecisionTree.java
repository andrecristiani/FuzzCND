package FuzzyProject.FuzzyDT.Models;

import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.SFMiC;
import FuzzyProject.FuzzyND.Models.SPFMiC;
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
    public int t;
    public String taxaPoda;
    public String arvoreJ48;
    public String[][] particao;
    public String[][] regrasAD;
    public List<String> rotulos = new ArrayList<String>();
    public List<String> atributos = new ArrayList<String>();
    public FastVector atributosParaWeka;
    Instances datasetParaWeka;
    public List<ArrayList<Vector>> elementosPorRegraKMeans;
    public List<ArrayList<Exemplo>> elementosPorRegraFuzzyCMeans;
    public List<List<MicroGrupo>> microGruposPorRegra;
    public List<List<SPFMiC>> sfMicPorRegra;
    public List<String> rotulosDasRegras;

    public DecisionTree(String caminho, String dataset, int numClassificador, String taxaPoda, int tempo) {
        this.caminho = caminho;
        this.dataset = dataset;
        this.numClassificador = numClassificador;
        this.taxaPoda = taxaPoda;
        this.inicializada = 0;
        this.t = tempo;
    }

    public void inicializaValores() {
        this.inicializada = 1;
        ManipulaArquivos mA = new ManipulaArquivos();
        mA.carregaParticao(this.particao, (this.caminho + "particao" + this.dataset + numClassificador  + ".txt"), this.numAtributos, this.numConjuntos);
        this.numRegrasAD = mA.getNumRegrasAD(this.caminho + "RegrasFC45-" + this.dataset + numClassificador + this.taxaPoda + ".txt");
        this.elementosPorRegraKMeans = new ArrayList<ArrayList<Vector>>();
        this.elementosPorRegraFuzzyCMeans = new ArrayList<ArrayList<Exemplo>>();
        this.rotulosDasRegras = new ArrayList<>();
        this.microGruposPorRegra = new ArrayList<List<MicroGrupo>>();
        this.sfMicPorRegra = new ArrayList<List<SPFMiC>>();
        for(int i=0; i<=numRegrasAD; i++) {
            this.microGruposPorRegra.add(new ArrayList<MicroGrupo>());
            this.sfMicPorRegra.add(new ArrayList<SPFMiC>());
            this.elementosPorRegraKMeans.add(new ArrayList<Vector>());
            this.elementosPorRegraFuzzyCMeans.add(new ArrayList<Exemplo>());
        }
        this.regrasAD = mA.carregaRegrasAD(this.caminho + "RegrasFC45-" + this.dataset + numClassificador  + this.taxaPoda + ".txt", this.numAtributos, this.numRegrasAD);
    }

    public void inicializaValoresParaClassificacaoWeka(FastVector atributos, Instances instancias) {
        this.atributosParaWeka = atributos;
        this.datasetParaWeka = instancias;
    }
}
