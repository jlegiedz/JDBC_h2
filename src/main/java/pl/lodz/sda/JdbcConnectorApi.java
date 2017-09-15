package pl.lodz.sda;

import java.util.List;

public interface JdbcConnectorApi {
    public void batchInsert(List<Employee> employees);

    // by zapisac dane z bazy w csv uzyjemy metody
    // zaimplementujemy ja w JDBCConnector
    //trzeba w niej swtorzyc selecta, przeiterowac, stworzyla model pracownikow(iste obiekotw employee) i zwrocila je

    public List<Employee> selectEmployees();
}
