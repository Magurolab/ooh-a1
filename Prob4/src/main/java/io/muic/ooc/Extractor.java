package io.muic.ooc;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.net.URL;

import java.util.HashSet;
import java.util.Set;

public class Extractor {
    private Document doc;
    private String currentURL;
    private String currentPath;

    public boolean connectToURL(URL url) throws IOException {


        Connection.Response connection = Jsoup.connect(url.toString())
                .ignoreHttpErrors(true)
                .execute();
        if (connection.statusCode() >= 200 && connection.statusCode() < 300) {
            this.doc = connection.parse();
            return true;
        }

        return false;

    }

    public Set<String> getAllURL() throws IOException {

        Elements links = doc.select("[href]");
        Set<String> urls = new HashSet<>();

        for (Element elementLink: links) {

            try {
                String link = elementLink.absUrl("href");
                link = trim(link);
                if (link.contains(currentURL)){

                    urls.add(link);
                }
            }catch (Exception e){
                System.out.println("This is where is breaks: " + links);

            }
        }
        return urls;
    }

    public Set<String> getAllSrc(){

        Elements sources = doc.select("[src]");
        Set<String> srcSet = new HashSet<>();

        for (Element source: sources) {
            String stringSource = source.attr("abs:src");
            stringSource = trim(stringSource);

            srcSet.add(stringSource);
        }
        return srcSet;
    }

    public Set<String> getAllCSS(){

        Elements cssElements = doc.select("link[href]");
        Set<String> cssSet = new HashSet<>();

        for (Element css: cssElements) {
//
            String stringCSS = css.attr("abs:href");
            stringCSS = trim(stringCSS);
            cssSet.add(stringCSS);
        }
        return cssSet;
    }

    public void printText(){
        Element body = doc.body();

        if(body == null){
            System.out.println("No body for this HTML");
            return;
        }
        String allText = body.text();
        System.out.println(allText);
    }

    public String[] getWords(){

        Element body = doc.body();
        if(body != null) {
            String words = body.text();

            return words.split(" ");
        }

        return new String[0];
    }

    public long getNumberOfWords(){

        return this.getWords().length;
    }
    private String trim(String link){
        if (link.lastIndexOf('/') < link.lastIndexOf('#')) {
            link = link.substring(0, link.lastIndexOf('#'));
        }
        //handel popup
        if (link.indexOf(".html") < link.indexOf('?')) {
            link = link.substring(0, link.indexOf('?'));
        }

        return link;

    }

}