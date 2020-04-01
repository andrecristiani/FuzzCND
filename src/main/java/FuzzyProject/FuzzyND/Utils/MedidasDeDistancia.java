package FuzzyProject.FuzzyND.Utils;

public class MedidasDeDistancia {
    /***
     * Function used to calculate the euclidian distance between two point, used to tipicity calc
     */
    public static double calculaDistanciaEuclidiana(double[] ponto1, double[] ponto2) {
        double somatorio = 0;
        for(int i=0; i<ponto1.length; i++) {
            somatorio = somatorio + Math.pow((ponto1[i]-ponto2[i]),2);
        }
        return Math.sqrt(somatorio);
    }
}
