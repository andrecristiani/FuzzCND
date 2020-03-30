package FuzzyProject.FuzzyND.Models;

import java.util.ArrayList;
import java.util.List;

public class ModeloNS {
    private List<MicroClassificador> modeloNS;

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
}
