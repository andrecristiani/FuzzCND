package FuzzyProject.FuzzyND.Models;

public class SPFMiC {
    public double LSm[];
    public double LSn[];
    public double LSpertinencias[];
    public double LStipicidades[];
    public double somaDasDistancias[];
    public double N;
    public double t;
    public String rotulo;

    public SPFMiC(int numAtributos) {
        this.LSm = new double[numAtributos];
        this.LSn = new double[numAtributos];
        this.LSpertinencias = new double[numAtributos];
        this.LStipicidades = new double[numAtributos];
        this.somaDasDistancias = new double[numAtributos];

        for(int i=0; i<numAtributos; i++) {
            this.LSm[i] = 0;
            this.LSn[i] = 0;
            this.LSpertinencias[i] = 0;
            this.LStipicidades[i] = 0;
            this.somaDasDistancias[i] = 0;
        }

        this.t = 0;
        this.N = 0;
    }

    public SPFMiC(int numAtributos, double[] centroide, int N) {
        this.LSm = centroide;
        this.LSn = centroide;
        this.LSpertinencias = centroide;
        this.LStipicidades = centroide;
        this.somaDasDistancias = centroide;
        this.N = N;
        this.t = 0;
    }
}
