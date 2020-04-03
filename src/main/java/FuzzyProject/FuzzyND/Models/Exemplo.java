package FuzzyProject.FuzzyND.Models;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class Exemplo implements Clusterable {
    double[] ponto;
    String rotulo;

    public Exemplo(double[] ponto) {
        this.ponto = ponto;
    }

    public Exemplo(double[] ponto, String rotulo) {
        this.ponto = ponto;
        this.rotulo = rotulo;
    }

    @Override
    public double[] getPoint() {
        return this.ponto;
    }

    public String getRotulo() {
        return this.rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public double getPontoPorPosicao(int i) {
        return this.ponto[i];
    }
}
