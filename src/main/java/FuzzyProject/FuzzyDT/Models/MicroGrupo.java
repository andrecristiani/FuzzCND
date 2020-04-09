package FuzzyProject.FuzzyDT.Models;

import java.util.Vector;

public class MicroGrupo {

    public float LS[];
    public float SS[];
    public float N;
    public double raio;

    public MicroGrupo(DecisionTree dt) {
        this.LS = new float[dt.numAtributos-1];
        this.SS = new float[dt.numAtributos-1];
        for(int i=0; i<dt.numAtributos-1; i++) {
            this.LS[i] = 0;
            this.SS[i] = 0;
        }
        this.N = 0;
        this.raio = 0;
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
            float parte1 = (SS[i]/N);
            float parte2 = (float) Math.pow((LS[i]/N), 2);
            float parte3 = parte1-parte2;
            if(parte3 < 0) {
                parte3 = parte3 * -1;
            }
            parte3 = (float) Math.sqrt(parte3);
            raio[i] = parte3;
        }
        return raio;
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
        double distRaioCentroide = calculaDistanciaEuclidiana(this.getRaio(), this.getCentroide());
        double distExemploCentroide = calculaDistanciaEuclidiana(exemplo, this.getCentroide());
        if(distExemploCentroide <= distRaioCentroide) {
            return true;
        }
        return false;
    }
}
