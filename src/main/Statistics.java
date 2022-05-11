package main;

import java.util.List;



public class Statistics {

    // Calculate all metrics for our classifications
    public static void evaluate() {

        double tp = .0, fp = .0, tn = .0, fn = .0;
        String results = "results/results.txt";
        String oraclePath = "data/labels";
        List<String> result = FileHelper.readFileToLines(results);
        List<String> oracle = FileHelper.readFileToLines(oraclePath);
        for (int i = 1; i < result.size(); i++) {
            String label = oracle.get(i).trim(), prediction = result.get(i).trim();
            // is positive, predicted positive, tp++
            if (label.equals("SATD") && prediction.equals("1")) tp++;
            // is positive, predicted negative, fn++
            if (label.equals("SATD") && prediction.equals("0")) fn++;
            // is negative, labelled positive, fp++
            if (label.equals("WITHOUT_CLASSIFICATION") && prediction.equals("1")) fp++;
            // is negative, labelled negative, tn++
            if (label.equals("WITHOUT_CLASSIFICATION") && prediction.equals("0")) tn++;
        }

        /*
        Applied formulae for calculating accuracy, precision, recall, and f1-measure
         */
        double accuracy = (tp + tn) / (tp + fp + fn + tn);
        double precision = tp / (tp + fp);
        double recall = tp / (tp + fn);
        double f1 = 2 * precision * recall / (precision + recall);


        // Create a formatted output containing these results
        StringBuilder text = new StringBuilder()
                .append("TP = ").append((int) tp).append("\n")
                .append("FN = ").append((int) fn).append("\n")
                .append("FP = ").append((int) fp).append("\n")
                .append("TN = ").append((int) tn).append("\n")
                .append("Accuracy = ").append(String.format("%.8f", accuracy)).append("%\n")
                .append("Precision = ").append(String.format("%.3f", precision)).append("\n")
                .append("Recall = ").append(String.format("%.3f", recall)).append("\n")
                .append("F-Measure = ").append(String.format("%.3f", f1)).append("\n");


        // Print it and write it to evaluation file
        System.out.println(text);
        FileHelper.writeStringToFile("results/evaluation.csv", text.toString());
    }

}