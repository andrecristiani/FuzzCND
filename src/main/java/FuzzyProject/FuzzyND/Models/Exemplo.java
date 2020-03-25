package FuzzyProject.FuzzyND.Models;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class Exemplo implements Clusterable {
    double[] ponto;

    public Exemplo(double[] ponto) {
        this.ponto = ponto;
    }

    @Override
    public double[] getPoint() {
        return this.ponto;
    }
}
