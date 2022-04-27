package main;

import java.util.List;



public class Statistics {

    public static void evaluate() {

        double tp = .0, fp = .0, tn = .0, fn = .0;
        String results = "results/results.txt";
        String oraclePath = "data/labels";
        List<String> result = FileHelper.readFileToLines(results);
        List<String> oracle = FileHelper.readFileToLines(oraclePath);
        for (int i = 1; i < result.size(); i++) {
            String label = oracle.get(i).trim(), prediction = result.get(i).trim();
            if (label.equals("SATD") && prediction.equals("1")) tp++;
            if (label.equals("SATD") && prediction.equals("0")) fn++;
            if (label.equals("WITHOUT_CLASSIFICATION") && prediction.equals("1")) fp++;
            if (label.equals("WITHOUT_CLASSIFICATION") && prediction.equals("0")) tn++;
        }

        double accuracy = (tp + tn) / (tp + fp + fn + tn);
        double precision = tp / (tp + fp);
        double recall = tp / (tp + fn);
        double f1 = 2 * precision * recall / (precision + recall);
        double x = tp + fp, y = tp, n = tp + fn, N = tp + fp + fn + tn;
        double ER = (y * N - x * n) / (y * N);
        double RI = (y * N - x * n) / (x * n);


        StringBuilder text = new StringBuilder()
                .append("TP = ").append((int) tp).append("\n")
                .append("FN = ").append((int) fn).append("\n")
                .append("FP = ").append((int) fp).append("\n")
                .append("TN = ").append((int) tn).append("\n")
                .append("Accuracy = ").append(String.format("%.8f", accuracy)).append("%\n")
                .append("Precision = ").append(String.format("%.3f", precision)).append("\n")
                .append("Recall = ").append(String.format("%.3f", recall)).append("\n")
                .append("F-Measure = ").append(String.format("%.3f", f1)).append("\n")
                .append("Error Rate = ").append(String.format("%.3f", ER)).append("\n")
                .append("RI = ").append(String.format("%.3f", RI)).append("\n");


        System.out.println(text);
        FileHelper.writeStringToFile("results/evaluation.csv", text.toString());
    }

}