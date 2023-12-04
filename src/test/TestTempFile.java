import data_access.TempFileWriterDataAccessObject;
import org.junit.Test;

public class TestTempFile {

    @Test
    public void testMakeFile() {
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("new name 10");
    }
}
