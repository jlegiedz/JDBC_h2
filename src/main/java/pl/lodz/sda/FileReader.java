package pl.lodz.sda;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FileReader {
    String FILE_NAME = "C:\\jdbc-connector-master\\jdbc-connector\\src\\main\\resources\\employees.csv";
    CSVFormat format = CSVFormat.DEFAULT.withDelimiter(',');
    String PATH = "";

    public void readFile(List<Employee> list){
        java.io.FileReader fileReader;
        try {
            //gosc przyjmuje plik
            fileReader = new java.io.FileReader(FILE_NAME);
            //parsuje
            CSVParser parser = new CSVParser(fileReader,format);
            //rekordy wrzuca do listy
            List<CSVRecord> records = parser.getRecords();
            System.out.println("Liczba rekordow: " + records.size());
            fileReader.close();
        }
       catch (IOException e) {
           e.printStackTrace();
       }

    }
}
