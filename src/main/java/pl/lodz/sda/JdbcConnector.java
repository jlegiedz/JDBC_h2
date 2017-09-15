package pl.lodz.sda;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcConnector implements JdbcConnectorApi {

    // h2 mala lokalna baza w pamieci, zazwyczaj uzywana do testow, serwer jest na naszym komputerze
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    @Override
    public void batchInsert(List<Employee> employees) {
        Connection connection = getDBConnection();
        Statement statement;
        try {
            statement= connection.createStatement();
            for (Employee emp : employees) {
                String sql = emp.toStatementInsertQuery();
                //https://www.tutorialspoint.com/jdbc/jdbc-batch-processing.htm
                statement.addBatch(sql);

            }
            int[] insertedRows = statement.executeBatch();
            System.out.println("inserted rows: " +
                    Arrays.toString(insertedRows));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                statement = connection.prepareStatement("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public List<Employee> selectEmployees(){
        Connection dbConnection = getDBConnection(DB.H2);
        List<Employee> employees = new ArrayList<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(Employee.selectQuery());

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                Date date_birth = resultSet.getDate(2);
                String first_name = resultSet.getString(3);
                String last_name = resultSet.getString(4);
                String gender = resultSet.getString(5);
                Date date_hire = resultSet.getDate(6);
                employees.add(new Employee(id,date_birth,first_name,last_name,gender.charAt(0),date_hire));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return employees;
    }

    public static Connection getDBConnection() {
        try {
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // tworze polazenie na podstawie fabryki z DBFactory
    public static Connection getDBConnection(DB db) {
        DBFactory dbFactory = new DBFactory();
        ConnectionCredentials connectionCredentials = dbFactory.chooseDb(db);
        try {
            return DriverManager.getConnection(
                    connectionCredentials.connection,
                    connectionCredentials.user,
                    connectionCredentials.password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
