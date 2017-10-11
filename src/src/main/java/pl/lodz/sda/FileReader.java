package pl.lodz.sda;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FileReader {
    String FILE_NAME = "/Users/asia/IdeaProjects/JDBC_h2/src/src/main/resources/employees.csv";
    CSVFormat format = CSVFormat.DEFAULT.withDelimiter(',').withFirstRecordAsHeader();


    public List<CSVRecord> readFile(){

        List<CSVRecord> records= null;
        try {
            //gosc przyjmuje plik
            java.io.FileReader fileReader = new java.io.FileReader(FILE_NAME);
            //parsuje
            CSVParser parser = new CSVParser(fileReader,format);
            //rekordy wrzuca do listy
            records = parser.getRecords();
            System.out.println("Liczba rekordow: " + records.size());
            fileReader.close();
        }
       catch (IOException e) {
           e.printStackTrace();
       }
       return records;

    }
}
