package pl.lodz.sda;

import java.util.List;

public interface JdbcConnectorApi {

    public void batchInsert(List<Employee> employees);

    // by zapisac dane z bazy w csv uzyjemy metody
    // zaimplementujemy ja w JDBCConnector
    //trzeba w niej swtorzyc selecta, przeiterowac, stworzyc model pracownikow (liste obiekotw employee) i zwrocic je

    public List<Employee> selectEmployees();
}
