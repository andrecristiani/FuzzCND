package main.java.FuzzyProject.FuzzyDT;

public class Particoes {
    private static String metodoRaciocinioAG;
    private static float[][] exemplosAG;
    private static float[][] exemplosAG2;
    private static int numExemplosAG;
    private static int numVariaveisEntradaAG;
    private static int numRegrasBR;
    private static String[][] regrasAG;
    private static String[][] particaoAG;

    public Particoes() {
    }

    public void geraParticao(String arqTrein, int cjtos, String caminho) {
        String[] termos = new String[cjtos];
        if (cjtos == 2) {
            termos[0] = "baixa";
            termos[1] = "alta";
        }

        if (cjtos == 3) {
            termos[0] = "baixa";
            termos[1] = "media";
            termos[2] = "alta";
        }

        if (cjtos == 4) {
            termos[0] = "baixa";
            termos[1] = "mediabaixa";
            termos[2] = "mediaalta";
            termos[3] = "alta";
        }

        if (cjtos == 5) {
            termos[0] = "baixa";
            termos[1] = "mediabaixa";
            termos[2] = "media";
            termos[3] = "mediaalta";
            termos[4] = "alta";
        }

        if (cjtos == 6) {
            termos[0] = "baixissima";
            termos[1] = "baixa";
            termos[2] = "mediabaixa";
            termos[3] = "mediaalta";
            termos[4] = "alta";
            termos[5] = "altissima";
        }

        if (cjtos == 7) {
            termos[0] = "baixissima";
            termos[1] = "baixa";
            termos[2] = "mediabaixa";
            termos[3] = "media";
            termos[4] = "mediaalta";
            termos[5] = "alta";
            termos[6] = "altissima";
        }

        if (cjtos == 8) {
            termos[0] = "baixissima";
            termos[1] = "muitobaixa";
            termos[2] = "baixa";
            termos[3] = "mediabaixa";
            termos[4] = "mediaalta";
            termos[5] = "alta";
            termos[6] = "muitoalta";
            termos[7] = "altissima";
        }

        if (cjtos > 8) {
            for(int a = 0; a < cjtos; ++a) {
                termos[a] = "termo" + a;
            }
        }

        manipulaArquivos mA = new manipulaArquivos();
        String arqTreinamento = arqTrein + ".txt";
        String arqMetaDados = arqTrein + ".names";
        String arqParticao = "particao" + arqTreinamento;
        int numVarEntrada = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + arqTreinamento);
        String[][] metaDados = new String[numVarEntrada][100];
        metaDados = mA.getMetaDados(caminho + arqMetaDados, numVarEntrada);
        int numExemplos = mA.getNumRegrasTreinamento2(caminho + arqTreinamento);
        exemplosAG = new float[numExemplos][numVarEntrada];
        mA.carregaExemplosAG(exemplosAG, caminho + arqTreinamento, numVarEntrada, numExemplos);
        particaoAG = new String[cjtos * numVarEntrada + 30][numVarEntrada * cjtos];
        int indice = 1;
        particaoAG[0][0] = Integer.toString(numVarEntrada);
        int numClasses = Integer.parseInt(metaDados[numVarEntrada - 1][1]);

        for(int a = 0; a < numVarEntrada; ++a) {
            int b;
            if (metaDados[a][1].compareTo("real") != 0 && metaDados[a][1].compareTo("float") != 0 && metaDados[a][1].compareTo("double") != 0 && metaDados[a][1].compareTo("integer") != 0) {
                for(b = 0; metaDados[a][b] != null; ++b) {
                }

                numClasses = b - 1;
                particaoAG[0][a + 1] = Integer.toString(numClasses);

                for(int j = 1; j <= numClasses; ++j) {
                    particaoAG[indice][0] = "triangular";
                    particaoAG[indice][1] = metaDados[a][j];
                    particaoAG[indice][2] = metaDados[a][j];
                    particaoAG[indice][3] = metaDados[a][j];
                    particaoAG[indice][4] = metaDados[a][j];
                    ++indice;
                }
            } else {
                particaoAG[0][a + 1] = Integer.toString(cjtos);
                float maior;
                float menor = maior = exemplosAG[0][a];

                for(b = 0; b < numExemplos; ++b) {
                    if (exemplosAG[b][a] > maior) {
                        maior = exemplosAG[b][a];
                    }

                    if (exemplosAG[b][a] < menor) {
                        menor = exemplosAG[b][a];
                    }
                }

                float intervalo = (maior - menor) / (float)(cjtos - 1);
                float aa = menor - intervalo;
                float bb = menor;
                float cc = menor + intervalo;

                for(int j = 0; j < cjtos; ++j) {
                    particaoAG[indice][0] = "triangular";
                    particaoAG[indice][1] = termos[j];
                    particaoAG[indice][2] = Float.toString(aa);
                    particaoAG[indice][3] = Float.toString(bb);
                    particaoAG[indice][4] = Float.toString(cc);
                    ++indice;
                    aa += intervalo;
                    bb += intervalo;
                    cc += intervalo;
                }
            }
        }

