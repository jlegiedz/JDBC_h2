package pl.lodz.sda;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FileWriter {

    String fileName= "bibliotekaWritten.csv";
    CSVFormat csvFormat  = CSVFormat.RFC4180.withDelimiter(',');


    public void printInCSV(List<Employee> employees){
        CSVPrinter printer =null;
        java.io.FileWriter fileWriter = null;
        try {
            printer = new CSVPrinter(fileWriter,csvFormat);
            fileWriter = new java.io.FileWriter(fileName);

            CSVPrinter finalPrinter = printer;
            employees.forEach(employee -> {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    finalPrinter.printRecord(
                            employee.getId(),
                            df.format(employee.getBirth_date()),
                            employee.getFirst_name(),
                            employee.getLast_name(),
                            employee.getGender(),
                            df.format(employee.getHire_date()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
