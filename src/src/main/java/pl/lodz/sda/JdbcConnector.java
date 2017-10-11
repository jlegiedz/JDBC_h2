package pl.lodz.sda;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// DRUGA WERSJA: STWORZENIE DBFactory, ConnectionCreditentials i JdbcConenctionApi

public class JdbcConnector implements JdbcConnectorApi {

    // tworze polaczenie na podstawie fabryki z DBFactory

    public static Connection getDBConnection(DB db) {
        DBFactory dbFactory = new DBFactory();
        //tworze obiekt CnnectionCreditentials uzywajac metody z DBFactory: utworzony
        // dla H2 lub Mysql
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


    @Override
    public void batchInsert(List<Employee> employees) {
        Connection connection = getDBConnection(DB.H2);
        Statement statement;
        try {
            statement= connection.createStatement();
            for (Employee emp : employees) {
                String sql = emp.toStatementInsertQuery();
                //https://www.tutorialspoint.com/jdbc/jdbc-batch-processing.html
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






}
