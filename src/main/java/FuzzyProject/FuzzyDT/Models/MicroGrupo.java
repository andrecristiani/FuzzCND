package FuzzyProject.FuzzyDT.Models;

import java.util.Vector;

public class MicroGrupo {

    public float LS[];
    public float SS[];
    public float N;
    public double[] centroidKmeans;
    public double somaDistancias;
    public double raio;

    public MicroGrupo(DecisionTree dt) {
        this.LS = new float[dt.numAtributos-1];
        this.SS = new float[dt.numAtributos-1];
        this.centroidKmeans = new double[dt.numAtributos-1];
        for(int i=0; i<dt.numAtributos-1; i++) {
            this.LS[i] = 0;
            this.SS[i] = 0;
        }
        this.N = 0;
        this.somaDistancias = 0;
        this.raio = 0;
    }

    public float[] getCentroide() {
        float centroide[] = new float[this.LS.length];
        for(int i=0; i<this.LS.length; i++) {
            centroide[i] = (this.LS[i]/N);
        }
        return centroide;
    }

    public double calculaDistanciaEuclidiana(Vector ponto1, float[] ponto2) {
        double somatorio = 0;
        for(int i=0; i<ponto1.size(); i++) {
            somatorio = somatorio + Math.pow((Float.parseFloat(ponto1.get(i).toString())-ponto2[i]),2);
        }
        return Math.sqrt(somatorio);
    }

    public double calculaDistanciaEuclidiana(float[] ponto1, float[] ponto2) {
        double somatorio = 0;
        for(int i=0; i<ponto1.length; i++) {
            somatorio = somatorio + Math.pow((ponto1[i]-ponto2[i]),2);
        }
        return Math.sqrt(somatorio);
    }

    public boolean verificaSeExemploPertenceAoGrupo(Vector exemplo) {
        double distExemploCentroide = calculaDistanciaEuclidiana(exemplo, this.getCentroide());
        if(distExemploCentroide <= this.raio) {
            return true;
        }
        return false;
    }

    public double getDistanciaMedia() {
        return this.somaDistancias/N;
    }
}
