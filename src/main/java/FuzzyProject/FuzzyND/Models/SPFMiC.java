package FuzzyProject.FuzzyND.Models;

import FuzzyProject.FuzzyND.Utils.MedidasDeDistancia;

public class SPFMiC {
    private double Mm; //soma linear das pertinências elevadas a m
    private double Tn; //soma linear das tipicidades elevadas a n
    private double CF1pertinencias[]; //soma linear dos ex ponderados por suas pertinencias
    private double CF1tipicidades[]; //soma linear dos ex ponderados por suas tipicidades
    private double SSDe; //soma das distâncias dos exemplos para o protótipo do micro-grupo, elevadas a m
    private double N;
    private double t;
    private String rotulo;
    private double centroide[];
    private double alpha;
    private double theta;

    public SPFMiC(double[] centroide, int N, double alpha, double theta) {
        this.CF1pertinencias = centroide;
        this.CF1tipicidades = centroide;
        this.centroide = centroide;
        this.N = N;
        this.alpha = alpha;
        this.theta = theta;
        this.Mm = 1;
        this.Tn = 1;
        this.SSDe = 0;
        this.t = 0;
        this.rotulo = "Teste";
    }

    public double getLSm() {
        return Mm;
    }

    public void setLSm(double LSm) {
        this.Mm = LSm;
    }

    public double getLSn() {
        return Tn;
    }

    public void setLSn(double LSn) {
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

    public double getSSDe() {
        return SSDe;
    }

    public void setSSDe(double SSDe) {
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
    private void atualizaCentroide(){
        int nAtributos = this.CF1pertinencias.length;
        this.centroide = new double[nAtributos];
        for(int i=0; i<nAtributos; i++) {
            this.centroide[i] = (
                    (this.alpha * CF1pertinencias[i] + this.theta * CF1tipicidades[i]) /
                    (this.alpha * this.Tn + this.theta * Mm)
            );
        }

    }

    /***
     * Function used to assign an exemple to SPFMiC
     */
    public void atribuiExemplo(Exemplo exemplo, double pertinencia, double tipicidade, double m, double n) {
        double dist = MedidasDeDistancia.calculaDistanciaEuclidiana(exemplo.getPoint(), this.centroide);
        this.N++;
        this.Mm += Math.pow(pertinencia, m);
        this.Tn += Math.pow(tipicidade, n);
        this.SSDe += Math.pow(dist, m) * pertinencia;
        for(int i=0; i<this.centroide.length; i++) {
            this.CF1pertinencias[i] += exemplo.getPontoPorPosicao(i) * pertinencia;
            this.CF1tipicidades[i] = exemplo.getPontoPorPosicao(i) * tipicidade;
        }
        this.atualizaCentroide();
    }

    /***
     * Function used to calculate an attribute used in the typicality function
     */
    public double calculaTipicidade(double[] exemplo, double n, double K) {
        double tipicidadeI = this.getTipicidadeI(K);
        return (1 /
                (1 + Math.pow(((this.theta/tipicidadeI) * MedidasDeDistancia.calculaDistanciaEuclidiana(exemplo, this.centroide)),
                        (1/(n-1))
                )
                ));
    }

    /***
     * Function used to calculate an attribute used in the typicality function
     */
    private double getTipicidadeI(double K) {
        return  K * (this.SSDe / this.Mm);
    }

    public double getDispersao() {
        return Math.sqrt((this.SSDe/this.N));
    }
}
