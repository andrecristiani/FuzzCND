package FuzzyProject.FuzzyND.Utils;

import FuzzyProject.FuzzyND.Models.Avaliacao.AcuraciaMedidas;
import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.MedidasClassicas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Avaliacao {

    public static MedidasClassicas calculaMedidasClassicas(int fp, int fn, int fe, int n, int nc, int i) {
        MedidasClassicas md = new MedidasClassicas();
        double mnew = ((double) fn * 100) / nc;
        double fnew = ((double) fp * 100) / (n - nc);
        double err = (((double) (fp+fn+fe) * 100) / n);
        md.setMnew(mnew);
        md.setFnew(fnew);
        md.setErr(err);
        md.setIndice(i);
        return md;
    }

    public static void  calculaMedidasFariaEtAl(List<Exemplo> exemplos, int nPoints) {
        for(int i=0; i<exemplos.size(); i++) {

        }
    }

    public static AcuraciaMedidas calculaAcuracia(int numAcertos, int numTotalExemplos, int indice) {
        AcuraciaMedidas acuraciaMedidas = new AcuraciaMedidas(indice, (Double.parseDouble(String.valueOf(numAcertos))/numTotalExemplos)*100);
        return acuraciaMedidas;
    }

    public static double calculaUnkR(Map<String, Integer> unki, Map<String, Integer> exci) {
        List<String> rotulos = new ArrayList<>();
        rotulos.addAll(unki.keySet());
        double unkR = 0;
        for(int i=0; i< unki.size(); i++) {
            double unk = unki.get(rotulos.get(i));
            double exc = exci.get(rotulos.get(i));
            unkR += (unk/exc);
        }
        return (unkR/ exci.size()) * 100;
    }
}
