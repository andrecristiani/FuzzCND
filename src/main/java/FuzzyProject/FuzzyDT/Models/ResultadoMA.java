package FuzzyProject.FuzzyDT.Models;

import java.util.ArrayList;
import java.util.List;

public class ResultadoMA {
    public int numClassificadores;
    public List<List<String>> rotulos = new ArrayList<>();

    public ResultadoMA(int numClassificadores) {
        this.numClassificadores = numClassificadores;
        for(int i=0; i<numClassificadores; i++) {
            rotulos.add(new ArrayList<>());
        }
    }

    public ResultadoMA() {
        rotulos.add(new ArrayList<>());
    }
}
