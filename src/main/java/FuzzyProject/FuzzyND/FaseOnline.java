package FuzzyProject.FuzzyND;

import FuzzyProject.FuzzyDT.Utils.manipulaArquivos;
import FuzzyProject.FuzzyND.Models.Exemplo;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;
import org.apache.ignite.ml.clustering.BaseFuzzyCMeansClusterer;
import org.apache.ignite.ml.clustering.FuzzyCMeansLocalClusterer;
import org.apache.ignite.ml.clustering.FuzzyCMeansModel;
import org.apache.ignite.ml.math.Vector;
import org.apache.ignite.ml.math.distances.DistanceMeasure;
import org.apache.ignite.ml.math.distances.EuclideanDistance;
import org.apache.ignite.ml.math.impls.matrix.DenseLocalOnHeapMatrix;

import java.util.ArrayList;
import java.util.List;

public class OnlinePhase {

    public void inicializar(String caminho, String dataset) {
        float[][] treinamento;
        treinamento = new float[150][4];
        manipulaArquivos mA = new manipulaArquivos();
        mA.carregaArquivoTreinamento(treinamento, caminho + dataset + "0.txt", 4);

        double[][] exemplos = new double[treinamento.length][4];
        List<Exemplo> l = new ArrayList<>();
        for(int i=0; i<treinamento.length; i++) {
            exemplos[i][0] = treinamento[i][0];
            exemplos[i][1] = treinamento[i][1];
            exemplos[i][2] = treinamento[i][2];
            exemplos[i][3] = treinamento[i][3];
            l.add(new Exemplo(exemplos[i]));
        }

        DenseLocalOnHeapMatrix points = new DenseLocalOnHeapMatrix(exemplos);

        FuzzyCMeansLocalClusterer clusterer = new FuzzyCMeansLocalClusterer(
                new EuclideanDistance(), 2.0,
                BaseFuzzyCMeansClusterer.StopCondition.STABLE_MEMBERSHIPS, 0.01, 50, null);
        FuzzyCMeansModel mdl = clusterer.cluster(points, 4);

        Vector[] vec =  mdl.centers();

        DistanceMeasure dist = mdl.distanceMeasure();

        int nClusters = mdl.clustersCount();

        FuzzyKMeansClusterer fuzzyClusterer = new FuzzyKMeansClusterer(2, 2.2);

        fuzzyClusterer.cluster(l);

        RealMatrix t = fuzzyClusterer.getMembershipMatrix();

        System.out.println("teste");
    }
}
