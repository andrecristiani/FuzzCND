package FuzzyProject.FuzzyDT.Utils;

import FuzzyProject.FuzzyDT.Fuzzy.CombinatoricException;
import FuzzyProject.FuzzyDT.Models.Exemplo;
import FuzzyProject.FuzzyDT.Fuzzy.Particoes;
import FuzzyProject.FuzzyDT.Fuzzy.wrapperWM;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class manipulaArquivos {
    String saida;
    private Component componente;
    private static String[][] particaoContexto;

    public manipulaArquivos() {
    }

    public int getNumeroVariaveisEntrada(String nomeArquivo) {
        BufferedReader inReader = null;
        String valor = "";

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumeroVariaveisEntrada - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.nextToken();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return Integer.parseInt(valor);
    }

    public int getNumeroTermos(String nomeArquivo) {
        BufferedReader inReader = null;
        String valor = "";

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumeroTermos - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.nextToken();
            valor = str.nextToken();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return Integer.parseInt(valor);
    }

    public int getNumeroVariaveisEntradaArqTreinamento(String nomeArquivo) {
        BufferedReader inReader = null;
        int valor = 0;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumeroVariaveisEntradaArqTreinamento - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.countTokens();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return valor;
    }

    public String[][] getMetaDados(String nomeArquivo, int numAtribs) {
        BufferedReader inReader = null;
        String[][] metaDados = new String[numAtribs][100];

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var9) {
            System.err.println("getMetaDados - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        int b = 0;

        try {
            for(int a = 0; a < numAtribs; ++a) {
                String line = inReader.readLine();

                for(StringTokenizer str = new StringTokenizer(line); str.hasMoreTokens(); ++b) {
                    metaDados[a][b] = str.nextToken();
                }

                b = 0;
            }

            inReader.close();
        } catch (IOException var10) {
            System.err.println(var10.getMessage());
        }

        return metaDados;
    }

    public int[] getDadosParticao(String nomeArquivo, int numAtribs) {
        BufferedReader inReader = null;
        int[] dados = new int[numAtribs - 1];

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var9) {
            System.err.println("getDadosParticao -- Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        boolean var6 = false;

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);

            for(int a = 0; a < numAtribs - 1; ++a) {
                dados[a] = Integer.parseInt(str.nextToken());
            }

            inReader.close();
        } catch (IOException var10) {
            System.err.println(var10.getMessage());
        }

        return dados;
    }

    public Vector getClasses(String nomeArquivo, int numAtribs) {
        BufferedReader inReader = null;
        String[][] metaDados = new String[numAtribs][20];
        Vector classes = new Vector();

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var9) {
            System.err.println("getClasses - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        boolean var7 = false;

        try {
            classes.clear();

            String line;
            for(int a = 0; a < numAtribs - 1; ++a) {
                line = inReader.readLine();
            }

            line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            str.nextToken();

            while(str.hasMoreTokens()) {
                classes.add(str.nextToken());
            }

            inReader.close();
        } catch (IOException var10) {
            System.err.println(var10.getMessage());
        }

        return classes;
    }

    public Vector getCjtosFuzzy(String nomeArquivo, int numAtribs, int numCjtosFuzzy) {
        BufferedReader inReader = null;
        String[][] metaDados = new String[numAtribs][20];
        Vector classes = new Vector();

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("getCjtosFuzzy - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        boolean var8 = false;

        try {
            classes.clear();

            String line;
            for(int a = 0; a < numAtribs - 1; ++a) {
                line = inReader.readLine();
            }

            line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            str.nextToken();

            for(int d = 0; d < numCjtosFuzzy; ++d) {
                classes.add(str.nextToken());
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

        return classes;
    }

    public int getNumeroVariaveisEntradaArqTreinamento2(String nomeArquivo) {
        BufferedReader inReader = null;
        int valor = 0;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumeroVariaveisEntradaArqTreinamento2 - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.countTokens();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return valor;
    }

    public int getNumeroVariaveisEntradaArqTreinamento3(String nomeArquivo, String caminho) {
        BufferedReader inReader = null;
        int valor = 0;

        try {
            inReader = new BufferedReader(new FileReader(caminho + nomeArquivo));
        } catch (FileNotFoundException var8) {
            System.err.println("getNumeroVariaveisEntradaArqTreinamento3 - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.countTokens();
            inReader.close();
        } catch (IOException var7) {
            System.err.println(var7.getMessage());
        }

        return valor;
    }

    public int getNumeroVariaveisEntradaArqTreinamento4(String nomeArquivo) {
        BufferedReader inReader = null;
        int valor = 0;
        System.out.println(nomeArquivo);

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumeroVariaveisEntradaArqTreinamento4 - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.countTokens();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return valor;
    }

    public int getNumConjuntos(String nomeArquivo) {
        BufferedReader inReader = null;
        int numConjuntos = 0;
        boolean var4 = false;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var8) {
            System.err.println("getNumConjuntos - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            int numVariaveisEntrada = Integer.parseInt(str.nextToken());

            for(int a = 0; a < numVariaveisEntrada; ++a) {
                numConjuntos += Integer.parseInt(str.nextToken());
            }

            inReader.close();
        } catch (IOException var9) {
            System.err.println(var9.getMessage());
        }

        return numConjuntos;
    }

    public int getNumClasses(String nomeArquivo) {
        BufferedReader inReader = null;
        int numClasses = 0;
        boolean var4 = false;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var8) {
            System.err.println("getNumClasses - Não foi possível abrir o arquivo: " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            int numVariaveisEntrada = Integer.parseInt(str.nextToken());

            for(int a = 0; a < numVariaveisEntrada - 1; ++a) {
                str.nextToken();
            }

            numClasses = Integer.parseInt(str.nextToken());
            inReader.close();
        } catch (IOException var9) {
            System.err.println(var9.getMessage());
        }

        return numClasses;
    }

    public int getNumeroClasses(int numVarEntrada, String nomeArquivo) {
        BufferedReader inReader = null;
        int numClasses = 0;
        String line = "";

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumeroClasses - Não foi possível abrir o arquivo: " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numVarEntrada; ++a) {
                line = inReader.readLine();
            }

            StringTokenizer str = new StringTokenizer(line);
            numClasses = str.countTokens() - 1;
            inReader.close();
        } catch (IOException var8) {
            System.err.println(var8.getMessage());
        }

        return numClasses;
    }

    public String[] getNomesClasses(int numVarEntrada, String nomeArquivo, int numClasses) {
        BufferedReader inReader = null;
        String line = "";
        String[] classesNomes = new String[numClasses];

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var9) {
            System.err.println("getNomesClasses - Não foi possível abrir o arquivo: " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numVarEntrada; ++a) {
                line = inReader.readLine();
            }

            StringTokenizer str = new StringTokenizer(line);
            line = str.nextToken();

            for(int a = 0; a < numClasses; ++a) {
                classesNomes[a] = str.nextToken();
            }

            inReader.close();
        } catch (IOException var10) {
            System.err.println(var10.getMessage());
        }

        return classesNomes;
    }

    public void carregaDadosParticao(String nomeArquivo, Vector dadosParticao) {
        BufferedReader inReader = null;
//        int numConjuntos = false;
        boolean var5 = false;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var8) {
            System.err.println("carregaDadosParticao - Não foi possível abrir o arquivo: " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);

            while(str.hasMoreElements()) {
                dadosParticao.addElement(str.nextToken());
            }

            inReader.close();
        } catch (IOException var9) {
            System.err.println(var9.getMessage());
        }

    }

    public int getNumRegrasPossiveis(String nomeArquivo) {
        BufferedReader inReader = null;
        String valor = "";

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumRegrasPossiveis - Não foi possível abrir o arquivo: " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            valor = str.nextToken();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return Integer.parseInt(valor);
    }

    public void carregaParticao(String[][] particao, String nomeArquivo, int numVariaveisEntrada, int numConjuntos) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaParticao - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            for(int a = 0; a <= numConjuntos; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);
                String valor = str.nextToken();
                particao[a][0] = valor;

                for(int b = 1; str.hasMoreTokens(); ++b) {
                    valor = str.nextToken();
                    particao[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    public void carregaContexto(boolean[][] contexto, String caminho, int numObjetos, int numConjuntos, String dataset) {
        BufferedReader inReader = null;
        String nomeArquivo = caminho + dataset;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var13) {
            System.err.println("carregaContexto - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numObjetos; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numConjuntos; ++b) {
                    int valor = Integer.parseInt(str.nextToken());
                    if ((double)valor > 0.5D) {
                        contexto[a][b] = true;
                    } else {
                        contexto[a][b] = false;
                    }
                }
            }

            inReader.close();
        } catch (IOException var14) {
            System.err.println(var14.getMessage());
        }

    }

    public Vector[] carregaNomesContexto(boolean[][] contexto, String caminho, int numConjuntos, int numObjetos, String dataset, int numVarEntrada) {
        BufferedReader inReader = null;
        String nomeArquivo = caminho + dataset + "Nomes.txt";
        Vector[] nomes = new Vector[]{new Vector(), new Vector()};
        File f = new File(nomeArquivo);
        int b;
        if (f.exists()) {
            try {
                inReader = new BufferedReader(new FileReader(nomeArquivo));
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(b = 0; b < numConjuntos; ++b) {
                    nomes[0].add(str.nextToken());
                }

                line = inReader.readLine();
                str = new StringTokenizer(line);

                for(b = 0; b < numObjetos; ++b) {
                    nomes[1].add(str.nextToken());
                }

                inReader.close();
            } catch (IOException var16) {
                System.err.println(var16.getMessage());
            }
        } else {
            manipulaArquivos mA = new manipulaArquivos();
            b = 1;
            particaoContexto = new String[numConjuntos + 1][numVarEntrada + 2];
            mA.carregaParticao(particaoContexto, caminho + "particao" + dataset + ".txt", numVarEntrada, numConjuntos);

            int a;
            for(a = 1; a < Integer.parseInt(particaoContexto[0][0]); ++a) {
                for(b = 0; b < Integer.parseInt(particaoContexto[0][a]); ++b) {
                    nomes[0].add("atrib" + a + "_" + particaoContexto[b][1]);
                    ++b;
                }
            }

            for(a = 0; a < Integer.parseInt(particaoContexto[0][Integer.parseInt(particaoContexto[0][0])]); ++a) {
                nomes[0].add("class_" + particaoContexto[b][1]);
                ++b;
            }

            for(a = 1; a <= numObjetos; ++a) {
                nomes[1].add("obj_" + a);
            }

            this.gravaNomes(caminho, dataset, nomes);
        }

        return nomes;
    }

    public Vector[] geraArquivoNomesContexto(String caminho, int numConjuntos, int numObjetos, String dataset, int numVarEntrada) {
        String nomeArquivo = caminho + dataset + "Nomes.txt";
        String arquivoParticao = caminho + "particao" + dataset + ".txt";
        Vector[] nomes = new Vector[]{new Vector(), new Vector()};
        new File(nomeArquivo);
        String[][] particaoContexto = new String[numConjuntos + 1][numVarEntrada + 2];
        this.carregaParticao(particaoContexto, arquivoParticao, numVarEntrada, numConjuntos);
        int indice = 1;

        int a;
        for(a = 1; a < Integer.parseInt(particaoContexto[0][0]); ++a) {
            for(int b = 0; b < Integer.parseInt(particaoContexto[0][a]); ++b) {
                nomes[0].add("atrib" + a + "_" + particaoContexto[indice][1]);
                ++indice;
            }
        }

        for(a = 0; a < Integer.parseInt(particaoContexto[0][Integer.parseInt(particaoContexto[0][0])]); ++a) {
            nomes[0].add("class_" + particaoContexto[indice][1]);
            ++indice;
        }

        for(a = 1; a <= numObjetos; ++a) {
            nomes[1].add("obj_" + a);
        }

        this.gravaNomes(caminho, dataset, nomes);
        return nomes;
    }

    public void carregaRegrasGC(String[][] regrasGC, String nomeArquivo, int numVariaveisEntrada, int numRegras) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var12) {
            System.err.println("carregaRegrasGC - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
//            int vars = false;
            String line = inReader.readLine();
            line = inReader.readLine();

            for(int a = 0; a < numRegras; ++a) {
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String valor = str.nextToken();
                    regrasGC[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var13) {
            System.err.println(var13.getMessage());
        }

    }

    public void carregaRegrasAG(String[][] regrasAG, String nomeArquivo, int numVariaveisEntrada, int numRegras) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaRegrasAG - Não foi possível abrir o arquivo:_ " + nomeArquivo);
            System.exit(1);
        }

        try {
            System.out.println("Atributos: " + numVariaveisEntrada);
            System.out.println("Regras: " + numRegras);

            for(int a = 0; a < numRegras; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String valor = str.nextToken();
                    regrasAG[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    public void carregaRegrasAG2(String[][] regrasAG, String nomeArquivo, int numVariaveisEntrada, int numRegras) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaRegrasAG2 - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            line = inReader.readLine();

            for(int a = 0; a < numRegras; ++a) {
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String valor = str.nextToken();
                    regrasAG[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    public int carregaRegras(String[][] regrasAG, String nomeArquivo, int numVariaveisEntrada, int numRegras) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaRegras - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numRegras; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String valor = str.nextToken();
                    regrasAG[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

        return numRegras;
    }

    public String[][] carregaRegrasAD(String nomeArquivo, int numVariaveisEntrada, int numRegras) {
        BufferedReader inReader = null;
        String[][] regras = new String[numRegras][numVariaveisEntrada];

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaRegrasAD - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            inReader.readLine();
            inReader.readLine();

            for(int a = 0; a < numRegras; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String valor = str.nextToken();
                    regras[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

        return regras;
    }

    public int getNumRegras(String nomeArquivo) {
        BufferedReader inReader = null;
        int numRegras = 0;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var5) {
            System.err.println("getNumRegras - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            while(inReader.readLine() != null) {
                ++numRegras;
            }

            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return numRegras;
    }

    public int getNumRegrasAD(String nomeArquivo) {
        BufferedReader inReader = null;
        int numRegras = 0;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumRegrasAD - Não foi possível abrir o arquivo: " + nomeArquivo);
            System.exit(1);
        }

        try {
            inReader.readLine();
            String linha = inReader.readLine();
            StringTokenizer str = new StringTokenizer(linha);
            numRegras = Integer.parseInt(str.nextToken());
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return numRegras;
    }

    public String carregaArvore(int nVE, String arquivo) {
        this.getNumRegrasTreinamento2(arquivo);
        String regras = "";
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(arquivo));
        } catch (FileNotFoundException var10) {
            System.err.println("carregaArvore - Não foi possível abrir o arquivo: " + arquivo);
            System.exit(1);
        }

        try {
            String line;
            for(int var7 = 0; (line = inReader.readLine()) != null; ++var7) {
                StringTokenizer str = new StringTokenizer(line);

                for(int var9 = 0; str.hasMoreElements(); ++var9) {
                    regras = regras + "\t" + str.nextToken();
                }

                regras = regras + "\t***";
            }

            inReader.close();
        } catch (IOException var11) {
            System.err.println(var11.getMessage());
        }

        return regras;
    }

    public int getNumRegrasTreinamento(String arquivoTreinamento) {
        BufferedReader inReader = null;
        String valor = "";

        try {
            inReader = new BufferedReader(new FileReader(arquivoTreinamento));
        } catch (FileNotFoundException var7) {
            System.err.println("getNumRegrasTreinamento - Não foi possível abrir o arquivo: " + arquivoTreinamento);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            new StringTokenizer(line);
            valor = line.trim();
            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return Integer.parseInt(valor);
    }

    public int getNumRegrasTreinamento2(String arquivoTreinamento) {
        BufferedReader inReader = null;
        int indice = 0;

        try {
            inReader = new BufferedReader(new FileReader(arquivoTreinamento));
        } catch (FileNotFoundException var5) {
            System.err.println("getNumRegrasTreinamento2 - Não foi possível abrir o arquivo: " + arquivoTreinamento);
            System.exit(1);
        }

        try {
            while(inReader.readLine() != null) {
                ++indice;
            }

            inReader.close();
        } catch (IOException var6) {
            System.err.println(var6.getMessage());
        }

        return indice;
    }

    public void imprimeRegras(String[][] regras, int numRegras, int numLinhasPorPagina, String info, int numConjuntos) {
        this.saida = "Num: \t ";

        int a;
        for(a = 0; a < numConjuntos - 1; ++a) {
            this.saida = this.saida + "Conjunto " + a + "\t";
        }

        this.saida = this.saida + "Classe\t";
        this.saida = this.saida + "\n";

        for(a = 0; a < numConjuntos; ++a) {
            this.saida = this.saida + "___________________";
        }

        this.saida = this.saida + "\n";

        for(a = 0; a < numRegras; ++a) {
            this.saida = this.saida + (a + 1) + "\t";

            for(int b = 0; b < numConjuntos; ++b) {
                this.saida = this.saida + " " + regras[a][b] + "\t";
            }

            this.saida = this.saida + "\n";
            if (a % numLinhasPorPagina == 0 && a != 0 || a == numRegras - 1) {
                this.saida = this.saida + "\n";
                JTextArea outputArea = new JTextArea();
                outputArea.setText(this.saida);
                JOptionPane.showMessageDialog((Component)null, outputArea, info, -1);
                this.saida = "Num: \t";

                int aux;
                for(aux = 0; aux < numConjuntos - 1; ++aux) {
                    this.saida = this.saida + "Conjunto " + aux + "\t";
                }

                this.saida = this.saida + "Classe\t";
                this.saida = this.saida + "\n";

                for(aux = 0; aux < numConjuntos; ++aux) {
                    this.saida = this.saida + "___________________";
                }

                this.saida = this.saida + "\n";
            }
        }

    }

    public void imprimeMatrizComCabecalho(String[][] matriz, int numLinhas, int numColunas, boolean indice) {
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("0.000");
        String linha = "\t";

        int a;
        for(a = 1; a < numColunas + 1; ++a) {
            linha = linha + a + "\t";
        }

        System.out.println(linha);

        for(a = 0; a < numLinhas - 1; ++a) {
            linha = "";

            for(int b = 0; b < numColunas; ++b) {
                double lixo = Double.parseDouble(matriz[a][b]);
                linha = linha + formatador.format(lixo) + "\t";
            }

            if (indice) {
                System.out.println(a + 2 + "\t" + linha);
            } else {
                System.out.println(linha);
            }
        }

        linha = "";

        for(a = 0; a < numColunas; ++a) {
            linha = linha + matriz[numLinhas - 1][a] + "\t";
        }

        System.out.println("best\t" + linha);
    }

    public void imprimeMatriz(String[][] matriz, int numLinhas, int numColunas, boolean indice) {
        for(int a = 0; a < numLinhas; ++a) {
            String linha = "";

            for(int b = 0; b < numColunas; ++b) {
                linha = linha + matriz[a][b] + "\t";
            }

            if (indice) {
                System.out.println(a + "\t" + linha);
            } else {
                System.out.println(linha);
            }
        }

    }

    public void carregaArquivoTreinamento(float[][] treinamento, String nomeArquivo, int numVariaveisEntrada) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaArquivoTreinamento - Não foi possível abrir o arquivo " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line;
            for(int a = 0; (line = inReader.readLine()) != null; ++a) {
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String dado = str.nextToken();
                    float valor = Float.parseFloat(dado);
                    treinamento[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    public void carregaArquivoTreinamento2(float[][] treinamento, String nomeArquivo, int numVariaveisEntrada) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaArquivoTreinamento2 - Não foi possível abrir o arquivo " + nomeArquivo);
            System.exit(1);
        }

        try {
            int a = 0;

            for(String line = inReader.readLine(); (line = inReader.readLine()) != null; ++a) {
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < numVariaveisEntrada; ++b) {
                    String dado = str.nextToken();
                    float valor = Float.parseFloat(dado);
                    treinamento[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    public void imprimeTreinamento(float[][] treinamento, int numLinhasPorPagina, String info, int numRegrasTreinamento, int numConjuntos) {
        this.saida = "Num: \t ";

        int a;
        for(a = 0; a < numConjuntos - 1; ++a) {
            this.saida = this.saida + "Conjunto " + a + "\t";
        }

        this.saida = this.saida + "Classe\t";
        this.saida = this.saida + "\n";

        for(a = 0; a < numConjuntos; ++a) {
            this.saida = this.saida + "___________________";
        }

        this.saida = this.saida + "\n";

        for(a = 0; a < numRegrasTreinamento; ++a) {
            this.saida = this.saida + (a + 1) + "\t";

            for(int b = 0; b < numConjuntos; ++b) {
                this.saida = this.saida + " " + treinamento[a][b] + "\t";
            }

            this.saida = this.saida + "\n";
            if (a % numLinhasPorPagina == 0 && a != 0 || a == numRegrasTreinamento - 1) {
                this.saida = this.saida + "\n";
                JTextArea outputArea = new JTextArea();
                outputArea.setText(this.saida);
                JOptionPane.showMessageDialog((Component)null, outputArea, info, -1);
                this.saida = "Num: \t";

                int aux;
                for(aux = 0; aux < numConjuntos - 1; ++aux) {
                    this.saida = this.saida + "Conjunto " + aux + "\t";
                }

                this.saida = this.saida + "Classe\t";
                this.saida = this.saida + "\n";

                for(aux = 0; aux < numConjuntos; ++aux) {
                    this.saida = this.saida + "___________________";
                }

                this.saida = this.saida + "\n";
            }
        }

    }

    public void imprimeGrausdeCobertura(String[][] regras, int lin, float[] grauCobertura, int numLinhasPorPagina) {
        this.saida = "Regra\tGrau de cobertura\n";
        this.saida = this.saida + "_____________________________\n";
        DecimalFormat formatador = new DecimalFormat();
        formatador.applyPattern("#0.0#");

        for(int a = 0; a < lin; ++a) {
            this.saida = this.saida + (a + 1) + "\t" + formatador.format((double)grauCobertura[a]) + "\n";
            if (a % numLinhasPorPagina == 0 && a != 0 || a == lin - 1) {
                JTextArea outputArea = new JTextArea();
                outputArea.setText(this.saida);
                JOptionPane.showMessageDialog((Component)null, outputArea, "Graus de Cobertura das Regras", -1);
                this.saida = "Regra\tGrau de cobertura\n";
                this.saida = this.saida + "_____________________________\n";
            }
        }

    }

    public int comparaRegras(String[][] regrasGCSelecionadas, String[][] regrasCriadasWM, int numRegrasParaComparacao, int numVarEntrada, int numRegrasCriadasWM) {
        boolean flag = true;
        int numRegrasIguais = 0;
        System.out.println("comparaRegras - Numero de regras pra comparacao: " + numRegrasParaComparacao);

        for(int a = 0; a < numRegrasCriadasWM; ++a) {
            for(int b = 0; b < numRegrasParaComparacao; ++b) {
                for(int c = 0; c < numVarEntrada; ++c) {
                    if (!regrasCriadasWM[a][c].equals(regrasGCSelecionadas[b][c])) {
                        flag = false;
                        c = numVarEntrada;
                    }
                }

                if (flag) {
                    ++numRegrasIguais;
                    b = numRegrasParaComparacao;
                }

                if (!flag) {
                    flag = true;
                }
            }
        }

        this.saida = "Numero de regras criadas por W&M: " + numRegrasCriadasWM + "\n Numero de regras iguais (Grau de cobertura e Wang&Mendell): " + numRegrasIguais;
        JTextArea outputArea = new JTextArea();
        outputArea.setText(this.saida);
        JOptionPane.showMessageDialog((Component)null, outputArea, "Resultado da comparação: ", 1);
        System.out.println("Numero de regras iguais: " + numRegrasIguais);
        return numRegrasIguais;
    }

    public void gravaArquivo(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.close();
        } catch (IOException var10) {
            System.err.println(var10);
            System.exit(1);
        }

    }

    public void gravaListaAtribs(String atribs, String nomeArquivo, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            StringTokenizer str = new StringTokenizer(atribs);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            for(int i = 0; i < numVariaveisEntrada; ++i) {
                line = line + str.nextToken() + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var9) {
            System.err.println(var9);
            System.exit(1);
        }

    }

    public void gravaArvore(String arvore, String nomeArquivo) {
        BufferedWriter buf_writer = null;
        String arvore2 = arvore.replaceFirst("J48 pruned tree", "FuzzyDT");

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            buf_writer.write(arvore2);
            buf_writer.close();
        } catch (IOException var6) {
            System.err.println(var6);
            System.exit(1);
        }

    }

    public String carregaArvore(String arquivo) {
        String regras = "";
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(arquivo));
        } catch (FileNotFoundException var6) {
            System.err.println("carregaArvore - Não foi possível abrir o arquivo: " + arquivo);
            System.exit(1);
        }

        try {
            String line;
            while((line = inReader.readLine()) != null) {
                regras = regras + line;
            }

            inReader.close();
        } catch (IOException var7) {
            System.err.println(var7.getMessage());
        }

        return regras;
    }

    public void gravaArquivos10Fold(float[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "" + numRegras;
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.close();
        } catch (IOException var10) {
            System.err.println(var10);
            System.exit(1);
        }

    }

    public void gravaArquivosFoldSeparado(float[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.close();
        } catch (IOException var10) {
            System.err.println(var10);
            System.exit(1);
        }

    }

    public void gravaEspacoBusca(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.close();
        } catch (IOException var10) {
            System.err.println(var10);
            System.exit(1);
        }

    }

    public void gravaParticao(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            int i;
            for(i = 0; i <= numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < 5; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.close();
        } catch (IOException var10) {
            System.err.println(var10);
            System.exit(1);
        }

    }

    public void gravaArquivo2(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "" + (numVariaveisEntrada - 1);
            buf_writer.write(line);
            buf_writer.newLine();
            line = "" + numRegras;
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    public void gravaCromos(float[][] matriz, String nomeArquivo, int numCromos, int numRegrasPorCromo) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            for(int i = 0; i < numCromos; ++i) {
                line = "";

                for(int j = 0; j < numRegrasPorCromo; ++j) {
                    line = line + matriz[i][j];
                    line = line + "\t";
                }

                buf_writer.write(line);
                buf_writer.newLine();
            }

            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    public void gravaArquivoParaAG(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = numVariaveisEntrada - 1 + "";
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";
            line = numRegras + "";
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    public void gravaBaseParaAG(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada, float fitness) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = numVariaveisEntrada + "";
            line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            line = "Fitness: ";
            line = line + fitness + "";
            buf_writer.write(line);
            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    public void gravaCromossomo(float[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = numVariaveisEntrada - 1 + "";
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";
            line = numRegras + "";

            for(int i = 0; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j <= numVariaveisEntrada; ++j) {
                        if (j <= numVariaveisEntrada - 1) {
                            int temp = (int)matriz[i][j];
                            line = line + temp;
                            line = line + "\t";
                        } else {
                            line = line + matriz[i][j];
                            line = line + "\t";
                        }
                    }
                }
            }

            line = "";
            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    public void gravaRegras(float[][] matriz, String nomeArquivo, int numRegras, int numItens) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);

            for(int i = 0; i <= numRegras; ++i) {
                String line = "";

                for(int j = 0; j <= numItens; ++j) {
                    float temp = matriz[i][j];
                    line = line + temp;
                    line = line + "\t";
                }

                buf_writer.write(line);
                buf_writer.newLine();
            }

            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    public void gravaRegrasCromos(int[][] matriz, String nomeArquivo, int numIteracoes, int numItens) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);

            for(int i = 0; i < numIteracoes; ++i) {
                String line = "";

                for(int j = 0; j < numItens; ++j) {
                    float temp = (float)matriz[i][j];
                    line = line + temp;
                    line = line + "\t";
                }

                buf_writer.write(line);
                buf_writer.newLine();
            }

            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    public void gravaFitness(float[][] matriz, String nomeArquivo, int numIteracoes, int numItens) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);

            for(int i = 0; i <= numIteracoes; ++i) {
                String line = "";

                for(int j = 0; j <= numItens; ++j) {
                    float temp = matriz[i][j];
                    line = line + temp;
                    line = line + "\t";
                }

                buf_writer.write(line);
                buf_writer.newLine();
            }

            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    public String[] coletaNomesArquivos() {
//        int opcao = false;
//        int opcao2 = false;
        String[] temp = new String[4];
        JFileChooser fC = new JFileChooser();
        fC.setCurrentDirectory(new File("."));
        fC.setDialogTitle("Arquivo de treinamento");
        int valorRetorno = fC.showOpenDialog(this.componente);
        File file;
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[1] = file.getName();
        }

        fC.setDialogTitle("Arquivo de partição");
        valorRetorno = fC.showOpenDialog(this.componente);
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[3] = file.getName();
            fC.setDialogTitle("Arquivo de regras");
            valorRetorno = fC.showOpenDialog(this.componente);
            if (valorRetorno == 0) {
                file = fC.getSelectedFile();
                temp[2] = file.getName();
            }
        }

        return temp;
    }

    public String[] coletaNomesArquivosSFC() {
//        int opcao = false;
//        int opcao2 = false;
        String[] temp = new String[3];
        JFileChooser fC = new JFileChooser();
        fC.setCurrentDirectory(new File("."));
        fC.setDialogTitle("Arquivo com dados a serem classificados");
        int valorRetorno = fC.showOpenDialog(this.componente);
        File file;
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[0] = file.getName();
        } else {
            fC.setDialogTitle("Arquivo com a Base de Regras do SFC");
        }

        valorRetorno = fC.showOpenDialog(this.componente);
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[1] = file.getName();
        } else {
            fC.setDialogTitle("Arquivo com a particao do SFC");
        }

        valorRetorno = fC.showOpenDialog(this.componente);
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[2] = file.getName();
        }

        return temp;
    }

    public String[] coletaNomesArquivosAG() {
//        int opcao = false;
//        int opcao2 = false;
        String[] temp = new String[3];
        JFileChooser fC = new JFileChooser();
        fC.setCurrentDirectory(new File("."));
        fC.setDialogTitle("Arquivo com dados de treinamento");
        int valorRetorno = fC.showOpenDialog(this.componente);
        File file;
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[0] = file.getName();
        } else {
            JOptionPane.showInputDialog("Não foi possível abrir o arquivo...");
        }

        fC.setDialogTitle("Arquivo com as regras para as BDs do AG");
        valorRetorno = fC.showOpenDialog(this.componente);
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[1] = file.getName();
        } else {
            JOptionPane.showInputDialog("Não foi possível abrir o arquivo...");
        }

        fC.setDialogTitle("Arquivo com a particao do AG");
        valorRetorno = fC.showOpenDialog(this.componente);
        if (valorRetorno == 0) {
            file = fC.getSelectedFile();
            temp[2] = file.getName();
        } else {
            JOptionPane.showInputDialog("Não foi possível abrir o arquivo...");
        }

        return temp;
    }

    public String[] defineVariaveisAG() {
        String[] temp = new String[10];
        temp[4] = JOptionPane.showInputDialog("Qual o número total de regras em cada cromossomo?", 135);
        temp[6] = "100";
        temp[7] = temp[4];
        temp[1] = "0.05";
        temp[5] = "1";
        temp[2] = "0.7";
        temp[3] = "0.09";
        temp[8] = "1";
        temp[9] = "100";
        if (temp[8].compareTo("1") == 0) {
            temp[8] = "classico";
        }

        if (temp[8].compareTo("2") == 0) {
            temp[8] = "geral";
        }

        return temp;
    }

    public void carregaPadroes(float[][] tre, String nomeArquivo, int nVE, int numPadroes) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var12) {
            System.err.println("carregaPadroes - Não foi possível abrir o arquivo " + nomeArquivo);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            line = inReader.readLine();

            for(int a = 0; a < numPadroes; ++a) {
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < nVE; ++b) {
                    String dado = str.nextToken();
                    float valor = Float.parseFloat(dado);
                    tre[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var13) {
            System.err.println(var13.getMessage());
        }

    }

    public void carregaPadroesAG(float[][] tre, String nomeArquivo, int nVE, int numPadroes) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var12) {
            System.err.println("mA.carregaPadroesAG - Não foi possível abrir o arquivo " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();

            for(int a = 0; a < numPadroes; ++a) {
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; str.hasMoreElements() && b < nVE; ++b) {
                    String dado = str.nextToken();
                    float valor = Float.parseFloat(dado);
                    tre[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var13) {
            System.err.println(var13.getMessage());
        }

    }

    public void carregaTreinamento(float[][] tre, String nomeArquivo, int nVE, int numPadroes) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var12) {
            System.err.println("carregaTreinamento - Não foi possível abrir o arquivo " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numPadroes; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < nVE; ++b) {
                    String dado = str.nextToken();
                    float valor = Float.parseFloat(dado);
                    tre[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var13) {
            System.err.println(var13.getMessage());
        }

    }

//    public void carregaTreinamentoAndre(ArrayList<Exemplo> tre, String nomeArquivo, int nVE, int numPadroes) {
//        BufferedReader inReader = null;
//        try {
//            inReader = new BufferedReader(new FileReader(nomeArquivo));
//        } catch (FileNotFoundException var12) {
//            System.err.println("carregaTreinamento - Não foi possível abrir o arquivo " + nomeArquivo);
//            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
//            System.exit(1);
//        }
//
//        try {
//            for(int a = 0; a < numPadroes; ++a) {
//                String line = inReader.readLine();
//                StringTokenizer str = new StringTokenizer(line);
//                Exemplo exemplo = new Exemplo(nVE);
//                for(int b = 0; b < nVE; ++b) {
//                    String dado = str.nextToken();
//                    float valor = Float.parseFloat(dado);
//                    exemplo.valores[b] = valor;
//                }
//                tre.add(exemplo);
//            }
//
//            inReader.close();
//        } catch (IOException var13) {
//            System.err.println(var13.getMessage());
//        }
//
//    }

    public void carregaExemplosAG(float[][] tre, String nomeArquivo, int nVE, int numPadroes) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var12) {
            System.err.println("carregaExemplosAG - Não foi possível abrir o arquivo " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numPadroes; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);

                for(int b = 0; b < nVE; ++b) {
                    String dado = str.nextToken();
                    float valor = Float.parseFloat(dado);
                    tre[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var13) {
            System.err.println(var13.getMessage());
        }

    }

    public void carregaArquivos10Fold(float[][] tre, String nomeArquivo, int nVE, int numPadroes) {
        BufferedReader inReader = null;
        int a = 0;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaArquivos10Fold  - Não foi possível abrir o arquivo " + nomeArquivo);
            System.exit(1);
        }

        try {
            for(String line = inReader.readLine(); (line = inReader.readLine()) != null; ++a) {
                StringTokenizer str = new StringTokenizer(line, ", ");

                for(int b = 0; b < nVE; ++b) {
                    float valor = Float.parseFloat(str.nextToken());
                    tre[a][b] = valor;
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    void gravaTudo(float[][] fitnessCromos, float[][] numRegrasCromos, String nomeArquivo, int numCromos, int iteracoesAG) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            for(int i = 0; i < numCromos; ++i) {
                line = "";

                for(int j = 0; j < iteracoesAG; ++j) {
                    line = line + fitnessCromos[i][j];
                    line = line + "\t";
                    line = line + numRegrasCromos[i][j];
                    line = line + "\t";
                }

                buf_writer.write(line);
                buf_writer.newLine();
            }

            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    float[] carregaParametros(String nomeArquivo) {
        BufferedReader inReader = null;
        float[] parametros = new float[4];

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var7) {
            System.err.println("carregaParametros - Não foi possível abrir o arquivo:: " + nomeArquivo);
            System.exit(1);
        }

        try {
            for(int a = 0; a < 4; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);
                parametros[a] = Float.parseFloat(str.nextToken());
            }
        } catch (IOException var8) {
            System.err.println(var8.getMessage());
        }

        return parametros;
    }

    public void carregaFriedman(String[][] friedman, String nomeArquivo, int numVariaveisEntrada, int numRegras) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (FileNotFoundException var11) {
            System.err.println("Não foi possível abrir o arquivo: " + nomeArquivo);
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + nomeArquivo, "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            for(int a = 0; a < numRegras; ++a) {
                String line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);
                String valor;
                int b;
                if (a == 0) {
                    friedman[a][0] = "";

                    for(b = 1; b < numVariaveisEntrada; ++b) {
                        valor = str.nextToken();
                        friedman[a][b] = valor;
                    }
                } else {
                    for(b = 0; b < numVariaveisEntrada; ++b) {
                        valor = str.nextToken();
                        friedman[a][b] = valor;
                    }
                }
            }

            inReader.close();
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

    }

    public void gravaBaseRegras(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada) {
        BufferedWriter buf_writer = null;
        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = numVariaveisEntrada + "";
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";
            line = numRegras + "";
            buf_writer.write(line);
            buf_writer.newLine();
            line = "";

            int i;
            for(i = 0; i < numVariaveisEntrada; ++i) {
                line = line + matriz[0][i] + "\t";
            }

            for(i = 1; i <= numRegras; ++i) {
                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
                if (i < numRegras) {
                    for(int j = 0; j < numVariaveisEntrada; ++j) {
                        line = line + matriz[i][j];
                        line = line + "\t";
                    }
                }
            }

            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    public void gravaBRparaUsuario(String[][] matriz, String nomeArquivo, int numRegras, int numVariaveisEntrada, String dataset, String caminho) {
        BufferedReader inReader = null;
        String line = "";
        String[] nomes = new String[numVariaveisEntrada];

        try {
            inReader = new BufferedReader(new FileReader(caminho + dataset + ".names"));
        } catch (FileNotFoundException var15) {
            System.err.println("gravaBRparaUsuario - Não foi possível abrir o arquivo: " + caminho + dataset + ".names");
            System.exit(1);
        }

        try {
            for(int a = 0; a < numVariaveisEntrada; ++a) {
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);
                nomes[a] = str.nextToken();
            }

            inReader.close();
        } catch (IOException var17) {
            System.err.println(var17.getMessage());
        }

        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            buf_writer.write(dataset + " - Total: " + numRegras + " rules.");
            buf_writer.newLine();
            buf_writer.newLine();

            for(int a = 0; a < numRegras; ++a) {
                line = "IF";
                boolean teste = false;

                for(int b = 0; b < numVariaveisEntrada - 1; ++b) {
                    if (matriz[a][b].compareTo("dc") != 0) {
                        if (!teste) {
                            line = line + " " + nomes[b] + " IS " + matriz[a][b] + " ";
                            teste = true;
                        } else if (teste) {
                            line = line + " AND " + nomes[b] + " IS " + matriz[a][b] + " ";
                        }
                    }
                }

                line = line + "THEN CLASS IS " + matriz[a][numVariaveisEntrada - 1];
                buf_writer.write(line);
                buf_writer.newLine();
            }

            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var16) {
            System.err.println(var16);
            System.exit(1);
        }

    }

    public void contaConjuncoes(String caminho) {
        BufferedReader inReader = null;

        try {
            inReader = new BufferedReader(new FileReader(caminho + "arvores.txt"));
        } catch (FileNotFoundException var9) {
            System.err.println("contaConjuncoes - Não foi possível abrir o arquivo: " + caminho + "arvores.txt");
            JOptionPane.showMessageDialog((Component)null, "Não foi possível abrir o arquivo: " + caminho + "arvores.txt", "Sistema Fuzzy  Classificador", 1);
            System.exit(1);
        }

        try {
            String line = inReader.readLine();
            StringTokenizer str = new StringTokenizer(line);
            new Vector();

            while(str.hasMoreElements()) {
                String token = str.nextToken();
                String regras = "";
                if (token.compareTo("Relation:") == 0) {
                    token = str.nextToken();
                    System.out.println(token + "\n");
                    line = inReader.readLine();
                    str = new StringTokenizer(line);
                    token = str.nextToken();
                    if (token.contains("------")) {
                        token = str.nextToken();
                        System.out.println(token + "\n");

                        for(token = str.nextToken(); str.hasMoreElements() && !token.contains("Time"); token = str.nextToken()) {
                            while(str.hasMoreElements() && !token.contains("Time")) {
                                regras = regras + str.nextToken();
                                str.nextToken();
                            }

                            line = inReader.readLine();
                            str = new StringTokenizer(line);
                        }

                        System.out.println(regras);
                    }
                }

                if (token.contains("(") && token.contains("/")) {
                    token = str.nextToken();
                }

                line = inReader.readLine();
                str = new StringTokenizer(line);
                token = str.nextToken();
            }

            inReader.close();
        } catch (IOException var10) {
            System.err.println(var10.getMessage());
        }

    }

    public void contaConjuncoesArvore(String dataset, String caminho) {
        float[] regra = new float[10];
        float[] conjun = new float[10];

        for(int fold = 0; fold < 10; ++fold) {
            BufferedReader inReader = null;
            String line = "";
            int conjuncoes = 0;
            int regras = 0;

            try {
                inReader = new BufferedReader(new FileReader(caminho + dataset + "_" + fold + "_arvores.txt"));
            } catch (FileNotFoundException var12) {
                System.err.println("contaConjuncoesArvore - Não foi possível abrir o arquivo: " + caminho + dataset + "_" + fold + "_arvores.txt");
                System.exit(1);
            }

            try {
                label46:
                while((line = inReader.readLine()) != null) {
                    StringTokenizer str = new StringTokenizer(line);

                    while(true) {
                        String token;
                        do {
                            if (!str.hasMoreTokens()) {
                                continue label46;
                            }

                            token = str.nextToken();
                        } while(!token.contains("=") && !token.contains("|"));

                        ++conjuncoes;
                        if (token.contains("=")) {
                            ++regras;
                        }
                    }
                }

                regra[fold] = (float)regras;
                conjun[fold] = (float)conjuncoes;
                inReader.close();
            } catch (IOException var13) {
                System.err.println(var13.getMessage());
            }
        }

        float[] regrasDP = new float[2];
        regrasDP = this.calculaDesvioPadrao(regra, 10);
        float[] conjuncoesDP = new float[2];
        conjuncoesDP = this.calculaDesvioPadrao(conjun, 10);
        System.out.println(regrasDP[0] + "\t" + regrasDP[1] + "\t" + conjuncoesDP[0] + "\t" + conjuncoesDP[1]);
    }

    public void gravaArquivoARFF(String[][] dadosFuzzificados, String caminho, String dataset, int numObjetos, int nVE) {
        BufferedReader inReader = null;
        String linha = "";

        try {
            inReader = new BufferedReader(new FileReader(caminho + dataset + ".names"));
        } catch (FileNotFoundException var17) {
            System.err.println("gravaArquivoARFF - Não foi possível abrir o arquivo: " + caminho + dataset + ".names");
            System.exit(1);
        }

        try {
            BufferedWriter buf_writer = null;
            int[] dadosCjtos = new int[nVE - 1];
            dadosCjtos = this.getDadosParticao(caminho + dataset + "DadosParticao.txt", nVE);

            try {
                FileWriter writer = new FileWriter(caminho + dataset + "Fuzzy.arff");
                buf_writer = new BufferedWriter(writer);
            } catch (Exception var16) {
            }

            try {
                String line = "@RELATION " + dataset;
                buf_writer.write(line);
                buf_writer.newLine();
                buf_writer.newLine();

                int a;
                int numTermos;
                for(a = 0; a < nVE; ++a) {
//                    int numTermos = false;
                    if (a == nVE - 1) {
                        numTermos = 0;
                    } else {
                        numTermos = dadosCjtos[a];
                    }

                    String[] termos = new String[numTermos];
                    if (numTermos == 2) {
                        termos[0] = "baixa";
                        termos[1] = "alta";
                    } else if (numTermos == 3) {
                        termos[0] = "baixa";
                        termos[1] = "media";
                        termos[2] = "alta";
                    } else if (numTermos == 4) {
                        termos[0] = "baixa";
                        termos[1] = "mediabaixa";
                        termos[2] = "mediaalta";
                        termos[3] = "alta";
                    } else if (numTermos == 5) {
                        termos[0] = "baixa";
                        termos[1] = "mediabaixa";
                        termos[2] = "media";
                        termos[3] = "mediaalta";
                        termos[4] = "alta";
                    } else if (numTermos == 6) {
                        termos[0] = "baixissima";
                        termos[1] = "baixa";
                        termos[2] = "mediabaixa";
                        termos[3] = "mediaalta";
                        termos[4] = "alta";
                        termos[5] = "altissima";
                    } else if (numTermos == 7) {
                        termos[0] = "baixissima";
                        termos[1] = "baixa";
                        termos[2] = "mediabaixa";
                        termos[3] = "media";
                        termos[4] = "mediaalta";
                        termos[5] = "alta";
                        termos[6] = "altissima";
                    } else if (numTermos == 8) {
                        termos[0] = "baixissima";
                        termos[1] = "muitobaixa";
                        termos[2] = "baixa";
                        termos[3] = "mediabaixa";
                        termos[4] = "mediaalta";
                        termos[5] = "alta";
                        termos[6] = "muitoalta";
                        termos[7] = "altissima";
                    } else {
                        for(a = 0; a < numTermos; ++a) {
                            termos[a] = "termo" + a;
                        }
                    }

                    line = inReader.readLine();
                    if(line == null) {
                        break;
                    }
                    StringTokenizer str = new StringTokenizer(line);
                    linha = "@ATTRIBUTE " + str.nextToken() + "\t{";
                    if (!line.contains("double") && !line.contains("float") && !line.contains("real") && !line.contains("integer")) {
                        while(str.hasMoreTokens()) {
                            linha = linha + str.nextToken();
                            if (str.hasMoreTokens()) {
                                linha = linha + ",";
                            }
                        }

                        linha = linha + "}";
                    } else {
                        for(int cjtos = 0; cjtos < numTermos - 1; ++cjtos) {
                            linha = linha + termos[cjtos] + ",";
                        }

                        linha = linha + termos[numTermos - 1] + "}";
                    }

                    buf_writer.write(linha);
                    buf_writer.newLine();
                    linha = "";
                }

                buf_writer.newLine();
                buf_writer.newLine();
                linha = "@DATA";
                buf_writer.write(linha);
                buf_writer.newLine();
                linha = "";

                for(a = 0; a < numObjetos; ++a) {
                    for(numTermos = 0; numTermos < nVE - 1; ++numTermos) {
                        linha = linha + dadosFuzzificados[a][numTermos] + ",";
                    }

                    linha = linha + dadosFuzzificados[a][nVE - 1];
                    buf_writer.write(linha);
                    buf_writer.newLine();
                    linha = "";
                }

                buf_writer.close();
            } catch (IOException var18) {
                System.err.println(var18);
                System.exit(1);
            }

            inReader.close();
        } catch (IOException var19) {
            System.err.println(var19.getMessage());
        }

    }

    void gravaContexto(boolean[][] contexto, int numObjetos, int numConjuntos, String dataset, String caminho) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + dataset + "Contexto.txt", true);
            buf_writer = new BufferedWriter(writer);
            String line = "";

            for(int i = 0; i < numObjetos; ++i) {
                for(int j = 0; j < numConjuntos; ++j) {
                    if (contexto[i][j]) {
                        line = line + "1\t";
                    } else {
                        line = line + "0\t";
                    }
                }

                buf_writer.write(line);
                buf_writer.newLine();
                line = "";
            }

            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    private void gravaNomes(String caminho, String dataset, Vector[] nomes) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + dataset + "Nomes.txt");
            buf_writer = new BufferedWriter(writer);
            String line = "";

            int a;
            for(a = 0; a < nomes[0].size(); ++a) {
                line = line + nomes[0].get(a) + "\t";
            }

            buf_writer.write(line);
            line = "";
            buf_writer.newLine();

            for(a = 0; a < nomes[1].size(); ++a) {
                line = line + nomes[1].get(a) + "\t";
            }

            buf_writer.write(line);
            buf_writer.newLine();
            buf_writer.close();
        } catch (IOException var8) {
            System.err.println(var8);
            System.exit(1);
        }

    }

    void gravaConceitos(Vector[] nomes, Vector conceitos, String caminho, String dataset, int numAtribs, int aa) {
        BufferedWriter buf_writer = null;
        String concepts = "";
        String var9 = "";

        try {
            FileWriter writer;
            if (aa == 0) {
                writer = new FileWriter(caminho + "Regras_FCA_" + dataset + ".txt");
            } else {
                writer = new FileWriter(caminho + "Regras_FCA_" + dataset + ".txt", true);
            }

            concepts = conceitos.toString();
            buf_writer = new BufferedWriter(writer);
            new StringTokenizer(concepts);
            int numRegras = 1;

            for(int i = 0; i < conceitos.size(); ++i) {
                String binme = conceitos.get(i) + "";
                if (binme.contains("class")) {
                    ++numRegras;
                }
            }

            String Conc2 = concepts.replace("[", "");
            concepts = Conc2.replace("]", " ");
            Conc2 = concepts.replace("null", "");
            Conc2 = Conc2.replace("{", "");
            concepts = Conc2.replace("Concept # ", "");
            Conc2 = concepts.replace("%", " ;");
            concepts = Conc2.replace(",", "");
            Conc2 = concepts.replace("}", "");
            concepts = Conc2.replace(":", "");
            Conc2 = concepts.replace("atrib", "");
            concepts = Conc2.replace("]", "");
            Conc2 = null;
            String[][] RegrasFinais = new String[numRegras - 1][numAtribs + 1];

            int indice;
            for(indice = 0; indice < numRegras - 1; ++indice) {
                for(int b = 0; b < numAtribs; ++b) {
                    RegrasFinais[indice][b] = "dc";
                }
            }

            indice = 0;
            StringTokenizer str = new StringTokenizer(concepts);
            String token = "";

            int b;
            while(str.hasMoreTokens()) {
                token = str.nextToken();
                if (token.contains("empty")) {
                    token = str.nextToken();
                    token = str.nextToken();
                }

                if (!token.contains(";")) {
                    String tok = token.replace("_", " ");
                    StringTokenizer str2 = new StringTokenizer(tok);
                    String tok2 = str2.nextToken();
                    if (tok2.contains("class")) {
                        RegrasFinais[indice][numAtribs - 1] = str2.nextToken();
                        RegrasFinais[indice][numAtribs] = str.nextToken();
                        ++indice;
                        token = str.nextToken();
                    } else {
                        b = Integer.parseInt(tok2) - 1;
                        RegrasFinais[indice][b] = str2.nextToken();
                    }
                }
            }

            boolean imprime = false;
            int a;
            if (imprime) {
                for(a = 0; a < numRegras - 1; ++a) {
                    for(a = 0; a <= numAtribs; ++a) {
                        System.out.print(RegrasFinais[a][a] + "\t");
                    }

                    System.out.println();
                }
            }

            String line = "";

            for(a = 0; a < numRegras - 1; ++a) {
                for(b = 0; b <= numAtribs; ++b) {
                    line = line + RegrasFinais[a][b] + "\t";
                }

                line = line + "\n";
                buf_writer.write(line);
                line = "";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var21) {
            System.err.println(var21);
            System.exit(1);
        }

    }

    void gravaNumConceitos(String caminho, String dataset, int numConceitos) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + dataset + "RegrasDeConceitos.txt", true);
            buf_writer = new BufferedWriter(writer);
            String line = "Numero de regras: " + numConceitos;
            buf_writer.newLine();
            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var7) {
            System.err.println(var7);
            System.exit(1);
        }

    }

    public void regras(String dataset, String caminho, String alg) {
        BufferedReader inReader = null;
        int regra = 0;
        int conjuncoes = 0;
        float[] Regras = new float[10];
        float[] Conjuncoes = new float[10];

        for(int a = 0; a < 10; ++a) {
            String line;
            String token;
            StringTokenizer str;
            if (alg.contains("j48")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + dataset + "ArvoreJ48" + a + ".txt"));
                } catch (FileNotFoundException var15) {
                    System.err.println("regras -fdt- Não foi possível abrir o arquivo: " + caminho + dataset + "ArvoreJ48" + a + ".txt");
                    System.exit(1);
                }

                try {
                    label203:
                    while(true) {
                        do {
                            if ((line = inReader.readLine()) == null) {
                                regra -= 2;
                                --conjuncoes;
                                Regras[a] = (float)regra;
                                Conjuncoes[a] = (float)conjuncoes;
                                inReader.close();
                                break label203;
                            }
                        } while(!line.contains(":"));

                        ++regra;
                        ++conjuncoes;
                        str = new StringTokenizer(line);

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("|")) {
                                ++conjuncoes;
                            }
                        }
                    }
                } catch (IOException var23) {
                    System.err.println(var23.getMessage());
                }
            }

            if (alg.contains("fdt")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + dataset + "_" + a + "_ArvoreJ48.txt"));
                } catch (FileNotFoundException var22) {
                    System.err.println("regras -fdt- Não foi possível abrir o arquivo: " + caminho + dataset + "_" + a + "_ArvoreJ48.txt");
                    System.exit(1);
                }

                try {
                    label221:
                    while(true) {
                        do {
                            if ((line = inReader.readLine()) == null) {
                                Regras[a] = (float)regra;
                                Conjuncoes[a] = (float)conjuncoes;
                                inReader.close();
                                break label221;
                            }
                        } while(!line.contains(":"));

                        ++regra;
                        ++conjuncoes;
                        str = new StringTokenizer(line);

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("|")) {
                                ++conjuncoes;
                            }
                        }
                    }
                } catch (IOException var24) {
                    System.err.println(var24.getMessage());
                }
            }

            if (alg.contains("fca")) {
                caminho = "C:/Marcos/SFuzzySemIG/datasets/" + dataset + "_FSS/";

                try {
                    inReader = new BufferedReader(new FileReader(caminho + "AGIntMelhorBase" + dataset + "_FSS-treinamentoC" + a + ".txt"));
                } catch (FileNotFoundException var21) {
                    System.err.println("regras -fca- Não foi possível abrir o arquivo: " + caminho + "AGIntMelhorBase" + dataset + "_FSS-treinamentoC" + a + ".txt");
                    System.exit(1);
                }

                try {
                    int atribs = Integer.parseInt(inReader.readLine()) - 1;
                    regra = Integer.parseInt(inReader.readLine());
                    int dcs = 0;

                    while(true) {
                        if ((line = inReader.readLine()) == null) {
                            Regras[a] = (float)regra;
                            Conjuncoes[a] = (float)(atribs * regra - dcs);
                            inReader.close();
                            break;
                        }

                        str = new StringTokenizer(line);

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("dc")) {
                                ++dcs;
                            }
                        }
                    }
                } catch (IOException var25) {
                    System.err.println(var25.getMessage());
                }
            }

            if (alg.contains("mplcs")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "s0e0.txt"));
                } catch (FileNotFoundException var20) {
                    System.err.println("regras -mplcs- Não foi possível abrir o arquivo " + caminho + "result0s" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();

                    label261:
                    while(true) {
                        do {
                            if ((line = inReader.readLine()) == null) {
                                inReader.close();
                                break label261;
                            }
                        } while(!line.contains("Phenotype"));

                        for(line = inReader.readLine(); !line.contains("Statistics"); line = inReader.readLine()) {
                            str = new StringTokenizer(line);

                            while(str.hasMoreTokens()) {
                                token = str.nextToken();
                                if (token.contains("is")) {
                                    ++conjuncoes;
                                }

                                if (token.contains(":")) {
                                    ++regra;
                                }
                            }
                        }
                    }
                } catch (IOException var26) {
                    System.err.println(var26.getMessage());
                }

                Regras[a] = Float.parseFloat(Integer.toString(regra));
                Conjuncoes[a] = Float.parseFloat(Integer.toString(conjuncoes));
            }

            if (alg.contains("C45")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "e0.txt"));
                } catch (FileNotFoundException var19) {
                    System.err.println("regras -c45- Não foi possível abrir o arquivo " + caminho + "result" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();

                    while((line = inReader.readLine()) != null) {
                        str = new StringTokenizer(line);

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("NumberOfLeafs")) {
                                regra = Integer.parseInt(str.nextToken());
                            }

                            if (token.contains("NumberOfAntecedentsByRule")) {
                                conjuncoes = (int)((float)regra * Float.parseFloat(str.nextToken()));
                            }
                        }
                    }

                    inReader.close();
                } catch (IOException var27) {
                    System.err.println(var27.getMessage());
                }

                Regras[a] = Float.parseFloat(Integer.toString(regra));
                Conjuncoes[a] = Float.parseFloat(Integer.toString(conjuncoes));
            }

            if (alg.contains("SLAVE")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "s0e0.txt"));
                } catch (FileNotFoundException var18) {
                    System.err.println("regras -slave- Não foi possível abrir o arquivo " + caminho + "result" + a + "s0e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();
                    String line2 = "";
                    if (line.contains("Number of rules = ")) {
                        line2 = line.replace("Number of rules = ", "");
                    }

                    regra = Integer.parseInt(line2);

                    while((line = inReader.readLine()) != null) {
                        str = new StringTokenizer(line);

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("L")) {
                                ++conjuncoes;
                            }
                        }
                    }

                    inReader.close();
                } catch (IOException var28) {
                    System.err.println(var28.getMessage());
                }

                if ((double)regra == 1.0D) {
                    conjuncoes = 1;
                }

                Regras[a] = Float.parseFloat(Integer.toString(regra));
                Conjuncoes[a] = Float.parseFloat(Integer.toString(conjuncoes));
            }

            if (alg.contains("PART")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "e0.txt"));
                } catch (FileNotFoundException var17) {
                    System.err.println("PART -PART- Não foi possível abrir o arquivo " + caminho + "result" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();

                    while((line = inReader.readLine()) != null) {
                        str = new StringTokenizer(line, ", ");

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("=") || token.contains(">") || token.contains("<")) {
                                ++conjuncoes;
                            }

                            if (token.contains("output")) {
                                ++regra;
                            }
                        }
                    }

                    inReader.close();
                    conjuncoes += regra;
                } catch (IOException var29) {
                    System.err.println(var29.getMessage());
                }

                Regras[a] = Float.parseFloat(Integer.toString(regra));
                Conjuncoes[a] = Float.parseFloat(Integer.toString(conjuncoes));
            }

            if (alg.contains("select")) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "e0.txt"));
                } catch (FileNotFoundException var16) {
                    System.err.println("Não foi possível abrir o arquivo " + caminho + "result" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();

                    while((line = inReader.readLine()) != null) {
                        str = new StringTokenizer(line, ", ");

                        while(str.hasMoreTokens()) {
                            token = str.nextToken();
                            if (token.contains("&&")) {
                                ++conjuncoes;
                            }

                            if (token.contains("output")) {
                                ++regra;
                            }
                        }
                    }

                    inReader.close();
                    conjuncoes += regra;
                } catch (IOException var30) {
                    System.err.println(var30.getMessage());
                }

                Regras[a] = Float.parseFloat(Integer.toString(regra));
                Conjuncoes[a] = Float.parseFloat(Integer.toString(conjuncoes));
            }

            regra = 0;
            conjuncoes = 0;
        }

        float[] regrasDP = new float[2];
        regrasDP = this.calculaDesvioPadrao(Regras, 10);
        float[] conjuncoesDP = new float[2];
        conjuncoesDP = this.calculaDesvioPadrao(Conjuncoes, 10);
        DecimalFormat mF = new DecimalFormat();
        mF.applyPattern("########0.00");
        System.out.println(mF.format((double)regrasDP[0]) + " \t " + mF.format((double)regrasDP[1]) + "  \t" + mF.format((double)conjuncoesDP[0]) + "\t\t" + mF.format((double)conjuncoesDP[1]));
    }

    public void tcc(String caminho, String alg) {
        BufferedReader inReader = null;
        String x = "s";
        float[] TCC = new float[10];
        String line;
        String token;
        int a;
        byte c;
        StringTokenizer str;
        float[] tccDP;
        if (alg.contains("select")) {
            for(a = 0; a < 10; ++a) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "s0e0.txt"));
                } catch (FileNotFoundException var16) {
                    System.err.println("tcc - Não foi possível abrir o arquivo " + caminho + "result0s" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();
                    c = 0;

                    while((line = inReader.readLine()) != null) {
                        if (line.contains("test accuracy")) {
                            str = new StringTokenizer(line, "= ");
                            token = str.nextToken();
                            token = str.nextToken();
                            TCC[c] = (1.0F - Float.parseFloat(token)) * 100.0F;
                        }
                    }

                    inReader.close();
                } catch (IOException var20) {
                    System.err.println(var20.getMessage());
                }

                tccDP = new float[2];
                tccDP = this.calculaDesvioPadrao(TCC, 10);
                System.out.println(tccDP[0] + "\t" + tccDP[1]);
            }
        } else if (alg.compareTo("C45") == 0) {
            for(a = 0; a < 10; ++a) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "e0.txt"));
                } catch (FileNotFoundException var15) {
                    System.err.println("tcc - Não foi possível abrir o arquivo " + caminho + "result" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();

                    while((line = inReader.readLine()) != null) {
                        if (line.contains("PercentageOfInCorrectlyClassifiedTest")) {
                            str = new StringTokenizer(line, "= ");
                            token = str.nextToken();
                            token = str.nextToken();
                            String lixo = token.replace("%", "");
                            TCC[a] = Float.parseFloat(lixo);
                        }
                    }

                    inReader.close();
                } catch (IOException var19) {
                    System.err.println(var19.getMessage());
                }
            }

