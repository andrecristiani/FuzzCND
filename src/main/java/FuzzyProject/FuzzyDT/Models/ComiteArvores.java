package FuzzyProject.FuzzyDT.Models;

import FuzzyProject.FuzzyDT.Utils.ConverteArquivos;
import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ComiteArvores {

    public int tamanhoMaximo;
    public int numAtributos;
    public List<String> atributos = new ArrayList<>();
    public List<DecisionTree> modelos = new ArrayList<>();

    public FDT fdt = new FDT();
    public ConverteArquivos ca = new ConverteArquivos();
    public ManipulaArquivos ma = new ManipulaArquivos();

    public ComiteArvores(int tamanhoMaximo) {
        this.tamanhoMaximo = tamanhoMaximo;
    }

    public void treinaComiteInicial(String dataset, String caminho, String taxaPoda, int numCjtos) throws Exception {
        int qtdClassificadores = ca.main(dataset, this, this.tamanhoMaximo);
        for(int i=0; i<qtdClassificadores; i++) {
            DecisionTree dt = new DecisionTree(caminho, dataset, i, taxaPoda);
            dt.numObjetos = ma.getNumExemplos(caminho+dataset + i + ".txt");
            dt.numAtributos = this.numAtributos;
            dt.atributos = this.atributos;
            fdt.geraFuzzyDT(dataset + i, taxaPoda, numCjtos, caminho, dt);
            fdt.criaGruposEmNosFolhas(dataset+i, caminho, dt);
            this.modelos.add(dt);
        }
    }

    public String classificaExemplo(float[] exemplo) {
        Vector v = new Vector<>();
        for(int i=0; i<exemplo.length; i++) {
            v.add(exemplo[i]);
        }
        return fdt.classificaExemplo(modelos.get(0), v);
    }
}
