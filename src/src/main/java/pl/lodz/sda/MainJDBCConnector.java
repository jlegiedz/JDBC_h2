package pl.lodz.sda;
import org.apache.commons.csv.CSVRecord;
import java.sql.*;
import java.util.List;
import static pl.lodz.sda.EmployeeMapper.toEmployeeList;
import static pl.lodz.sda.JdbcConnector.getDBConnection;

/**
 * Created by asia on 17/09/17.
 */
public class MainJDBCConnector {

    public static void main(String[] args) {

        try {
            withFactoryPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

         FileReader reader = new FileReader();
         List<CSVRecord> records = reader.readFile();
         List<Employee> employeesListFromCSV = toEmployeeList(records);
         JdbcConnector jdbcConnector = new JdbcConnector();
         jdbcConnector.batchInsert(employeesListFromCSV);
            try {
            printDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void printDB() throws SQLException{
        Connection connection = getDBConnection(DB.H2);

        String selectQuery = "select * from Employee";

        PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
        ResultSet rs = selectPreparedStatement.executeQuery();
        while(rs.next()){
            System.out.println("Id: " + rs.getInt("id") +
                    "Birth date: " + rs.getDate("birth_date") +
                    " Name: " + rs.getString("first_name") +
                    "Last name: " + rs.getString("last_name")+
                    "Gender: " + rs.getString("gender")+
                    "Hire date: " + rs.getDate("hire_date"));
        }
        connection.commit();
        connection.close();
    }


        private static void withFactoryPreparedStatement() throws SQLException {
            Connection connection = getDBConnection(DB.H2);

            String createQuery = "CREATE TABLE EMPLOYEE (" +
                    "ID INT PRIMARY KEY auto_increment, " +
                    "BIRTH_DATE DATE, " +
                    "FIRST_NAME VARCHAR(50), " +
                    "LAST_NAME VARCHAR(50), " +
                    "GENDER varchar(1), " +
                    "HIRE_DATE DATE)";



            try {
                connection.setAutoCommit(false);
                //wstepne skompliowanie zapytania
                PreparedStatement createPreparedStatement = connection.prepareStatement(createQuery);
                createPreparedStatement.executeUpdate();
                createPreparedStatement.close();
                //no SQL statements are committed until the method commit is called when AutoCommit is set to false:
                connection.commit();



            } catch (SQLException e) {
                System.out.println("Exception Message " + e.getLocalizedMessage());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }

}
