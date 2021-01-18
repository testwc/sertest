import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class SerObj implements Serializable {
    public Map map;

    private void readObject(java.io.ObjectInputStream in) throws ClassNotFoundException , IOException {
        in.defaultReadObject();
        Map.Entry e = (Map.Entry)map.entrySet().iterator().next();
        e.setValue("test3");
    }
}