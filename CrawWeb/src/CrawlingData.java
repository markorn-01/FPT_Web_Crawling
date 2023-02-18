import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CrawlingData {
    public static void main(String[] args) throws Exception {
        String link = "https://fptshop.com.vn/apple/iphone";
        Document doc = Jsoup.connect(link).timeout(50000).get();
        HashMap<String, Integer> productInfo = new HashMap<String, Integer>();
        ArrayList<Element> products = doc.getElementsByClass("product-grid productitem").get(0).getElementsByClass("product product-grid__item product--absolute");
        for (Element product : products) {
            String name = product.getElementsByClass("product_name").get(0).text();
            Integer price = Integer.parseInt(product.getElementsByClass("product_progress").get(0).text().replaceAll("[.₫\\s]", ""));
            productInfo.put(name, price);
            System.out.println( name + ": " + productInfo.get(name));
            System.out.println("==================================");
        }
    }
}
