package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.MicroClassificador;
import FuzzyProject.FuzzyND.Models.ModeloNS;
import FuzzyProject.FuzzyND.Models.SPFMiC;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaseOnline {

    public int K; //numero de clusters por chunk
    public int T; //tamanho do chunk
    public int m;
    public int n;
    public double fuzzificacao;
    public double tipicidade;
    public double alpha;
    public double theta;
    public List<Exemplo> memTempRotulados;
    public List<Exemplo> memTempDesconhecidos;
    ModeloNS modeloNS;

    public FaseOnline(double fuzzificacao, double tipicidade, int K) {
        this.K = K;
        this.fuzzificacao = fuzzificacao;
        this.tipicidade = tipicidade;
        memTempRotulados = new ArrayList<>();
        memTempDesconhecidos = new ArrayList<>();
        modeloNS = new ModeloNS();
    }

    public void inicializar(String caminho, String dataset) {
        float[][] treinamento;
        treinamento = new float[150][4];
        ManipulaArquivos mA = new ManipulaArquivos();
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

        this.modeloNS.addAllMicroClassificadores(criarFuzzyMicroGruposComFCMDadosSemRotulos(exemplos));

        double[] t = new double[4];
        t[0] = 5.0;
        t[1] = 2.0;
        t[2] = 3.5;
        t[3] = 1.0;

        Exemplo vector = new Exemplo(t);

        List<Double> l = this.modeloNS.calculaPertinencia(vector, this.m);

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
            RealMatrix r = fuzzyClusterer.getMembershipMatrix();
            SPFMiC microGrupo = new SPFMiC(centroide, nExemplos, this.alpha, this.theta);
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
//                microClassificadores.get(index).getMicroGrupos().get(0).setLSm[l] = 0;
//                microClassificadores.get(index).getMicroGrupos().get(0).LSn[l] = 0;
////                microClassificadores.get(index).getMicroGrupos().get(0).LSpertinencias[l] += Math.pow(dadosMatriz[i][index], microClassificadores.get(index).getMicroGrupos().get(0));
////                microClassificadores.get(index).getMicroGrupos().get(0).LStipicidades[l] += Math.pow(exemplo[l], microClassificadores.get(index).getMicroGrupos().get(0).);
//                microClassificadores.get(index).getMicroGrupos().get(0).somaDasDistancias[l] = 0;
            }
//            microClassificadores.get(index).getMicroGrupos().get(0).N++;
        }
        return microClassificadores;
    }

    private double silhuetaFuzzy(MicroClassificador microClassificador, List<Exemplo> desconhecidos, int k, double m, double alpha) {
        double silhuetaFuzzy = 0;
        double numerador = 0;
        double denominador = 0;
        int n = desconhecidos.size();

        for (int j=0; j < n; j++) {
            double a = 0;
            for (int i=0; i < n; i++) {
                if (i != j) {
                    a = a + this.distancia(desconhecidos.get(j), desconhecidos.get(i));
                }
            }
            a = a / (double)desconhecidos.size();

            double b = Double.MAX_VALUE;
            List<Exemplo> microGrupos = modeloNS.getSFMiCExemplos();
            Map<String, Double> distanciasMedias = new HashMap<>();
            Map<String, Integer> quantidadeMicroGrupos = new HashMap<>();
            for (int i=0; i<modeloNS.getMicroClassificadores().size(); i++) {
                distanciasMedias.put(Integer.toString(i), 0.0);
                quantidadeMicroGrupos.put(Integer.toString(i), 0);
            }
            for (int i=0; i < microGrupos.size(); i++) {
                String classe = microGrupos.get(i).getRotulo();
                distanciasMedias.replace(classe, distanciasMedias.get(classe) + this.distancia(desconhecidos.get(j), microGrupos.get(i)));
                quantidadeMicroGrupos.put(classe, quantidadeMicroGrupos.get(classe) + 1);
            }
            for (int i=0; i < microGrupos.size(); i++) {
                distanciasMedias.replace(Integer.toString(i), distanciasMedias.get(i) / (double)quantidadeMicroGrupos.get(i));
            }

            for (int i=0; i < microGrupos.size(); i++) {
                if (distanciasMedias.get(i) < b) {
                    b = distanciasMedias.get(i);
                }
            }

            double[] pertinencias = this.maioresPertinencia(desconhecidos.get(j), microClassificador.getMicroGrupos(), k, m);
            double silhueta = (b - a) / Math.max(b, a);
            numerador = numerador + (Math.pow(pertinencias[0] - pertinencias[1], alpha) * silhueta);
            denominador = denominador + Math.pow(pertinencias[0] - pertinencias[1], alpha);
        }

        silhuetaFuzzy = numerador / denominador;

        return silhuetaFuzzy;
    }

    private double distancia(Exemplo x1, Exemplo x2) {
        double distancia = Math.sqrt((Math.pow(x1.getPoint()[0]-x2.getPoint()[0],2) + Math.pow(x1.getPoint()[1]-x2.getPoint()[1],2)));
        return distancia;
    }

    private double[] maioresPertinencia(Exemplo x, List<SPFMiC> microGrupos, int k, double m) {
        double[] max = new double[2];
        max[0] = Double.MIN_VALUE;
        List<Exemplo> centroides = new ArrayList<>();

        for (int i=0; i < microGrupos.size(); i++) {
            Exemplo centroide = new Exemplo(microGrupos.get(i).getCentroide(), microGrupos.get(i).getRotulo());
            centroides.add(centroide);
        }

        for (int i=0; i < centroides.size(); i++) {
            double pertinencia = this.calculoPertinencia(x, centroides, i, k, m);
            if (pertinencia > max[0]) {
                max[1] = max[0];
                max[0] = pertinencia;
            }
            else {
                if (pertinencia > max[1]) {
                    max[1] = pertinencia;
                }
            }
        }

        return max;
    }

    private double calculoPertinencia (Exemplo x, List<Exemplo> centroides, int indiceCentroide, int k, double m) {
        double pertinencia, somatorio = 0;

        for (int i=0; i < centroides.size(); i++) {
            double numerador = distancia(x, centroides.get(indiceCentroide));
            double denominador = distancia(x, centroides.get(i));
            double potencia = 2.0 / (m - 1.0);
            somatorio = somatorio + Math.pow(numerador / denominador, potencia);
        }

        pertinencia = 1.0 / somatorio;

        return pertinencia;
    }
}
