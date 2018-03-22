package io.muic.ooc;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Walker {
    private Set<String> visited;
    private long numberOfWords;
    private URLDownLoader downloader;
    private Extractor extractor;
    private Queue<String> destinations;

    public Walker()  {
        visited = new HashSet<>();
        downloader = new URLDownLoader();
        extractor = new Extractor();
        destinations = new ArrayDeque<>();
        numberOfWords = 0;



    }
    public void walk(String startingURL) throws Exception {
        URL currentURL;
        String stringURL;
        destinations.add(startingURL);


        while (!destinations.isEmpty()){

            stringURL = destinations.poll();
            if(stringURL.endsWith("/"))
                stringURL = stringURL + "index.html";

            try{
                currentURL = new URL(stringURL);
            }catch (Exception e){
                System.out.println("Breaks here: " + stringURL);
                return;
            }

            if(!startingURL.contains(currentURL.getHost()) || visited.contains(stringURL)) {
                continue;
            }


            System.out.printf("At: %s \n",currentURL);

            if(currentURL.getFile().endsWith(".html") || currentURL.getFile().endsWith(".htm")) {

                boolean isConnected = extractor.connectToURL(currentURL);

                if(isConnected) {
                    numberOfWords+=extractor.getNumberOfWords();
                    destinations.addAll(extractor.getAllURL());
                    this.saveCssAndSrc(extractor);
                }
            }

            this.saveLink(currentURL);
            visited.add(currentURL.toString());


        }



    }

    public void saveCssAndSrc(Extractor extractor) throws Exception {
        Set<String> cssSet = extractor.getAllCSS();
        Set<String> srcSet = extractor.getAllSrc();

        for (String css: cssSet) {
            downloader.save(css);
        }

        for(String src : srcSet){
            downloader.save(src);
        }

    }
    public void saveLink(URL url) throws IOException, URISyntaxException {
        downloader.save(url);
    }


    public long getNumberOfWords() {
        return numberOfWords;
    }
}
