import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class CrawlingData {
    public static void main(String[] args) throws Exception {
        String link = "https://fptshop.com.vn/apple/iphone";
        Document doc = Jsoup.connect(link).timeout(50000).get();
        List<Pair> productInfo = new ArrayList<Pair>();
        ArrayList<Element> products = doc.getElementsByClass("product-grid productitem").get(0).getElementsByClass("product product-grid__item product--absolute");
        for (Element product : products) {
            String name = product.getElementsByClass("product_name").get(0).text();
            Integer price = Integer.parseInt(product.getElementsByClass("product_progress").get(0).text().replaceAll("[.₫\\s]", ""));
            productInfo.add(new Pair(name, price));
        }
        Collections.sort(productInfo, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if(o1.getName().compareTo(o2.getName()) != 0){
                    return o1.getName().compareTo(o2.getName());
                }
                return o1.getPrice().compareTo(o2.getPrice());
            }
        });
        for (Pair pair : productInfo) {
            System.out.println(pair.getName() + ": " + pair.getPrice());
        }
    }
}
