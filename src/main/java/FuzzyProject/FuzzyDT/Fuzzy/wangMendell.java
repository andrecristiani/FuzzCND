package main.java.FuzzyProject.FuzzyDT.Fuzzy;

import main.java.FuzzyProject.FuzzyDT.Utils.manipulaArquivos;

import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;

public class wangMendell {
    static String[][] regrasCriadasTemp;
    static String[][] regrasCriadasTemp2;

    public wangMendell() {
    }

    public int wangMendell(String[][] regrasCriadasWM, float[][] treinamento, int numRegrasTreinamento, int numVarEntrada, String nomeArquivo, String[][] particao, String gravarFuzzificado, String metodoRaciocinio) {
        regrasCriadasTemp = new String[numRegrasTreinamento][numVarEntrada + 3];
        manipulaArquivos mA = new manipulaArquivos();
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[numVarEntrada];
        String[] resultados = new String[2];
        String dc = "dc";
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int a;
        int b;
        int c;
        int indice;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            for(b = 0; b < numVarEntrada; ++b) {
                indice = 1;
                if (b != 0) {
                    for(c = 1; c <= b; ++c) {
                        indice += Integer.parseInt(particao[0][c]);
                    }
                }

                for(c = 1; c <= Integer.parseInt(particao[0][b + 1]); ++c) {
                    part.clear();
                    int d;
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

                    if ((double)treinamento[a][b] != -11111.0D) {
                        float grau = cG.calculaGrauRegra(treinamento[a][b], particao[indice][0], part);
                        part1.add(grau);
                        part2.add(particao[indice][1]);
                    }

                    ++indice;
                }

                resultados = this.maior(part1, part2);
                regrasCriadasTemp[a][b] = resultados[0];
                grauPertinenciaRegra[b] = Float.parseFloat(resultados[1]);
                part1.clear();
                part2.clear();
            }

            regrasCriadasTemp[a][numVarEntrada + 2] = "" + this.menorGdP(grauPertinenciaRegra, numVarEntrada);
        }

