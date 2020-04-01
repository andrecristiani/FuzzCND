package FuzzyProject.FuzzyND.Models;

import FuzzyProject.FuzzyND.Utils.MedidasDeDistancia;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ModeloNS {
    private List<MicroClassificador> modeloNS = new ArrayList<>();

    public List<MicroClassificador> getMicroClassificadores() {
        return this.modeloNS;
    }

    public void addMicroClassificador(MicroClassificador mc) {
        this.modeloNS.add(mc);
    }

    public void addAllMicroClassificadores(List<MicroClassificador> microClassificadores) {
        this.modeloNS.addAll(microClassificadores);
    }

    public List<Exemplo> getSFMiCExemplos() {
        List<Exemplo> microGrupos = new ArrayList<Exemplo>();
        for (MicroClassificador microClassificador : this.modeloNS) {
            for(int i=0; i<microClassificador.getMicroGrupos().size(); i++) {
                Exemplo exemplo = new Exemplo(microClassificador.getMicroGrupos().get(i).getCentroide(), microClassificador.getMicroGrupos().get(i).getRotulo());
                microGrupos.add(exemplo);
            }
        }
        return microGrupos;
    }

    public List<Double> calculaPertinencia (Exemplo exemplo, double m) {
        List<Exemplo> centroides = this.getSFMiCExemplos();
        List<Double> pertinencias = new ArrayList<>();

        for (int i=0; i < centroides.size(); i++) {
            double pertinencia, somatorio = 0;

            for(int j=0; j < centroides.size(); j++) {
                double numerador = MedidasDeDistancia.calculaDistanciaEuclidiana(exemplo.getPoint(), centroides.get(i).getPoint());
                double denominador = MedidasDeDistancia.calculaDistanciaEuclidiana(exemplo.getPoint(), centroides.get(j).getPoint());
                double potencia = 2.0 / (m - 1.0);
                somatorio = somatorio + Math.pow(numerador / denominador, potencia);
            }
            pertinencia = 1.0 / somatorio;
            pertinencias.add(pertinencia);
        }
        return pertinencias;
    }
}
