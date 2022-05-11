package main;

public class Main {

    /**
     * Times the execution of NLP.predict
     */
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        NLP.predict();
        long end = System.currentTimeMillis();
        System.out.println("" +
                "RUN FINISHED IN " +
                (end - start) +
                "ms");
    }
}