//            float[] tccDP = new float[2];
            tccDP = this.calculaDesvioPadrao(TCC, 10);
            System.out.println("Média\tDesvio Padrão");
            System.out.print(tccDP[0] + "\t" + tccDP[1] + "\t");
        } else if (alg.compareTo("MPLCS") == 0) {
            for(a = 0; a < 10; ++a) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + "s0e0.txt"));
                } catch (FileNotFoundException var14) {
                    System.err.println("tcc - Não foi possível abrir o arquivo " + caminho + "result0s" + a + "e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();
                    c = 0;

                    while((line = inReader.readLine()) != null) {
                        if (line.contains("test accuracy")) {
                            str = new StringTokenizer(line, "= ");
                            token = str.nextToken();
                            token = str.nextToken();
                            TCC[c] = (1.0F - Float.parseFloat(token)) * 100.0F;
                        }
                    }

                    inReader.close();
                } catch (IOException var18) {
                    System.err.println(var18.getMessage());
                }

                tccDP = new float[2];
                tccDP = this.calculaDesvioPadrao(TCC, 10);
                System.out.println(tccDP[0] + "\t" + tccDP[1]);
            }
        } else {
            for(a = 0; a < 10; ++a) {
                try {
                    inReader = new BufferedReader(new FileReader(caminho + "result" + a + x + "0.stat"));
                } catch (FileNotFoundException var13) {
                    System.err.println("tcc - Não foi possível abrir o arquivo " + caminho + "result" + a + x + "0.stat");
                    System.exit(1);
                }

                try {
                    line = inReader.readLine();
                    c = 0;
                    boolean teste = true;

                    while(true) {
                        if ((line = inReader.readLine()) == null) {
                            inReader.close();
                            break;
                        }

                        str = new StringTokenizer(line, ", ");

                        while(str.hasMoreTokens() && teste) {
                            token = str.nextToken();
                            if (token.contains("CORRECT=")) {
                                String temp = token.replace("CORRECT=", "");
                                TCC[c] = (1.0F - Float.parseFloat(temp)) * 100.0F;
                                ++c;
                            }

                            if (c == 9) {
                                teste = false;
                            }
                        }
                    }
                } catch (IOException var17) {
                    System.err.println(var17.getMessage());
                }

                tccDP = new float[2];
                tccDP = this.calculaDesvioPadrao(TCC, 10);
                DecimalFormat mF = new DecimalFormat();
                mF.applyPattern("########0.00");
                System.out.println(mF.format((double)tccDP[0]) + "\t" + mF.format((double)tccDP[1]) + "\t");
            }
        }

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
        float[] results = new float[]{media, desvioPadrao};
        return results;
    }

    public void gravaArquivoARFFComNumCjtosFuzzyFixo(String[][] dadosFuzzificados, String caminho, String dataset, int numObjetos, int nVE, int numTermos) {
        BufferedReader inReader = null;
        String linha = "";

        try {
            inReader = new BufferedReader(new FileReader(caminho + dataset + ".names"));
        } catch (FileNotFoundException var16) {
            System.err.println("gravaArquivoARFFComNumCjtosFuzzyFixo - Não foi possível abrir o arquivo: " + caminho + dataset + ".names");
            System.exit(1);
        }

        try {
            BufferedWriter buf_writer = null;
            String[] termos = new String[numTermos];
            int a;
            if (numTermos == 2) {
                termos[0] = "baixa";
                termos[1] = "alta";
            } else if (numTermos == 3) {
                termos[0] = "baixa";
                termos[1] = "media";
                termos[2] = "alta";
            } else if (numTermos == 4) {
                termos[0] = "baixa";
                termos[1] = "mediabaixa";
                termos[2] = "mediaalta";
                termos[3] = "alta";
            } else if (numTermos == 5) {
                termos[0] = "baixa";
                termos[1] = "mediabaixa";
                termos[2] = "media";
                termos[3] = "mediaalta";
                termos[4] = "alta";
            } else if (numTermos == 6) {
                termos[0] = "baixissima";
                termos[1] = "baixa";
                termos[2] = "mediabaixa";
                termos[3] = "mediaalta";
                termos[4] = "alta";
                termos[5] = "altissima";
            } else if (numTermos == 7) {
                termos[0] = "baixissima";
                termos[1] = "baixa";
                termos[2] = "mediabaixa";
                termos[3] = "media";
                termos[4] = "mediaalta";
                termos[5] = "alta";
                termos[6] = "altissima";
            } else if (numTermos == 8) {
                termos[0] = "baixissima";
                termos[1] = "muitobaixa";
                termos[2] = "baixa";
                termos[3] = "mediabaixa";
                termos[4] = "mediaalta";
                termos[5] = "alta";
                termos[6] = "muitoalta";
                termos[7] = "altissima";
            } else {
                for(a = 0; a < numTermos; ++a) {
                    termos[a] = "termo" + a;
                }
            }

            try {
                FileWriter writer = new FileWriter(caminho + dataset + "Fuzzy.arff");
                buf_writer = new BufferedWriter(writer);
            } catch (Exception var15) {
            }

            try {
                String line = "@RELATION " + dataset;
                buf_writer.write(line);
                buf_writer.newLine();
                buf_writer.newLine();

                for(a = 0; a < nVE; ++a) {
                    line = inReader.readLine();
                    StringTokenizer str = new StringTokenizer(line);
                    linha = "@ATTRIBUTE " + str.nextToken() + "\t{";
                    if (!line.contains("double") && !line.contains("float") && !line.contains("real") && !line.contains("integer")) {
                        while(str.hasMoreTokens()) {
                            linha = linha + str.nextToken();
                            if (str.hasMoreTokens()) {
                                linha = linha + ",";
                            }
                        }

                        linha = linha + "}";
                    } else {
                        for(int cjtos = 0; cjtos < numTermos - 1; ++cjtos) {
                            linha = linha + termos[cjtos] + ",";
                        }

                        linha = linha + termos[numTermos - 1] + "}";
                    }

                    buf_writer.write(linha);
                    buf_writer.newLine();
                    linha = "";
                }

                buf_writer.newLine();
                buf_writer.newLine();
                linha = "@DATA";
                buf_writer.write(linha);
                buf_writer.newLine();
                linha = "";

                for(a = 0; a < numObjetos; ++a) {
                    for(int b = 0; b < nVE - 1; ++b) {
                        linha = linha + dadosFuzzificados[a][b] + ",";
                    }

                    linha = linha + dadosFuzzificados[a][nVE - 1];
                    buf_writer.write(linha);
                    buf_writer.newLine();
                    linha = "";
                }

                buf_writer.close();
            } catch (IOException var17) {
                System.err.println(var17);
                System.exit(1);
            }

            inReader.close();
        } catch (IOException var18) {
            System.err.println(var18.getMessage());
        }

    }

    void geraArquivoParametros(String caminho, String dataset) throws CombinatoricException {
        manipulaArquivos mA = new manipulaArquivos();
        int numVariaveisEntrada = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento0.txt");
        wrapperWM wWM = new wrapperWM();
        float[] parametros = new float[2];
        parametros = wWM.rodaWM(dataset, numVariaveisEntrada, "classico", caminho);
        System.out.println("TCC de WM: " + parametros[0] + " \nNum de regrasWM: " + parametros[1]);
        int numRegrasGeradasFCA = this.getNumRegras(caminho + "Regras_FCA_" + dataset + ".txt");
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + "parametros" + dataset + ".txt", false);
            buf_writer = new BufferedWriter(writer);
            String line = numRegrasGeradasFCA - 1 + "\n" + (numRegrasGeradasFCA - 1) + "\n" + parametros[1] + "\n" + parametros[0];
            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    void geraArquivoParametrosUmTreino(String caminho, String dataset, int arqTreino, String metodo) throws CombinatoricException {
        manipulaArquivos mA = new manipulaArquivos();
        int numVariaveisEntrada = mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + "-treinamento" + arqTreino + ".txt");
        wrapperWM wWM = new wrapperWM();
        float[] parametros = new float[2];
        parametros = wWM.rodaWMUmTreino(dataset, numVariaveisEntrada, "classico", caminho, arqTreino);
        System.out.println("TCC de WM: " + parametros[0] + " \nNum de regrasWM: " + parametros[1]);
        int numRegrasGeradasFCA = this.getNumRegras(caminho + "Regras_" + metodo + "_" + dataset + ".txt");
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + "parametros" + dataset + ".txt", false);
            buf_writer = new BufferedWriter(writer);
            String line = numRegrasGeradasFCA - 1 + "\n" + (numRegrasGeradasFCA - 1) + "\n" + parametros[1] + "\n" + parametros[0];
            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var13) {
            System.err.println(var13);
            System.exit(1);
        }

    }

    public void contaRegrasSlave(String caminho, String[] datasets) {
        BufferedReader inReader = null;
        String line = "";
        double[] tcc = new double[10];

        for(int a = 0; a < datasets.length; ++a) {
            String conjunto = datasets[a];
            caminho = "C:/Users/Marcos/kell/experimentos/results/SLAVE-C." + conjunto + "/";

            try {
                inReader = new BufferedReader(new FileReader(caminho + "Vis-Clas-Tabular/TSTSLAVE-C/" + conjunto + "_ByFoldByClassifier_s0.stat"));
            } catch (FileNotFoundException var13) {
                System.err.println("contaRegrasSlave - Não foi possível abrir o arquivo...");
                System.exit(1);
            }

            int b;
            try {
                for(b = 0; b < 12; ++b) {
                    line = inReader.readLine();
                }

                for(b = 0; b < 10; ++b) {
                    StringTokenizer str = new StringTokenizer(line, ",");
                    str.nextToken();
                    tcc[b] = Double.parseDouble(str.nextToken());
                    line = inReader.readLine();
                }

                inReader.close();
            } catch (IOException var14) {
                System.err.println(var14.getMessage());
            }

            for(b = 0; b < 10; ++b) {
                BufferedReader inReader2 = null;
                line = "";

                try {
                    inReader2 = new BufferedReader(new FileReader(caminho + "result" + b + "s0e0.txt"));
                } catch (FileNotFoundException var12) {
                    System.err.println("contaRegrasSlave - Não foi possível abrir o arquivo: " + caminho + "result" + b + "s0e0.txt");
                    System.exit(1);
                }

                try {
                    line = inReader2.readLine();
                    String line2 = line.replace("Number of rules = ", "");
                    System.out.println(tcc[b] + "\t" + tcc[b] + "\t" + line2);
                    inReader2.close();
                } catch (IOException var11) {
                    System.err.println(var11.getMessage());
                }
            }
        }

    }

    void criaDiretorio(String caminho, String dataset, String atribs, int numTermos) throws FileNotFoundException, IOException {
        int numFolds = 10;
        String novoDir = caminho + dataset + "_FSS/";
        File f = new File(novoDir);

        try {
            if (!f.exists()) {
                f.mkdir();
                System.out.println("Directory Created");
            } else {
                System.out.println("Directory is not created");
            }
        } catch (Exception var28) {
        }

        String dadosOrigem = caminho + dataset + "/" + dataset + ".txt";
        String dadosDestino = caminho + dataset + "_FSS/" + dataset + ".txt";
        InputStream in = new FileInputStream(dadosOrigem);
        OutputStream out = new FileOutputStream(dadosDestino);
        byte[] buf = new byte[1024];

        int len;
        while((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
        int numExemplos = this.getNumRegrasTreinamento2(caminho + dataset + "/" + dataset + ".txt");
        System.out.println("Número total de exemplos: " + numExemplos);
        int numVarEnt = this.getNumeroVariaveisEntradaArqTreinamento(caminho + dataset + "/" + dataset + ".txt");
        float[][] exemplos = new float[numExemplos][numVarEnt];
        StringTokenizer str = new StringTokenizer(atribs);
        int novoNumVar = str.countTokens();
        int[] atributos = new int[novoNumVar];
        String[][] novosExemplos = new String[numExemplos][novoNumVar + 1];
        this.carregaExemplosAG(exemplos, caminho + dataset + "/" + dataset + ".txt", numVarEnt, numExemplos);

        int a;
        for(a = 0; a < novoNumVar; ++a) {
            int c = Integer.parseInt(str.nextToken());
            atributos[a] = c;
            System.err.println("atrib: " + c);

            for(int b = 0; b < numExemplos; ++b) {
                novosExemplos[b][a] = "" + exemplos[b][c];
            }
        }

        for(a = 0; a < numExemplos; ++a) {
            novosExemplos[a][novoNumVar] = "" + exemplos[a][numVarEnt - 1];
        }

        this.gravaArquivo(novosExemplos, novoDir + "/" + dataset + "_FSS.txt", numExemplos, novoNumVar + 1);
        System.err.println("Gravou novo arquivo de dados");
        BufferedReader inReader = null;
        String line = "";
        String nomes = "";

        try {
            inReader = new BufferedReader(new FileReader(caminho + dataset + "/" + dataset + ".names"));
        } catch (FileNotFoundException var27) {
            System.err.println("criaDiretorio - Não foi possível abrir o arquivo...");
            System.exit(1);
        }

        try {
            int ini = 0;

            int x;
            for(x = 0; x < novoNumVar; ++x) {
                if (x > 0) {
                    ini = atributos[x - 1] + 1;
                }

                for(int y = ini; y <= atributos[x]; ++y) {
                    line = inReader.readLine();
                }

                nomes = nomes + line + "\n";
            }

            ini = atributos[novoNumVar - 1] + 1;

            for(x = ini; x < numVarEnt; ++x) {
                line = inReader.readLine();
            }

            nomes = nomes + line + "\n";
            inReader.close();
            BufferedWriter buf_writer = null;
            FileWriter writer = new FileWriter(novoDir + dataset + "_FSS.names");
            buf_writer = new BufferedWriter(writer);
            buf_writer.write(nomes);
            buf_writer.close();
        } catch (IOException var29) {
            System.err.println(var29);
            System.exit(1);
        }

        Particoes pt = new Particoes();
        gera10Folds gF = new gera10Folds();
        gF.geraNFolds(dataset + "_FSS", novoDir, numFolds);
        pt.geraParticao(dataset + "_FSS", numTermos, novoDir);
    }

    int[] ColetaDadosDataset(String caminho, String dataset, int numVarEnt, int numFuzzySets) {
        int[] num_rotulos = new int[numVarEnt];
        BufferedReader inReader = null;
        String line = "";

        try {
            inReader = new BufferedReader(new FileReader(caminho + dataset + ".names"));
        } catch (FileNotFoundException var12) {
            System.err.println("ColetaDadosDataset - Não foi possível abrir arquivo de nomes");
            System.exit(1);
        }

        try {
            for(int a = 0; a < numVarEnt; ++a) {
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);
                int numTokens = str.countTokens();
                String token = str.nextToken();
                token = str.nextToken();
                if (token.compareTo("double") != 0 && token.compareTo("integer") != 0 && token.compareTo("float") != 0) {
                    num_rotulos[a] = numTokens - 1;
                } else {
                    num_rotulos[a] = numFuzzySets;
                }
            }
        } catch (IOException var13) {
            System.err.println(var13.getMessage());
        }

        return num_rotulos;
    }

    String[][] carregaValoresLinguisticos(String caminho, String dataset, int numMaxVarLinguisticas, int numVarEnt) {
        String[][] names = new String[numVarEnt][numMaxVarLinguisticas];
        BufferedReader inReader = null;
        String line = "";

        try {
            inReader = new BufferedReader(new FileReader(caminho + dataset + ".names"));
        } catch (FileNotFoundException var11) {
            System.err.println("carregaValoresLinguisticos - Não foi possível abrir arquivo de nomes");
            System.exit(1);
        }

        try {
            for(int a = 0; a < numVarEnt; ++a) {
                int indice = 0;
                line = inReader.readLine();
                StringTokenizer str = new StringTokenizer(line);
                str.nextToken();

                while(str.hasMoreTokens()) {
                    names[a][indice] = str.nextToken();
                    ++indice;
                }
            }
        } catch (IOException var12) {
            System.err.println(var12.getMessage());
        }

        return names;
    }

    void gravaSaidaAG(String caminho, float[][] resultados, String dataset) {
        BufferedWriter buf_writer = null;
        String nomeArquivo = caminho + dataset + "TCCeRegrasAG.txt";

        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            buf_writer = new BufferedWriter(writer);
            String line = "";
            buf_writer.write("Tcc Treino \t TCC Teste \t Regras");
            buf_writer.write(line);
            buf_writer.newLine();
            float tccTreino = 0.0F;
            float tccTeste = 0.0F;
            float regras = 0.0F;

            for(int i = 0; i < 10; ++i) {
                line = line + resultados[i][0] + "\t" + resultados[i][1] + "\t" + resultados[i][2] + "\n";
                tccTreino += resultados[i][0];
                tccTeste += resultados[i][1];
                regras += resultados[i][2];
            }

            buf_writer.write(line);
            buf_writer.newLine();
            buf_writer.newLine();
            buf_writer.write("Médias");
            buf_writer.newLine();
            line = line + tccTreino / 10.0F + "\t" + tccTeste / 10.0F + "\t" + regras / 10.0F + "\n";
            buf_writer.close();
        } catch (IOException var12) {
            System.err.println(var12);
            System.exit(1);
        }

    }

    void gravaDadosParticao(String dataset, int numCjtos, String caminho) {
        BufferedWriter buf_writer = null;

        try {
            FileWriter writer = new FileWriter(caminho + dataset + "DadosParticao.txt");
            buf_writer = new BufferedWriter(writer);
            String line = "";
            manipulaArquivos mA = new manipulaArquivos();
            mA.getNumeroVariaveisEntradaArqTreinamento2(caminho + dataset + ".txt");
            Vector dadosParticao = new Vector();
            this.carregaDadosParticao(caminho + "particao" + dataset + ".txt", dadosParticao);

            for(int i = 1; i < dadosParticao.size(); ++i) {
                line = line + dadosParticao.get(i) + "\t";
            }

            buf_writer.write(line);
            buf_writer.close();
        } catch (IOException var11) {
            System.err.println(var11);
            System.exit(1);
        }

    }

    public void apagaArqsTemporarios(String dataset, String caminho) {
        for(int a = 0; a < 10; ++a) {
            try {
                File file = new File(caminho + "/" + dataset + "-treinamento" + a + ".txt");
                file.delete();
                file = new File(caminho + "/" + dataset + "-teste" + a + ".txt");
                file.delete();
//                file = new File(caminho + "/" + dataset + "ArvoreJ48" + a + ".txt");
//                file.delete();
            } catch (Exception var6) {
            }
        }

        try {
//            new File(caminho + "/particao" + dataset + ".txt");
//            File file = new File(caminho + "/" + dataset + "dadosparticao.txt");
//            file.delete();
//            file = new File(caminho + "/" + dataset + ".names");
//            file.delete();
//            file = new File(caminho + "/" + dataset + ".txt");
//            file.delete();
        } catch (Exception var5) {
        }

    }
}

