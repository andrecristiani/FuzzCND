package main.java.FuzzyProject.FuzzyDT.Models;

public class MicroGrupo {

    public float LS[];
    public float SS[];
    public float N;

    public MicroGrupo(DecisionTree dt) {
        this.LS = new float[dt.numRegrasAD];
        this.SS = new float[dt.numRegrasAD];
        for(int i=0; i<dt.numRegrasAD; i++) {
            this.LS[i] = 0;
            this.SS[i] = 0;
        }
        this.N = 0;
    }

    public float[] getCentroide() {
        float centroide[] = new float[this.LS.length];
        for(int i=0; i<this.LS.length; i++) {
            centroide[i] = (this.LS[i]/N);
        }
        return centroide;
    }

    public float[] getRaio() {
        float raio[] = new float[this.LS.length];
        for(int i=0; i<this.LS.length; i++) {
            raio[i] = (float) Math.pow(((SS[i]/N)-Math.pow((LS[i]/N), 2)),(1/2));
        }
        return raio;
    }

    public double calculaDistanciaEuclidiana(float[] ponto1, float[] ponto2) {
        double somatorio = 0;
        for(int i=0; i<ponto1.length; i++) {
            somatorio = somatorio + Math.pow((ponto1[i]-ponto2[i]),2);
        }
        return Math.sqrt(somatorio);
    }
}
