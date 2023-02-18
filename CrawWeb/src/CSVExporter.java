import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;
public class CSVExporter {
    private List<Pair> data;
    public CSVExporter(List<Pair> data){
        this.data = data;
    }
    public List<String[]> createCsvDataSimple() {
        List<String[]> list = new ArrayList<>();
        for (Pair info: data){
            String[] record = {info.getName(), String.valueOf(info.getPrice())};
            list.add(record);
        }
        return list;
    }
    public void writeCSV() throws IOException{
        List<String[]> info = createCsvDataSimple();
        try (CSVWriter writer = new CSVWriter(new FileWriter("Products.csv"))) {
            writer.writeAll(info);
        }
    }
}
