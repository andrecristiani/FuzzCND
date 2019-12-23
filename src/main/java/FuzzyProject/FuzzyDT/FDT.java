package main.java.FuzzyProject.FuzzyDT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class FDT {
    private static String[][] particaoFDT;
    private static String[][] termos;
    private static String[][] dadosFuzzificados;
    public static float[][] exemplosTeste;
    private static Integer[][][] contagem;
    private static float[][] treinamento;

    public FDT() {
    }

    public void geraArvore(String dataset, int rodada, String caminho, String metodoRaciocinio) {
        String particao = caminho + "particao" + dataset + ".txt";
        manipulaArquivos mA = new manipulaArquivos();
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento" + rodada + ".txt");
        int numObjetos = mA.getNumRegrasTreinamento2(caminho + dataset + ".txt");
        int numConjuntos = mA.getNumConjuntos(particao);
        particaoFDT = new String[numConjuntos + 1][nVE + 2];
        mA.carregaParticao(particaoFDT, particao, nVE, numConjuntos);
        treinamento = new float[numObjetos][nVE];
        mA.carregaArquivoTreinamento(treinamento, caminho + dataset + ".txt", nVE);
        dadosFuzzificados = new String[numObjetos][nVE + 1];
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[nVE];
        String[] resultados = new String[2];
        String[][] metaDados = new String[nVE][100];
        mA.getMetaDados(caminho + dataset + ".names", nVE);
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int i;
        int b;
        for(i = 0; i < numObjetos; ++i) {
            for(int j = 0; j < nVE; ++j) {
                int indice = 1;
                if (j != 0) {
                    for(b = 1; b <= j; ++b) {
                        indice += Integer.parseInt(particaoFDT[0][b]);
                    }
                }

                for(b = 1; b <= Integer.parseInt(particaoFDT[0][j + 1]); ++b) {
                    part.clear();
                    int d;
                    if (particaoFDT[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoFDT[indice][d]);
                        }
                    }

                    if (particaoFDT[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoFDT[indice][d]);
                        }
                    }

                    if ((double)treinamento[i][j] != -11111.0D) {
                        float grau = cG.calculaGrauRegra(treinamento[i][j], particaoFDT[indice][0], part);
                        part1.add(grau);
                        part2.add(particaoFDT[indice][1]);
                    }

                    ++indice;
                }

                resultados = this.maior(part1, part2);
                dadosFuzzificados[i][j] = resultados[0];
                grauPertinenciaRegra[j] = Float.parseFloat(resultados[1]);
                part1.clear();
                part2.clear();
            }

            dadosFuzzificados[i][nVE] = "" + this.menorGdP(grauPertinenciaRegra, nVE);
        }

        mA.gravaArquivoARFF(dadosFuzzificados, caminho, dataset, numObjetos, nVE);
    }

    public String geraArvore2(Integer[] atribs, String[][] dF, String[][] ter, String[][] particao, int numOb, int nVE, int numClasses, String[] classes, String cabeca, String[][] metaDados) {
        String regras = "";
        float infoTotal = this.calculaInfoTotal(dF, numOb, nVE, numClasses, classes);
        int numAtribsAtivos = 0;

        int a;
        for(a = 0; a < nVE - 1; ++a) {
            if (atribs[a] > -1) {
                ++numAtribsAtivos;
            }
        }

        a = this.escolheAtributoParaParticionar(atribs, dF, ter, particao, numOb, nVE, infoTotal, numClasses, classes);
        System.out.println("Atributo com maior ganho: " + (a + 1) + ": " + metaDados[a][0]);
        return regras;
    }

    public String geraRamo(int termo, int atributo, Integer[] atribs, String[][] dF, String[][] ter, String[][] particao, int numOb, int nVE, int numClasses, String[] classes, int somaIndice, String cabeca, String[][] metaDados) {
        String nomeTermo = particao[this.verificaIndice(atributo, particao) + somaIndice][1];
        String classe = "";
        System.out.println("Termo da vez: " + nomeTermo);

        int numAtribsAtivos;
        for(numAtribsAtivos = 0; numAtribsAtivos < numOb; ++numAtribsAtivos) {
            if (dF[numAtribsAtivos][atributo].compareTo(nomeTermo) == 0) {
                classe = dF[numAtribsAtivos][nVE - 1];
                numAtribsAtivos = numOb;
                System.out.println("classe: " + classe);
            }
        }

        if (this.verificaSeTodosExemplosAtivosSaoDaMesmaClasse(dF, nVE, numOb, nomeTermo, atributo, classe)) {
            System.out.println("Ramo final alcançado para atributo" + atributo + ": " + metaDados[atributo][0] + ", termo " + nomeTermo + ", classe " + classe + "\nnum objetos: " + numOb);
            cabeca = cabeca + "\t" + classe + "\n";
        } else {
            numAtribsAtivos = 0;

            for(int a = 0; a < nVE - 1; ++a) {
                if (atribs[a] > -1) {
                    ++numAtribsAtivos;
                }
            }

            System.out.println("numero de atributos ativos: " + numAtribsAtivos);
            if (numAtribsAtivos > 0) {
                cabeca = this.geraArvore2(atribs, dF, ter, particao, numOb, nVE, numClasses, classes, cabeca, metaDados);
            } else {
                int[] contagemExemplosPorClasse = new int[numClasses];

                int totalCadaClasse;
                for(totalCadaClasse = 0; totalCadaClasse < numClasses; ++totalCadaClasse) {
                    contagemExemplosPorClasse[totalCadaClasse] = 0;
                }

                int b;
                for(totalCadaClasse = 0; totalCadaClasse < numOb; ++totalCadaClasse) {
                    for(b = 0; b < numClasses; ++b) {
                        if (dF[totalCadaClasse][nVE - 1].compareTo(classes[b]) == 0) {
                            int var10002 = contagemExemplosPorClasse[b]++;
                        }
                    }
                }

                totalCadaClasse = 0;
                b = -1;

                for(b = 0; b < numClasses; ++b) {
                    System.out.println("Classe " + b + ": " + classes[b] + " total: " + contagemExemplosPorClasse[b]);
                    if (contagemExemplosPorClasse[b] > totalCadaClasse) {
                        totalCadaClasse = contagemExemplosPorClasse[b];
                        b = b;
                    }
                }

                cabeca = cabeca + "\t" + classes[b] + "\n";
                System.out.println("Ramo final alcançado para atributo" + (atributo + 1) + ": " + metaDados[atributo][0] + ", termo " + nomeTermo + ", classe " + classe + "\nnum objetos: " + numOb);
            }
        }

        return cabeca;
    }

    public boolean verificaSeTodosExemplosAtivosSaoDaMesmaClasse(String[][] dF, int nVe, int numOb, String termo, int atributo, String classe) {
        boolean retorno = true;

        for(int a = 0; a < numOb; ++a) {
            if (dF[a][atributo].compareTo(termo) == 0 && dF[a][nVe - 1].compareTo(classe) != 0) {
                retorno = false;
                a = numOb;
            }
        }

        System.out.println("\t\t\tTodos são da mesma classe??? " + retorno);
        return retorno;
    }

    public int verificaIndice(int atributo, String[][] particao) {
        int indice = 1;

        for(int a = 1; a <= atributo; ++a) {
            indice += Integer.parseInt(particao[0][a]);
        }

        return indice;
    }

    private int escolheAtributoParaParticionar(Integer[] atribs, String[][] dF, String[][] ter, String[][] particao, int numOb, int nVE, float infoTotal, int numClasses, String[] classes) {
        int atributo = 0;
        int atribsAtivos = 0;

        int a;
        for(a = 0; a < nVE - 1; ++a) {
            if (atribs[a] >= 0) {
                ++atribsAtivos;
                atributo = a;
            }
        }

        if (atribsAtivos > 0) {
            if (atribsAtivos == 1) {
                for(a = 0; a < nVE - 1; ++a) {
                    if (atribs[a] >= 0) {
                        atributo = a;
                        a = nVE;
                    }
                }
            } else {
                float[] infos = new float[nVE - 1];
                float ganho = 0.0F;
                float diferenca = 0.0F;

                for(a = 0; a < nVE - 1; ++a) {
                    if (atribs[a] != -1) {
                        infos[a] = this.calculaInfoAtrib(a, dF, numOb, nVE, numClasses, classes, particao, ter);
                        diferenca = infoTotal - infos[a];
                        System.out.println("info atributo " + (a + 1) + ": " + infos[a] + "\tGanho: " + diferenca);
                        if (diferenca > ganho) {
                            atributo = a;
                            ganho = diferenca;
                        }

                        if (ganho == diferenca) {
                            float dado = (float)Math.random();
                            if ((double)dado < 0.5D) {
                                atributo = a;
                                ganho = diferenca;
                            }
                        }
                    }
                }
            }
        } else {
            atributo = -1;
        }

        return atributo;
    }

    public float calculaInfoAtrib(int x, String[][] df, int numObjetos, int nVE, int numClasses, String[] classes, String[][] particao, String[][] termos) {
        float iT = 0.0F;
        int indice = 1;
        int numTermos = Integer.parseInt(particao[0][x]);
        int[][] conTagem = new int[numClasses][numTermos];
        String[] termosAtrib = new String[numTermos];

        int b;
        for(b = 1; b < x; ++b) {
            indice += Integer.parseInt(particao[0][b]);
        }

        for(b = 0; b < numTermos; ++b) {
            termosAtrib[b] = particao[indice][1];
            ++indice;
        }

        int[] numExeClasses = new int[numClasses];

        for(b = 0; b < numClasses; ++b) {
            numExeClasses[b] = 0;
        }

        int a;
        for(b = 0; b < numObjetos; ++b) {
            for(a = 0; a < numClasses; ++a) {
                if (df[b][nVE - 1].compareTo(classes[a]) == 0) {
                    int var10002 = numExeClasses[a]++;
                }
            }
        }

        for(b = 0; b < numClasses; ++b) {
            for(a = 0; a < Integer.parseInt(particao[0][x]); ++a) {
                conTagem[b][a] = 0;
            }
        }

        int numExeTermoX;
        for(b = 0; b < numClasses; ++b) {
            for(a = 0; a < Integer.parseInt(particao[0][x + 1]); ++a) {
                System.out.println(b + "\t" + a + "\t" + x);

                for(numExeTermoX = 0; numExeTermoX < numObjetos; ++numExeTermoX) {
                    if (df[numExeTermoX][x].compareTo(termos[x + 1][a]) == 0 && df[numExeTermoX][nVE - 1].compareTo(termos[0][b]) == 0) {
                        ++conTagem[b][a];
                    }
                }
            }
        }

        float[] infoTermos = new float[numTermos];

        for(a = 0; a < numTermos; ++a) {
            infoTermos[a] = 0.0F;
            numExeTermoX = 0;

            for(int c = 0; c < numClasses; ++c) {
                numExeTermoX += conTagem[c][a];
            }

            float nET = (float)numExeTermoX;

            for(b = 0; b < numClasses; ++b) {
                float nETC = (float)conTagem[b][a];
                if (nETC != 0.0F) {
                    float lixo = (float)((double)(nETC / nET) * (Math.log((double)(nETC / nET)) / Math.log(2.0D)));
                    infoTermos[a] -= lixo;
                }
            }

            infoTermos[a] *= nET / (float)numObjetos;
        }

        for(a = 0; a < numTermos; ++a) {
            iT += infoTermos[a];
        }

        return iT;
    }

    public float calculaInfoTotal(String[][] df, int numObjetos, int nVE, int numClasses, String[] classes) {
        float iT = 0.0F;
        int[] numExe = new int[numClasses];

        int a;
        for(a = 0; a < numClasses; ++a) {
            numExe[a] = 0;
        }

        for(a = 0; a < numObjetos; ++a) {
            for(int b = 0; b < numClasses; ++b) {
                if (df[a][nVE - 1].compareTo(classes[b]) == 0) {
                    int var10002 = numExe[b]++;
                }
            }
        }

        for(a = 0; a < numClasses; ++a) {
            float nET = (float)numObjetos;
            float nE = (float)numExe[a];
            nE /= nET;
            if (numExe[a] != 0) {
                iT = (float)((double)iT - (double)nE * (Math.log((double)nE) / Math.log(2.0D)));
            }
        }

        System.out.println("Info total: " + iT);
        return iT;
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
                double dado = Math.random();
                if (dado >= 0.5D) {
                    indice = i;
                }
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

    private String[] geraArquivoRegras(String arvore, int numRegras) {
        int indiceRegras = 0;
        int tamanhoArvore = arvore.length();
        char[] proximo = new char[1];
        String[] Regras = new String[numRegras];

        int a;
        for(a = 0; a < numRegras; ++a) {
            Regras[a] = "";
        }

        for(a = 0; a < tamanhoArvore; ++a) {
            arvore.getChars(a, a + 1, proximo, 0);
            if (proximo[0] == '\n') {
                ++indiceRegras;
            } else {
                Regras[indiceRegras] = Regras[indiceRegras] + proximo[0];
            }
        }

        System.out.println("Conjunto final de (" + numRegras + ") regras: \n");

        for(a = 0; a < numRegras; ++a) {
            System.out.println(Regras[a]);
        }

        return Regras;
    }

    private String[][] converteRegrasParaRegrasPadrao(String[] regras, int numRegras, int nVE, String[][] metaDados) {
        String[][] regrasPadrao = new String[numRegras][nVE];
        int a;
        for(a = 0; a < numRegras; ++a) {
            for(int b = 0; b < nVE - 1; ++b) {
                regrasPadrao[a][b] = "dc";
            }
        }

        for(a = 0; a < numRegras; ++a) {
            String line = regras[a];

            StringTokenizer str = new StringTokenizer(line);
            int numTokens = str.countTokens();

            String token;
            for(int b = 0; b < (numTokens - 1) / 2; ++b) {
                if(str.hasMoreTokens()) {
                    token = str.nextToken();
                    for (int c = 0; c < nVE - 1; ++c) {
                        if (token.compareTo(metaDados[c][0]) == 0) {
                            token = str.nextToken();
                            regrasPadrao[a][c] = token;
                            c = nVE;
                        }
                    }
                }
            }

            if(str.hasMoreTokens()) {
                token = str.nextToken();
                regrasPadrao[a][nVE - 1] = token;
            }
        }

        new manipulaArquivos();
        return regrasPadrao;
    }

    private float calculaTCC(String[][] regrasPadrao, int nVE, int numRegras, String dataset, String[][] particao, String caminho) {
        float TCC = 0.0F;
        String arqTeste = dataset + "-teste0.txt";
        int numRegrasTeste = carregaArquivoTeste(arqTeste, nVE, caminho);
        String[] entradasClassificadas = new String[numRegrasTeste];
        wangMendell wM = new wangMendell();
        wrapperWM wWM = new wrapperWM();
        wM.classificaEntradas(entradasClassificadas, exemplosTeste, numRegrasTeste, nVE, particao);
        TCC = wWM.calculaFitness(entradasClassificadas, numRegras, nVE, particao, "geral", exemplosTeste, regrasPadrao, numRegrasTeste);
        System.out.println("TCC: " + TCC);
        return TCC;
    }

    private static int carregaArquivoTeste(String arqTeste, int numVariaveisEntrada, String caminho) {
        manipulaArquivos mA = new manipulaArquivos();
        int numTestes = mA.getNumRegrasTreinamento(caminho + arqTeste);
        exemplosTeste = new float[numTestes][numVariaveisEntrada];
        mA.carregaPadroesAG(exemplosTeste, caminho + arqTeste, numVariaveisEntrada, numTestes);
        return numTestes;
    }

    public void converteArvoreEmRegras(String dataset, String caminho, String tp, String arvoreJ48) {
        manipulaArquivos mA = new manipulaArquivos();
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento" + 0 + ".txt");
        String[][] metaDados = new String[nVE][100];
        metaDados = mA.getMetaDados(caminho + dataset + ".names", nVE);
        int numRegras = 0;
        StringTokenizer str = new StringTokenizer(arvoreJ48);
        String temp = arvoreJ48.replace("J48 pruned tree", "");
        arvoreJ48 = temp.replace("------------------", "");
        temp = arvoreJ48.replace("=", "");

        while(str.hasMoreElements()) {
            if (str.nextToken().contains("(")) {
                ++numRegras;
            }
        }

        new StringTokenizer(temp);
        String[] regrasSujas = new String[numRegras];
        StringTokenizer tudo = new StringTokenizer(temp);
        String token = "";
        String lastToken = "";

        for(int aa = 0; aa < numRegras; ++aa) {
            regrasSujas[aa] = "";
            lastToken = token;
            token = tudo.nextToken();

            do {
                if (!token.contains("|")) {
                    regrasSujas[aa] = regrasSujas[aa] + token + "\t";
                    lastToken = token;
                }

                if (token.compareTo("|") == 0 && (lastToken.contains("|") || lastToken.contains(")"))) {
                    regrasSujas[aa] = regrasSujas[aa] + "|\t|\t";
                }

                token = tudo.nextToken();
            } while(!token.contains(")"));
        }

        String[][] regras = new String[numRegras][nVE * 2];

        int aa;
        for(aa = 0; aa < numRegras; ++aa) {
            for(int bb = 0; bb < nVE * 2; ++bb) {
                regras[aa][bb] = "dc";
            }
        }
        String regraTemp;
        int b;
        for(aa = 0; aa < numRegras; ++aa) {
            StringTokenizer lin = new StringTokenizer(regrasSujas[aa]);
            b = 0;

            while(lin.hasMoreElements()) {
                regraTemp = lin.nextToken();
                if (regraTemp.compareTo("|") == 0) {
                    regras[aa][b] = regras[aa - 1][b];
                    ++b;
                } else {
                    if (regraTemp.contains(":")) {
                        regraTemp = regraTemp.replace(":", "");
                    }

                    regras[aa][b] = regraTemp;
                    ++b;
                }
            }
        }

        String[] regraS = new String[numRegras];

        for(aa = 0; aa < numRegras; ++aa) {
            regraS[aa] = "";
        }

        for(aa = 0; aa < numRegras; ++aa) {
            for(b = 0; b < nVE * 2 - 1; ++b) {
                String lixo = regras[aa][b];
                if (lixo.compareTo("dc") != 0) {
                    regraS[aa] = regraS[aa] + regras[aa][b] + "\t";
                } else {
                    b = nVE * 2;
                }
            }
        }

        String[][] var10000 = new String[numRegras][nVE];
        String[][] regrasFinais = this.converteRegrasParaRegrasPadrao(regraS, numRegras, nVE, metaDados);
        mA.gravaBaseRegras(regrasFinais, caminho + "RegrasFC45-" + dataset + tp + ".txt", numRegras, nVE);
        mA.gravaBRparaUsuario(regrasFinais, caminho + "RegrasFC45User-" + dataset + tp + ".txt", numRegras, nVE, dataset, caminho);
    }

    public float[] inferenciaAD(String dataset, String caminho, String tp, String arvoreJ48) {
        String arqRegras = caminho + "RegrasFC45-" + dataset + tp + ".txt";
        DecimalFormat mF = new DecimalFormat();
        mF.applyPattern("########0.0000");
        this.converteArvoreEmRegras(dataset, caminho, tp, arvoreJ48);
        manipulaArquivos mA = new manipulaArquivos();
        String[] metodoRaciocinio = new String[]{"classico", "geral"};
        int rodadas = 1;
        wrapperWM wWM = new wrapperWM();
        String arqParticao = "particao" + dataset + ".txt";
        int numConjuntos = mA.getNumConjuntos(caminho + arqParticao);
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + ".txt");
        String[][] particao = new String[numConjuntos + 1][nVE + 2];
        mA.carregaParticao(particao, caminho + arqParticao, nVE, numConjuntos);
        int numRegrasAD = mA.getNumRegrasAD(arqRegras);
        String[][] var10000 = new String[numRegrasAD][nVE];
        String[][] regrasAD = mA.carregaRegrasAD(arqRegras, nVE, numRegrasAD);
        float[] precisao = new float[rodadas];

        for(int a = 0; a < rodadas; ++a) {
            String arqTeste = dataset + "-teste" + a + ".txt";
            int numExemplosTeste = mA.getNumRegrasTreinamento(caminho + arqTeste);
            float[][] teste = new float[numExemplosTeste][nVE];
            mA.carregaPadroesAG(teste, caminho + arqTeste, nVE, numExemplosTeste);
            String[] entradasClassificadas = new String[numExemplosTeste];
            wangMendell wM = new wangMendell();
            wM.classificaEntradas(entradasClassificadas, teste, numExemplosTeste, nVE, particao);
            precisao[a] = wWM.calculaFitness(entradasClassificadas, numRegrasAD, nVE, particao, metodoRaciocinio[0], teste, regrasAD, numExemplosTeste);
        }

        return precisao;
    }

    public float inferenciaADNFolds(String dataset, String caminho, String tp, String arvoreJ48, int rodada) {
        String arqRegras = caminho + "RegrasFC45-" + dataset + tp + ".txt";
        DecimalFormat mF = new DecimalFormat();
        mF.applyPattern("########0.00");
        this.converteArvoreEmRegras(dataset, caminho, tp, arvoreJ48);
        manipulaArquivos mA = new manipulaArquivos();
        String[] metodoRaciocinio = new String[]{"classico", "geral"};
        wrapperWM wWM = new wrapperWM();
        String arqParticao = "particao" + dataset + ".txt";
        int numConjuntos = mA.getNumConjuntos(caminho + arqParticao);
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + ".txt");
        String[][] particao = new String[numConjuntos + 1][nVE + 2];
        mA.carregaParticao(particao, caminho + arqParticao, nVE, numConjuntos);
        int numRegrasAD = mA.getNumRegrasAD(arqRegras);
        String[][] var10000 = new String[numRegrasAD][nVE];
        String[][] regrasAD = mA.carregaRegrasAD(arqRegras, nVE, numRegrasAD);
        String arqTeste = dataset + "-teste" + rodada + ".txt";
        int numExemplosTeste = mA.getNumRegrasTreinamento(caminho + arqTeste);
        float[][] teste = new float[numExemplosTeste][nVE];
        mA.carregaPadroesAG(teste, caminho + arqTeste, nVE, numExemplosTeste);
        String[] entradasClassificadas = new String[numExemplosTeste];
        wangMendell wM = new wangMendell();
        wM.classificaEntradas(entradasClassificadas, teste, numExemplosTeste, nVE, particao);
        float precisao = wWM.calculaFitness(entradasClassificadas, numRegrasAD, nVE, particao, metodoRaciocinio[0], teste, regrasAD, numExemplosTeste);
        return 1.0F - precisao;
    }

    public void rodaArvore(String dataset, String caminho, int numTermos) {
        float[] precisao = new float[10];
        int rodadas = 1;
        String metodoRaciocinio = "classico";

        for(int a = 0; a < rodadas; ++a) {
            this.geraArvore(dataset, a, caminho, metodoRaciocinio);
        }

        System.out.println("\n\n\n\n\n\n\n\nValores de TCC: \n");
        float[] resultados = new float[2];
        wrapperWM wWM = new wrapperWM();
        resultados = wWM.calculaDesvioPadrao(precisao, rodadas);
        System.out.println("Fitness médio:\t Desvio padrão:");
        System.out.println(resultados[1] + "\t" + resultados[0]);
    }

    void resumoDasRegras(String dataset, String caminho, String tp) {
        DecimalFormat mF = new DecimalFormat();
        mF.applyPattern("########0.00");
        manipulaArquivos mA = new manipulaArquivos();
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + ".txt");
        int numRegrasAD = mA.getNumRegrasAD(caminho + "RegrasFC45-" + dataset + tp + ".txt");
        String[][] var10000 = new String[numRegrasAD][nVE];
        String[][] regrasAD = mA.carregaRegrasAD(caminho + "RegrasFC45-" + dataset + tp + ".txt", nVE, numRegrasAD);
        float numMedioConjuncoes = 0.0F;
        float numConjuncoes = 0.0F;

        for(int a = 0; a < numRegrasAD; ++a) {
            for(int b = 0; b < nVE - 1; ++b) {
                if (regrasAD[a][b].compareTo("dc") != 0) {
                    ++numConjuncoes;
                }
            }
        }

        numMedioConjuncoes = numConjuncoes / (float)numRegrasAD;
        System.out.println("\t" + numRegrasAD + "\t" + mF.format((double)numMedioConjuncoes));
    }

    public void geraFuzzyDecisionTree(String dataset, String taxaPoda, int numCjtos, String caminho) throws Exception {
        J48 j48 = new J48();
        manipulaArquivos mA = new manipulaArquivos();
        String metodoRaciocinio = "classico";
        float[] precisao = new float[10];

        for(int b = 0; b < 10; ++b) {
            Particoes pt = new Particoes();

            wrapperWM wWM = new wrapperWM();
            wWM.classificaAtribsWMUmFold(dataset, caminho, b);
            pt.geraParticaoNumCjtosFuzzyVariavel(dataset, caminho);

            this.geraArvore(dataset, 0, caminho, metodoRaciocinio);
            DataSource source = new DataSource(caminho + dataset + "Fuzzy.arff");
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            j48.buildClassifier(instances);
            String arvoreJ48 = j48.toString();
            float[] r = new float[2];
            r = this.inferenciaAD(dataset, caminho, taxaPoda, arvoreJ48);
            mA.gravaArvore(arvoreJ48, caminho + dataset + "ArvoreJ48" + b + ".txt");
            precisao[b] = r[0];
        }

        float[] res = new float[2];
        wrapperWM vWM = new wrapperWM();
        res = vWM.calculaDesvioPadrao(precisao, 10);
        DecimalFormat mF = new DecimalFormat();
        mF.applyPattern("########0.00");
        System.out.println("Error: \tStandard Deviation: ");
        System.out.println(mF.format((double)(100.0F - 100.0F * res[1])) + "\t" + mF.format((double)res[0]));
    }

    void calculaValorInfoGainRFUmDataset(String dataset, String funcao, String caminho, int arqTreino) throws Exception {
        manipulaArquivos mA = new manipulaArquivos();
        int numMaxFuzzySets = 9;
        new J48();
        InfoGainAttributeEval IG = new InfoGainAttributeEval();
        ReliefFAttributeEval rf = new ReliefFAttributeEval();
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");
        double diferenca = 0.0D;
        int nve = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + dataset + ".txt");
        String arqMetaDados = dataset + ".names";
        String[][] metaDados = new String[nve][100];
        metaDados = mA.getMetaDados(caminho + arqMetaDados, nve);
        String[][] resultados = new String[numMaxFuzzySets + 1][nve - 1];
        int[] tipoAtribs = new int[nve - 1];

        int x;
        for(int m = 0; m < nve - 1; ++m) {
            if (metaDados[m][1].compareTo("double") != 0 && metaDados[m][1].compareTo("integer") != 0) {
                int temp = 0;

                for(x = 1; x < 100; ++x) {
                    if (metaDados[m][x] != null) {
                        ++temp;
                    }
                }

                tipoAtribs[m] = temp;
            } else {
                tipoAtribs[m] = 0;
            }
        }

        boolean imprimeDadosFuzzificados = false;
        String metodoRaciocinio = "classico";

        for(x = 2; x <= numMaxFuzzySets; ++x) {
            Particoes pt = new Particoes();
            pt.geraParticao(dataset, x, caminho);
            this.geraArvoreNumTermosFixoTreinamento(dataset, arqTreino, caminho, imprimeDadosFuzzificados, metodoRaciocinio, x);
            DataSource source = new DataSource(caminho + dataset + "Fuzzy.arff");
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            if (funcao.compareTo("infogain") == 0) {
                diferenca = 0.005D;
                IG.buildEvaluator(instances);

                for(int v = 0; v < instances.numAttributes() - 1; ++v) {
                    if (tipoAtribs[v] == 0) {
                        resultados[x - 2][v] = IG.evaluateAttribute(v) + "";
                    } else {
                        resultados[x - 2][v] = tipoAtribs[v] + "";
                    }
                }
            }

            if (funcao.compareTo("rf") == 0) {
                diferenca = 0.005D;
                int numRepeticoes = 10;
                double[][] results = new double[numRepeticoes][nve - 1];

                int v;
                for(v = 0; v < numRepeticoes; ++v) {
                    rf.buildEvaluator(instances);

                    for(v = 0; v < instances.numAttributes() - 1; ++v) {
                        if (tipoAtribs[v] == 0) {
                            results[v][v] = rf.evaluateAttribute(v);
                        }
                    }
                }

                for(v = 0; v < instances.numAttributes() - 1; ++v) {
                    if (tipoAtribs[v] != 0) {
                        resultados[x - 2][v] = tipoAtribs[v] + "";
                    } else {
                        double temp = 0.0D;

                        for(int z = 0; z < numRepeticoes; ++z) {
                            temp += results[z][v];
                        }

                        temp /= (double)numRepeticoes;
                        resultados[x - 2][v] = temp + "";
                    }
                }
            }
        }

        for(x = 0; x < nve - 1; ++x) {
            float maior = 0.0F;
            int indice = 0;

            int y;
            double result;
            for(y = 0; y < numMaxFuzzySets - 1; ++y) {
                result = Double.parseDouble(resultados[y][x]);
                if (result > (double)maior) {
                    maior = Float.parseFloat(resultados[y][x]);
                    indice = y;
                }
            }

            for(y = numMaxFuzzySets - 2; y >= 0; --y) {
                result = Double.parseDouble(resultados[y][x]);
                if (result >= (double)maior - diferenca) {
                    indice = y;
                }
            }

            resultados[numMaxFuzzySets - 1][x] = indice + 2 + "";
        }

        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + dataset + "DadosParticao.txt");
            buf_writer = new BufferedWriter(writer);
            String line = "";

            for(int i = 0; i < nve - 1; ++i) {
                line = line + resultados[numMaxFuzzySets - 1][i] + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var31) {
            System.err.println(var31);
            System.exit(1);
        }

    }

    public void geraArvoreNumTermosFixoTreinamento(String dataset, int rodada, String caminho, boolean imprimeDF, String metodoRaciocinio, int numTermos) {
        String particao = caminho + "particao" + dataset + ".txt";
        manipulaArquivos mA = new manipulaArquivos();
        String rod = rodada + "";
        if (rodada == -1) {
            rod = "";
        }

        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento" + rod + ".txt");
        int numObjetos = mA.getNumRegrasTreinamento2(caminho + dataset + ".txt");
        int numConjuntos = mA.getNumConjuntos(particao);
        particaoFDT = new String[numConjuntos + 1][nVE + 2];
        mA.carregaParticao(particaoFDT, particao, nVE, numConjuntos);
        treinamento = new float[numObjetos][nVE];
        mA.carregaArquivoTreinamento2(treinamento, caminho + dataset + "-treinamento" + rod + ".txt", nVE);
        int indice = 0;
        dadosFuzzificados = new String[numObjetos][nVE + 1];
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[nVE];
        String[] resultados = new String[2];
        String[][] metaDados = new String[nVE][100];
        mA.getMetaDados(caminho + dataset + ".names", nVE);
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int i;
        int b;
        for(i = 0; i < numObjetos; ++i) {
            for(int j = 0; j < nVE; ++j) {
                indice = 1;
                if (j != 0) {
                    for(b = 1; b <= j; ++b) {
                        indice += Integer.parseInt(particaoFDT[0][b]);
                    }
                }

                for(b = 1; b <= Integer.parseInt(particaoFDT[0][j + 1]); ++b) {
                    part.clear();
                    int d;
                    if (particaoFDT[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoFDT[indice][d]);
                        }
                    }

                    if (particaoFDT[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoFDT[indice][d]);
                        }
                    }

                    if ((double)treinamento[i][j] != -11111.0D) {
                        float grau = cG.calculaGrauRegra(treinamento[i][j], particaoFDT[indice][0], part);
                        part1.add(grau);
                        part2.add(particaoFDT[indice][1]);
                    }

                    ++indice;
                }

                resultados = this.maior(part1, part2);
                dadosFuzzificados[i][j] = resultados[0];
                grauPertinenciaRegra[j] = Float.parseFloat(resultados[1]);
                part1.clear();
                part2.clear();
            }

            dadosFuzzificados[i][nVE] = "" + this.menorGdP(grauPertinenciaRegra, nVE);
        }

        if (imprimeDF) {
            System.out.println("\n\nDados fuzzificados");

            for(i = 0; i < numObjetos; ++i) {
                String temp = "";

                for(b = 0; b < nVE; ++b) {
                    if (b < nVE - 1) {
                        temp = temp + dadosFuzzificados[i][b] + ",";
                    } else {
                        temp = temp + dadosFuzzificados[i][b];
                    }
                }

                System.out.println(temp);
            }

            System.out.println("terminou");
        }

        mA.gravaArquivoARFFComNumCjtosFuzzyFixo(dadosFuzzificados, caminho, dataset, numObjetos, nVE, numTermos);
    }

    public void converteFDT(String dataset, String caminho, String tp, String arvoreJ48) {
        manipulaArquivos mA = new manipulaArquivos();
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + ".txt");
        String[][] metaDados = new String[nVE][100];
        metaDados = mA.getMetaDados(caminho + dataset + ".names", nVE);
        int numRegras = 0;
        StringTokenizer str = new StringTokenizer(arvoreJ48);
        String temp = arvoreJ48.replace("J48 pruned tree", "");
        arvoreJ48 = temp.replace("------------------", "");
        temp = arvoreJ48.replace("=", "");

        while(str.hasMoreElements()) {
            if (str.nextToken().contains("(")) {
                ++numRegras;
            }
        }

        new StringTokenizer(temp);
        String[] regrasSujas = new String[numRegras];
        StringTokenizer tudo = new StringTokenizer(temp);
        String token = "";
        String lastToken = "";

        for(int aa = 0; aa < numRegras; ++aa) {
            regrasSujas[aa] = "";
            lastToken = token;
            token = tudo.nextToken();

            do {
                if (!token.contains("|")) {
                    regrasSujas[aa] = regrasSujas[aa] + token + "\t";
                    lastToken = token;
                }

                if (token.compareTo("|") == 0 && (lastToken.contains("|") || lastToken.contains(")"))) {
                    regrasSujas[aa] = regrasSujas[aa] + "|\t|\t";
                }

                token = tudo.nextToken();
            } while(!token.contains(")"));
        }

        String[][] regras = new String[numRegras][nVE * 2];

        int aa;
        for(aa = 0; aa < numRegras; ++aa) {
            for(aa = 0; aa < nVE * 2; ++aa) {
                regras[aa][aa] = "dc";
            }
        }

        String regraTemp;
        int b;
        for(aa = 0; aa < numRegras; ++aa) {
            StringTokenizer lin = new StringTokenizer(regrasSujas[aa]);
            b = 0;

            while(lin.hasMoreElements()) {
                regraTemp = lin.nextToken();
                if (regraTemp.compareTo("|") == 0) {
                    regras[aa][b] = regras[aa - 1][b];
                    ++b;
                } else {
                    if (regraTemp.contains(":")) {
                        regraTemp = regraTemp.replace(":", "");
                    }

                    regras[aa][b] = regraTemp;
                    ++b;
                }
            }
        }

        String[][] regrasPadrao = new String[numRegras][nVE * 2 - 1];

        for(aa = 0; aa < numRegras; ++aa) {
            for(aa = 0; aa < nVE * 2 - 1; ++aa) {
                regrasPadrao[aa][aa] = "dc";
            }
        }

        int linha = 0;

        for(aa = 0; aa < numRegras; ++aa) {
            aa = 0;

            for(b = 0; b < nVE * 2 - 1; ++b) {
                if (!regras[aa][b].contains("(") && !regras[aa][b].contains("|")) {
                    regrasPadrao[linha][aa] = regras[aa][b];
                    ++aa;
                }
            }

            ++linha;
        }

        for(aa = 0; aa < numRegras; ++aa) {
            regraTemp = "";

            for(b = 0; b < nVE * 2 - 1; ++b) {
                regraTemp = regraTemp + "\t" + regrasPadrao[aa][b];
            }
        }

        String[] regraS = new String[numRegras];

        for(aa = 0; aa < numRegras; ++aa) {
            regraS[aa] = "";
        }

        for(aa = 0; aa < numRegras; ++aa) {
            for(b = 0; b < nVE * 2 - 1; ++b) {
                String lixo = regrasPadrao[aa][b];
                if (lixo.compareTo("dc") != 0) {
                    regraS[aa] = regraS[aa] + regrasPadrao[aa][b] + "\t";
                } else {
                    b = nVE * 2;
                }
            }
        }

        String[][] var10000 = new String[numRegras][nVE];
        String[][] regrasFinais = this.converteRegrasParaRegrasPadrao(regraS, numRegras, nVE, metaDados);
        mA.gravaBaseRegras(regrasFinais, caminho + "FuzzyDT-Regras-" + dataset + tp + ".txt", numRegras, nVE);
        mA.gravaBRparaUsuario(regrasFinais, caminho + "FuzzyDT-RegrasUser-" + dataset + tp + ".txt", numRegras, nVE, dataset, caminho);
    }

    public void geraFuzzyDecisionTreeAndre(DecisionTree dt, String dataset, String taxaPoda, int numCjtos, String caminho, ArrayList<Exemplo> exemplos) throws Exception {
        J48 j48 = new J48();
        Particoes pt = new Particoes();
        wrapperWM wWM = new wrapperWM();
        manipulaArquivos mA = new manipulaArquivos();
        float precisao;

        wWM.classificaAtribsWMUmFoldAndre(dt, dataset, caminho, 0, exemplos);
//        pt.geraParticaoNumCjtosFuzzyVariavel(dataset, caminho);

        //this.geraFuzzyDT(dataset, 0, caminho);
        //DataSource source = new DataSource(caminho + dataset + "Fuzzy.arff");
//        Instances instances = source.getDataSet();
//        instances.setClassIndex(instances.numAttributes() - 1);
//        j48.buildClassifier(instances);
//        String arvoreJ48 = j48.toString();
//        float[] r = new float[2];
//        r = this.inferenciaAD(dataset, caminho, taxaPoda, arvoreJ48);
//        mA.gravaArvore(arvoreJ48, caminho + dataset + "ArvoreJ48" + 0 + ".txt");
//        precisao = r[0];
//
//
//        float[] res = new float[2];
//        wrapperWM vWM = new wrapperWM();
////        res = vWM.calculaDesvioPadrao(precisao, 10);
//        DecimalFormat mF = new DecimalFormat();
//        mF.applyPattern("########0.00");
//        System.out.println("Error: \tStandard Deviation: ");
//        System.out.println(mF.format((double)(100.0F - 100.0F * res[1])) + "\t" + mF.format((double)res[0]));
    }

    public void geraFuzzyDT(String dataset, int rodada, String caminho) {
        String particao = caminho + "particao" + dataset + ".txt";
        manipulaArquivos mA = new manipulaArquivos();
        //número de "colunas" que um dataset contém, contando também o rótulo
        int nVE = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento" + rodada + ".txt");
        //número de exemplos no dataset original
        int numObjetos = mA.getNumRegrasTreinamento2(caminho + dataset + ".txt");
        int numConjuntos = mA.getNumConjuntos(particao);
        particaoFDT = new String[numConjuntos + 1][nVE + 2];
        mA.carregaParticao(particaoFDT, particao, nVE, numConjuntos);
        treinamento = new float[numObjetos][nVE];
        mA.carregaArquivoTreinamento(treinamento, caminho + dataset + ".txt", nVE);
        dadosFuzzificados = new String[numObjetos][nVE + 1];
        calculaGdCobertura cG = new calculaGdCobertura();
        Vector part = new Vector(1);
        Vector part1 = new Vector(1);
        Vector part2 = new Vector(1);
        boolean flag = true;
        float[] grauPertinenciaRegra = new float[nVE];
        String[] resultados = new String[2];
        String[][] metaDados = new String[nVE][100];
        mA.getMetaDados(caminho + dataset + ".names", nVE);
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");

        int i;
        int b;
        for(i = 0; i < numObjetos; ++i) {
            for(int j = 0; j < nVE; ++j) {
                int indice = 1;
                if (j != 0) {
                    for(b = 1; b <= j; ++b) {
                        indice += Integer.parseInt(particaoFDT[0][b]);
                    }
                }

                for(b = 1; b <= Integer.parseInt(particaoFDT[0][j + 1]); ++b) {
                    part.clear();
                    int d;
                    if (particaoFDT[indice][0].equals("triangular")) {
                        for(d = 2; d < 5; ++d) {
                            part.add(particaoFDT[indice][d]);
                        }
                    }

                    if (particaoFDT[indice][0].equals("trapezoidal")) {
                        for(d = 2; d < 6; ++d) {
                            part.add(particaoFDT[indice][d]);
                        }
                    }

                    if ((double)treinamento[i][j] != -11111.0D) {
                        float grau = cG.calculaGrauRegra(treinamento[i][j], particaoFDT[indice][0], part);
                        part1.add(grau);
                        part2.add(particaoFDT[indice][1]);
                    }

                    ++indice;
                }

                resultados = this.maior(part1, part2);
                dadosFuzzificados[i][j] = resultados[0];
                grauPertinenciaRegra[j] = Float.parseFloat(resultados[1]);
                part1.clear();
                part2.clear();
            }

            dadosFuzzificados[i][nVE] = "" + this.menorGdP(grauPertinenciaRegra, nVE);
        }

        mA.gravaArquivoARFF(dadosFuzzificados, caminho, dataset, numObjetos, nVE);
    }
}

