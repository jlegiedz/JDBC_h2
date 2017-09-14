package pl.lodz.sda;

import java.sql.*;

public class H2MemoryDatabaseExample {
    // h2 mala lokalna baza w pamieci, zazwyczaj uzywana do testow, serwer jest na naszym komputerze
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws Exception {
        try {
           // insertWithStatement();
            //insertWithPreparedStatement();
            insertTestData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertTestData() {
        Connection connection = getDBConnection();

        String insertQuery = "insert into employee values("+
                "1,'Janusz','Kowalski', 'T', 21)";
        String createQuery = "CREATE TABLE EMPLOYEE (" +
                "ID INT PRIMARY KEY, " +
                "FIRST_NAME VARCHAR(50), " +
                "LAST_NAME VARCHAR(50), " +
                "GENDER CHAR(1), " +
                "AGE TINYINT)";

        String selectQuery = "select * from Employee";
        Statement statement;
        try {
            statement = connection.createStatement();
            System.out.println("create query:" + statement.execute(createQuery));
            System.out.println("insert query:" + statement.execute(insertQuery));

           // System.out.println("select query:" + statement.execute(selectQuery));
            ResultSet rs = statement.executeQuery(selectQuery);
            while(rs.next()){
                System.out.println("id: " + rs.getInt(1)+ " " +
                "imie: " + rs.getString(2)+ ", " +
                "nazwisko: " + rs.getString(3)+ ", " +
                "plec: " + rs.getString(4)+ ", "+
                "wiek: " + rs.getInt(5));

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}

    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = getDBConnection();

        String CreateQuery = "CREATE TABLE PERSON(id int primary key, name varchar(255))";
        String InsertQuery = "INSERT INTO PERSON" + "(id, name) values" + "(?,?)";
        String SelectQuery = "select * from PERSON";

        try {
            connection.setAutoCommit(false);
            PreparedStatement createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            PreparedStatement insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setInt(1, 1);
            insertPreparedStatement.setString(2, "Jose");
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            PreparedStatement selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 In-Memory Database inserted through PreparedStatement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
            }
            selectPreparedStatement.close();
            connection.commit();

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    private static void insertWithStatement() throws SQLException {
        Connection connection = getDBConnection();
        try {
            //The way to allow two or more statements to be grouped into a transaction is to disable the auto-commit mode.
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255))");
            stmt.execute("INSERT INTO PERSON(id, name) VALUES(1, 'Anju')");
            stmt.execute("INSERT INTO PERSON(id, name) VALUES(2, 'Sonia')");
            stmt.execute("INSERT INTO PERSON(id, name) VALUES(3, 'Asha')");

            ResultSet rs = stmt.executeQuery("select * from PERSON");
            System.out.println("H2 In-Memory Database inserted through Statement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
            }

            stmt.execute("DROP TABLE PERSON");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    private static Connection getDBConnection() {
        try {
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
