package FuzzyProject;

import FuzzyProject.FuzzyDT.Utils.ManipulaArquivos;
import FuzzyProject.FuzzyND.Models.Avaliacao.AcuraciaMedidas;
import FuzzyProject.FuzzyND.Models.Exemplo;
import FuzzyProject.FuzzyND.Models.MedidasClassicas;
import FuzzyProject.FuzzyND.Utils.Avaliacao;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class J48Test {

    public static void main(String[] args) throws Exception {
        String dataset = "forest";
        String caminho = "";
        String current = (new File(".")).getCanonicalPath();
        caminho = current + "/datasets-j48/" + dataset + "/";

        ConverterUtils.DataSource source;
        Instances data;

        ConverterUtils.DataSource sourceTrain;
        Instances dataTrain;

        sourceTrain = new ConverterUtils.DataSource(caminho + dataset + "-train.arff");
        dataTrain = sourceTrain.getDataSet();
        dataTrain.setClassIndex(dataTrain.numAttributes() - 1);

        source = new ConverterUtils.DataSource(caminho + dataset + "-instances.arff");
        data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
        List<AcuraciaMedidas> acuracias = new ArrayList<>();
        J48 j48 = new J48();
        j48.buildClassifier(dataTrain);
        int acertos = 0;
        for(int i=0, j=0, h=0; i<data.size(); i++, j++, h++) {
            Instance ins = data.get(i);
            Exemplo exemplo = new Exemplo(ins.toDoubleArray(), true);
            int rotulo = (int) j48.classifyInstance(ins);

            if (exemplo.getRotuloVerdadeiro().equals(Integer.toString(rotulo))) {
                acertos++;
            }

            if(h == 1000) {
                h=0;
                System.err.println(acertos);
                acuracias.add(Avaliacao.calculaAcuracia(acertos, 1000, i));
                acertos = 0;
            }
        }
        acuracias.add(Avaliacao.calculaAcuracia(acertos, 1000, data.size()));
        ManipulaArquivos.salvaPredicoes(acuracias, dataset);
        System.out.println(acertos);
    }
}
