package main.java.FuzzyProject.FuzzyDT.Models;

public class MicroGrupo {

    public MicroGrupo(DecisionTree dt) {
        this.LS = new float[dt.numRegrasAD];
        this.LS[0] = 0;
        this.LS[1] = 0;
        this.LS[2] = 0;
        this.LS[3] = 0;
        this.SS = new float[dt.numRegrasAD];
        this.SS[0] = 0;
        this.SS[1] = 0;
        this.SS[2] = 0;
        this.SS[3] = 0;
        this.N = 0;
    }

    public float LS[];
    public float SS[];
    public double N;
}
