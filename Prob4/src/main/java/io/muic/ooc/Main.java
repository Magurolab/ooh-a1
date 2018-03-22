package io.muic.ooc;


public class Main {
    public static void main(String[] args) throws Exception {

        String url = "http://localhost:8000/docs/";
//        String url  = "https://cs.muic.mahidol.ac.th/courses/ooc/docs/";
        Walker w = new Walker();
        w.walk(url);
        System.out.printf("The total number of words found is %d \n", w.getNumberOfWords());


    }

}
