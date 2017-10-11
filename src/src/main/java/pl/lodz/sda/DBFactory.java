package pl.lodz.sda;


import static pl.lodz.sda.DB.H2;
import static pl.lodz.sda.DB.MYSQL;

public class DBFactory {

    // h2 mala lokalna baza w pamieci, zazwyczaj uzywana do testow, serwer jest na naszym komputerze
    private static final String H2_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    public ConnectionCredentials chooseDb(DB db){
        if(db == H2){
            return new ConnectionCredentials(H2_CONNECTION, H2_USER,H2_PASSWORD);
        }
        else if(db==MYSQL){
            return new ConnectionCredentials("","","");
        }
        else{
            throw new RuntimeException("WRONG DB");
        }
    }
}
