package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Models.ComiteArvores;
import FuzzyProject.FuzzyND.Models.*;
import FuzzyProject.FuzzyND.Utils.Avaliacao;
import FuzzyProject.FuzzyND.Utils.LineChart_AWT;
import FuzzyProject.FuzzyND.Utils.MedidasDeDistancia;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;
import org.jfree.ui.RefineryUtilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.*;

public class FaseOnline {

    public int K; //numero de clusters por chunk
    public int T; //tamanho do chunk
    public int m;
    public int n;
    public int tChunk;
    public double fuzzificacao;
    public double tipicidade;
    public double alpha;
    public double betha;
    public double pesoMinimoGrupo;
    public List<Exemplo> memTempRotulados = new ArrayList<>();
    public List<Exemplo> memTempDesconhecidos = new ArrayList<>();
    public List<Exemplo> exemplosEsperandoTempo = new ArrayList<>();
    public List<MedidasClassicas> desempenho = new ArrayList<>();
    ModeloNS modeloNS;
    public int novidadesClassificadas;
    public int fp;
    public int fn;
    public int nInstances = 90000;
    public int nc = 28169;

    public FaseOnline(double fuzzificacao, double tipicidade, int K, int m, int n, double alpha, double betha, int pesoMinimoGrupo, int tChunk) {
        this.fuzzificacao = fuzzificacao;
        this.tipicidade = tipicidade;
        this.K = K;
        this.m = m;
        this.n = n;
        this.tChunk = tChunk;
        modeloNS = new ModeloNS();
        this.alpha = alpha;
        this.betha = betha;
        this.pesoMinimoGrupo = pesoMinimoGrupo;
    }

    public void inicializar(String caminho, String dataset, ComiteArvores comite, int latencia, int T) {
        DataSource source;
        Instances data;
        this.novidadesClassificadas = 0;
        this.fp = 0;
        this.fn = 0;
        try {
            source = new DataSource(caminho + dataset + "-instances.arff");
            data = source.getDataSet();
            int acertou = 0;
            int fe = 0;
            int desconhecido = 0;
            int kCurto = 4;
            for(int i=0, j=0, h=0; i<data.size(); i++, j++, h++) {
                Instance ins = data.get(i);
                Exemplo exemplo = new Exemplo(ins.toDoubleArray(), true);
                String rotulo = comite.classificaExemploAgrupamentoExterno(exemplo.getPoint());
                if(rotulo.equals("desconhecido")) {
                    desconhecido++;
                    this.memTempDesconhecidos.add(exemplo);
                    if(this.memTempDesconhecidos.size() >= T) {
                        this.memTempDesconhecidos = this.detectaNovidadesBinario(this.memTempDesconhecidos, kCurto, comite);
                    }
                } else {
                    exemplo.setRotuloClassificado(rotulo);
                }
                if(rotulo.equals(exemplo.getRotuloVerdadeiro())) {
                    acertou++;
                } else {
                    if(comite.rotulosConhecidos.contains(exemplo.getRotuloVerdadeiro())) {
                        fe++;
                    } else {
                        this.fn++;
                    }
                }
                this.exemplosEsperandoTempo.add(exemplo);
                if(j >= latencia) {
                    Exemplo exemploRotulado = this.exemplosEsperandoTempo.get(0);
                    this.exemplosEsperandoTempo.remove(0);
                    this.memTempRotulados.add(exemploRotulado);

                    if(this.memTempRotulados.size() >= tChunk) {
                        comite.treinaNovaArvore(memTempRotulados, tChunk, K);
                        this.memTempRotulados.clear();
                    }
                }

                if(h == 999) {
                    h=0;
                    MedidasClassicas mc = Avaliacao.calculaMedidasClassicas(fp, fn, fe, nInstances, nc, i);
                    System.out.println("FP: " + fp + "|| FN: " + fn + "|| FE: " + fe);
                    desempenho.add(mc);
                    fp = 0;
                    fn = 0;
                    fe = 0;
                }
            }
            System.out.println("Acertou " + acertou + " exemplos");
//            System.out.println("Zero " + zero + " exemplos");
//            System.out.println("Um " + um + " exemplos");
//            System.out.println("Dois " + dois + " exemplos");
//            System.out.println("Tres " + tres + " exemplos");
            System.err.println("Errou " + fe + " exemplos");
            System.err.println("Desconhecidos = " + desconhecido);
            System.out.println("Novidades classificadas = " + novidadesClassificadas);
            System.err.println("Novidades que eram conhecidas = " + fp);
            System.err.println("Classificações que eram novidades = " + fn);

            LineChart_AWT chart = new LineChart_AWT(
                    "Avaliação Fnew" ,
                    "", this.desempenho, "fnew");

            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );

            chart = new LineChart_AWT(
                    "Avaliação Mnew" ,
                    "", this.desempenho, "mnew");

            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );

