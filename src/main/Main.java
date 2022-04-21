package main;

public class Main {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        new NLP().predict();
        long end = System.currentTimeMillis();
        System.out.println("" +
                "RUN FINISHED IN " +
                (end - start) +
                "ms");
    }
}