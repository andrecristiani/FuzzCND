package FuzzyProject.FuzzyDT.Models;

import FuzzyProject.FuzzyDT.Utils.ConverteArquivos;
import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Exemplo;

import java.util.*;

public class ComiteArvores {

    public int tamanhoMaximo;
    public int numAtributos;
    public List<String> atributos = new ArrayList<>();
    public List<DecisionTree> modelos = new ArrayList<>();
    public List<String> rotulosConhecidos = new ArrayList<>();
    public Map<String, Integer> hashmapRotulos = new HashMap<>(); //para converter String para Integer

    public FDT fdt = new FDT();
    public ConverteArquivos ca = new ConverteArquivos();
    public ManipulaArquivos ma = new ManipulaArquivos();

    public ComiteArvores(int tamanhoMaximo) {
        this.tamanhoMaximo = tamanhoMaximo;
    }

    public void treinaComiteInicial(String dataset, String caminho, String taxaPoda, int numCjtos, int tChunk) throws Exception {
        int qtdClassificadores = ca.main(dataset, this, tChunk);
        for(int i=0; i<qtdClassificadores; i++) {
            DecisionTree dt = new DecisionTree(caminho, dataset, i, taxaPoda);
            dt.numObjetos = ma.getNumExemplos(caminho+dataset + i + ".txt");
            dt.numAtributos = this.numAtributos;
            dt.atributos = this.atributos;
            fdt.geraFuzzyDT(dataset + i, taxaPoda, numCjtos, caminho, dt);
            fdt.criaGruposEmNosFolhas(dataset+i, caminho, dt);
//            ma.apagaArqsTemporarios(dataset + i, caminho);
            this.modelos.add(dt);
        }
//        this.atualizaRotulosConhecidos();
    }

    public String classificaExemploVotoMajoritario(double[] exemplo) {
        Map<String, Integer> numeroVotos = new HashMap<>();
        for(int i=0; i<rotulosConhecidos.size(); i++) {
            numeroVotos.put(rotulosConhecidos.get(i), 0);
        }
        numeroVotos.put("desconhecido", 0);

        Vector v = new Vector<>();
        for(int i=0; i<exemplo.length; i++) {
            v.add(exemplo[i]);
        }

        for(int i=0; i<modelos.size(); i++) {
            String rotuloVotado = fdt.classificaExemplo(modelos.get(0), v);
            numeroVotos.replace(rotuloVotado, numeroVotos.get(rotuloVotado) + 1);
        }

        if(numeroVotos.get("desconhecido") == numeroVotos.size()) {
            return "desconhecido";
        } else {
            numeroVotos.remove("desconhecido");
            int valorMaior = -1;
            String indiceMaior = null;

            for(int i=0; i<numeroVotos.size(); i++) {
                String rotulo = rotulosConhecidos.get(i);
                if(valorMaior < numeroVotos.get(rotulo)) {
                    valorMaior = numeroVotos.get(rotulo);
                    indiceMaior = rotulo;
                }
            }
            return indiceMaior;
        }
    }

    public void removeClassificadorComMenorDesempenho(List<Exemplo> exemplosRotulados) {
        double[] pontuacaoArvores = new double[this.modelos.size()];
        for(int i=0; i<this.modelos.size(); i++) {
            pontuacaoArvores[i] = 0;
        }

        for(int i=0; i<exemplosRotulados.size(); i++) {
            Vector v = new Vector<>();
            double[] exemplo = exemplosRotulados.get(i).getPoint();
            for(int j=0; j<exemplo.length; j++) {
                v.add(exemplo[j]);
            }
            for(int k=0; k<this.modelos.size(); k++) {
                String rotuloClassificado = this.fdt.classificaExemplo(this.modelos.get(k), v);
                String rotuloVerdadeiro = exemplosRotulados.get(i).getRotulo();

                if(rotuloClassificado.equals(rotuloVerdadeiro)) {
                    pontuacaoArvores[k]++;
                }
            }
        }

        List<Double> acuraciaArvores = new ArrayList<>();
        for(int i=0; i<this.modelos.size(); i++) {
            acuraciaArvores.add(((pontuacaoArvores[i]/exemplosRotulados.size())*100));
        }

        System.out.println("TESTE DE NOVO");

    }

    private void atualizaRotulosConhecidos() {
        this.rotulosConhecidos.clear();
        for(int i=0; i<this.modelos.size(); i++) {
            for(int j=0; j<this.modelos.get(i).rotulos.size(); j++) {
                if(!this.rotulosConhecidos.contains(this.modelos.get(i).rotulos.get(j))) {
                    this.rotulosConhecidos.add(this.modelos.get(i).rotulos.get(j));
                }
            }
        }
    }
}
