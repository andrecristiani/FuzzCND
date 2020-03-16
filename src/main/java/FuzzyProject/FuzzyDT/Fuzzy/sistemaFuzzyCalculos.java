package FuzzyProject.FuzzyDT.Fuzzy;

import FuzzyProject.FuzzyDT.Models.DecisionTree;

import java.util.Vector;

public class sistemaFuzzyCalculos {
    static double[] compat;

    public sistemaFuzzyCalculos() {
    }

    public String sistemaFuzzyCalculos(int numVariaveisEntrada, String[][] regrasSFC, int numRegrasSFC, Vector padrao, String[][] particaoSFC) {
        compat = new double[numRegrasSFC];
        double grau = 0.0D;
        Vector part = new Vector(1);

        int indice;
        for(int i = 0; i < numRegrasSFC; ++i) {
            compat[i] = 1.0D;

            for(int j = 0; j < numVariaveisEntrada - 1; ++j) {
                float valor = Float.parseFloat(padrao.get(j).toString());
                if ((double)valor != -11111.0D && regrasSFC[i][j].compareTo("dc") != 0) {
                    indice = 1;
                    part.clear();
                    int d;
                    if (j != 0) {
                        for(d = 1; d <= j; ++d) {
                            indice += Integer.parseInt(particaoSFC[0][d]);
                        }
                    }

                    for(d = indice; d < indice + Integer.parseInt(particaoSFC[0][j + 1]); ++d) {
                        if (regrasSFC[i][j].compareTo(particaoSFC[d][1]) == 0) {
                            indice = d;
                            d += Integer.parseInt(particaoSFC[0][j + 1]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("gaussian")) {
                        for(d = 2; d < 4; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    grau = this.calculaGrauRegra((double)valor, particaoSFC[indice][0], part);
                    if (compat[i] > grau) {
                        compat[i] = grau;
                    }
                }
            }
        }

        indice = this.max(compat, numRegrasSFC);
        return regrasSFC[indice][numVariaveisEntrada - 1];
    }

    public String sistemaFuzzyCalculosTreinamento(int numVariaveisEntrada, String[][] regrasSFC, int numRegrasSFC, Vector padrao, String[][] particaoSFC, DecisionTree dt) {
        compat = new double[numRegrasSFC];
        double grau = 0.0D;
        Vector part = new Vector(1);

        int indice;
        for(int i = 0; i < numRegrasSFC; ++i) {
            compat[i] = 1.0D;

            for(int j = 0; j < numVariaveisEntrada - 1; ++j) {
                float valor = Float.parseFloat(padrao.get(j).toString());
                if ((double)valor != -11111.0D && regrasSFC[i][j].compareTo("dc") != 0) {
                    indice = 1;
                    part.clear();
                    int d;
                    if (j != 0) {
                        for(d = 1; d <= j; ++d) {
                            indice += Integer.parseInt(particaoSFC[0][d]);
                        }
                    }

                    for(d = indice; d < indice + Integer.parseInt(particaoSFC[0][j + 1]); ++d) {
                        if (regrasSFC[i][j].compareTo(particaoSFC[d][1]) == 0) {
                            indice = d;
                            d += Integer.parseInt(particaoSFC[0][j + 1]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("gaussian")) {
                        for(d = 2; d < 4; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    grau = this.calculaGrauRegra((double)valor, particaoSFC[indice][0], part);
                    if (compat[i] > grau) {
                        compat[i] = grau;
                    }
                }
            }
        }

        indice = this.max(compat, numRegrasSFC);
        dt.numClassificadosPorRegraClassificacao.get(indice).add(padrao);
        return regrasSFC[indice][numVariaveisEntrada - 1];
    }

    public String sistemaFuzzyCalculos(int numVariaveisEntrada, String[][] regrasSFC, int numRegrasSFC, Vector padrao, String[][] particaoSFC, DecisionTree dt) {
        compat = new double[numRegrasSFC];
        double grau = 0.0D;
        Vector part = new Vector(1);

        int indice;
        for(int i = 0; i < numRegrasSFC; ++i) {
            compat[i] = 1.0D;

            for(int j = 0; j < numVariaveisEntrada - 1; ++j) {
                float valor = Float.parseFloat(padrao.get(j).toString());
                if ((double)valor != -11111.0D && regrasSFC[i][j].compareTo("dc") != 0) {
                    indice = 1;
                    part.clear();
                    int d;
                    if (j != 0) {
                        for(d = 1; d <= j; ++d) {
                            indice += Integer.parseInt(particaoSFC[0][d]);
                        }
                    }

                    for(d = indice; d < indice + Integer.parseInt(particaoSFC[0][j + 1]); ++d) {
                        if (regrasSFC[i][j].compareTo(particaoSFC[d][1]) == 0) {
                            indice = d;
                            d += Integer.parseInt(particaoSFC[0][j + 1]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("gaussian")) {
                        for(d = 2; d < 4; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    grau = this.calculaGrauRegra((double)valor, particaoSFC[indice][0], part);
                    if (compat[i] > grau) {
                        compat[i] = grau;
                    }
                }
            }
        }

        indice = this.max(compat, numRegrasSFC);
        dt.numClassificadosPorRegraClassificacao.get(indice).add(padrao);
        return regrasSFC[indice][numVariaveisEntrada - 1];
    }

    public String geral(int numVariaveisEntrada, String[][] regrasSFC, int numRegrasSFC, Vector padrao, String[][] particaoSFC, int numerodeClasses) {
        int indice = 1;
        double grau = 0.0D;
        double pertinencia = 0.0D;
        double valor = 0.0D;
        String[] classes = new String[numerodeClasses];
        double[] valorClasse = new double[numerodeClasses];
        Vector part = new Vector(1);

        int i;
        for(i = 1; i < numVariaveisEntrada; ++i) {
            indice += Integer.parseInt(particaoSFC[0][i]);
        }

        for(i = 0; i < numerodeClasses; ++i) {
            valorClasse[i] = 0.0D;
            classes[i] = particaoSFC[indice][1];
            ++indice;
        }

        indice = 1;

        for(i = 0; i < numRegrasSFC; ++i) {
            pertinencia = 1.0D;

            int j;
            for(j = 0; j < numVariaveisEntrada - 1; ++j) {
                valor = (double)(Float)padrao.get(j);
                if (valor != -11111.0D && regrasSFC[i][j].compareTo("dc") != 0) {
                    indice = 1;
                    part.clear();
                    int d;
                    if (j != 0) {
                        for(d = 1; d <= j; ++d) {
                            indice += Integer.parseInt(particaoSFC[0][d]);
                        }
                    }

                    for(d = indice; d < indice + Integer.parseInt(particaoSFC[0][j + 1]); ++d) {
                        if (regrasSFC[i][j].compareTo(particaoSFC[d][1]) == 0) {
                            indice = d;
                            d += Integer.parseInt(particaoSFC[0][j + 1]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    if (particaoSFC[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoSFC[indice][d]);
                        }
                    }

                    grau = this.calculaGrauRegra(valor, particaoSFC[indice][0], part);
                    if (grau < pertinencia) {
                        pertinencia = grau;
                    }
                }
            }

            if (pertinencia > 0.0D) {
                for(j = 0; j < numerodeClasses; ++j) {
                    if (regrasSFC[i][numVariaveisEntrada - 1].compareTo(classes[j]) == 0) {
                        valorClasse[j] += pertinencia;
                    }
                }
            }
        }

        String classeCalculada = this.maiorClasse(classes, valorClasse, numerodeClasses);
        return classeCalculada;
    }

    public double calculaGrauRegra(double grau, String tipoConjunto, Vector part) {
        double valor = 0.0D;
        double a;
        double b;
        double c;
        if (tipoConjunto.equals("triangular")) {
            a = Double.parseDouble("" + part.get(0));
            b = Double.parseDouble("" + part.get(1));
            c = Double.parseDouble("" + part.get(2));
            if (grau <= c && grau >= a) {
                if (grau == b) {
                    valor = 1.0D;
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
                valor = 0.0D;
            }
        } else if (tipoConjunto.equals("trapezoidal")) {
            a = Double.parseDouble("" + part.get(0));
            b = Double.parseDouble("" + part.get(1));
            c = Double.parseDouble("" + part.get(2));
            double d = Double.parseDouble("" + part.get(3));
            if (grau <= d && grau >= a) {
                if (grau >= b && grau <= c) {
                    valor = 1.0D;
                } else if (grau < b) {
                    valor = (grau - a) / (b - a);
                } else if (grau > b) {
                    valor = (d - grau) / (d - c);
                }
            } else {
                valor = 0.0D;
            }
        } else if (tipoConjunto.equals("gaussian")) {
            a = Double.parseDouble("" + part.get(0));
            b = Double.parseDouble("" + part.get(1));
            valor = Math.exp(-b * (grau - a) * (grau - a));
        }

        return valor;
    }

    private int max(double[] compat, int numRegrasSFC) {
        int posMaior = 0;
        double valorMaior = 0.0D;

        for(int a = 0; a < numRegrasSFC; ++a) {
            if (compat[a] > valorMaior) {
                valorMaior = compat[a];
                posMaior = a;
            }
        }

        return posMaior;
    }

    private String maiorClasse(String[] classe, double[] valorClasse, int numerodeClasses) {
        double valorMaior = 0.0D;
        String Classe = "v";

        for(int a = 0; a < numerodeClasses; ++a) {
            if (valorClasse[a] >= valorMaior) {
                valorMaior = valorClasse[a];
                Classe = classe[a];
            }
        }

        return Classe;
    }
}

