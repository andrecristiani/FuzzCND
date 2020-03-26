package FuzzyProject.FuzzyDT.Utils;

public class gera10Folds {
    private static float[][] exemplosAG;

    public gera10Folds() {
    }

    public void geraNFolds(String dataSet, String caminho, int numFolds) {
        boolean flag = false;
        ManipulaArquivos mA = new ManipulaArquivos();
        int numVarEntrada = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + dataSet + ".txt");
        int numClasses = mA.getNumeroClasses(numVarEntrada, caminho + dataSet + ".names");
        int numExemplos = mA.getNumRegrasTreinamento2(caminho + dataSet + ".txt");
        exemplosAG = new float[numExemplos][numVarEntrada];
        mA.carregaExemplosAG(exemplosAG, caminho + dataSet + ".txt", numVarEntrada, numExemplos);
        String[] classes = new String[numClasses];
        classes = mA.getNomesClasses(numVarEntrada, caminho + dataSet + ".names", numClasses);
        int[][] numExemplosPorClasse = new int[numClasses][4];

        for(int b = 0; b < numClasses; ++b) {
            numExemplosPorClasse[b][0] = 0;
        }

        float[][][] treinamentoPorClasses = new float[numClasses][numExemplos][numVarEntrada];

        int a;
        int indiceTeste;
        int indiceTreinamento;
        for(a = 0; a < numExemplos; ++a) {
            for(int b = 0; b < numClasses; ++b) {
                indiceTeste = Integer.parseInt(classes[b]);
                if ((float)indiceTeste == exemplosAG[a][numVarEntrada - 1]) {
                    for(indiceTreinamento = 0; indiceTreinamento < numVarEntrada; ++indiceTreinamento) {
                        treinamentoPorClasses[b][numExemplosPorClasse[b][0]][indiceTreinamento] = exemplosAG[a][indiceTreinamento];
                    }

                    ++numExemplosPorClasse[b][0];
                }
            }
        }

        for(a = 0; a < numClasses; ++a) {
            if (numExemplosPorClasse[a][0] >= numFolds) {
                if (numExemplosPorClasse[a][1] % 2 == 0) {
                    numExemplosPorClasse[a][1] = numExemplosPorClasse[a][0] - numExemplosPorClasse[a][0] / numFolds;
                    numExemplosPorClasse[a][3] = numExemplosPorClasse[a][0] / numFolds;
                } else {
                    numExemplosPorClasse[a][1] = numExemplosPorClasse[a][0] - (numExemplosPorClasse[a][0] - 1) / numFolds;
                    numExemplosPorClasse[a][3] = numExemplosPorClasse[a][0] / numFolds;
                }

                numExemplosPorClasse[a][2] = numExemplosPorClasse[a][0];
            } else if (numExemplosPorClasse[a][0] >= numFolds / 2) {
                numExemplosPorClasse[a][1] = -1;
                numExemplosPorClasse[a][2] = -1;
            } else {
                numExemplosPorClasse[a][1] = -2;
                numExemplosPorClasse[a][2] = -2;
            }
        }

