package FuzzyProject.FuzzyDT.Utils;

import FuzzyProject.FuzzyDT.Models.ComiteArvores;
import FuzzyProject.FuzzyDT.Models.ResultadoMA;
import FuzzyProject.FuzzyND.Models.Exemplo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ConverteArquivos {
    int numExemplos;
    String[] exemplos;
    static String[][] atribs;
    static int numAtribs;
    String [][] classes;
    private Object path;

    public ConverteArquivos() {
        numAtribs = 0;
    }

    public ResultadoMA main(String arquivo, ComiteArvores comite, int tChunk) throws FileNotFoundException, IOException {
        boolean numAtributos = false;
        if (numAtribs != 0) {
            numAtributos = true;
        }
        String current = (new File(".")).getCanonicalPath();
        String arquivoOriginal = arquivo + "-train.dat";
        String arq = current + "/" + arquivo + "/" + arquivoOriginal;
        new File(current + "/" + arquivo);
        (new File(current + "/" + arquivo)).mkdir();
        File source = new File(arquivoOriginal);
        File dest = new File(arq);

        try {
            Path var8 = Files.copy(source.toPath(), dest.toPath());
        } catch (IOException var23) {
            System.err.println(var23.getMessage());
        }

        BufferedReader inReader = null;
        LineNumberReader lnr = new LineNumberReader(new FileReader(new File(arq)));
        long skip = lnr.skip(9223372036854775807L);
        int linhas = lnr.getLineNumber();

        try {
            inReader = new BufferedReader(new FileReader(arq));
        } catch (FileNotFoundException var22) {
            System.err.println("Unable to open file: " + arq);
            System.exit(1);
        }

        StringTokenizer str;
        int i;
        String temp;
        try {
            String line = inReader.readLine();
            int m;
            if(numAtribs == 0) {
                str = new StringTokenizer(line);
                String nomeBaseDeDados = str.nextToken();
                nomeBaseDeDados = str.nextToken();
                atribs = new String[200][2];
                i = 0;
                line = inReader.readLine();
                str = new StringTokenizer(line);
                ++numAtribs;
                str.nextToken();

                do {
                    atribs[i][0] = str.nextToken();
                    temp = str.nextToken();
                    comite.atributos.add(atribs[i][0]);
                    if (temp.contains("real")) {
                        atribs[i][1] = "real";
                    } else if (temp.contains("integer")) {
                        atribs[i][1] = "integer";
                    } else {
                        int y;
                        String valoresAtrib;
                        if (temp.contains("{")) {
                            y = str.countTokens();

                            for(m = 0; m < y; ++m) {
                                temp = temp + str.nextToken() + " ";
                            }

                            valoresAtrib = temp.replaceAll("\\{", "");
                            temp = valoresAtrib.replaceAll(",", " ");
                            valoresAtrib = temp.replaceAll("\\}", "");
                            String[] vetorAtributos = valoresAtrib.split(" ");
                            String valoresAtribNovo = "";
                            for(int k=0; k<vetorAtributos.length; k++) {
                                comite.hashmapRotulos.put(vetorAtributos[k], k);
                                valoresAtribNovo += k;
                                if(k<(vetorAtributos.length-1)) {
                                    valoresAtribNovo += " ";
                                }
                            }
                            atribs[i][1] = valoresAtribNovo;
                        } else {
                            y = str.countTokens();
                            valoresAtrib = temp + " ";

                            for(int h = 0; h < y; ++h) {
                                valoresAtrib = valoresAtrib + str.nextToken() + " ";
                            }

                            String temp2 = valoresAtrib.replace(",", " ");
                            atribs[i][1] = temp2;
                        }
                    }

                    ++i;
                    line = inReader.readLine();
                    str = new StringTokenizer(line);
                    ++numAtribs;
                } while("@attribute".equals(str.nextToken()));
            }
            comite.numAtributos = numAtribs-1;

            if(!numAtributos) {
                line = inReader.readLine();
                new StringTokenizer(line);
                line = inReader.readLine();
                new StringTokenizer(line);
                line = inReader.readLine();
                numExemplos = linhas - numAtribs - 3;
            } else {
                numExemplos = linhas;
            }
            str = new StringTokenizer(line);
            exemplos = new String[numExemplos];
            temp = "";

            do {
                temp = temp + str.nextToken() + " ";
            } while(str.hasMoreTokens());


            String lixo = "";
            String[] lixoQuebrado = temp.split(",");
            int ultimoElemento = lixoQuebrado.length-1;
            Integer t = comite.hashmapRotulos.get(lixoQuebrado[ultimoElemento].replace(" ",""));
            lixoQuebrado[ultimoElemento] = String.valueOf(t);
            lixo = String.join("\t", lixoQuebrado);
            exemplos[0] = lixo;
            temp = "";

            for(m = 1; m < numExemplos; ++m) {
                line = inReader.readLine();
                str = new StringTokenizer(line);

                do {
                    temp = temp + str.nextToken() + " ";
                } while(str.hasMoreTokens());

                lixo = "";
                lixoQuebrado = temp.split(",");
                ultimoElemento = lixoQuebrado.length-1;
                t = comite.hashmapRotulos.get(lixoQuebrado[ultimoElemento].replace(" ",""));
                lixoQuebrado[ultimoElemento] = String.valueOf(t);
                lixo = String.join("\t", lixoQuebrado);
                exemplos[m] = lixo;
                temp = "";
            }
            inReader.close();
        } catch (IOException var26) {
            System.err.println(var26.getMessage());
        }

        int numClassificadores = 0;
        if(numExemplos % tChunk == 0) {
            numClassificadores = numExemplos/tChunk;
        } else {
            numClassificadores = (int) Math.ceil((double)numExemplos/tChunk);
        }

        ResultadoMA rMa = new ResultadoMA(numClassificadores);

        str = null;

        FileWriter writer;
        BufferedWriter buf_writer;
        FileWriter writerNames;
        BufferedWriter buf_writerNames;
        ArrayList<String> classesDivididas = new ArrayList<String>();

        try {
            int countClassificadores = 0;
            if (numAtributos) {
                for (i = 0; i < numExemplos;) {
                    writer = new FileWriter(current + "/" + arquivo + "/" + arquivo + countClassificadores + ".txt");
                    buf_writer = new BufferedWriter(writer);
                    writerNames = new FileWriter(current + "/" + arquivo + "/" + arquivo + countClassificadores + ".names");
                    buf_writerNames = new BufferedWriter(writerNames);
                    List<String> classes = new ArrayList<>();
                    for(int j = 0; j<tChunk; j++, i++) {
                        String[] aux = exemplos[i].split("\t");
                        if (!classesDivididas.contains(aux[aux.length - 1])) {
                            classesDivididas.add(aux[aux.length - 1]);
                        }
                        buf_writer.write(exemplos[i]);
                        buf_writer.newLine();
                    }
                    for(i = 0; i < numAtribs - 1; ++i) {
                        if(atribs[i][0] != "Class") {
                            temp = atribs[i][0] + " " + atribs[i][1];
                            buf_writer.write(temp);
                            buf_writer.newLine();
                        }
                    }
                    comite.numeroClassificadores.add(countClassificadores);
                    countClassificadores++;
                    buf_writer.close();
                }
            } else {
                for (i = 0; i < numExemplos;) {
                    writer = new FileWriter(current + "/" + arquivo + "/" + arquivo + countClassificadores + ".txt");
                    buf_writer = new BufferedWriter(writer);
                    writerNames = new FileWriter(current + "/" + arquivo + "/" + arquivo + countClassificadores + ".names");
                    buf_writerNames = new BufferedWriter(writerNames);
                    List<String> classes = new ArrayList<>();
                    for(int j = 0; j<tChunk && i<numExemplos; j++, i++) {
                        String[] aux = exemplos[i].split("\t");
                        if(!classes.contains(aux[aux.length-1])) {
                            classes.add(aux[aux.length-1]);
                        }
                        buf_writer.write(exemplos[i]);
                        buf_writer.newLine();
                    }

                    for(int k = 0; k < numAtribs - 1; ++k) {
                        if(atribs[k][0].compareTo("Class") != 0) {
                            temp = atribs[k][0] + " " + atribs[k][1];
                            buf_writerNames.write(temp);
                            buf_writerNames.newLine();
                        }
                    }

                    String classesConcatenadas = new String();
                    classesConcatenadas = classes.get(0).replace(" ","");
                    comite.rotulosConhecidos.addAll(classes);
                    rMa.rotulos.get(countClassificadores).addAll(classes);
                    for(int j=1; j<classes.size(); j++) {
                        String classe = classes.get(j).replace(" ", "");
                        classesConcatenadas = classesConcatenadas + " " + classe;
                    }

                    temp = "Class " + classesConcatenadas;
                    buf_writerNames.write(temp);
                    buf_writerNames.newLine();

                    comite.numeroClassificadores.add(countClassificadores);
                    countClassificadores++;
                    buf_writer.close();
                    buf_writerNames.close();
                }
            }
        } catch (IOException var25) {
            System.err.println(var25);
            System.exit(1);
        }

        return rMa;
    }

    public ResultadoMA mainParaExemplosRotulados(String arquivo, List<Exemplo> exemplosRotulados, ComiteArvores comite, int tChunk) throws FileNotFoundException, IOException {
        ResultadoMA resultadoMA = new ResultadoMA();
        FileWriter writer;
        BufferedWriter buf_writer;
        FileWriter writerNames;
        BufferedWriter buf_writerNames;
        ArrayList<String> classesDivididas = new ArrayList<String>();
        String temp = "";
        int countClassificadores = comite.numeroClassificadores.get(comite.numeroClassificadores.size()-1) + 1;
        String current = (new File(".")).getCanonicalPath();
        writer = new FileWriter(current + "/" + arquivo + "/" + arquivo + countClassificadores + ".txt");
        buf_writer = new BufferedWriter(writer);
        writerNames = new FileWriter(current + "/" + arquivo + "/" + arquivo + countClassificadores + ".names");
        buf_writerNames = new BufferedWriter(writerNames);
        List<String> classes = new ArrayList<>();

        for(int i = 0; i<exemplosRotulados.size(); i++) {
            String ex = exemplosRotulados.get(i).arrayToString();
            String[] aux = ex.split("\t");
            if(!classes.contains(aux[aux.length-1])) {
                classes.add(aux[aux.length-1]);
            }
            buf_writer.write(ex);
            buf_writer.newLine();
        }

        for(int k = 0; k < numAtribs - 1; ++k) {
            if(atribs[k][0].compareTo("Class") != 0) {
                temp = atribs[k][0] + " " + atribs[k][1];
                buf_writerNames.write(temp);
                buf_writerNames.newLine();
            }
        }

        String classesConcatenadas = new String();
        classesConcatenadas = classes.get(0).replace(" ","");
        for(int j=1; j<classes.size(); j++) {
            String classe = classes.get(j).replace(" ", "");
            if(!comite.rotulosConhecidos.contains(classe)) {
                comite.rotulosConhecidos.add(classe);
            }
            if(!comite.hashmapRotulos.containsKey(classe)) {
                comite.hashmapRotulos.put(classe,comite.hashmapRotulos.size());
            }
            resultadoMA.rotulos.get(0).add(classe);
            classesConcatenadas = classesConcatenadas + " " + classe;
        }

        temp = "Class " + classesConcatenadas;
        buf_writerNames.write(temp);
        buf_writerNames.newLine();

        comite.numeroClassificadores.add(countClassificadores);
        buf_writer.close();
        buf_writerNames.close();
        resultadoMA.numClassificadores = countClassificadores;
        return resultadoMA;
    }
}

