package writer.objects;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ComplexObject {

    private int intVar;
    public transient String transientStringTest;
    public PrimitiveObject primitiveTestObject;
    public Set<PrimitiveObject> primitiveTestObjectSet = new HashSet<>();
}
