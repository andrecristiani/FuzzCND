package FuzzyProject.FuzzyDT.Models;

import FuzzyProject.FuzzyDT.Utils.ConverteArquivos;
import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Utils.MedidasDeDistancia;

import java.util.*;

public class ComiteArvores {

    public int tamanhoMaximo;
    public int numAtributos;
    public String dataset;
    public String caminho;
    public String taxaPoda;
    public int numCjtos;
    public List<String> atributos = new ArrayList<>();
    public List<DecisionTree> modelos = new ArrayList<>();
    public List<String> rotulosConhecidos = new ArrayList<>();
    public List<Integer> numeroClassificadores = new ArrayList<>();
    public Map<String, Integer> hashmapRotulos = new HashMap<>(); //para converter String para Integer

    public FDT fdt = new FDT();
    public ConverteArquivos ca = new ConverteArquivos();
    public ManipulaArquivos ma = new ManipulaArquivos();

    public ComiteArvores(String dataset, String caminho, String taxaPoda, int numCjtos, int tamanhoMaximo) {
        this.dataset = dataset;
        this.caminho = caminho;
        this.taxaPoda = taxaPoda;
        this.numCjtos = numCjtos;
        this.tamanhoMaximo = tamanhoMaximo;
    }

    public void treinaComiteInicialKMeans(int tChunk, int K) throws Exception {
        int qtdClassificadores = ca.main(this.dataset, this, tChunk);
        for(int i=0; i<qtdClassificadores; i++) {
            DecisionTree dt = new DecisionTree(this.caminho, this.dataset, i, this.taxaPoda);
            dt.numObjetos = ma.getNumExemplos(this.caminho+this.dataset + i + ".txt");
            dt.numAtributos = this.numAtributos;
            dt.atributos = this.atributos;
            fdt.geraFuzzyDT(this.dataset + i, this.taxaPoda, this.numCjtos, this.caminho, dt);
            fdt.criaGruposEmNosFolhasKMeans(this.dataset+i, this.caminho, dt, tChunk, K);
            ma.apagaArqsTemporarios(dataset + i, caminho);
            this.modelos.add(dt);
        }
    }

    public void treinaComiteInicialFuzzyCMeans(int tChunk, int K, double fuzzificacao) throws Exception {
        int qtdClassificadores = ca.main(this.dataset, this, tChunk);
        for(int i=0; i<qtdClassificadores; i++) {
            DecisionTree dt = new DecisionTree(this.caminho, this.dataset, i, this.taxaPoda);
            dt.numObjetos = ma.getNumExemplos(this.caminho+this.dataset + i + ".txt");
            dt.numAtributos = this.numAtributos;
            dt.atributos = this.atributos;
            fdt.geraFuzzyDT(this.dataset + i, this.taxaPoda, this.numCjtos, this.caminho, dt);
            fdt.criaGruposEmNosFolhasFuzzyCMeans(this.dataset+i, this.caminho, dt, tChunk, K, fuzzificacao);
            ma.apagaArqsTemporarios(dataset + i, caminho);
            this.modelos.add(dt);
        }
    }

    public String classificaExemploAgrupamentoExterno(double[] exemplo) {
        if(this.calculaFoutlier(exemplo)) {
            return "desconhecido";
        } else {
            Map<String, Integer> numeroVotos = new HashMap<>();
            for (int i = 0; i < rotulosConhecidos.size(); i++) {
                numeroVotos.put(rotulosConhecidos.get(i), 0);
            }

            Vector v = new Vector<>();
            for (int i = 0; i < exemplo.length; i++) {
                v.add(exemplo[i]);
            }

            for (int i = 0; i < modelos.size(); i++) {
                String rotuloVotado = fdt.classificaExemploSemGruposNosFolhas(modelos.get(0), v);
                numeroVotos.replace(rotuloVotado, numeroVotos.get(rotuloVotado) + 1);
            }

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

    public String classificaExemploAgrupamentoNoFolha(double[] exemplo) {
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
            String rotuloVotado = fdt.classificaExemploFuzzyCMeans(modelos.get(0), v);
            numeroVotos.replace(rotuloVotado, numeroVotos.get(rotuloVotado) + 1);
        }

        if(numeroVotos.get("desconhecido") == this.modelos.size()) {
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

    private void removeClassificadorComMenorDesempenho(List<Exemplo> exemplosRotulados) {
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
                String rotuloClassificado = this.fdt.classificaExemploSemGruposNosFolhas(this.modelos.get(k), v);
                String rotuloVerdadeiro = exemplosRotulados.get(i).getRotuloVerdadeiro();

                if(rotuloClassificado.equals(rotuloVerdadeiro)) {
                    pontuacaoArvores[k]++;
                }
            }
        }

        List<Double> acuraciaArvores = new ArrayList<>();
        double acuraciaMinima = 100;
        int index = 0;
        for(int i=0; i<this.modelos.size(); i++) {
            double acuracia = ((pontuacaoArvores[i]/exemplosRotulados.size())*100);
            acuraciaArvores.add(acuracia);
            if(acuracia < acuraciaMinima) {
                acuraciaMinima = acuracia;
                index = i;
            }
        }
        this.modelos.remove(index);
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

    public void treinaNovaArvore(List<Exemplo> exemplosRotulados, int tChunk, int K) throws Exception {
        if(this.modelos.size() >= tamanhoMaximo) {
            this.removeClassificadorComMenorDesempenho(exemplosRotulados);
        }
        int nClassificador = ca.mainParaExemplosRotulados(this.dataset, exemplosRotulados, this, tChunk);
        DecisionTree dt = new DecisionTree(this.caminho, this.dataset, nClassificador, this.taxaPoda);
        dt.numObjetos = ma.getNumExemplos(this.caminho+this.dataset + nClassificador + ".txt");
        dt.numAtributos = this.numAtributos;
        dt.atributos = this.atributos;
        fdt.geraFuzzyDT(this.dataset + nClassificador, this.taxaPoda, this.numCjtos, this.caminho, dt);
        fdt.criaGruposEmNosFolhasKMeans(this.dataset+nClassificador, this.caminho, dt, tChunk, K);
        ma.apagaArqsTemporarios(dataset + nClassificador, caminho);
        this.modelos.add(dt);
        dt.elementosPorRegraKMeans.clear();
    }

    public boolean calculaFoutlier(double[] exemplo) {
        double distExemploCentroide = 0;
        for(int i=0; i<this.modelos.size(); i++) {
            List<List<MicroGrupo>> microGruposPorRegra = this.modelos.get(i).microGruposPorRegra;
            for(int j=0; j<microGruposPorRegra.size(); j++) {
                List<MicroGrupo> regras = microGruposPorRegra.get(j);
                for(int k=0; k<regras.size(); k++) {
                    distExemploCentroide = MedidasDeDistancia.calculaDistanciaEuclidiana(exemplo, regras.get(k).getCentroide());
                    if (distExemploCentroide <= regras.get(k).raio) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
