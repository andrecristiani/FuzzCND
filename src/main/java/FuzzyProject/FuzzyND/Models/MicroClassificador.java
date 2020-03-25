package FuzzyProject.FuzzyND.Models;

import java.util.ArrayList;
import java.util.List;

public class MicroClassificador {
    private List<SPFMiC> microGrupos;
    private String rotulo;

    public MicroClassificador() {
        microGrupos = new ArrayList<>();
    }

    public List<SPFMiC> getMicroGrupos() {
        return microGrupos;
    }

    public void setMicroGrupos(List<SPFMiC> microGrupos) {
        this.microGrupos = microGrupos;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void addMicroGrupo(SPFMiC microGrupo) {
        microGrupos.add(microGrupo);
    }
}
