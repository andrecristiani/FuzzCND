package FuzzyProject.FuzzyND.Models;

public class TipicidadeMaxima {
    private double todasTipMax = 0;
    private int qtd = 0;

    public TipicidadeMaxima(double value) {
        this.todasTipMax = value;
        this.qtd++;
    }

    public double getTodasTipMax() {
        return todasTipMax;
    }

    public void setTodasTipMax(double todasTipMax) {
        this.todasTipMax = todasTipMax;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getValorMedio() {
        return this.todasTipMax/this.qtd;
    }

    public void addValorTipicidade(double tipicidade) {
        this.todasTipMax += tipicidade;
        this.qtd++;
    }
}