        if (gravarFuzzificado.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp, "Fuzzificado-" + metodoRaciocinio + "-" + nomeArquivo, numRegrasTreinamento, numVarEntrada + 3);
        }

        for(a = 0; a < numRegrasTreinamento; ++a) {
            regrasCriadasTemp[a][numVarEntrada + 1] = "nl";
        }

        indice = 0;

        float aa;
        float be;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (regrasCriadasTemp[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp[a][numVarEntrada + 1] = "ok";
                ++indice;

                for(b = a + 1; b < numRegrasTreinamento; ++b) {
                    if (regrasCriadasTemp[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada; ++c) {
                            if (!regrasCriadasTemp[a][c].equals(regrasCriadasTemp[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp[a][numVarEntrada + 2]);
                            if (be < aa) {
                                regrasCriadasTemp[b][numVarEntrada + 1] = "ld";
                            } else {
                                regrasCriadasTemp[a][numVarEntrada + 1] = "ld";
                                --a;
                                --indice;
                                b = numRegrasTreinamento;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        regrasCriadasTemp2 = new String[indice][numVarEntrada + 3];
        int ind = 0;

        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (regrasCriadasTemp[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada + 3; ++b) {
                    regrasCriadasTemp2[ind][b] = regrasCriadasTemp[a][b];
                }

                ++ind;
            }
        }

        if (gravarFuzzificado.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp2, "RegrasSemDuplicatasWM-" + metodoRaciocinio + "-" + nomeArquivo, indice, numVarEntrada + 3);
        }

        for(a = 0; a < indice; ++a) {
            regrasCriadasTemp2[a][numVarEntrada + 1] = "nl";
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp2[a][numVarEntrada + 1] = "ok";
                ++ind;

                for(b = a + 1; b < indice; ++b) {
                    if (regrasCriadasTemp2[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada - 1; ++c) {
                            if (!regrasCriadasTemp2[a][c].equals(regrasCriadasTemp2[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp2[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp2[a][numVarEntrada + 2]);
                            if (aa > be) {
                                regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                            }

                            if (aa == be) {
                                float ale = (float)Math.random();
                                if ((double)ale < 0.5D) {
                                    regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                                }

                                if ((double)ale >= 0.5D) {
                                    regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                    b = indice;
                                }
                            }

                            if (be > aa) {
                                regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                b = indice;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada; ++b) {
                    regrasCriadasWM[ind][b] = regrasCriadasTemp2[a][b];
                }

                ++ind;
            }
        }

        mA.gravaArquivo(regrasCriadasWM, "RegrasWM--" + nomeArquivo + "-" + metodoRaciocinio, ind, numVarEntrada);
        return ind;
    }

    public void classificaEntradas(String[] entradasClassificadas, float[][] treinamento, int numRegrasTreinamento, int numVarEntrada, String[][] particao) {
        int j = numVarEntrada - 1;
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        String[] resultados = new String[2];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("#.###");

        for(int i = 0; i < numRegrasTreinamento; ++i) {
            int indice = 1;

            int k;
            for(k = 1; k <= j; ++k) {
                indice += Integer.parseInt(particao[0][k]);
            }

            for(k = 1; k <= Integer.parseInt(particao[0][j + 1]); ++k) {
                part.clear();
                int d;
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

                float grau = cG.calculaGrauRegra(treinamento[i][j], particao[indice][0], part);
                part1.add(grau);
                part2.add(particao[indice][1]);
                ++indice;
            }

            resultados = this.maior(part1, part2);
            entradasClassificadas[i] = resultados[0];
            part1.clear();
            part2.clear();
        }

    }

    public void classificaEntrada(String[] entradaClassificada, float[] treinamento, int numRegrasTreinamento, int numVarEntrada, String[][] particao) {
        int j = numVarEntrada - 1;
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        String[] resultados = new String[2];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("#.###");

        for(int i = 0; i < numRegrasTreinamento; ++i) {
            int indice = 1;

            int k;
            for(k = 1; k <= j; ++k) {
                indice += Integer.parseInt(particao[0][k]);
            }

            for(k = 1; k <= Integer.parseInt(particao[0][j + 1]); ++k) {
                part.clear();
                int d;
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

//                float grau = cG.calculaGrauRegra(treinamento[i][j], particao[indice][0], part);
//                part1.add(grau);
                part2.add(particao[indice][1]);
                ++indice;
            }

            resultados = this.maior(part1, part2);
            //entradasClassificadas[i] = resultados[0];
            part1.clear();
            part2.clear();
        }

    }

    public String[] maior(Vector grausDeCobertura, Vector variaveisLinguisticas) {
        int indice = 0;
        String[] s1 = new String[2];

        float v1;
        for(int i = 1; i < grausDeCobertura.size(); ++i) {
            v1 = (Float)grausDeCobertura.get(i);
            float v2 = (Float)grausDeCobertura.get(indice);
            if (v1 > v2) {
                indice = i;
            } else if (v1 == v2) {
                float dado = (float)Math.random();
                if ((double)dado > 0.5D) {
                    indice = i;
                }
            }
        }

        s1[0] = (String)variaveisLinguisticas.get(indice);
        v1 = (Float)grausDeCobertura.get(indice);
        s1[1] = Float.toString(v1);
        return s1;
    }

    public String[] maior2(Vector grausDeCobertura, Vector variaveisLinguisticas) {
        int indice = 0;
        String[] s1 = new String[2];

        float v1;
        for(int i = 1; i < grausDeCobertura.size(); ++i) {
            v1 = (Float)grausDeCobertura.get(i);
            float v2 = (Float)grausDeCobertura.get(indice);
            if (v1 > v2) {
                indice = i;
            }

            if ((double)v1 < 0.51D && (double)v1 > 0.49D && (double)v2 < 0.51D && (double)v2 > 0.49D) {
                s1[1] = "0.5";
                s1[0] = "duplicado  " + variaveisLinguisticas.get(0) + "  " + variaveisLinguisticas.get(1);
                return s1;
            }
        }

        s1[0] = (String)variaveisLinguisticas.get(indice);
        v1 = (Float)grausDeCobertura.get(indice);
        s1[1] = Float.toString(v1);
        return s1;
    }

    private float menorGdP(float[] grauPertinenciaRegra, int numGraus) {
        int indice = 0;
        if (numGraus < 2) {
            return grauPertinenciaRegra[0];
        } else {
            for(int i = 1; i < numGraus; ++i) {
                float a = grauPertinenciaRegra[i];
                float b = grauPertinenciaRegra[indice];
                if (a < b) {
                    indice = i;
                }
            }

            return grauPertinenciaRegra[indice];
        }
    }

    public int wangMendell2(String[][] regrasCriadasWM, float[][] treinamento, int numRegrasTreinamento, int numVarEntrada, String nomeArquivo, String[][] particao, String cjtosF, String gravaFuzzificados) {
        regrasCriadasTemp = new String[numRegrasTreinamento][numVarEntrada + 3];
        manipulaArquivos mA = new manipulaArquivos();
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[numVarEntrada];
        String[] resultados = new String[2];
        String dc = "dc";
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int a;
        int b;
        int c;
        int indice;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            for(b = 0; b < numVarEntrada; ++b) {
                indice = 1;
                if (b != 0) {
                    for(c = 1; c <= b; ++c) {
                        indice += Integer.parseInt(particao[0][c]);
                    }
                }

                for(c = 1; c <= Integer.parseInt(particao[0][b + 1]); ++c) {
                    part.clear();
                    int d;
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

                    if ((double)treinamento[a][b] != -11111.0D) {
                        float grau = cG.calculaGrauRegra(treinamento[a][b], particao[indice][0], part);
                        part1.add(grau);
                        part2.add(particao[indice][1]);
                    }

                    ++indice;
                }

                resultados = this.maior(part1, part2);
                regrasCriadasTemp[a][b] = resultados[0];
                grauPertinenciaRegra[b] = Float.parseFloat(resultados[1]);
                part1.clear();
                part2.clear();
            }

            regrasCriadasTemp[a][numVarEntrada + 2] = "" + this.menorGdP(grauPertinenciaRegra, numVarEntrada);
        }

        if (gravaFuzzificados.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp, "Fuzzificado-" + nomeArquivo, numRegrasTreinamento, numVarEntrada + 3);
        }

        for(a = 0; a < numRegrasTreinamento; ++a) {
            regrasCriadasTemp[a][numVarEntrada + 1] = "nl";
        }

        indice = 0;

        float aa;
        float be;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (regrasCriadasTemp[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp[a][numVarEntrada + 1] = "ok";
                ++indice;

                for(b = a + 1; b < numRegrasTreinamento; ++b) {
                    if (regrasCriadasTemp[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada; ++c) {
                            if (!regrasCriadasTemp[a][c].equals(regrasCriadasTemp[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp[a][numVarEntrada + 2]);
                            if (be < aa) {
                                regrasCriadasTemp[b][numVarEntrada + 1] = "ld";
                            } else {
                                regrasCriadasTemp[a][numVarEntrada + 1] = "ld";
                                --a;
                                --indice;
                                b = numRegrasTreinamento;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        regrasCriadasTemp2 = new String[indice][numVarEntrada + 3];
        int ind = 0;

        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (regrasCriadasTemp[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada + 3; ++b) {
                    regrasCriadasTemp2[ind][b] = regrasCriadasTemp[a][b];
                }

                ++ind;
            }
        }

        mA.gravaArquivo(regrasCriadasTemp2, "RegrasSemDuplicatasWM" + nomeArquivo, indice, numVarEntrada + 3);

        for(a = 0; a < indice; ++a) {
            regrasCriadasTemp2[a][numVarEntrada + 1] = "nl";
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp2[a][numVarEntrada + 1] = "ok";
                ++ind;

                for(b = a + 1; b < indice; ++b) {
                    if (regrasCriadasTemp2[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada - 1; ++c) {
                            if (!regrasCriadasTemp2[a][c].equals(regrasCriadasTemp2[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp2[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp2[a][numVarEntrada + 2]);
                            if (aa > be) {
                                regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                            }

                            if (aa == be) {
                                float ale = (float)Math.random();
                                if ((double)ale < 0.5D) {
                                    regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                                }

                                if ((double)ale >= 0.5D) {
                                    regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                    b = indice;
                                }
                            }

                            if (be > aa) {
                                regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                b = indice;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada; ++b) {
                    regrasCriadasWM[ind][b] = regrasCriadasTemp2[a][b];
                }

                ++ind;
            }
        }

        mA.gravaArquivo(regrasCriadasWM, "RegrasWM--" + nomeArquivo, ind, numVarEntrada);
        return ind;
    }

    public int wangMendellWrapper(String[][] regrasCriadasWM, float[][] treinamento, int numRegrasTreinamento, int numVarEntrada, String nomeArquivo, String[][] particao, String gravarFuzzificado, String gravarBWM, String metodoRaciocinio) {
        regrasCriadasTemp = new String[numRegrasTreinamento][numVarEntrada + 3];
        manipulaArquivos mA = new manipulaArquivos();
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[numVarEntrada];
        String[] resultados = new String[2];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int a;
        int b;
        int c;
        int indice;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            for(b = 0; b < numVarEntrada; ++b) {
                if ((double)treinamento[a][b] == -11111.0D) {
                    regrasCriadasTemp[a][b] = "dc";
                } else {
                    indice = 1;
                    if (b != 0) {
                        for(c = 1; c <= b; ++c) {
                            indice += Integer.parseInt(particao[0][c]);
                        }
                    }

                    for(c = 1; c <= Integer.parseInt(particao[0][b + 1]); ++c) {
                        part.clear();
                        int d;
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

                        float grau = cG.calculaGrauRegra(treinamento[a][b], particao[indice][0], part);
                        part1.add(grau);
                        part2.add(particao[indice][1]);
                        ++indice;
                        resultados = this.maior(part1, part2);
                        regrasCriadasTemp[a][b] = resultados[0];
                        grauPertinenciaRegra[b] = Float.parseFloat(resultados[1]);
                    }
                }

                part1.clear();
                part2.clear();
            }

            regrasCriadasTemp[a][numVarEntrada + 2] = Float.toString(this.menorGdP(grauPertinenciaRegra, numVarEntrada));
        }

        if (gravarFuzzificado.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp, "Fuzzificado-" + metodoRaciocinio + "-" + nomeArquivo, numRegrasTreinamento, numVarEntrada + 3);
        }

        for(a = 0; a < numRegrasTreinamento; ++a) {
            regrasCriadasTemp[a][numVarEntrada + 1] = "nl";
        }

        indice = 0;

        float aa;
        float be;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (regrasCriadasTemp[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp[a][numVarEntrada + 1] = "ok";
                ++indice;

                for(b = a + 1; b < numRegrasTreinamento; ++b) {
                    if (regrasCriadasTemp[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada; ++c) {
                            if (!regrasCriadasTemp[a][c].equals(regrasCriadasTemp[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp[a][numVarEntrada + 2]);
                            if (be < aa) {
                                regrasCriadasTemp[b][numVarEntrada + 1] = "ld";
                            } else {
                                regrasCriadasTemp[a][numVarEntrada + 1] = "ld";
                                --indice;
                                b = numRegrasTreinamento;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        regrasCriadasTemp2 = new String[indice][numVarEntrada + 3];
        int ind = 0;

        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (regrasCriadasTemp[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada + 3; ++b) {
                    regrasCriadasTemp2[ind][b] = regrasCriadasTemp[a][b];
                }

                ++ind;
            }
        }

        if (gravarFuzzificado.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp2, "RegrasSemDuplicatasWM" + metodoRaciocinio + "-" + nomeArquivo, indice, numVarEntrada + 3);
        }

        for(a = 0; a < indice; ++a) {
            regrasCriadasTemp2[a][numVarEntrada + 1] = "nl";
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp2[a][numVarEntrada + 1] = "ok";
                ++ind;

                for(b = a + 1; b < indice; ++b) {
                    if (regrasCriadasTemp2[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada - 1; ++c) {
                            if (!regrasCriadasTemp2[a][c].equals(regrasCriadasTemp2[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp2[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp2[a][numVarEntrada + 2]);
                            if (aa > be) {
                                regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                            }

                            if (aa == be) {
                                float ale = (float)Math.random();
                                if ((double)ale < 0.5D) {
                                    regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                                }

                                if ((double)ale >= 0.5D) {
                                    regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                    b = indice;
                                }
                            }

                            if (be > aa) {
                                regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                b = indice;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada; ++b) {
                    regrasCriadasWM[ind][b] = regrasCriadasTemp2[a][b];
                }

                ++ind;
            }
        }

        if (gravarBWM.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasWM, "RegrasWM--" + metodoRaciocinio + "-" + nomeArquivo, ind, numVarEntrada);
        }

        return ind;
    }

    public int wangMendellWrapper2(String[][] regrasCriadasWM, float[][] treinamento, int numRegrasTreinamento, int numVarEntrada, String nomeArquivo, String[][] particao, String gravarFuzzificado, String gravarBWM, String metodoRaciocinio) {
        regrasCriadasTemp = new String[numRegrasTreinamento][numVarEntrada + 3];
        manipulaArquivos mA = new manipulaArquivos();
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[numVarEntrada];
        String[] resultados = new String[2];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int i;
        int nVE;
        int d;
        int indice;
        for(i = 0; i < numRegrasTreinamento; ++i) {
            for(nVE = 0; nVE < numVarEntrada; ++nVE) {
                if ((double)treinamento[i][nVE] == -11111.0D) {
                    regrasCriadasTemp[i][nVE] = "dc";
                } else {
                    indice = 1;
                    if (nVE != 0) {
                        for(d = 1; d <= nVE; ++d) {
                            indice += Integer.parseInt(particao[0][d]);
                        }
                    }

                    for(d = 1; d <= Integer.parseInt(particao[0][nVE + 1]); ++d) {
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

                        float grau = cG.calculaGrauRegra(treinamento[i][nVE], particao[indice][0], part);
                        part1.add(grau);
                        part2.add(particao[indice][1]);
                        ++indice;
                        resultados = this.maior2(part1, part2);
                        regrasCriadasTemp[i][nVE] = resultados[0];
                        grauPertinenciaRegra[nVE] = Float.parseFloat(resultados[1]);
                    }
                }

                part1.clear();
                part2.clear();
            }

            regrasCriadasTemp[i][numVarEntrada + 2] = Float.toString(this.menorGdP(grauPertinenciaRegra, numVarEntrada));
        }

        if (gravarFuzzificado.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp, "Fuzzificado-" + metodoRaciocinio + "-" + nomeArquivo, numRegrasTreinamento, numVarEntrada + 3);
        }

        i = numRegrasTreinamento;
        nVE = numVarEntrada;
        d = 0;
        d = 0;

        int indiceNovaRegra;
        for(indiceNovaRegra = 0; indiceNovaRegra < i; ++indiceNovaRegra) {
            for(int b = 0; b < nVE; ++b) {
                if (regrasCriadasTemp[indiceNovaRegra][b].contains("duplicado")) {
                    ++d;
                }
            }

            d = (int)Math.pow(2.0D, (double)d);
            d += d;
            if (d > 0) {
                --d;
            }

            d = 0;
        }

        indiceNovaRegra = i;
        i += d;
        String[][] dadosFuzzy = new String[i][nVE + 3];

        int a;
        int b;
        for(a = 0; a < i - d; ++a) {
            for(b = 0; b < nVE + 3; ++b) {
                dadosFuzzy[a][b] = regrasCriadasTemp[a][b];
            }
        }

        for(a = 0; a < i; ++a) {
            for(b = 0; b < nVE; ++b) {
                if (dadosFuzzy[a][b].contains("duplicado")) {
                    StringTokenizer elem = new StringTokenizer(dadosFuzzy[a][b]);
                    String primeiro = elem.nextToken();
                    primeiro = elem.nextToken();
                    String segundo = elem.nextToken();
                    dadosFuzzy[a][b] = primeiro;

                    int c;
                    for(c = 0; c < b; ++c) {
                        dadosFuzzy[indiceNovaRegra][c] = dadosFuzzy[a][c];
                    }

                    dadosFuzzy[indiceNovaRegra][b] = segundo;

                    for(c = b + 1; c < nVE + 3; ++c) {
                        dadosFuzzy[indiceNovaRegra][c] = dadosFuzzy[a][c];
                    }

                    ++indiceNovaRegra;
                }
            }
        }

        numRegrasTreinamento = i;

        for(a = 0; a < numRegrasTreinamento; ++a) {
            dadosFuzzy[a][numVarEntrada + 1] = "nl";
        }

        indice = 0;

        int c;
        float be;
        float aa;
        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (dadosFuzzy[a][numVarEntrada + 1].equals("nl")) {
                dadosFuzzy[a][numVarEntrada + 1] = "ok";
                ++indice;

                for(b = a + 1; b < numRegrasTreinamento; ++b) {
                    if (dadosFuzzy[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada; ++c) {
                            if (!dadosFuzzy[a][c].equals(dadosFuzzy[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(dadosFuzzy[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(dadosFuzzy[a][numVarEntrada + 2]);
                            if (be < aa) {
                                dadosFuzzy[b][numVarEntrada + 1] = "ld";
                            } else {
                                dadosFuzzy[a][numVarEntrada + 1] = "ld";
                                --indice;
                                b = numRegrasTreinamento;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        regrasCriadasTemp2 = new String[indice][numVarEntrada + 3];
        int ind = 0;

        for(a = 0; a < numRegrasTreinamento; ++a) {
            if (dadosFuzzy[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada + 3; ++b) {
                    regrasCriadasTemp2[ind][b] = dadosFuzzy[a][b];
                }

                ++ind;
            }
        }

        if (gravarFuzzificado.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasTemp2, "RegrasSemDuplicatasWM" + metodoRaciocinio + "-" + nomeArquivo, indice, numVarEntrada + 3);
        }

        for(a = 0; a < indice; ++a) {
            regrasCriadasTemp2[a][numVarEntrada + 1] = "nl";
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("nl")) {
                regrasCriadasTemp2[a][numVarEntrada + 1] = "ok";
                ++ind;

                for(b = a + 1; b < indice; ++b) {
                    if (regrasCriadasTemp2[b][numVarEntrada + 1].equals("nl")) {
                        for(c = 0; c < numVarEntrada - 1; ++c) {
                            if (!regrasCriadasTemp2[a][c].equals(regrasCriadasTemp2[b][c])) {
                                flag = false;
                                c = numVarEntrada;
                            }
                        }

                        if (flag) {
                            be = Float.parseFloat(regrasCriadasTemp2[b][numVarEntrada + 2]);
                            aa = Float.parseFloat(regrasCriadasTemp2[a][numVarEntrada + 2]);
                            if (aa > be) {
                                regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                            }

                            if (aa == be) {
                                float ale = (float)Math.random();
                                if ((double)ale < 0.5D) {
                                    regrasCriadasTemp2[b][numVarEntrada + 1] = "ld";
                                }

                                if ((double)ale >= 0.5D) {
                                    regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                    b = indice;
                                }
                            }

                            if (be > aa) {
                                regrasCriadasTemp2[a][numVarEntrada + 1] = "ld";
                                b = indice;
                            }
                        }

                        if (!flag) {
                            flag = true;
                        }
                    }
                }
            }
        }

        ind = 0;

        for(a = 0; a < indice; ++a) {
            if (regrasCriadasTemp2[a][numVarEntrada + 1].equals("ok")) {
                for(b = 0; b < numVarEntrada; ++b) {
                    regrasCriadasWM[ind][b] = regrasCriadasTemp2[a][b];
                }

                ++ind;
            }
        }

        if (gravarBWM.compareTo("sim") == 0) {
            mA.gravaArquivo(regrasCriadasWM, "RegrasWM--" + metodoRaciocinio + "-" + nomeArquivo, ind, numVarEntrada);
        }

        return ind;
    }
}

