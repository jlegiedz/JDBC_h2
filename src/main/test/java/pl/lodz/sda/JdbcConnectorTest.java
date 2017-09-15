package pl.lodz.sda;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;
import static pl.lodz.sda.EmployeeMapper.toEmployeeList;

public class JdbcConnectorTest  {
        JdbcConnector jdbcConnector = new JdbcConnector();
        FileReader fileReader = new FileReader();

//@Before
//public void initDB(){
//        try(Connection dBConnection = JdbcConnector.getDBConnection();
//            Statement statement = dBConnection.createStatement();
//            statement.execute(cre);
//
//        }
//         catch (SQLException e) {
//            e.printStackTrace();
//        }

    @Test
public void batchInsert() throws Exception {
        List<CSVRecord> csvRecords = fileReader.readFile();
        jdbcConnector.batchInsert(toEmployeeList(csvRecords));
        }
        }