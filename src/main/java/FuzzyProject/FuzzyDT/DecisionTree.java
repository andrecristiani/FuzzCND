package main.java.FuzzyProject.FuzzyDT;

import java.util.ArrayList;

public class DecisionTree {
    private int nVE;
    private int numObjetos;
    private int numAtributos;
    private int numConjuntos;
    private ArrayList<String> rotulos;
    private ArrayList<Metadata> atributos;

    public DecisionTree() {
        rotulos = new ArrayList<String>();
        atributos = new ArrayList<Metadata>();
    }

    public int getnVE() {
        return nVE;
    }

    public void setnVE(int nVE) {
        this.nVE = nVE;
    }

    public int getNumExemplos() {
        return numObjetos;
    }

    public void setNumExemplos(int numObjetos) {
        this.numObjetos = numObjetos;
    }

    public int getNumObjetos() {
        return numObjetos;
    }

    public void setNumObjetos(int numObjetos) {
        this.numObjetos = numObjetos;
    }

    public int getNumAtributos() {
        return numAtributos;
    }

    public void setNumAtributos(int numAtributos) {
        this.numAtributos = numAtributos;
    }

    public void setRotulos(ArrayList<String> rotulos) {
        this.rotulos = rotulos;
    }

    public void setAtributos(ArrayList<Metadata> atributos) {
        this.atributos = atributos;
    }

    public int getNumConjuntos() {
        return numConjuntos;
    }

    public void setNumConjuntos(int numConjuntos) {
        this.numConjuntos = numConjuntos;
    }

    public ArrayList<String> getRotulos() {
        return rotulos;
    }

    public void setRotulos(String rotulo) {
        this.rotulos.add(rotulo);
    }

    public ArrayList<Metadata> getAtributos() {
        return atributos;
    }

    public void setAtributos(String atributo, String tipo) {
        Metadata metadata = new Metadata();
        metadata.setAtributo(atributo);
        metadata.setTipo(tipo);
        this.atributos.add(metadata);
    }
}
