package pl.lodz.sda;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileReaderTest {
    @Test
    public void readFile() throws Exception {
        FileReader fileReader = new FileReader();
        fileReader.readFile();
    }

}