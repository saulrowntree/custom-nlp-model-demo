package main;

import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import main.Statistics;
import main.FileHelper;

import java.util.List;

public class NLP {
//    public static String rootPath = "./exp_data/";
//    public static String methodPath = rootPath + "nlp/";
//    public static String resultPath = "./results/";


    public void prepareData() {
        StringBuilder test = new StringBuilder();
        StringBuilder train = new StringBuilder();

        // build data of single project
        String allData = "data/combined.csv";
        String testData = "data/test.txt";
        String trainData = "data/train.txt";
        // read all data in
        List<String> lines = FileHelper.readFileToLines(allData);
        // remove the csv header line
        lines.remove(0);

        // Iterate over the first 10% of the allData List<String>
        for (int i = 0; i < lines.size(); i++) {
            // If the line is empty, ignore it -- to avoid errors
            if (lines.get(i).isEmpty()) continue;

            // Split the line into data and label
            String[] splits = lines.get(i).split(",");
            // Get the comment from the newly split line
            String comment = splits[0].replace("'", "");

            //Swap out the labels for SATD and WITHOUT_CLASSIFICATION
            String label = "WITHOUT_CLASSIFICATION";
            try {
                if (splits[1].equals("positive")) label = "SATD";
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
            if (i < 10000)
                test.append(label).append("\t ").append(comment).append("\n");
            else
                train.append(label).append("\t ").append(comment).append("\n");
        }
        // Write this test data to a file.
        FileHelper.writeStringToFile(testData, test.toString());
        FileHelper.writeStringToFile(trainData, train.toString());
    }


    public void predict() throws Exception {
        prepareData();

        String trainFile = "data/train.txt";
        String testFile = "data/test.txt";
        String resultFile = "results/results.txt";
        StringBuilder text = new StringBuilder();

        ColumnDataClassifier cdc = new ColumnDataClassifier("data/stanfordProps.prop");
        cdc.trainClassifier(trainFile);

        for (String line : ObjectBank.getLineIterator(testFile, "utf-8")) {
            Datum<String, String> d = cdc.makeDatumFromLine(line);
            System.out.printf("%s  ==>  %s (%.4f)%n", line, cdc.classOf(d), cdc.scoresOf(d).getCount(cdc.classOf(d)));
            if (cdc.classOf(d).equals("WITHOUT_CLASSIFICATION")) text.append("0").append("\n");
            else text.append("1").append("\n");


            FileHelper.writeStringToFile(resultFile, text.toString());
        }

        Statistics.evaluate();
    }
}