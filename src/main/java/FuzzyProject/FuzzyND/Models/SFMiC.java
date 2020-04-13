package FuzzyProject.FuzzyND.Models;

import java.util.Vector;
import java.util.function.DoubleBinaryOperator;

import static FuzzyProject.FuzzyND.Utils.MedidasDeDistancia.calculaDistanciaEuclidiana;

public class SFMiC {
    private double M;
    private int n;
    private double[] CF1;
    private double SSD;
    private double[] centroide;
    private int t;
    private double raio;

    public SFMiC(double M, double[] CF1, double SSD, int t, int n) {
        this.M = M;
        this.CF1 = CF1;
        this.SSD = SSD;
        this.t = t;
        this.n = n;
    }

    public SFMiC(double[] centroide) {
        this.M = 1;
        this.n = 1;
        this.CF1 = centroide;
        this.SSD = Double.MIN_VALUE;
        this.centroide = centroide;
        this.t = 0;
        this.raio = Double.MIN_VALUE;
    }

    public SFMiC(int numAtributos) {
        this.CF1 = new double[numAtributos];
        for(int i=0; i<numAtributos; i++) {
            this.CF1[i] = 0;
        }
        this.M = 0;
        this.SSD = 0;
        this.t = 0;
        this.n = 0;
    }

    public double getM() {
        return M;
    }

    public void setM(double m) {
        M = m;
    }

    public double[] getCF1() {
        return CF1;
    }

    public void setCF1(double[] CF1) {
        this.CF1 = CF1;
    }

    public double getSSD() {
        return SSD;
    }

    public void setSSD(double SSD) {
        this.SSD = SSD;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[] getCentroide() {
        return centroide;
    }

    public void setCentroide(double[] centroide) {
        this.centroide = centroide;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void adicionaNovoPonto(double[] exemplo, double pertinencia, double distancia, double fuzzificacao) {
        this.M += pertinencia;
        this.n++;
        this.SSD += Math.pow(pertinencia, fuzzificacao) * Math.pow(distancia, 2);

        for(int i=0; i<this.CF1.length; i++) {
            this.CF1[i] += exemplo[i] * pertinencia;
        }
        
        this.atualizaCentroide();
    }

    private void atualizaCentroide() {
        this.centroide = new double[this.CF1.length];
        for(int i=0; i<centroide.length; i++) {
            this.centroide[i] = this.CF1[i]/this.M;
        }
    }

    public boolean verificaSeExemploPertenceAoGrupo(Vector exemplo) {
        double distExemploCentroide = calculaDistanciaEuclidiana(exemplo, this.getCentroide());
        if(distExemploCentroide <= this.raio) {
            return true;
        }
        return false;
    }
}
