package FuzzyProject.FuzzyND.Utils;

import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.MedidasClassicas;

import java.util.List;

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

    public static void  calculaMedidasFariaEtAl(List<Exemplo> exemplos) {

    }
}
