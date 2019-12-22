package main.java.FuzzyProject.FuzzyDT;

import java.util.Vector;

public class calculaGdCobertura {
    public calculaGdCobertura() {
    }

    public float calcGdCobertura(String[] regra, float[][] treinamento, String[][] particao, int numRegrasTreinamento, int numVariaveisEntrada) {
//        int ind = false;
//        int indice = false;
        float grauRegra = 1.0F;
        float grauFinalRegra = 0.0F;
        float temp = 0.0F;
        Vector part = new Vector(1);
        boolean flagDC = false;

        for(int b = 0; b < numRegrasTreinamento; ++b) {
            grauRegra = 1.0F;

            for(int c = 1; c <= numVariaveisEntrada; ++c) {
                int indice = 1;
                int d;
                if (c != 1) {
                    for(d = 1; d < c; ++d) {
                        indice += Integer.parseInt(particao[0][d]);
                    }
                }

                int ind = indice + Integer.parseInt(particao[0][c]);

                for(d = indice; d < ind; ++d) {
                    if (regra[c - 1].equals(particao[d][1])) {
                        indice = d;
                        flagDC = true;
                    }
                }

                if (flagDC) {
                    part.clear();
                    if (particao[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particao[indice][d]);
                        }
                    }

                    if (particao[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particao[indice][d]);
                        }
                    }

                    temp = this.calculaGrauRegra(treinamento[b][c - 1], particao[indice][0], part);
                    if (temp < grauRegra) {
                        grauRegra = temp;
                    }

                    flagDC = false;
                }
            }

            grauFinalRegra += grauRegra;
        }

        return grauFinalRegra;
    }

    public float calculaGrauRegra(float grau, String tipoConjunto, Vector part) {
        float valor = 0.0F;
        float a;
        float b;
        float c;
        if (tipoConjunto.equals("triangular")) {
            a = Float.parseFloat("" + part.get(0));
            b = Float.parseFloat("" + part.get(1));
            c = Float.parseFloat("" + part.get(2));
            if (grau <= c && grau >= a) {
                if (grau == b) {
                    valor = 1.0F;
                } else if (a == b) {
                    valor = (c - grau) / (c - a);
                } else if (b == c) {
                    valor = (grau - a) / (b - a);
                } else if (grau < b) {
                    valor = (grau - a) / (b - a);
                } else {
                    valor = (c - grau) / (c - b);
                }
            } else {
                valor = 0.0F;
            }
        }

        if (tipoConjunto.equals("trapezoidal")) {
            a = Float.parseFloat("" + part.get(0));
            b = Float.parseFloat("" + part.get(1));
            c = Float.parseFloat("" + part.get(2));
            float d = Float.parseFloat("" + part.get(3));
            if (grau <= d && grau >= a) {
                if (grau >= b && grau <= c) {
                    valor = 1.0F;
                } else if (grau < b) {
                    valor = (grau - a) / (b - a);
                } else if (grau > b) {
                    valor = (d - grau) / (d - c);
                }
            } else {
                valor = 0.0F;
            }
        }

        return valor;
    }
}

