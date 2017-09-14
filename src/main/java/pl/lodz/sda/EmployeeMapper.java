package pl.lodz.sda;

import org.apache.commons.csv.CSVRecord;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    List<Employee> toEmployeeList(List<CSVRecord> records){
        List<Employee> employees = new ArrayList<>();
        return employees;
    }
    Employee toEmployee(CSVRecord record){
        return new Employee(Integer.parseInt(record.get(0)),
                new Date(record.get(1)),
                record.get(2),
                record.get(3),
                record.get(4).toCharArray()[0],
                new Date(record.get(5)));
    }

}
