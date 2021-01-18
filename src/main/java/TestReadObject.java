import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestReadObject {
    public void testReadObject() throws IOException, ClassNotFoundException {
        FileInputStream fis =new FileInputStream("aa.ser");
        ObjectInputStream os =new ObjectInputStream(fis);
        os.readObject();
        os.close();
    }
}
