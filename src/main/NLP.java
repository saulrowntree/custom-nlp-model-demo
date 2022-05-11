package main;

import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;

import java.util.List;

public class NLP {

    final int TEST_SET_SIZE = 10000;
    String testData = "data/test.txt";
    String trainData = "data/train.txt";

    public void prepareData() {
        StringBuilder test = new StringBuilder();
        StringBuilder train = new StringBuilder();

        // The locations of our various data files
        String allData = "data/combined.csv";

        // Contains our combined data
        List<String> lines = FileHelper.readFileToLines(allData);

        // Removes our CSV Column Header line
        lines.remove(0);


        for (int i = 0; i < lines.size(); i++) {
            // Ignore empty lines, thus avoiding NullPointerExceptions
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
            // For the first 10% of our dataset, create the test set
            if (i < TEST_SET_SIZE) test.append(label).append("\t ").append(comment).append("\n");
                // With the rest, create our training set
            else train.append(label).append("\t ").append(comment).append("\n");
        }
        // Write this test data to a file.
        FileHelper.writeStringToFile(testData, test.toString());
        FileHelper.writeStringToFile(trainData, train.toString());
    }


    public void predict() throws Exception {
        prepareData();

        String resultFile = "results/results.txt";
        StringBuilder text = new StringBuilder();

        // Instantiate the classifer
        ColumnDataClassifier cdc = new ColumnDataClassifier("data/stanfordProps.prop");
        // Train the classifier using our training data
        cdc.trainClassifier(trainData);

        // For each piece of training data
        for (String line : ObjectBank.getLineIterator(testData, "utf-8")) {
            // Classify it using our model
            Datum<String, String> d = cdc.makeDatumFromLine(line);
            // Print the correct label, the line, and our classification of that label, along with a score (confidence)
//            System.out.printf("%s  ==>  %s (%.4f)%n", line, cdc.classOf(d), cdc.scoresOf(d).getCount(cdc.classOf(d)));
            if (cdc.classOf(d).equals("WITHOUT_CLASSIFICATION")) text.append("0").append("\n");
            else text.append("1").append("\n");


            FileHelper.writeStringToFile(resultFile, text.toString());
        }

        Statistics.evaluate();
    }
}