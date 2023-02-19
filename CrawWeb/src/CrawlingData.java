import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class CrawlingData {
    public static void crawlData(String link, List<Pair> productInfo) throws Exception{     
        Document doc = Jsoup.connect(link).timeout(50000).get();
        ArrayList<Element> products = doc.getElementsByClass("product-grid productitem").get(0).getElementsByClass("product product-grid__item product--absolute");
        for (Element product : products) {
            String name = product.getElementsByClass("product_name").get(0).text();
            Integer price = 0;
            if (product.getElementsByClass("product_info").first().children().hasClass("product_timing")){
                price = Integer.parseInt(product.getElementsByClass("product_progress").get(0).text().replaceAll("[.₫\\s]", ""));
            }
            if (product.getElementsByClass("product_info").first().children().hasClass("product__price")) {
                price = Integer.parseInt(product.getElementsByClass("price").get(0).text().replaceAll("[.₫\\s]", ""));
            }
            productInfo.add(new Pair(name, price));
        }
        
    }
    public static void sortData(List<Pair> productInfo){
        Collections.sort(productInfo, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if(o1.getName().compareTo(o2.getName()) != 0){
                    return o2.getName().compareTo(o1.getName());
                }
                return o2.getPrice().compareTo(o1.getPrice());
            }
        });
    }
    public static void showData(List<Pair> productInfo){
        for (Pair pair : productInfo) {
            System.out.println(pair.getName() + ": " + pair.getPrice());
        }
    }
    public static void exportCSV(List<Pair> productInfo, String filename) throws IOException{
        List<String[]> list = new ArrayList<>();
        for (Pair info: productInfo){
            String[] record = {info.getName(), String.valueOf(info.getPrice())};
            list.add(record);
        }
        CSVExporter csv = new CSVExporter(list);     
        String[] title = {"Product name", "Price"};
        csv.writeCSV(title, filename);
    }
    public static void main(String[] args) throws Exception {
        String linkIphone = "https://fptshop.com.vn/apple/iphone";
        String linkMacbook = "https://fptshop.com.vn/apple/macbook";
        String linkIpad = "https://fptshop.com.vn/apple/ipad";
        List<Pair> list = new ArrayList<Pair>();
        crawlData(linkIphone, list);
        crawlData(linkMacbook, list);
        crawlData(linkIpad, list);
        sortData(list);
        showData(list);
        exportCSV(list, "Products");
    }
}
