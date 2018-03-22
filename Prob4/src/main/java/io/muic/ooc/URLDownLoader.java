package io.muic.ooc;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;



public class URLDownLoader {

    private void saveFromURL(URL url) throws IOException, URISyntaxException {

        String path;
        String placeToSave;

        String tmp = url.getPath();
        path = tmp.substring(tmp.indexOf("docs"),tmp.length());

        placeToSave = path.substring(path.indexOf("docs"),path.lastIndexOf('/'));

        FileUtils.forceMkdir(new File(placeToSave));
        if(isValid(url)) {
            File file = new File(path);
            makeFile(url, file);

        }



    }
   private void makeFile(URL url, File file) throws IOException {
        try{
            FileUtils.copyURLToFile(url, file);
        }catch (Exception e){
            FileUtils.forceMkdir(file);

        }

   }


    public void save(String stringURL) throws IOException, URISyntaxException {
        URL url = new URL(stringURL);
        saveFromURL(url);
    }
    public void save(URL url) throws IOException, URISyntaxException {
        saveFromURL(url);
    }
    private boolean isValid(URL url) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url.toURI());
        HttpResponse response = httpclient.execute(httpget);
        httpclient.close();

        int statusCode = response.getStatusLine().getStatusCode();


        return statusCode >= 200 && statusCode < 300 ;
    }



}