        mA.gravaParticao(particaoAG, caminho + arqParticao, indice, numVarEntrada);
    }

    public void geraParticaoFDT(String arqTrein, int cjtos, String caminho) {
        String[] termos = new String[cjtos];
        if (cjtos == 2) {
            termos[0] = "baixa";
            termos[1] = "alta";
        }

        if (cjtos == 3) {
            termos[0] = "baixa";
            termos[1] = "media";
            termos[2] = "alta";
        }

        if (cjtos == 4) {
            termos[0] = "baixa";
            termos[1] = "mediabaixa";
            termos[2] = "mediaalta";
            termos[3] = "alta";
        }

        if (cjtos == 5) {
            termos[0] = "baixa";
            termos[1] = "mediabaixa";
            termos[2] = "media";
            termos[3] = "mediaalta";
            termos[4] = "alta";
        }

        if (cjtos == 6) {
            termos[0] = "baixissima";
            termos[1] = "baixa";
            termos[2] = "mediabaixa";
            termos[3] = "mediaalta";
            termos[4] = "alta";
            termos[5] = "altissima";
        }

        if (cjtos == 7) {
            termos[0] = "baixissima";
            termos[1] = "baixa";
            termos[2] = "mediabaixa";
            termos[3] = "media";
            termos[4] = "mediaalta";
            termos[5] = "alta";
            termos[6] = "altissima";
        }

        if (cjtos == 8) {
            termos[0] = "baixissima";
            termos[1] = "muitobaixa";
            termos[2] = "baixa";
            termos[3] = "mediabaixa";
            termos[4] = "mediaalta";
            termos[5] = "alta";
            termos[6] = "muitoalta";
            termos[7] = "altissima";
        }

        if (cjtos > 8) {
            for(int a = 0; a < cjtos; ++a) {
                termos[a] = "termo" + a;
            }
        }

        manipulaArquivos mA = new manipulaArquivos();
        String arqTreinamento = arqTrein + ".txt";
        String arqMetaDados = arqTrein + ".names";
        String arqParticao = "particao" + arqTreinamento;
        int numVarEntrada = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + arqTreinamento);
        String[][] metaDados = new String[numVarEntrada][100];
        metaDados = mA.getMetaDados(caminho + arqMetaDados, numVarEntrada);
        int numExemplos = mA.getNumRegrasTreinamento2(caminho + arqTreinamento);
        exemplosAG = new float[numExemplos][numVarEntrada];
        mA.carregaExemplosAG(exemplosAG, caminho + arqTreinamento, numVarEntrada, numExemplos);
        particaoAG = new String[cjtos * numVarEntrada + 30][numVarEntrada * cjtos];
        int indice = 1;
        particaoAG[0][0] = Integer.toString(numVarEntrada);
        int numClasses = Integer.parseInt(metaDados[numVarEntrada - 1][1]);

        for(int a = 0; a < numVarEntrada; ++a) {
            int b;
            if (metaDados[a][1].compareTo("real") != 0 && metaDados[a][1].compareTo("float") != 0 && metaDados[a][1].compareTo("double") != 0 && metaDados[a][1].compareTo("integer") != 0) {
                for(b = 0; metaDados[a][b] != null; ++b) {
                }

                numClasses = b - 1;
                particaoAG[0][a + 1] = Integer.toString(numClasses);

                for(int j = 1; j <= numClasses; ++j) {
                    particaoAG[indice][0] = "triangular";
                    particaoAG[indice][1] = metaDados[a][j];
                    particaoAG[indice][2] = metaDados[a][j];
                    particaoAG[indice][3] = metaDados[a][j];
                    particaoAG[indice][4] = metaDados[a][j];
                    ++indice;
                }
            } else {
                particaoAG[0][a + 1] = Integer.toString(cjtos);
                float maior;
                float menor = maior = exemplosAG[0][a];

                for(b = 0; b < numExemplos; ++b) {
                    if (exemplosAG[b][a] > maior) {
                        maior = exemplosAG[b][a];
                    }

                    if (exemplosAG[b][a] < menor) {
                        menor = exemplosAG[b][a];
                    }
                }

                float intervalo = (maior - menor) / (float)(cjtos - 1);
                float aa = menor - intervalo;
                float bb = menor;
                float cc = menor + intervalo;

                for(int j = 0; j < cjtos; ++j) {
                    particaoAG[indice][0] = "triangular";
                    particaoAG[indice][1] = termos[j];
                    particaoAG[indice][2] = Float.toString(aa);
                    particaoAG[indice][3] = Float.toString(bb);
                    particaoAG[indice][4] = Float.toString(cc);
                    ++indice;
                    aa += intervalo;
                    bb += intervalo;
                    cc += intervalo;
                }
            }
        }

        mA.gravaParticao(particaoAG, caminho + arqParticao, indice, numVarEntrada);
    }

    public void geraParticaoNumCjtosFuzzyVariavel(String arqTrein, String caminho) {
        manipulaArquivos mA = new manipulaArquivos();
        String arqDados = arqTrein + "DadosParticao.txt";
        String arqTreinamento = arqTrein + ".txt";
        String arqMetaDados = arqTrein + ".names";
        String arqParticao = "particao" + arqTreinamento;
        int numVarEntrada = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + arqTreinamento);
        int[] dadosCjtos = new int[numVarEntrada - 1];
        String[][] metaDados = new String[numVarEntrada][100];
        metaDados = mA.getMetaDados(caminho + arqMetaDados, numVarEntrada);
        dadosCjtos = mA.getDadosParticao(caminho + arqDados, numVarEntrada);
        int cjtos = 0;

        int numExemplos;
        for(numExemplos = 0; numExemplos < numVarEntrada - 1; ++numExemplos) {
            cjtos += dadosCjtos[numExemplos];
        }

        numExemplos = mA.getNumRegrasTreinamento2(caminho + arqTreinamento);
        exemplosAG = new float[numExemplos][numVarEntrada];
        mA.carregaExemplosAG(exemplosAG, caminho + arqTreinamento, numVarEntrada, numExemplos);
        particaoAG = new String[cjtos * numVarEntrada + 10][numVarEntrada * cjtos];
        int indice = 1;
        particaoAG[0][0] = Integer.toString(numVarEntrada);
        int numClasses = Integer.parseInt(metaDados[numVarEntrada - 1][1]);

        for(int a = 0; a < numVarEntrada; ++a) {
            int b;
            if (metaDados[a][1].compareTo("real") != 0 && metaDados[a][1].compareTo("float") != 0 && metaDados[a][1].compareTo("double") != 0 && metaDados[a][1].compareTo("integer") != 0) {
                for(b = 0; metaDados[a][b] != null; ++b) {
                }

                numClasses = b - 1;
                particaoAG[0][a + 1] = Integer.toString(numClasses);

                for(b = 1; b <= numClasses; ++b) {
                    particaoAG[indice][0] = "triangular";
                    particaoAG[indice][1] = metaDados[a][b];
                    particaoAG[indice][2] = metaDados[a][b];
                    particaoAG[indice][3] = metaDados[a][b];
                    particaoAG[indice][4] = metaDados[a][b];
                    ++indice;
                }
            } else {
                cjtos = dadosCjtos[a];
                String[] termos = new String[cjtos];
                if (cjtos == 2) {
                    termos[0] = "baixa";
                    termos[1] = "alta";
                }

                if (cjtos == 3) {
                    termos[0] = "baixa";
                    termos[1] = "media";
                    termos[2] = "alta";
                }

                if (cjtos == 4) {
                    termos[0] = "baixa";
                    termos[1] = "mediabaixa";
                    termos[2] = "mediaalta";
                    termos[3] = "alta";
                }

                if (cjtos == 5) {
                    termos[0] = "baixa";
                    termos[1] = "mediabaixa";
                    termos[2] = "media";
                    termos[3] = "mediaalta";
                    termos[4] = "alta";
                }

                if (cjtos == 6) {
                    termos[0] = "baixissima";
                    termos[1] = "baixa";
                    termos[2] = "mediabaixa";
                    termos[3] = "mediaalta";
                    termos[4] = "alta";
                    termos[5] = "altissima";
                }

                if (cjtos == 7) {
                    termos[0] = "baixissima";
                    termos[1] = "baixa";
                    termos[2] = "mediabaixa";
                    termos[3] = "media";
                    termos[4] = "mediaalta";
                    termos[5] = "alta";
                    termos[6] = "altissima";
                }

                if (cjtos == 8) {
                    termos[0] = "baixissima";
                    termos[1] = "muitobaixa";
                    termos[2] = "baixa";
                    termos[3] = "mediabaixa";
                    termos[4] = "mediaalta";
                    termos[5] = "alta";
                    termos[6] = "muitoalta";
                    termos[7] = "altissima";
                }

                if (cjtos > 8) {
                    for(b = 0; b < cjtos; ++b) {
                        termos[b] = "termo" + b;
                    }
                }

                particaoAG[0][a + 1] = Integer.toString(cjtos);
                float maior;
                float menor = maior = exemplosAG[0][a];

                for(b = 0; b < numExemplos; ++b) {
                    if (exemplosAG[b][a] > maior) {
                        maior = exemplosAG[b][a];
                    }

                    if (exemplosAG[b][a] < menor) {
                        menor = exemplosAG[b][a];
                    }
                }

                float intervalo = (maior - menor) / (float)(cjtos - 1);
                float aa = menor - intervalo;
                float bb = menor;
                float cc = menor + intervalo;

                for(int j = 0; j < cjtos; ++j) {
                    particaoAG[indice][0] = "triangular";
                    particaoAG[indice][1] = termos[j];
                    particaoAG[indice][2] = Float.toString(aa);
                    particaoAG[indice][3] = Float.toString(bb);
                    particaoAG[indice][4] = Float.toString(cc);
                    ++indice;
                    aa += intervalo;
                    bb += intervalo;
                    cc += intervalo;
                }
            }
        }

        mA.gravaParticao(particaoAG, caminho + arqParticao, indice, numVarEntrada);
    }
}

