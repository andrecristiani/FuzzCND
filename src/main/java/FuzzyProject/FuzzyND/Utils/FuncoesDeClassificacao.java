package FuzzyProject.FuzzyND.Utils;

import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.SFMiC;
import FuzzyProject.FuzzyND.Models.SPFMiC;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;

import java.util.ArrayList;
import java.util.List;

public class FuncoesDeClassificacao {
    public static List<SPFMiC> separaExemplosPorGrupoClassificado(List<Exemplo> exemplos, FuzzyKMeansClusterer fuzzyClusterer, double fuzzificacao, double alpha, double theta) {
        List<SPFMiC> sfMiCS = new ArrayList<SPFMiC>();
        double[][] matriz = fuzzyClusterer.getMembershipMatrix().getData();
        List<CentroidCluster> centroides = fuzzyClusterer.getClusters();
        for(int j=0; j<centroides.size(); j++) {
            SPFMiC sfMiC = null;
            double SSD = 0;
            for (int k = 0; k < exemplos.size(); k++) {
                int indiceMaior = 0;
                double array[] = matriz[k];
                double maior = -1000000;
                for (int i = 0; i < array.length; i++) {
                    if (array[i] > maior && array[i] < 1) {
                        indiceMaior = i;
                        maior = array[i];
                    }
                }
                if (indiceMaior == j) {
                    if (sfMiC == null) {
                        sfMiC = new SPFMiC(centroides.get(indiceMaior).getCenter().getPoint(),
                                           centroides.get(indiceMaior).getPoints().size(),
                                           alpha,
                                           theta);
                    } else {
                        double valorPertinencia = matriz[k][indiceMaior];
                        double[] ex = exemplos.get(k).getPoint();
                        double distancia = MedidasDeDistancia.calculaDistanciaEuclidiana(sfMiC.getCentroide(), ex);
                        SSD += Math.pow(valorPertinencia, fuzzificacao) * Math.pow(distancia, 2);
                    }
                }
            }
            sfMiC.setSSDe(SSD);
            sfMiCS.add(sfMiC);
        }

        return sfMiCS;
    }

    public static double calculoPertinencia (double[] x, List<SFMiC> centroides, int indiceCentroide, double m) {
        double pertinencia, somatorio = 0;

        for (int i=0; i < centroides.size(); i++) {
            double numerador = MedidasDeDistancia.calculaDistanciaEuclidiana(x, centroides.get(indiceCentroide).getCentroide());
            double denominador = MedidasDeDistancia.calculaDistanciaEuclidiana(x, centroides.get(i).getCentroide());
            double potencia = 2.0 / (m - 1.0);
            somatorio = somatorio + Math.pow(numerador / denominador, potencia);
        }

        pertinencia = 1.0 / somatorio;

        return pertinencia;
    }
}
