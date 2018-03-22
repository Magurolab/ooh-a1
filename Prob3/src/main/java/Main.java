import java.net.URL;
import org.jsoup.Jsoup;

public class Main {
    public static void main(String[] args) throws Exception {
        String URLString = "https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html";
        URLDownLoader reader = new URLDownLoader(URLString);

        reader.performURLConnectionMethod();
        reader.performCommonApacheMethod();
        reader.performHttpApacheMethod();


    }
}
