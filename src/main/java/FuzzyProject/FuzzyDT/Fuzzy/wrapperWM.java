package FuzzyProject.FuzzyDT.Fuzzy;

import FuzzyProject.FuzzyDT.Models.Exemplo;
import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class wrapperWM {
    private String[][] regrasWM;
    private ArrayList<Exemplo> regrasWMAndre;
    private String[][] resumoRegrasWM;
    private static float[][] treinamento;
    private ArrayList<Exemplo> exemplosTreinamento;
    private static float[][] teste;
    private static float[][] testeWM;
    private static float[][] exemplosWM;
    private static String[][] particaoWM;
    private static int numExemplosWM;
    private static String[] treinamentoClassificadoPorSFC;
    private static float[][] matrizRanker;
    private static float[][] matrizRanker2;

    public wrapperWM() {
    }

    public String[][] wrapperWMBestFirst(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho) throws CombinatoricException {
        System.out.println("dominio:" + dominio);
        wangMendell wM = new wangMendell();
        ManipulaArquivos mA = new ManipulaArquivos();
        float fitnessWM = 0.0F;
        int contadorIteracoes = 0;
        int numMaxAtributosDescartados = numVariaveisEntrada - 2;
        String[] atributos = new String[numVariaveisEntrada - 1];
        Vector listaAtributosDescartados = new Vector();

        int numAtributosDescartados;
        for(numAtributosDescartados = 0; numAtributosDescartados < numVariaveisEntrada - 1; ++numAtributosDescartados) {
            atributos[numAtributosDescartados] = Integer.toString(numAtributosDescartados);
        }

        this.resumoRegrasWM = new String[100000][numVariaveisEntrada + 1];
        numAtributosDescartados = 0;
        System.out.println("Combinação número " + contadorIteracoes);
        String arqParticao = "particao" + dominio + ".txt";

        int numRegrasTreinamento;
        int numRegrasTeste;
        int b;
        int a;
        for(b = 0; b < 10; ++b) {
            String arqTreinamento = dominio + "-treinamento" + b + ".txt";
            String arqTeste = dominio + "-teste" + b + ".txt";
            System.out.println("arqTreinamento:" + arqTreinamento);
            numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
            numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
            this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada + 1];
            treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
            teste = new float[numRegrasTeste][numVariaveisEntrada];
            String[] entradasClassificadas = new String[numRegrasTeste];
            wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
            a = wM.wangMendellWrapper(this.regrasWM, exemplosWM, numRegrasTreinamento, numVariaveisEntrada, contadorIteracoes + "BestFirst" + arqTreinamento, particaoWM, "nao", "nao", metodoRaciocinio);
            fitnessWM += this.calculaFitness(entradasClassificadas, a, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
        }

        for(b = 0; b < numVariaveisEntrada - 1; ++b) {
            this.resumoRegrasWM[contadorIteracoes][b] = "1";
        }

        this.resumoRegrasWM[contadorIteracoes][numVariaveisEntrada - 1] = Float.toString(fitnessWM / 10.0F);
        contadorIteracoes = contadorIteracoes + 1;

        while(true) {
            while(numAtributosDescartados < numMaxAtributosDescartados) {
                System.out.println("Num de atribs descartados: " + numAtributosDescartados);

                int numRegrasCriadasWM;
                int contadorAtributos;
                for(Combinations co = new Combinations(atributos, 1); co.hasMoreElements(); ++contadorIteracoes) {
                    Object[] combo = (Object[])((Object[])co.nextElement());
                    fitnessWM = 0.0F;
                    System.out.println("Combinação número " + contadorIteracoes);

                    for(contadorAtributos = 0; contadorAtributos < 10; ++contadorAtributos) {
                        String arqTreinamento = dominio + "-treinamento" + contadorAtributos + ".txt";
                        String arqTeste = dominio + "-teste" + contadorAtributos + ".txt";
                        numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
                        numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
                        this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada + 1];
                        treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
                        teste = new float[numRegrasTeste][numVariaveisEntrada];
                        String[] entradasClassificadas = new String[numRegrasTeste];

//                        int b;
                        for(numRegrasCriadasWM = 0; numRegrasCriadasWM < numRegrasTeste; ++numRegrasCriadasWM) {
                            for(b = 0; b < numVariaveisEntrada; ++b) {
                                teste[numRegrasCriadasWM][b] = testeWM[numRegrasCriadasWM][b];
                            }
                        }

                        wM.classificaEntradas(entradasClassificadas, teste, numRegrasTeste, numVariaveisEntrada, particaoWM);

                        for(numRegrasCriadasWM = 0; numRegrasCriadasWM < numRegrasTreinamento; ++numRegrasCriadasWM) {
                            for(b = 0; b < numVariaveisEntrada; ++b) {
                                treinamento[numRegrasCriadasWM][b] = exemplosWM[numRegrasCriadasWM][b];
                            }
                        }

                        int indiceAtributoRemovidoDasCombinacoesPassadas;
                        int j;
                        String atributoRemovidoI;
                        for(numRegrasCriadasWM = 0; numRegrasCriadasWM < combo.length; ++numRegrasCriadasWM) {
                            atributoRemovidoI = (String)combo[numRegrasCriadasWM];
                            indiceAtributoRemovidoDasCombinacoesPassadas = Integer.parseInt(atributoRemovidoI);

                            for(j = 0; j < numRegrasTreinamento; ++j) {
                                treinamento[j][indiceAtributoRemovidoDasCombinacoesPassadas] = -11111.0F;
                            }
                        }

                        for(numRegrasCriadasWM = 0; numRegrasCriadasWM < listaAtributosDescartados.size(); ++numRegrasCriadasWM) {
                            atributoRemovidoI = "" + listaAtributosDescartados.get(numRegrasCriadasWM);
                            indiceAtributoRemovidoDasCombinacoesPassadas = Integer.parseInt(atributoRemovidoI);

                            for(j = 0; j < numRegrasTreinamento; ++j) {
                                treinamento[j][indiceAtributoRemovidoDasCombinacoesPassadas] = -11111.0F;
                            }
                        }

                        numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, treinamento, numRegrasTreinamento, numVariaveisEntrada, contadorIteracoes + "BestFirst" + arqTreinamento, particaoWM, "nao", "não", metodoRaciocinio);
                        fitnessWM += this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, teste, this.regrasWM, numRegrasTeste);
                    }

                    for(contadorAtributos = 0; contadorAtributos < numVariaveisEntrada - 1; ++contadorAtributos) {
                        if (this.regrasWM[0][contadorAtributos].compareTo("dc") == 0) {
                            this.resumoRegrasWM[contadorIteracoes][contadorAtributos] = "0";
                        } else {
                            this.resumoRegrasWM[contadorIteracoes][contadorAtributos] = "1";
                        }
                    }

                    this.resumoRegrasWM[contadorIteracoes][numVariaveisEntrada - 1] = Float.toString(fitnessWM / 10.0F);
                }

                ++numAtributosDescartados;
                int atributoDescartadoNestaIteracao = piorAtributo(this.resumoRegrasWM, listaAtributosDescartados, contadorIteracoes, contadorIteracoes, numVariaveisEntrada);
                if (atributoDescartadoNestaIteracao != -1) {
                    listaAtributosDescartados.add(atributoDescartadoNestaIteracao);
                    atributos = new String[numVariaveisEntrada - 1 - listaAtributosDescartados.size()];
                    contadorAtributos = 0;
                    int lixo = 0;

                    for(a = 0; a < numVariaveisEntrada - 1; ++a) {
                        boolean flag = false;

                        for(numRegrasCriadasWM = 0; numRegrasCriadasWM < listaAtributosDescartados.size(); ++numRegrasCriadasWM) {
                            lixo = Integer.parseInt(listaAtributosDescartados.get(numRegrasCriadasWM) + "");
                        }

                        if (a == lixo) {
                            flag = true;
                        }

                        if (!flag) {
                            atributos[contadorAtributos] = Integer.toString(a);
                            ++contadorAtributos;
                        }
                    }
                } else {
                    numAtributosDescartados = numMaxAtributosDescartados;
                }
            }

            mA.gravaArquivo(this.resumoRegrasWM, "ResumoRegrasWMBestFirst-" + dominio + ".txt", contadorIteracoes, numVariaveisEntrada);
            return this.resumoRegrasWM;
        }
    }

    public void wrapperWMForcaBruta(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        ManipulaArquivos mA = new ManipulaArquivos();
        int contadorIteracoes = 0;
        int numMaxAtributosDescartados = numVariaveisEntrada - 2;
        String[] atributos = new String[numVariaveisEntrada - 1];
        Vector atributosDescartados = new Vector();

        int numCombinacoesPossiveis;
        for(numCombinacoesPossiveis = 0; numCombinacoesPossiveis < numVariaveisEntrada - 1; ++numCombinacoesPossiveis) {
            atributos[numCombinacoesPossiveis] = Integer.toString(numCombinacoesPossiveis);
        }

        numCombinacoesPossiveis = (int)Math.pow(2.0D, (double)(numVariaveisEntrada - 1));
        this.resumoRegrasWM = new String[numCombinacoesPossiveis][numVariaveisEntrada];

        for(int numAtributosDescartados = 1; numAtributosDescartados < numMaxAtributosDescartados; ++numAtributosDescartados) {
            Combinations co = new Combinations(atributos, numAtributosDescartados);

            while(co.hasMoreElements()) {
                String arqParticao = "particao" + dominio + "3.txt";
                Object[] combo = (Object[])((Object[])co.nextElement());
                float fitnessWM = 0.0F;

                int b;
                for(b = 0; b < 10; ++b) {
                    String conjuntos = "3";
                    String dataSet = dominio + ".txt";
                    String arqTreinamento = dominio + "-treinamento" + b + ".txt";
                    String arqTeste = dominio + "-teste" + b + ".txt";
                    int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
                    int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
                    this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada + 1];
                    treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
                    teste = new float[numRegrasTeste][numVariaveisEntrada];
                    String[] entradasClassificadas = new String[numRegrasTeste];

                    int numRegrasCriadasWM;
//                    int b;
                    for(numRegrasCriadasWM = 0; numRegrasCriadasWM < numRegrasTeste; ++numRegrasCriadasWM) {
                        for(b = 0; b < numVariaveisEntrada; ++b) {
                            teste[numRegrasCriadasWM][b] = testeWM[numRegrasCriadasWM][b];
                        }
                    }

                    wM.classificaEntradas(entradasClassificadas, teste, numRegrasTeste, numVariaveisEntrada, particaoWM);

                    for(numRegrasCriadasWM = 0; numRegrasCriadasWM < numRegrasTreinamento; ++numRegrasCriadasWM) {
                        for(b = 0; b < numVariaveisEntrada; ++b) {
                            treinamento[numRegrasCriadasWM][b] = exemplosWM[numRegrasCriadasWM][b];
                        }
                    }

                    int indiceAtributoRemovidoDaCombinacaoAtual;
                    int j;
                    String atributoRemovidoDaCombinacaoAtual;
                    for(numRegrasCriadasWM = 0; numRegrasCriadasWM < combo.length; ++numRegrasCriadasWM) {
                        atributoRemovidoDaCombinacaoAtual = (String)combo[numRegrasCriadasWM];
                        indiceAtributoRemovidoDaCombinacaoAtual = Integer.parseInt(atributoRemovidoDaCombinacaoAtual);

                        for(j = 0; j < numRegrasTreinamento; ++j) {
                            treinamento[j][indiceAtributoRemovidoDaCombinacaoAtual] = -11111.0F;
                        }
                    }

                    for(numRegrasCriadasWM = 0; numRegrasCriadasWM < combo.length; ++numRegrasCriadasWM) {
                        atributoRemovidoDaCombinacaoAtual = (String)combo[numRegrasCriadasWM];
                        indiceAtributoRemovidoDaCombinacaoAtual = Integer.parseInt(atributoRemovidoDaCombinacaoAtual);

                        for(j = 0; j < numRegrasTeste; ++j) {
                            teste[j][indiceAtributoRemovidoDaCombinacaoAtual] = -11111.0F;
                        }
                    }

                    for(numRegrasCriadasWM = 0; numRegrasCriadasWM < atributosDescartados.size(); ++numRegrasCriadasWM) {
                        atributoRemovidoDaCombinacaoAtual = (String)atributosDescartados.get(numRegrasCriadasWM);
                        indiceAtributoRemovidoDaCombinacaoAtual = Integer.parseInt(atributoRemovidoDaCombinacaoAtual);

                        for(j = 0; j < numRegrasTeste; ++j) {
                            teste[j][indiceAtributoRemovidoDaCombinacaoAtual] = -11111.0F;
                        }
                    }

                    numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, treinamento, numRegrasTreinamento, numVariaveisEntrada, contadorIteracoes + "ForcaBruta" + arqTreinamento, particaoWM, "nao", "nao", metodoRaciocinio);
                    fitnessWM += this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, teste, this.regrasWM, numRegrasTeste);
                }

                for(b = 0; b < numVariaveisEntrada - 1; ++b) {
                    if (this.regrasWM[0][b].compareTo("dc") == 0) {
                        this.resumoRegrasWM[contadorIteracoes][b] = "0";
                    } else {
                        this.resumoRegrasWM[contadorIteracoes][b] = "1";
                    }
                }

                this.resumoRegrasWM[contadorIteracoes][numVariaveisEntrada - 1] = Float.toString(fitnessWM / 10.0F);
                ++contadorIteracoes;
                System.out.println("Combinação número " + contadorIteracoes);
            }
        }

        mA.gravaArquivo(this.resumoRegrasWM, "ResumoRegrasWMForcaBruta" + dominio + metodoRaciocinio + ".txt", contadorIteracoes, numVariaveisEntrada);
        System.out.println("Terminou!");
    }

    public void rodaWM(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho, Vector listaAtribs) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        float fitnessWM = 0.0F;
        float fitnessWM1 = 0.0F;
        int indice = 0;
        Vector listaAtributosDescartados = new Vector();
        System.out.println("Atributos usados: " + listaAtribs.toString());

        int c;
        for(int a = 0; a < numVariaveisEntrada - 1; ++a) {
            if (indice < listaAtribs.size()) {
                String temp = listaAtribs.get(indice) + "";
                c = Integer.parseInt(temp);
                if (c == a) {
                    ++indice;
                } else {
                    listaAtributosDescartados.add(a);
                }
            } else {
                listaAtributosDescartados.add(a);
            }
        }

        System.out.println("Atributos descartados: " + listaAtributosDescartados.toString());
        float[] precisao = new float[10];
        float[] numregras = new float[10];

        for(c = 0; c < 10; ++c) {
            String arqTreinamento = dominio + "-treinamento" + c + ".txt";
            String arqTeste = dominio + "-teste" + c + ".txt";
            String arqParticao = "particao" + dominio + ".txt";
            int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
            int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
            this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada];

            int i;
            String atributoRemovidoI;
            int indiceAtributoRemovidoDaCombinacaoAtual;
            int j;
            for(i = 0; i < listaAtributosDescartados.size(); ++i) {
                atributoRemovidoI = "" + listaAtributosDescartados.get(i);
                indiceAtributoRemovidoDaCombinacaoAtual = Integer.parseInt(atributoRemovidoI);

                for(j = 0; j < numRegrasTeste; ++j) {
                    testeWM[j][indiceAtributoRemovidoDaCombinacaoAtual] = -11111.0F;
                }
            }

            for(i = 0; i < listaAtributosDescartados.size(); ++i) {
                atributoRemovidoI = "" + listaAtributosDescartados.get(i);
                indiceAtributoRemovidoDaCombinacaoAtual = Integer.parseInt(atributoRemovidoI);

                for(j = 0; j < numRegrasTreinamento; ++j) {
                    exemplosWM[j][indiceAtributoRemovidoDaCombinacaoAtual] = -11111.0F;
                }
            }

            String[] entradasClassificadas = new String[numRegrasTeste];
            wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
            int numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, exemplosWM, numRegrasTreinamento, numVariaveisEntrada, "RemocaoAtribs" + metodoRaciocinio + arqTreinamento, particaoWM, "nao", "sim", metodoRaciocinio);
            numregras[c] = (float)numRegrasCriadasWM;
            precisao[c] = this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
            fitnessWM += fitnessWM1;
        }

        float[] resultados = new float[2];
        resultados = this.calculaDesvioPadrao(precisao, 10);
        float[] estatiRegras = new float[2];
        estatiRegras = this.calculaDesvioPadrao(numregras, 10);
        System.out.println(1.0F - resultados[1] + "\t" + resultados[0] + "\t" + estatiRegras[1] + "\t" + estatiRegras[0]);
    }

    private static int piorAtributo(String[][] regras, Vector atributosDescartados, int primeiraRegra, int numRegras, int numAtribs) {
        float TCC = 0.0F;
        int posicaoPiorAtributo = -1;
        int melhorBR = -2;
        boolean flag = false;

        int b;
        for(b = primeiraRegra; b < numRegras; ++b) {
            float temp = Float.parseFloat(regras[b][numAtribs - 1]);
            if (temp >= TCC) {
                melhorBR = b;
                TCC = Float.parseFloat(regras[b][numAtribs - 1]);
            }
        }

        for(b = 0; b < numAtribs - 1; ++b) {
            if (regras[melhorBR][b].compareTo("0") == 0) {
                if (atributosDescartados.size() == 0) {
                    posicaoPiorAtributo = b;
                    b = numAtribs;
                }

                if (b < numAtribs) {
                    for(int c = 0; c < atributosDescartados.size(); ++c) {
                        int elemParaComparacao = (Integer)atributosDescartados.get(c);
                        if (elemParaComparacao == b) {
                            flag = true;
                            c = atributosDescartados.size();
                        }
                    }

                    if (!flag) {
                        posicaoPiorAtributo = b;
                        b = numAtribs;
                    }
                }
            }

            if (flag) {
                flag = false;
            }
        }

        return posicaoPiorAtributo;
    }

    private static int carregaArquivosWM(String arqTreinamento, String arqParticao, int numVariaveisEntrada, String caminho) {
        ManipulaArquivos mA = new ManipulaArquivos();
        numExemplosWM = mA.getNumRegrasTreinamento2(caminho + arqTreinamento);
        exemplosWM = new float[numExemplosWM][numVariaveisEntrada];
        mA.carregaTreinamento(exemplosWM, caminho + arqTreinamento, numVariaveisEntrada, numExemplosWM);
        int numConjuntos = mA.getNumConjuntos(caminho + arqParticao);
        particaoWM = new String[numConjuntos + 1][numVariaveisEntrada + 2];
        mA.carregaParticao(particaoWM, caminho + arqParticao, numVariaveisEntrada, numConjuntos);
        return numExemplosWM;
    }

    private static int carregaTreino(String arqTreinamento, int numVariaveisEntrada, String caminho) {
        ManipulaArquivos mA = new ManipulaArquivos();
        numExemplosWM = mA.getNumRegrasTreinamento2(caminho + arqTreinamento);
        exemplosWM = new float[numExemplosWM][numVariaveisEntrada];
        mA.carregaTreinamento(exemplosWM, caminho + arqTreinamento, numVariaveisEntrada, numExemplosWM);
        return numExemplosWM;
    }

    private static int carregaParticao(String arqParticao, int numVariaveisEntrada, String caminho) {
        ManipulaArquivos mA = new ManipulaArquivos();
        int numConjuntos = mA.getNumConjuntos(caminho + arqParticao);
        particaoWM = new String[numConjuntos + 1][numVariaveisEntrada + 2];
        mA.carregaParticao(particaoWM, caminho + arqParticao, numVariaveisEntrada, numConjuntos);
        return numExemplosWM;
    }

    private static int carregaArquivoTeste(String arqTeste, int numVariaveisEntrada, String caminho) {
        ManipulaArquivos mA = new ManipulaArquivos();
        int numTestes = mA.getNumRegrasTreinamento2(caminho + arqTeste);
        testeWM = new float[numTestes][numVariaveisEntrada];
        mA.carregaTreinamento(testeWM, caminho + arqTeste, numVariaveisEntrada, numTestes);
        return numTestes;
    }

    public float calculaFitness(String[] entradasClassificadas, int numRegrasWM, int numVariaveisEntrada, String[][] particaoWM, String metodoRaciocinio, float[][] treinamentoWM, String[][] regrasWM, int numExemplosTreinamento) {
        float fitness = 0.0F;
        Vector padrao = new Vector();
        treinamentoClassificadoPorSFC = new String[numExemplosTreinamento];
        sistemaFuzzyCalculos sFC = new sistemaFuzzyCalculos();
        int numerodeClasses = Integer.parseInt(particaoWM[0][numVariaveisEntrada]);

        int a;
        for(a = 0; a < numExemplosTreinamento; ++a) {
            padrao.clear();

            for(int b = 0; b < numVariaveisEntrada; ++b) {
                padrao.add(treinamentoWM[a][b]);
            }

            if (metodoRaciocinio.compareTo("classico") == 0) {
                treinamentoClassificadoPorSFC[a] = sFC.sistemaFuzzyCalculos(numVariaveisEntrada, regrasWM, numRegrasWM, padrao, particaoWM);
            } else if (metodoRaciocinio.compareTo("geral") == 0) {
                treinamentoClassificadoPorSFC[a] = sFC.geral(numVariaveisEntrada, regrasWM, numRegrasWM, padrao, particaoWM, numerodeClasses);
            } else {
                System.out.println("Método de raciocíonio inválido.");
            }
        }

        for(a = 0; a < numExemplosTreinamento; ++a) {
            if (entradasClassificadas[a].compareTo(treinamentoClassificadoPorSFC[a]) == 0) {
                ++fitness;
            }
        }

        float fitnessF = fitness / (float)numExemplosTreinamento;
        return fitnessF;
    }

    public void rodaWangMendel(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        float[] numRegrasGeradas = new float[10];
        String arqParticao = "particao" + dominio + ".txt";
        float[] fitnessWM = new float[10];

        for(int c = 0; c < 10; ++c) {
            String arqTreinamento = dominio + "-treinamento" + c + ".txt";
            String arqTeste = dominio + "-teste" + c + ".txt";
            int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
            int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
            this.regrasWM = new String[numRegrasTreinamento * 2][numVariaveisEntrada];
            String[] entradasClassificadas = new String[numRegrasTeste];
            wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
            int numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, exemplosWM, numRegrasTreinamento, numVariaveisEntrada, arqTreinamento, particaoWM, "nao", "sim", metodoRaciocinio);
            fitnessWM[c] = this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
            numRegrasGeradas[c] = (float)numRegrasCriadasWM;
        }

        float[] resultados = new float[2];
        resultados = this.calculaDesvioPadrao(fitnessWM, 10);
        float[] estatiRegras = new float[2];
        estatiRegras = this.calculaDesvioPadrao(numRegrasGeradas, 10);
        System.out.println(1.0F - resultados[1] + "\t" + resultados[0] + "\t" + estatiRegras[1] + "\t" + estatiRegras[0]);
    }

    public float[] rodaWM(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        float[] numRegrasGeradas = new float[10];
        float[] dados = new float[2];
        String arqParticao = "particao" + dominio + ".txt";
        float[] fitnessWM = new float[10];

        for(int c = 0; c < 10; ++c) {
            String arqTreinamento = dominio + "-treinamento" + c + ".txt";
            String arqTeste = dominio + "-teste" + c + ".txt";
            int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
            int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
            this.regrasWM = new String[numRegrasTreinamento * 2][numVariaveisEntrada];
            String[] entradasClassificadas = new String[numRegrasTeste];
            wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
            int numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, exemplosWM, numRegrasTreinamento, numVariaveisEntrada, arqTreinamento, particaoWM, "nao", "sim", metodoRaciocinio);
            fitnessWM[c] = this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
            numRegrasGeradas[c] = (float)numRegrasCriadasWM;
        }

        float[] resultados = new float[2];
        resultados = this.calculaDesvioPadrao(fitnessWM, 10);
        float[] estatiRegras = new float[2];
        estatiRegras = this.calculaDesvioPadrao(numRegrasGeradas, 10);
        dados[0] = 1.0F - resultados[1];
        dados[1] = estatiRegras[1];
        return dados;
    }

    public float[] calculaDesvioPadrao(float[] fitness, int numValores) {
        float media = 0.0F;
        float variancia = 0.0F;
        float desvioPadrao = 0.0F;

        for(int a = 0; a < numValores; ++a) {
            media += fitness[a];
        }

        media /= (float)numValores;
        float[] desvioM = new float[numValores];

        for(int a = 0; a < numValores; ++a) {
            desvioM[a] = fitness[a] - media;
            variancia = (float)((double)variancia + Math.pow((double)desvioM[a], 2.0D));
        }

        variancia /= (float)numValores;
        desvioPadrao = (float)Math.sqrt((double)variancia);
        float[] results = new float[]{desvioPadrao, media};
        return results;
    }

    public float[] rodaWangMendel2(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        float[] numRegrasGeradas = new float[10];
        float[] results = new float[4];
        String arqParticao = "particao" + dominio + ".txt";
        float[] fitnessWM = new float[10];

        for(int c = 0; c < 10; ++c) {
            String arqTreinamento = dominio + "-treinamento" + c + ".txt";
            String arqTeste = dominio + "-teste" + c + ".txt";
            int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
            int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
            this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada];
            String[] entradasClassificadas = new String[numRegrasTeste];
            wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
            int numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, exemplosWM, numRegrasTreinamento, numVariaveisEntrada, arqTreinamento, particaoWM, "nao", "nao", metodoRaciocinio);
            fitnessWM[c] = this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
            numRegrasGeradas[c] = (float)numRegrasCriadasWM;
        }

        float[] resultados = new float[2];
        resultados = this.calculaDesvioPadrao(fitnessWM, 10);
        float[] estatiRegras = new float[2];
        estatiRegras = this.calculaDesvioPadrao(numRegrasGeradas, 10);
        results[0] = 1.0F - resultados[1];
        results[1] = resultados[0];
        results[2] = estatiRegras[0];
        results[3] = estatiRegras[1];
        return results;
    }

    public void classificaAtribsWM(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        ManipulaArquivos mA = new ManipulaArquivos();
        float fitnessWM = 0.0F;
        double diferenca = 0.05D;
        float[][] erros = new float[9][numVariaveisEntrada - 1];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");
        int numMaxFuzzySets = 9;
        String arqParticao = "particao" + dominio + ".txt";
        String arqMetaDados = dominio + ".names";
        String[][] metaDados = new String[numVariaveisEntrada][100];
        metaDados = mA.getMetaDados(caminho + arqMetaDados, numVariaveisEntrada);
        int[] tipoAtribs = new int[numVariaveisEntrada - 1];

        int atribAtual;
        int x;
        int y;
        for(atribAtual = 0; atribAtual < numVariaveisEntrada - 1; ++atribAtual) {
            if (metaDados[atribAtual][1].compareTo("double") != 0 && metaDados[atribAtual][1].compareTo("integer") != 0) {
                x = 0;

                for(y = 1; y < 100; ++y) {
                    if (metaDados[atribAtual][y] != null) {
                        ++x;
                    }
                }

                tipoAtribs[atribAtual] = x;
            } else {
                tipoAtribs[atribAtual] = 0;
            }
        }

        String line;
        for(atribAtual = 0; atribAtual < numVariaveisEntrada - 1; ++atribAtual) {
            if (tipoAtribs[atribAtual] == 0) {
                for(x = 2; x < numMaxFuzzySets; ++x) {
                    Particoes Pa = new Particoes();
                    Pa.geraParticao(dominio, x, caminho);
                    fitnessWM = 0.0F;

                    for(x = 0; x < 10; ++x) {
                        String arqTreinamento = dominio + "-treinamento" + x + ".txt";
                        line = dominio + "-teste" + x + ".txt";
                        int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
                        int numRegrasTeste = carregaArquivoTeste(line, numVariaveisEntrada, caminho);
                        this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada];
                        treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
                        numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
                        String[] entradasClassificadas = new String[numRegrasTeste];
                        wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);

                        int i;
                        for(i = 0; i < numRegrasTreinamento; ++i) {
                            for(int j = 0; j < numVariaveisEntrada; ++j) {
                                if (j == atribAtual) {
                                    treinamento[i][j] = exemplosWM[i][j];
                                } else if (j == numVariaveisEntrada - 1) {
                                    treinamento[i][j] = exemplosWM[i][j];
                                } else {
                                    treinamento[i][j] = -11111.0F;
                                }
                            }
                        }

                        i = wM.wangMendellWrapper(this.regrasWM, treinamento, numRegrasTreinamento, numVariaveisEntrada, dominio + x + ".txt", particaoWM, "nao", "sim", metodoRaciocinio);
                        fitnessWM += this.calculaFitness(entradasClassificadas, i, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
                    }

                    erros[x - 2][atribAtual] = fitnessWM / 10.0F;
                }
            }
        }

        String lixo = "FSets\t";

        for(x = 0; x < numVariaveisEntrada - 1; ++x) {
            lixo = lixo + (x + 1) + "\t";
        }

        System.out.println(lixo);
        lixo = "";

        for(x = 0; x < numVariaveisEntrada; ++x) {
            lixo = lixo + "========";
        }

        System.out.println(lixo);

        for(x = 0; x < numMaxFuzzySets - 2; ++x) {
            lixo = x + 2 + "\t";

            for(y = 0; y < numVariaveisEntrada - 1; ++y) {
                if (tipoAtribs[y] == 0) {
                    lixo = lixo + formatador.format((double)erros[x][y]) + "\t";
                } else {
                    lixo = lixo + "xxx\t";
                }
            }

            System.out.println(lixo);
        }

        String[] resultados = new String[numVariaveisEntrada - 1];

        for(x = 0; x < numVariaveisEntrada - 1; ++x) {
            int indice = 0;
            if (tipoAtribs[x] != 0) {
                resultados[x] = tipoAtribs[x] + "";
            } else {
                float maior = 0.0F;

//                int y;
                double result;
                for(y = 0; y < numMaxFuzzySets - 1; ++y) {
                    result = (double)erros[y][x];
                    if (result > (double)maior) {
                        maior = erros[y][x];
                    }
                }

                for(y = numMaxFuzzySets - 1; y >= 0; --y) {
                    result = (double)erros[y][x];
                    if (result > (double)maior - diferenca) {
                        indice = y;
                    }
                }

                resultados[x] = indice + 2 + "";
            }

            System.out.print("\t" + resultados[x]);
        }

        System.out.println();
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + dominio + "DadosParticao.txt");
            buf_writer = new BufferedWriter(writer);
            line = "";

            for(int i = 0; i < numVariaveisEntrada - 1; ++i) {
                line = line + resultados[i] + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var28) {
            System.err.println(var28);
            System.exit(1);
        }

    }

    void rodaWMVariosDatasets(String caminho) throws CombinatoricException {
        ManipulaArquivos mA = new ManipulaArquivos();
        int numDominios = 10;
        String[] dominio = new String[numDominios];
        dominio[0] = "credit";
        dominio[1] = "cylinder";
        dominio[2] = "dermatology";
        dominio[3] = "diabetes";
        dominio[4] = "glass";
        dominio[5] = "heart";
        dominio[6] = "ionosphere";
        dominio[7] = "segmentation";
        dominio[8] = "vehicle";
        dominio[9] = "wine";
        int[] numVariaveisEntrada2 = new int[numDominios];
        String[] metodoRaciocinio = new String[]{"classico", "geral"};

        for(int a = 0; a < numDominios; ++a) {
            caminho = "c:/Users/MECintra/MARCOS/SFuzzySemIG/datasets/" + dominio[a] + "/";
            numVariaveisEntrada2[a] = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dominio[a] + "-treinamento0.txt");
            this.rodaWangMendel(dominio[a], numVariaveisEntrada2[a], metodoRaciocinio[0], caminho);
        }

    }

    void calculaFitnessCadaAtribComWM(String caminho, String[] datasets) throws CombinatoricException {
        ManipulaArquivos mA = new ManipulaArquivos();
        int numDatasets = datasets.length;
        int[] numVariaveisEntrada2 = new int[4];
        String[] metodoRaciocinio = new String[]{"classico", "geral"};

        for(int a = 0; a < numDatasets; ++a) {
            caminho = "c:/MARCOS/SFuzzySemIG/datasets/" + datasets[a] + "/";
            System.out.println("\n" + datasets[a].toUpperCase());
            numVariaveisEntrada2[0] = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + datasets[a] + "-treinamento0.txt");
            this.classificaAtribsWM(datasets[a], numVariaveisEntrada2[0], metodoRaciocinio[0], caminho);
        }

    }

    String rodaWrapperBestFirst(String caminho, String dominio) throws CombinatoricException {
        ManipulaArquivos mA = new ManipulaArquivos();
        String atribs = "";
        String[] metodoRaciocinio = new String[]{"classico", "geral"};

        for(int a = 0; a < 1; ++a) {
            int numVarEnt = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dominio + "-treinamento0.txt");
            String[][] resumoRegrasWM = new String[100000][numVarEnt + 1];
            System.out.println("nve: " + numVarEnt);
            resumoRegrasWM = this.wrapperWMBestFirst(dominio, numVarEnt, metodoRaciocinio[0], caminho);
            atribs = this.defineAtributosSelecinados(caminho, dominio, resumoRegrasWM, numVarEnt);
        }

        return atribs;
    }

    void rodaWMcomSelecaoDeAtributos(String caminho) throws CombinatoricException {
        ManipulaArquivos mA = new ManipulaArquivos();
        String dataset = "iris";
        caminho = caminho + dataset + "/";
        int numVars = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento0.txt");
        System.out.println("Wang Mendell classico - " + dataset);
        Vector atributos = new Vector();
        String lista = "1 2 4 5 6 7 8 9 10 11 13 14 15 17 18 19 21 22 23 24 29 30 31 32";
        StringTokenizer elementos = new StringTokenizer(lista);

        while(elementos.hasMoreElements()) {
            String elem = elementos.nextElement() + "";
            int indice = Integer.parseInt(elem) - 1;
            atributos.add(indice);
        }

        this.rodaWM(dataset, numVars, "classico", caminho, atributos);
    }

    public static void ranker() {
        ManipulaArquivos mA = new ManipulaArquivos();
        String arquivoDeRankings = "rankwine.txt";
        System.out.println("Arquivo de entrada: " + arquivoDeRankings);
        int numColunas = mA.getNumeroVariaveisEntradaArqTreinamento2(arquivoDeRankings);
        System.out.println("Num de colunas: " + numColunas);
        int numLinhas = mA.getNumRegrasTreinamento2(arquivoDeRankings);
        System.out.println("Num de linhas: " + numLinhas);
        matrizRanker = new float[numLinhas][numColunas];
        matrizRanker2 = new float[numLinhas + 1][numColunas];
        mA.carregaExemplosAG(matrizRanker, arquivoDeRankings, numColunas, numLinhas);
        System.out.println("Matriz original:");

        int b;
//        int b;
        for(b = 0; b < numLinhas; ++b) {
            for(b = 0; b < numColunas; ++b) {
                System.out.print(matrizRanker[b][b] + " ");
            }

            System.out.println();
        }

        System.out.println();

        for(b = 0; b < numLinhas; ++b) {
            for(b = 0; b < numColunas; ++b) {
                int temp = (int)matrizRanker[b][b] - 1;
                matrizRanker2[b][temp] = (float)(b + 1);
            }
        }

        System.out.println("Matriz com rankings:");

        for(b = 0; b < numLinhas; ++b) {
            for(b = 0; b < numColunas; ++b) {
                System.out.print(matrizRanker2[b][b] + " ");
            }

            System.out.println();
        }

        for(b = 0; b < numColunas; ++b) {
            float soma = 0.0F;

            for(b = 0; b < numLinhas; ++b) {
                soma += matrizRanker2[b][b];
            }

            matrizRanker2[numLinhas][b] = soma / (float)numLinhas;
        }

        System.out.println("Média dos rankings");

        for(b = 0; b < numColunas; ++b) {
            System.out.print(matrizRanker2[numLinhas][b] + " ");
        }

        System.out.println();
        float[] vetor = new float[numColunas];
        float[] vetor2 = new float[numColunas];
        int[] vetor3 = new int[numColunas];

//        int b;
//        int b;
        float aux;
        for(b = 0; b < numColunas; ++b) {
            for(b = b + 1; b < numColunas; ++b) {
                if (matrizRanker2[numLinhas][b] == matrizRanker2[numLinhas][b]) {
                    aux = (float)Math.random();
                    if ((double)aux < 0.5D) {
                        matrizRanker2[numLinhas][b] += aux;
                    }

                    if ((double)aux >= 0.5D) {
                        matrizRanker2[numLinhas][b] += aux;
                    }
                }
            }
        }

        System.out.println();

        for(b = 0; b < numColunas; ++b) {
            vetor[b] = matrizRanker2[numLinhas][b];
            System.out.print(vetor[b] + "\t");
        }

        System.out.println();

        for(b = 0; b < numColunas; ++b) {
            for(b = b + 1; b < numColunas; ++b) {
                if (vetor[b] > vetor[b]) {
                    aux = vetor[b];
                    vetor[b] = vetor[b];
                    vetor[b] = aux;
                }
            }
        }

        System.out.println("Valores ordenados em ordem crescente");

        for(b = 0; b < numColunas; ++b) {
            System.out.print(vetor[b] + "\t");
        }

        System.out.println();

        for(b = 0; b < numColunas; ++b) {
            for(b = 0; b < numColunas; ++b) {
                if (vetor[b] == matrizRanker2[numLinhas][b]) {
                    vetor2[b] = (float)(b + 1);
                    b = numColunas;
                }
            }
        }

        for(b = 0; b < numColunas; ++b) {
            vetor3[b] = (int)vetor2[b];
        }

        System.out.println("Ranqueamento final");

        for(b = 0; b < numColunas; ++b) {
            System.out.print(vetor3[b] + " ");
        }

        System.out.println("FIM !");
    }

    public float[] rodaWMUmTreino(String dominio, int numVariaveisEntrada, String metodoRaciocinio, String caminho, int arqTreino) throws CombinatoricException {
        wangMendell wM = new wangMendell();
        float[] dados = new float[2];
        String arqParticao = "particao" + dominio + ".txt";
        String arqTreinamento = dominio + "-treinamento" + arqTreino + ".txt";
        String arqTeste = dominio + "-teste" + arqTreino + ".txt";
        int numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, caminho);
        int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
        this.regrasWM = new String[numRegrasTreinamento * 2][numVariaveisEntrada];
        String[] entradasClassificadas = new String[numRegrasTeste];
        wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
        int numRegrasCriadasWM = wM.wangMendellWrapper(this.regrasWM, exemplosWM, numRegrasTreinamento, numVariaveisEntrada, arqTreinamento, particaoWM, "nao", "nao", metodoRaciocinio);
        dados[0] = this.calculaFitness(entradasClassificadas, numRegrasCriadasWM, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
        dados[1] = (float)numRegrasCriadasWM;
        return dados;
    }

    public void classificaPorWM(String dominio, String caminho, int fold) throws CombinatoricException {
        String metodoRaciocinio = "classico";
        wangMendell wM = new wangMendell();
        ManipulaArquivos mA = new ManipulaArquivos();
        float fitnessWM = 0.0F;
        double diferenca = 0.05D;
        System.out.println("ok inté qui");
        int numVariaveisEntrada = mA.getNumeroVariaveisEntradaArqTreinamento3(caminho, dominio + ".txt");
        System.out.println("Número de variáveis do dataset: " + numVariaveisEntrada);
        float[][] erros = new float[9][numVariaveisEntrada - 1];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");
        int numMaxFuzzySets = 9;
        String arqParticao = "particao" + dominio + ".txt";
        String arqMetaDados = dominio + ".names";
        String[][] metaDados = new String[numVariaveisEntrada][100];
        metaDados = mA.getMetaDados(caminho + arqMetaDados, numVariaveisEntrada);
        int[] tipoAtribs = new int[numVariaveisEntrada - 1];

        for(int m = 0; m < numVariaveisEntrada - 1; ++m) {
            if (metaDados[m][1].compareTo("double") != 0 && metaDados[m][1].compareTo("integer") != 0) {
                int temp = 0;

                for(int n = 1; n < 100; ++n) {
                    if (metaDados[m][n] != null) {
                        ++temp;
                    }
                }

                tipoAtribs[m] = temp;
            } else {
                tipoAtribs[m] = 0;
            }
        }

        String arqTreinamento = dominio + ".txt";
        String arqTeste = dominio + ".txt";
        int numRegrasTreinamento = carregaTreino(arqTreinamento, numVariaveisEntrada, caminho);
        int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, caminho);
        this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada];
        treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
        numRegrasTreinamento = carregaTreino(arqTreinamento, numVariaveisEntrada, caminho);
        System.out.println("Número de exemplos no arquivo de dados: " + numExemplosWM);
        String[] entradasClassificadas = new String[numRegrasTeste];

        Particoes Pa;
        int i;
        int j;
        for(int atribAtual = 0; atribAtual < numVariaveisEntrada - 1; ++atribAtual) {
            if (tipoAtribs[atribAtual] == 0) {
                for(int fs = 2; fs < numMaxFuzzySets; ++fs) {
                    Pa = new Particoes();
                    Pa.geraParticao(dominio, fs, caminho);
                    fitnessWM = 0.0F;
                    carregaParticao(arqParticao, numVariaveisEntrada, caminho);
                    wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);

                    for(i = 0; i < numRegrasTreinamento; ++i) {
                        for(j = 0; j < numVariaveisEntrada; ++j) {
                            if (j == atribAtual) {
                                treinamento[i][j] = exemplosWM[i][j];
                            } else if (j == numVariaveisEntrada - 1) {
                                treinamento[i][j] = exemplosWM[i][j];
                            } else {
                                treinamento[i][j] = -11111.0F;
                            }
                        }
                    }

                    i = wM.wangMendellWrapper(this.regrasWM, treinamento, numRegrasTreinamento, numVariaveisEntrada, dominio + fold + ".txt", particaoWM, "nao", "nao", metodoRaciocinio);
                    fitnessWM = this.calculaFitness(entradasClassificadas, i, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
                    erros[fs - 2][atribAtual] = fitnessWM;
                }
            }
        }

        String[] resultados = new String[numVariaveisEntrada - 1];

        for(int x = 0; x < numVariaveisEntrada - 1; ++x) {
            i = 0;
            if (tipoAtribs[x] != 0) {
                resultados[x] = tipoAtribs[x] + "";
            } else {
                float maior = 0.0F;

                double result;
                for(j = 0; j < numMaxFuzzySets - 1; ++j) {
                    result = (double)erros[j][x];
                    if (result > (double)maior) {
                        maior = erros[j][x];
                    }
                }

                for(j = numMaxFuzzySets - 1; j >= 0; --j) {
                    result = (double)erros[j][x];
                    if (result > (double)maior - diferenca) {
                        i = j;
                    }
                }

                resultados[x] = i + 2 + "";
            }
        }

        Pa = null;

        try {
            FileWriter writer = new FileWriter(caminho + dominio + "DadosParticao.txt");
            BufferedWriter buf_writer = new BufferedWriter(writer);
            String line = "";

            for(i = 0; i < numVariaveisEntrada - 1; ++i) {
                line = line + resultados[i] + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var30) {
            System.err.println(var30);
            System.exit(1);
        }

    }

    public void classificaAtribsWMUmFold(String dominio, String caminho, int fold) throws CombinatoricException {
        String metodoRaciocinio = "classico";
        wangMendell wM = new wangMendell();
        ManipulaArquivos mA = new ManipulaArquivos();
        float fitnessWM = 0.0F;
        double diferenca = 0.05D;
        int numVariaveisEntrada = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dominio + ".txt");
        float[][] erros = new float[9][numVariaveisEntrada - 1];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");
        int numMaxFuzzySets = 9;
        String arqParticao = caminho + "particao" + dominio + ".txt";
        String arqMetaDados = caminho + dominio + ".names";
        String[][] metaDados = new String[numVariaveisEntrada][100];
        metaDados = mA.getMetaDados(arqMetaDados, numVariaveisEntrada);
        int[] tipoAtribs = new int[numVariaveisEntrada - 1];

        for(int m = 0; m < numVariaveisEntrada - 1; ++m) {
            if (metaDados[m][1].compareTo("double") != 0 && metaDados[m][1].compareTo("real") != 0 && metaDados[m][1].compareTo("integer") != 0) {
                int temp = 0;

                for(int n = 1; n < 100; ++n) {
                    if (metaDados[m][n] != null) {
                        ++temp;
                    }
                }

                tipoAtribs[m] = temp;
            } else {
                tipoAtribs[m] = 0;
            }
        }

        String arqTreinamento = caminho + dominio + ".txt";
        String arqTeste = caminho + dominio + ".txt";
        int numRegrasTreinamento = mA.getNumRegrasTreinamento2(arqTreinamento);
        exemplosWM = new float[numRegrasTreinamento][numVariaveisEntrada];
        mA.carregaTreinamento(exemplosWM, arqTreinamento, numVariaveisEntrada, numRegrasTreinamento);
        int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, "");
        this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada];
        treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
        String[] entradasClassificadas = new String[numRegrasTeste];

        Particoes Pa;
        int i;
        int j;
        for(int atribAtual = 0; atribAtual < numVariaveisEntrada - 1; ++atribAtual) {
            if (tipoAtribs[atribAtual] == 0) {
                for(int fs = 2; fs < numMaxFuzzySets; ++fs) {
                    Pa = new Particoes();
                    Pa.geraParticao(dominio, fs, caminho);
                    fitnessWM = 0.0F;

                    for(i = 0; i < numRegrasTreinamento; ++i) {
                        for(j = 0; j < numVariaveisEntrada; ++j) {
                            if (j == atribAtual) {
                                treinamento[i][j] = exemplosWM[i][j];
                            } else if (j == numVariaveisEntrada - 1) {
                                treinamento[i][j] = exemplosWM[i][j];
                            } else {
                                treinamento[i][j] = -11111.0F;
                            }
                        }
                    }

                    numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, "");
                    i = wM.wangMendellWrapper(this.regrasWM, treinamento, numRegrasTreinamento, numVariaveisEntrada, dominio + fold + ".txt", particaoWM, "nao", "nao", metodoRaciocinio);
                    wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
                    fitnessWM = this.calculaFitness(entradasClassificadas, i, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
                    erros[fs - 2][atribAtual] = fitnessWM;
                }
            }
        }

        String[] resultados = new String[numVariaveisEntrada - 1];

        for(int x = 0; x < numVariaveisEntrada - 1; ++x) {
            i = 0;
            if (tipoAtribs[x] != 0) {
                resultados[x] = tipoAtribs[x] + "";
            } else {
                float maior = 0.0F;

                double result;
                for(j = 0; j < numMaxFuzzySets - 1; ++j) {
                    result = (double)erros[j][x];
                    if (result > (double)maior) {
                        maior = erros[j][x];
                    }
                }

                for(j = numMaxFuzzySets - 1; j >= 0; --j) {
                    result = (double)erros[j][x];
                    if (result > (double)maior - diferenca) {
                        i = j;
                    }
                }

                resultados[x] = i + 2 + "";
            }
        }

        Pa = null;

        try {
            FileWriter writer = new FileWriter(caminho + dominio + "DadosParticao.txt");
            BufferedWriter buf_writer = new BufferedWriter(writer);
            String line = "";

            for(i = 0; i < numVariaveisEntrada - 1; ++i) {
                line = line + resultados[i] + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var30) {
            System.err.println(var30);
            System.exit(1);
        }

    }

    private String defineAtributosSelecinados(String caminho, String dominio, String[][] resumoRegrasWM, int numVarEnt) {
        double melhorTCC = 0.0D;
        int melhorCombinacao = -1;
        int rodadas = 0;
        int melhorNumAtribs = 0;

        int a;
        int b;
        double temp;
        for(a = 0; a < 100000; ++a) {
            if (resumoRegrasWM[a][0] == null) {
                rodadas = a;
                a = 100000;
            } else {
                resumoRegrasWM[a][numVarEnt] = "0";

                for(b = 0; b < numVarEnt; ++b) {
                    if (resumoRegrasWM[a][b].compareTo("1") == 0) {
                        a = Integer.parseInt(resumoRegrasWM[a][numVarEnt]);
                        ++a;
                        resumoRegrasWM[a][numVarEnt] = "" + a;
                    }
                }

                temp = Double.parseDouble(resumoRegrasWM[a][numVarEnt - 1]);
                if (melhorTCC < temp) {
                    melhorTCC = temp;
                    melhorCombinacao = a;
                    melhorNumAtribs = Integer.parseInt(resumoRegrasWM[a][numVarEnt]);
                }
            }
        }

        for(a = 0; a < rodadas; ++a) {
            temp = Double.parseDouble(resumoRegrasWM[a][numVarEnt - 1]);
            temp += 0.05D;
            if (melhorTCC <= temp) {
                int numAtribs = Integer.parseInt(resumoRegrasWM[a][numVarEnt]);
                if (melhorNumAtribs > numAtribs) {
                    melhorCombinacao = a;
                    melhorNumAtribs = numAtribs;
                }
            }
        }

        System.out.println("melhor combinação: " + (melhorCombinacao + 1));
        String atributos = "";
        b = 0;

        for(a = 0; a < numVarEnt; ++a) {
            if (resumoRegrasWM[melhorCombinacao][a].compareTo("1") == 0) {
                atributos = atributos + a + "\t";
                ++b;
            }
        }

        ManipulaArquivos mA = new ManipulaArquivos();
        mA.gravaListaAtribs(atributos, "Atribs-" + dominio + ".txt", b);
        System.out.println("Atributos selecionados: " + atributos);
        return atributos;
    }

    public void classificaAtribsWMUmFoldNovo(String dominio, String caminho) throws CombinatoricException {
        String metodoRaciocinio = "classico";
        wangMendell wM = new wangMendell();
        ManipulaArquivos mA = new ManipulaArquivos();
        float fitnessWM = 0.0F;
        double diferenca = 0.05D;
        int numVariaveisEntrada = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dominio + ".txt");
        float[][] erros = new float[9][numVariaveisEntrada - 1];
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");
        int numMaxFuzzySets = 9;
        String arqParticao = caminho + "particao" + dominio + ".txt";
        String arqMetaDados = caminho + dominio + ".names";
        String[][] metaDados = new String[numVariaveisEntrada][100];
        metaDados = mA.getMetaDados(arqMetaDados, numVariaveisEntrada);
        int[] tipoAtribs = new int[numVariaveisEntrada - 1];

        for(int m = 0; m < numVariaveisEntrada - 1; ++m) {
            if (metaDados[m][1].compareTo("double") != 0 && metaDados[m][1].compareTo("real") != 0 && metaDados[m][1].compareTo("integer") != 0 && metaDados[m][1].compareTo("numeric") != 0) {
                int temp = 0;

                for(int n = 1; n < 100; ++n) {
                    if (metaDados[m][n] != null) {
                        ++temp;
                    }
                }

                tipoAtribs[m] = temp;
            } else {
                tipoAtribs[m] = 0;
            }
        }

        String arqTreinamento = caminho + dominio + ".txt";
        String arqTeste = caminho + dominio + ".txt";
        int numRegrasTreinamento = mA.getNumRegrasTreinamento2(arqTreinamento);
        exemplosWM = new float[numRegrasTreinamento][numVariaveisEntrada];
        mA.carregaTreinamento(exemplosWM, arqTreinamento, numVariaveisEntrada, numRegrasTreinamento);
        int numRegrasTeste = carregaArquivoTeste(arqTeste, numVariaveisEntrada, "");
        this.regrasWM = new String[numRegrasTreinamento][numVariaveisEntrada];
        treinamento = new float[numRegrasTreinamento][numVariaveisEntrada];
        String[] entradasClassificadas = new String[numRegrasTeste];

        Particoes Pa;
        int i;
        int j;
        for(int atribAtual = 0; atribAtual < numVariaveisEntrada - 1; ++atribAtual) {
            if (tipoAtribs[atribAtual] == 0) {
                for(int fs = 2; fs < numMaxFuzzySets; ++fs) {
                    Pa = new Particoes();
                    Pa.geraParticao(dominio, fs, caminho);
                    fitnessWM = 0.0F;

                    for(i = 0; i < numRegrasTreinamento; ++i) {
                        for(j = 0; j < numVariaveisEntrada; ++j) {
                            if (j == atribAtual) {
                                treinamento[i][j] = exemplosWM[i][j];
                            } else if (j == numVariaveisEntrada - 1) {
                                treinamento[i][j] = exemplosWM[i][j];
                            } else {
                                treinamento[i][j] = -11111.0F;
                            }
                        }
                    }

                    numRegrasTreinamento = carregaArquivosWM(arqTreinamento, arqParticao, numVariaveisEntrada, "");
                    i = wM.wangMendellWrapper(this.regrasWM, treinamento, numRegrasTreinamento, numVariaveisEntrada, dominio + ".txt", particaoWM, "nao", "nao", metodoRaciocinio);
                    wM.classificaEntradas(entradasClassificadas, testeWM, numRegrasTeste, numVariaveisEntrada, particaoWM);
                    fitnessWM = this.calculaFitness(entradasClassificadas, i, numVariaveisEntrada, particaoWM, metodoRaciocinio, testeWM, this.regrasWM, numRegrasTeste);
                    erros[fs - 2][atribAtual] = fitnessWM;
                }
            }
        }

        String[] resultados = new String[numVariaveisEntrada - 1];

        for(int x = 0; x < numVariaveisEntrada - 1; ++x) {
            i = 0;
            if (tipoAtribs[x] != 0) {
                resultados[x] = tipoAtribs[x] + "";
            } else {
                float maior = 0.0F;

                double result;
                for(j = 0; j < numMaxFuzzySets - 1; ++j) {
                    result = (double)erros[j][x];
                    if (result > (double)maior) {
                        maior = erros[j][x];
                    }
                }

                for(j = numMaxFuzzySets - 1; j >= 0; --j) {
                    result = (double)erros[j][x];
                    if (result > (double)maior - diferenca) {
                        i = j;
                    }
                }

                resultados[x] = i + 2 + "";
            }
        }

        Pa = null;

        try {
            FileWriter writer = new FileWriter(caminho + dominio + "DadosParticao.txt");
            BufferedWriter buf_writer = new BufferedWriter(writer);
            String line = "";

            for(i = 0; i < numVariaveisEntrada - 1; ++i) {
                line = line + resultados[i] + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var30) {
            System.err.println(var30);
            System.exit(1);
        }

    }
}

