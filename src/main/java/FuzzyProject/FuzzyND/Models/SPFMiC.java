package FuzzyProject.FuzzyND.Models;

public class SPFMiC {
    private double Mm[]; //soma linear das pertinências elevadas a m
    private double Tn[]; //soma linear das tipicidades elevadas a n
    private double CF1pertinencias[]; //soma linear dos ex ponderados por suas pertinencias
    private double CF1tipicidades[]; //soma linear dos ex ponderados por suas tipicidades
    private double SSDe[]; //soma das distâncias dos exemplos para o protótipo do micro-grupo, elevadas a m
    private double N;
    private double t;
    private String rotulo;
    private double centroide[];
    private double alpha;
    private double theta;

    public SPFMiC(int numAtributos, double alpha, double theta) {
        this.Mm = new double[numAtributos];
        this.Tn = new double[numAtributos];
        this.CF1pertinencias = new double[numAtributos];
        this.CF1tipicidades = new double[numAtributos];
        this.SSDe = new double[numAtributos];

        for(int i=0; i<numAtributos; i++) {
            this.Mm[i] = 0;
            this.Tn[i] = 0;
            this.CF1pertinencias[i] = 0;
            this.CF1tipicidades[i] = 0;
            this.SSDe[i] = 0;
        }

        this.alpha = alpha;
        this.theta = theta;
        this.t = 0;
        this.N = 0;
    }

    public SPFMiC(int numAtributos, double[] centroide, int N, double alpha, double theta) {
        this.Mm = centroide;
        this.Tn = centroide;
        this.CF1pertinencias = centroide;
        this.CF1tipicidades = centroide;
        this.SSDe = centroide;
        this.alpha = alpha;
        this.theta = theta;
        this.N = N;
        this.t = 0;
    }

    public double[] getLSm() {
        return Mm;
    }

    public void setLSm(double[] LSm) {
        this.Mm = LSm;
    }

    public double[] getLSn() {
        return Tn;
    }

    public void setLSn(double[] LSn) {
        this.Tn = LSn;
    }

    public double[] getCF1pertinencias() {
        return CF1pertinencias;
    }

    public void setCF1pertinencias(double[] CF1pertinencias) {
        this.CF1pertinencias = CF1pertinencias;
    }

    public double[] getCF1tipicidades() {
        return CF1tipicidades;
    }

    public void setCF1tipicidades(double[] CF1tipicidades) {
        this.CF1tipicidades = CF1tipicidades;
    }

    public double[] getSSDe() {
        return SSDe;
    }

    public void setSSDe(double[] SSDe) {
        this.SSDe = SSDe;
    }

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public String getRotulo() {
        return this.rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public double[] getCentroide() {
        return this.centroide;
    }

    public void setCentroide(double[] centroide) {
        this.centroide = centroide;
    }

    /***
     * Updates the center position.
     */
    private void updateCenter(){
        int nAtributos = this.Mm.length;
        this.centroide = new double[nAtributos];
        for(int i=0; i<nAtributos; i++) {
            this.centroide[i] = (
                    (this.alpha * CF1pertinencias[i] + this.theta * CF1tipicidades[i]) /
                    (this.alpha * this.Tn[i] + this.theta * Mm[i])
            );
        }
    }

    /***
     * Function used to calculate an attribute used in the typicality function
     */
    private double[] getTipicidadeI(double K) {
        int nAtributos = this.Mm.length;
        double[] tipicidadeI = new double[nAtributos];
        for(int i=0; i<nAtributos; i++) {
            tipicidadeI[i] = K * (this.SSDe[i] / this.Mm[i]);
        }
        return tipicidadeI;
    }

    /***
     * Function used to calculate an attribute used in the typicality function
     */
    private double[] calcTipicidade(Exemplo exemplo, double n, double K) {
        int nAtributos = this.Mm.length;
        double[] tipicidade = new double[nAtributos];
        double[] tipicidadeI = this.getTipicidadeI(K);
        for(int i=0; i<nAtributos; i++) {
            tipicidade[i] = 1 /
                    (1 + Math.pow(((this.theta/tipicidadeI[i]) * Math.pow(Math.abs((exemplo.getPontoPorPosicao(i) - this.centroide[i])), 2)),
                            (1/(n-1))
                         )
                    );
        }
        return tipicidade;
    }


}
