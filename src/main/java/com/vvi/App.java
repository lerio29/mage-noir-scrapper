package com.vvi;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Main App
 *
 */
public class App {

    private static final String IMAGE_HOME = "/tmp";
    private static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        logger.info("Scrapper On :  ");
        Document doc = null;
        try {
            doc = Jsoup.connect("https://magenoir.com/collection_fr.html")
                    .userAgent("Mozilla")
                    // .cookie("auth", "token")
                    .timeout(3000).get();

                    

            logger.debug(doc.title());
            // System.out.println(doc.body().text());
            Elements links = doc.select(".container .portfolioContainer .isotope-item  a");
            logger.debug(links.text());

            for (Element link : links) {
                System.out.println(link.attr("title"));
                logger.debug(link.attr("title"));
                logger.debug(link.absUrl("href"));

                
            }
        } catch (IOException e) {
            logger.error(e.getMessage());

        }

    }

    public static String storeImageIntoFS(String imageUrl, String fileName, String relativePath) {
        String imagePath = null;
        try {
            byte[] bytes = Jsoup.connect(imageUrl).ignoreContentType(true).execute().bodyAsBytes();
            
            try(FileOutputStream fos = new FileOutputStream("/tmp/test.png")){
                fos.write(bytes);
            }catch(Exception e){
                logger.debug(e.getMessage());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}