            chart = new LineChart_AWT(
                    "Avaliação Err" ,
                    "", this.desempenho, "err");

            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    private FuzzyKMeansClusterer fuzzyCMeans(List<Exemplo> exemplos, int kCurto) {
        FuzzyKMeansClusterer fuzzyClusterer = new FuzzyKMeansClusterer(kCurto, this.fuzzificacao);
        fuzzyClusterer.cluster(exemplos);
        return fuzzyClusterer;
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

    private void detectaNovidadesMultiRotulo(List<Exemplo> listaDesconhecidos, int kCurto) {
        FuzzyKMeansClusterer clusters = this.fuzzyCMeans(listaDesconhecidos, kCurto);
        List<CentroidCluster> centroides = clusters.getClusters();
        List<Double> silhuetas = this.calculaSilhuetaFuzzy2(clusters, listaDesconhecidos);

        for(int i=0; i<silhuetas.size(); i++) {
            if(silhuetas.get(i) > 0 && centroides.get(i).getPoints().size() >= pesoMinimoGrupo) {
                Exemplo ex = listaDesconhecidos.get(0);
                List<MicroClassificador> microClassificadores = new ArrayList<>();
                for (int j = 0; j < this.K; j++) {
                    MicroClassificador microClassificador = new MicroClassificador();
                    double[] centroide = centroides.get(j).getCenter().getPoint();
                    int nExemplos = centroides.get(j).getPoints().size();
                    SPFMiC microGrupo = new SPFMiC(centroide, nExemplos, this.alpha, this.betha);
                    microClassificador.addMicroGrupo(microGrupo);
                    microClassificadores.add(microClassificador);
                }
            }
        }
    }

    private List<Exemplo> detectaNovidadesBinario(List<Exemplo> listaDesconhecidos, int kCurto, ComiteArvores comite) {
        FuzzyKMeansClusterer clusters = this.fuzzyCMeans(listaDesconhecidos, kCurto);
        List<CentroidCluster> centroides = clusters.getClusters();
        List<Double> silhuetas = this.calculaSilhuetaFuzzy2(clusters, listaDesconhecidos);
        List<Integer> silhuetasValidas = new ArrayList<>();
        double[][] matrizPertinencia = clusters.getMembershipMatrix().getData();

        for(int i=0; i<silhuetas.size(); i++) {
            if(silhuetas.get(i) > 0 && centroides.get(i).getPoints().size() >= pesoMinimoGrupo) {
                silhuetasValidas.add(i);
            }
        }

        for(int i=0; i<listaDesconhecidos.size(); i++) {
            if(silhuetasValidas.contains(this.getIndiceDoMaiorValor(matrizPertinencia[i]))) {
                listaDesconhecidos.get(i).setRotuloClassificado("Novidade");
                novidadesClassificadas++;
                if(comite.rotulosConhecidos.contains(listaDesconhecidos.get(i).getRotuloVerdadeiro())) {
                    fp++;
                }
                listaDesconhecidos.remove(i);
            }
        }

        return listaDesconhecidos;
    }

    private List<Double> calculaSilhuetaFuzzy2(FuzzyKMeansClusterer clusters, List<Exemplo> desconhecidos) {
        int nExemplos = desconhecidos.size();
        double[][] matriz = clusters.getMembershipMatrix().getData();
        double numerador = 0;
        double denominador = 0;
        double apj = 0;
        List<Double> dqj = new ArrayList<>();
        List<Double> silhuetas = new ArrayList<>();
        for(int i=0; i<clusters.getK(); i++) {
            for (int j = 0; j < nExemplos; j++) {
                int indexClasse = this.getIndiceDoMaiorValor(matriz[j]);
                if (indexClasse == i) {
                    for (int k = 0; k < nExemplos; k++) {
                        if (this.getIndiceDoMaiorValor(matriz[k]) == indexClasse) {
                            apj += MedidasDeDistancia.calculaDistanciaEuclidiana(desconhecidos.get(j).getPoint(), desconhecidos.get(k).getPoint());
                        } else {
                            dqj.add(MedidasDeDistancia.calculaDistanciaEuclidiana(desconhecidos.get(j).getPoint(), desconhecidos.get(k).getPoint()));
                        }
                    }

                    apj = apj / nExemplos;
                    double bpj = Collections.min(dqj);
                    double sj = (bpj - apj) / Math.max(apj, bpj);
                    double[] maiorESegundaMeiorPertinencia = this.getMaiorESegundoMaiorPertinencia(matriz[j], j);
                    double upj = maiorESegundaMeiorPertinencia[0];
                    double uqj = maiorESegundaMeiorPertinencia[1];

                    numerador += Math.pow((upj - uqj), this.alpha) * sj;
                    denominador += Math.pow((upj - uqj), this.alpha);
                }
            }
            double fs = numerador / denominador;
            silhuetas.add(fs);
        }
        return silhuetas;
    }

    private double calculaSilhuetaFuzzy(FuzzyKMeansClusterer clusters, List<Exemplo> desconhecidos) {
        int nExemplos = desconhecidos.size();
        double[][] matriz = clusters.getMembershipMatrix().getData();
        double numerador = 0;
        double denominador = 0;
        double apj = 0;
        List<Double> dqj = new ArrayList<>();
        for(int j=0; j<nExemplos; j++) {
            int indexClasse = this.getIndiceDoMaiorValor(matriz[j]);
            for (int k = 0; k < nExemplos; k++) {
                if (this.getIndiceDoMaiorValor(matriz[k]) == indexClasse) {
                    apj += MedidasDeDistancia.calculaDistanciaEuclidiana(desconhecidos.get(j).getPoint(), desconhecidos.get(k).getPoint());
                } else {
                    dqj.add(MedidasDeDistancia.calculaDistanciaEuclidiana(desconhecidos.get(j).getPoint(), desconhecidos.get(k).getPoint()));
                }
            }

            apj = apj/nExemplos;
            double bpj = Collections.min(dqj);
            double sj = (bpj - apj) / Math.max(apj, bpj);
            double[] maiorESegundaMeiorPertinencia = this.getMaiorESegundoMaiorPertinencia(matriz[j], j);
            double upj = maiorESegundaMeiorPertinencia[0];
            double uqj = maiorESegundaMeiorPertinencia[1];

            numerador += Math.pow((upj - uqj), this.alpha) * sj;
            denominador += Math.pow((upj - uqj), this.alpha);
        }
        double fs = numerador/denominador;
        return fs;
    }

    private int getIndiceDoMaiorValor(double[] array) {
        int index = 0;
        double maior = -1000000;
        for(int i=0; i<array.length; i++) {
            if(array[i] > maior && array[i] < 1){
                index = i;
                maior = array[i];
            }
        }
        return index;
    }

    private double[] getMaiorESegundoMaiorPertinencia(double valores[], int j) {
        double[] resultado = new double[2];
        List<Double> lista = new ArrayList<>();
        for(int i=0; i<valores.length; i++) {
            lista.add(valores[i]);
        }
        Collections.sort(lista, Collections.reverseOrder());
        resultado[0] = lista.get(0);
        resultado[1] = lista.get(1);
        return resultado;
    }

    private double silhuetaFuzzy(MicroClassificador microClassificador, List<Exemplo> desconhecidos) {
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
                String classe = microGrupos.get(i).getRotuloVerdadeiro();
                distanciasMedias.replace(Integer.toString(i), distanciasMedias.get(Integer.toString(i)) + this.distancia(desconhecidos.get(j), microGrupos.get(i)));
                quantidadeMicroGrupos.put(Integer.toString(i), quantidadeMicroGrupos.get(Integer.toString(i)) + 1);
            }
            for (int i=0; i < microGrupos.size(); i++) {
                distanciasMedias.replace(Integer.toString(i), distanciasMedias.get(Integer.toString(i)) / (double)quantidadeMicroGrupos.get(Integer.toString(i)));
            }

            for (int i=0; i < microGrupos.size(); i++) {
                if (distanciasMedias.get(Integer.toString(i)) < b) {
                    b = distanciasMedias.get(Integer.toString(i));
                }
            }

            double[] pertinencias = this.maioresPertinencia(desconhecidos.get(j), microClassificador.getMicroGrupos(), this.K, this.m);
            double silhueta = (b - a) / Math.max(b, a);
            numerador = numerador + (Math.pow(pertinencias[0] - pertinencias[1], this.alpha) * silhueta);
            denominador = denominador + Math.pow(pertinencias[0] - pertinencias[1], this.alpha);
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