        float[][] treinamento = new float[numExemplos][numVarEntrada];
        float[][] teste = new float[numExemplos][numVarEntrada];
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(int fold = 0; fold < numFolds; ++fold) {
            for(a = 0; a < numClasses; ++a) {
                int b;
                int c;
                if (numExemplosPorClasse[a][2] > 0) {
                    b = 0;

                    label124:
                    while(true) {
                        if (b >= numExemplosPorClasse[a][1]) {
                            for(b = numExemplosPorClasse[a][1]; b < numExemplosPorClasse[a][2]; ++b) {
                                for(c = 0; c < numVarEntrada; ++c) {
                                    teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                                }

                                ++indiceTeste;
                            }

                            b = numExemplosPorClasse[a][2];

                            while(true) {
                                if (b >= numExemplosPorClasse[a][0]) {
                                    break label124;
                                }

                                for(c = 0; c < numVarEntrada; ++c) {
                                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                                }

                                ++indiceTreinamento;
                                ++b;
                            }
                        }

                        for(c = 0; c < numVarEntrada; ++c) {
                            treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                        }

                        ++indiceTreinamento;
                        ++b;
                    }
                } else if (numExemplosPorClasse[a][0] >= numFolds / 2) {
                    b = 0;

                    label144:
                    while(true) {
                        if (b >= numExemplosPorClasse[a][0] / 3 * 2) {
                            b = numExemplosPorClasse[a][0] / 3 * 2;

                            while(true) {
                                if (b >= numExemplosPorClasse[a][0]) {
                                    break label144;
                                }

                                for(c = 0; c < numVarEntrada; ++c) {
                                    teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                                }

                                ++indiceTeste;
                                ++b;
                            }
                        }

                        for(c = 0; c < numVarEntrada; ++c) {
                            treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                        }

                        ++indiceTreinamento;
                        ++b;
                    }
                } else {
                    b = 0;

                    label165:
                    while(true) {
                        if (b >= numExemplosPorClasse[a][0]) {
                            b = 0;

                            while(true) {
                                if (b >= numExemplosPorClasse[a][0]) {
                                    break label165;
                                }

                                for(c = 0; c < numVarEntrada; ++c) {
                                    teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                                }

                                ++indiceTeste;
                                ++b;
                            }
                        }

                        for(c = 0; c < numVarEntrada; ++c) {
                            treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                        }

                        ++indiceTreinamento;
                        ++b;
                    }
                }

                numExemplosPorClasse[a][1] -= numExemplosPorClasse[a][3];
                numExemplosPorClasse[a][2] -= numExemplosPorClasse[a][3];
            }

            mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste" + fold + ".txt", indiceTeste, numVarEntrada);
            mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento" + fold + ".txt", indiceTreinamento, numVarEntrada);
            indiceTeste = 0;
            indiceTreinamento = 0;
        }

    }

    void gera10Folds(String dataSet, int numClasses, String caminho) {
        int cont = 0;
        boolean flag = false;
        ManipulaArquivos mA = new ManipulaArquivos();
        int numExemplos = mA.getNumRegrasTreinamento2(caminho + dataSet + ".txt");
        System.out.println("Número total de exemplos: " + numExemplos);
        int numVarEntrada = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + dataSet + ".txt");
        System.out.println("Número de atributos: " + numVarEntrada);
        exemplosAG = new float[numExemplos][numVarEntrada];
        mA.carregaExemplosAG(exemplosAG, caminho + dataSet + ".txt", numVarEntrada, numExemplos);
        String[] classes = new String[numClasses];
        classes[cont] = Float.toString(exemplosAG[0][numVarEntrada - 1]);
        int[] numExemplosPorClasse = new int[numClasses];

        int a;
        for(a = 0; a < numClasses; ++a) {
            numExemplosPorClasse[a] = 0;
        }

        for(a = 1; a < numExemplos; ++a) {
            for(a = 0; a <= cont; ++a) {
                if (classes[a].compareTo(Float.toString(exemplosAG[a][numVarEntrada - 1])) == 0) {
                    flag = true;
                }
            }

            if (!flag) {
                ++cont;
                classes[cont] = Float.toString(exemplosAG[a][numVarEntrada - 1]);
            } else {
                flag = false;
            }

            if (cont == numClasses) {
                a = numExemplos;
            }
        }

        float[][][] treinamentoPorClasses = new float[numClasses][numExemplos][numVarEntrada];

        int indiceTeste;
        for(a = 0; a < numExemplos; ++a) {
            for(int b = 0; b < numClasses; ++b) {
                if (classes[b].compareTo(Float.toString(exemplosAG[a][numVarEntrada - 1])) == 0) {
                    for(indiceTeste = 0; indiceTeste < numVarEntrada; ++indiceTeste) {
                        treinamentoPorClasses[b][numExemplosPorClasse[b]][indiceTeste] = exemplosAG[a][indiceTeste];
                    }

                    int var10002 = numExemplosPorClasse[b]++;
                }
            }
        }

        for(a = 0; a < numClasses; ++a) {
            System.out.println("# de exemplos da classe " + classes[a] + ": " + numExemplosPorClasse[a]);
        }

        float[][] treinamento = new float[numExemplos][numVarEntrada];
        float[][] teste = new float[numExemplos][numVarEntrada];
        indiceTeste = 0;
        int indiceTreinamento = 0;

        int indiceVariavel;
        int b;
        int c;
        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10;

            for(b = 0; b < indiceVariavel; ++b) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                }

                ++indiceTeste;
            }

            for(b = indiceVariavel; b < numExemplosPorClasse[a]; ++b) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste0.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento0.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10;
            b = numExemplosPorClasse[a] / 10 * 2;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste1.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento1.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 2;
            b = numExemplosPorClasse[a] / 10 * 3;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste2.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento2.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 3;
            b = numExemplosPorClasse[a] / 10 * 4;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste3.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento3.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 4;
            b = numExemplosPorClasse[a] / 10 * 5;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste4.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento4.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 5;
            b = numExemplosPorClasse[a] / 10 * 6;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste5.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento5.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 6;
            b = numExemplosPorClasse[a] / 10 * 7;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste6.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento6.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 7;
            b = numExemplosPorClasse[a] / 10 * 8;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste7.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento7.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 8;
            b = numExemplosPorClasse[a] / 10 * 9;

            for(c = 0; c < indiceVariavel; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }

            for(c = indiceVariavel; c < b; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTeste;
            }

            for(c = b; c < numExemplosPorClasse[a]; ++c) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][c][c];
                }

                ++indiceTreinamento;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste8.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento8.txt", indiceTreinamento, numVarEntrada);
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            indiceVariavel = numExemplosPorClasse[a] / 10 * 9;

            for(b = 0; b < indiceVariavel; ++b) {
                for(c = 0; c < numVarEntrada; ++c) {
                    treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                }

                ++indiceTreinamento;
            }

            for(b = indiceVariavel; b < numExemplosPorClasse[a]; ++b) {
                for(c = 0; c < numVarEntrada; ++c) {
                    teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                }

                ++indiceTeste;
            }
        }

        mA.gravaArquivos10Fold(teste, caminho + dataSet + "-teste9.txt", indiceTeste, numVarEntrada);
        mA.gravaArquivos10Fold(treinamento, caminho + dataSet + "-treinamento9.txt", indiceTreinamento, numVarEntrada);
        System.out.println("FIM !");
    }

    void separaXPorCento(String dataSet, String caminho, int separar) {
//        int numFolds = false;
        byte numFolds;
        if (separar == 25) {
            numFolds = 4;
        } else if (separar == 50) {
            numFolds = 2;
        } else if (separar == 30) {
            numFolds = 2;
        } else if (separar == 20) {
            numFolds = 5;
        } else if (separar == 10) {
            numFolds = 10;
        } else {
            numFolds = 4;
            System.out.println("Separação inválida...\nSeparação será feita usando 25% dos exemplos como reserva.");
        }

        ManipulaArquivos mA = new ManipulaArquivos();
        int numVarEntrada = mA.getNumeroVariaveisEntradaArqTreinamento(caminho + dataSet + "_original.txt");
        System.out.println("Número de atributos: " + numVarEntrada);
        int numClasses = mA.getNumeroClasses(numVarEntrada, caminho + dataSet + ".names");
        System.out.println("Número de classes: " + numClasses);
        int numExemplos = mA.getNumRegrasTreinamento2(caminho + dataSet + "_original.txt");
        System.out.println("Número total de exemplos: " + numExemplos);
        exemplosAG = new float[numExemplos][numVarEntrada];
        mA.carregaExemplosAG(exemplosAG, caminho + dataSet + "_original.txt", numVarEntrada, numExemplos);
        String[] classes = new String[numClasses];
        classes = mA.getNomesClasses(numVarEntrada, caminho + dataSet + ".names", numClasses);

        for(int a = 0; a < numClasses; ++a) {
            System.out.println("Classe " + a + ": " + classes[a]);
        }

        int[][] numExemplosPorClasse = new int[numClasses][2];

        for(int b = 0; b < numClasses; ++b) {
            numExemplosPorClasse[b][0] = 0;
        }

        float[][][] treinamentoPorClasses = new float[numClasses][numExemplos][numVarEntrada];

        int a;
        int indiceTeste;
        int indiceTreinamento;
        for(a = 0; a < numExemplos; ++a) {
            for(int b = 0; b < numClasses; ++b) {
                indiceTeste = Integer.parseInt(classes[b]);
                if ((float)indiceTeste == exemplosAG[a][numVarEntrada - 1]) {
                    for(indiceTreinamento = 0; indiceTreinamento < numVarEntrada; ++indiceTreinamento) {
                        treinamentoPorClasses[b][numExemplosPorClasse[b][0]][indiceTreinamento] = exemplosAG[a][indiceTreinamento];
                    }

                    ++numExemplosPorClasse[b][0];
                }
            }
        }

        for(a = 0; a < numClasses; ++a) {
            if (numExemplosPorClasse[a][0] >= numFolds) {
                if (numExemplosPorClasse[a][1] % 2 == 0) {
                    numExemplosPorClasse[a][1] = numExemplosPorClasse[a][0] / numFolds;
                } else {
                    numExemplosPorClasse[a][1] = numExemplosPorClasse[a][0] - (numExemplosPorClasse[a][0] - 1) / numFolds;
                }
            } else if (numExemplosPorClasse[a][0] >= numFolds / 2) {
                numExemplosPorClasse[a][1] = -1;
            } else {
                numExemplosPorClasse[a][1] = -2;
            }

            System.out.println("# de exemplos da classe " + classes[a] + ": " + numExemplosPorClasse[a][0] + " Indice separação: " + numExemplosPorClasse[a][1]);
        }

        float[][] treinamento = new float[numExemplos][numVarEntrada];
        float[][] teste = new float[numExemplos][numVarEntrada];
        indiceTeste = 0;
        indiceTreinamento = 0;

        for(a = 0; a < numClasses; ++a) {
            int b;
            int c;
            if (numExemplosPorClasse[a][1] > 0) {
                for(b = 0; b < numExemplosPorClasse[a][1]; ++b) {
                    for(c = 0; c < numVarEntrada; ++c) {
                        treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                    }

                    ++indiceTreinamento;
                }

                for(b = numExemplosPorClasse[a][1]; b < numExemplosPorClasse[a][0]; ++b) {
                    for(c = 0; c < numVarEntrada; ++c) {
                        teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                    }

                    ++indiceTeste;
                }
            } else if (numExemplosPorClasse[a][1] == -1) {
                for(b = 0; b < numExemplosPorClasse[a][0] / 3 * 2; ++b) {
                    for(c = 0; c < numVarEntrada; ++c) {
                        treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                    }

                    ++indiceTreinamento;
                }

                for(b = numExemplosPorClasse[a][0] / 3 * 2; b < numExemplosPorClasse[a][0]; ++b) {
                    for(c = 0; c < numVarEntrada; ++c) {
                        teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                    }

                    ++indiceTeste;
                }
            } else {
                for(b = 0; b < numExemplosPorClasse[a][0]; ++b) {
                    for(c = 0; c < numVarEntrada; ++c) {
                        treinamento[indiceTreinamento][c] = treinamentoPorClasses[a][b][c];
                    }

                    ++indiceTreinamento;
                }

                for(b = 0; b < numExemplosPorClasse[a][0]; ++b) {
                    for(c = 0; c < numVarEntrada; ++c) {
                        teste[indiceTeste][c] = treinamentoPorClasses[a][b][c];
                    }

                    ++indiceTeste;
                }
            }
        }

        mA.gravaArquivosFoldSeparado(exemplosAG, caminho + dataSet + "_Original.txt", numExemplos, numVarEntrada);
        mA.gravaArquivosFoldSeparado(treinamento, caminho + dataSet + "_" + separar + "Reserved.txt", indiceTreinamento, numVarEntrada);
        mA.gravaArquivosFoldSeparado(teste, caminho + dataSet + ".txt", indiceTeste, numVarEntrada);
        System.out.println("Arquivos gerados!");
    }
}

