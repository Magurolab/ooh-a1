import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;




public class URLDownLoader {
    private URL url;
    private String fileName;

    public URLDownLoader(String URLString) throws Exception {
        url = new URL(URLString);
        fileName = URLString.substring(URLString.lastIndexOf('/')+1);
        System.out.println("Downloading: " + fileName);
    }

    public void performURLConnectionMethod() throws IOException {
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                fileWriter.write(inputLine + "\n");

        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("performURLConnectionMethod() Done!");

    }


    public void performCommonApacheMethod() throws IOException {
        File file = new File(fileName);
        FileUtils.copyURLToFile(url, file);
        System.out.println("performCommonApacheMethod() Done!");

    }

    public void performHttpApacheMethod() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url.toURI());
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        InputStream inputStream = entity.getContent();
        FileOutputStream fileOut = new FileOutputStream(new File(fileName));

        FileUtils.copyInputStreamToFile(inputStream, file);
        int inByte;
        while((inByte = inputStream.read()) != -1)
            fileOut.write(inByte);
        inputStream.close();
        fileOut.close();

        System.out.println("performHttpApacheMethod() Done!");

    }
}
