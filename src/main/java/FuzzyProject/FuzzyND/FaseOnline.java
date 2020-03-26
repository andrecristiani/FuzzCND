package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Utils.manipulaArquivos;
import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.MicroClassificador;
import FuzzyProject.FuzzyND.Models.SPFMiC;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;

import java.util.ArrayList;
import java.util.List;

public class FaseOnline {

    public int K; //numero de clusters por chunk
    public int T; //tamanho do chunk
    public double fuzzificacao;
    public double tipicidade;
    public List<Exemplo> memTempRotulados;
    public List<Exemplo> memTempDesconhecidos;
    public List<MicroClassificador> microClassificadores;

    public FaseOnline(double fuzzificacao, double tipicidade, int K) {
        this.K = K;
        this.fuzzificacao = fuzzificacao;
        this.tipicidade = tipicidade;
        memTempRotulados = new ArrayList<>();
        memTempDesconhecidos = new ArrayList<>();
        microClassificadores = new ArrayList<>();
    }

    public void inicializar(String caminho, String dataset) {
        float[][] treinamento;
        treinamento = new float[150][4];
        manipulaArquivos mA = new manipulaArquivos();
        mA.carregaArquivoTreinamento(treinamento, caminho + dataset + "0.txt", 4);

        double[][] exemplos2 = new double[treinamento.length][4];
        List<Exemplo> exemplos = new ArrayList<>();
        for(int i=0; i<treinamento.length; i++) {
            exemplos2[i][0] = treinamento[i][0];
            exemplos2[i][1] = treinamento[i][1];
            exemplos2[i][2] = treinamento[i][2];
            exemplos2[i][3] = treinamento[i][3];
            exemplos.add(new Exemplo(exemplos2[i]));
        }

        this.microClassificadores = criarFuzzyMicroGruposComFCMDadosSemRotulos(exemplos);
        System.out.println("teste");
    }

    public List<MicroClassificador> criarFuzzyMicroGruposComFCMDadosSemRotulos(List<Exemplo> exemplos) {
        FuzzyKMeansClusterer fuzzyClusterer = new FuzzyKMeansClusterer(this.K, this.fuzzificacao);
        fuzzyClusterer.cluster(exemplos);
        List<CentroidCluster> clusters = fuzzyClusterer.getClusters();
        Exemplo ex = exemplos.get(0);
        List<MicroClassificador> microClassificadores = new ArrayList<>();
        for(int i=0; i<this.K; i++) {
            MicroClassificador microClassificador = new MicroClassificador();
            double[] centroide = clusters.get(i).getCenter().getPoint();
            int nExemplos = clusters.get(i).getPoints().size();
            SPFMiC microGrupo = new SPFMiC(ex.getPoint().length, centroide, nExemplos);
            microClassificador.addMicroGrupo(microGrupo);
            microClassificadores.add(microClassificador);
        }
        return microClassificadores;
    }

    //TODO:Testar e refazer este método
    public List<MicroClassificador> criaFuzzyMicroGruposComFCDDadosRotulados(List<Exemplo> exemplos) {
        FuzzyKMeansClusterer fuzzyClusterer = new FuzzyKMeansClusterer(this.K, this.fuzzificacao);
        fuzzyClusterer.cluster(exemplos);
        List<CentroidCluster> clusters = fuzzyClusterer.getClusters();
        Exemplo ex = exemplos.get(0);
        List<MicroClassificador> microClassificadores = new ArrayList<>();
        double[][] dadosMatriz = fuzzyClusterer.getMembershipMatrix().getData();
        for(int i=0; i<exemplos.size(); i++) {
            int index = 0;
            double valorMaior = 0;
            for (int j = 0; j < this.K; j++) {
                if (valorMaior < dadosMatriz[i][j]) {
                    index = j;
                    valorMaior = dadosMatriz[i][j];
                }
            }
            //TODO: verificar o n de microGrupo que vai ser pego no 3 get (default 0 temporariamente)
            for (int l = 0; l < ex.getPoint().length; l++) { //TODO: ver como pegar o n de atributos automaticamente
                double[] exemplo = exemplos.get(i).getPoint();
                microClassificadores.get(index).getMicroGrupos().get(0).LSm[l] = 0;
                microClassificadores.get(index).getMicroGrupos().get(0).LSn[l] = 0;
//                microClassificadores.get(index).getMicroGrupos().get(0).LSpertinencias[l] += Math.pow(dadosMatriz[i][index], microClassificadores.get(index).getMicroGrupos().get(0));
//                microClassificadores.get(index).getMicroGrupos().get(0).LStipicidades[l] += Math.pow(exemplo[l], microClassificadores.get(index).getMicroGrupos().get(0).);
                microClassificadores.get(index).getMicroGrupos().get(0).somaDasDistancias[l] = 0;
            }
            microClassificadores.get(index).getMicroGrupos().get(0).N++;
        }
        return microClassificadores;
    }
}
