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
}
