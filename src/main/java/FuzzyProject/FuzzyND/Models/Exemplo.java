package FuzzyProject.FuzzyND.Models;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class Exemplo implements Clusterable {
    private double[] ponto;
    private String rotuloVerdadeiro;
    private String rotuloClassificado;
    private boolean desconhecido;

    public Exemplo(double[] ponto) {
        this.ponto = ponto;
        this.rotuloVerdadeiro = null;
        this.rotuloClassificado = null;
        this.desconhecido = false;
    }

    public Exemplo(double[] ponto, boolean comRotulo) {
        if(comRotulo) {
            double[] novoPonto = new double[ponto.length-1];
            for(int i=0; i<ponto.length-1; i++) {
                novoPonto[i] = ponto[i];
            }
            this.rotuloVerdadeiro = Integer.toString((int) ponto[ponto.length-1]);
            this.ponto = novoPonto;
        } else {
            this.ponto = ponto;
            this.rotuloVerdadeiro = null;
        }
    }

    public Exemplo(double[] ponto, String rotuloVerdadeiro) {
        this.ponto = ponto;
        this.rotuloVerdadeiro = rotuloVerdadeiro;
    }

    @Override
    public double[] getPoint() {
        return this.ponto;
    }

    public String getRotuloVerdadeiro() {
        return this.rotuloVerdadeiro;
    }

    public void setRotuloVerdadeiro(String rotuloVerdadeiro) {
        this.rotuloVerdadeiro = rotuloVerdadeiro;
    }

    public String getRotuloClassificado() {
        return this.rotuloClassificado;
    }

    public void setRotuloClassificado(String rotuloClassificado) {
        this.rotuloClassificado = rotuloClassificado;
    }

    public double[] getPonto() {
        return ponto;
    }

    public void setPonto(double[] ponto) {
        this.ponto = ponto;
    }

    public boolean isDesconhecido() {
        return desconhecido;
    }

    public void setDesconhecido() {
        this.desconhecido = true;
    }

    public double getPontoPorPosicao(int i) {
        return this.ponto[i];
    }

    public String[] arrayToStringArray() {
        String exemplo[] = new String[this.ponto.length];
        for(int i=0; i<this.ponto.length; i++) {
            exemplo[i] = Double.toString(this.ponto[i]);
        }
        return exemplo;
    }

    public String arrayToString() {
        String exemplo = Double.toString(this.ponto[0]);
        for(int i=1; i<this.ponto.length; i++) {
            exemplo += "\t" + this.ponto[i];
        }
        exemplo += "\t" + this.rotuloVerdadeiro;
        return exemplo;
    }
}
