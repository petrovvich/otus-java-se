package writer.objects;

import lombok.Data;

@Data
public class PrimitiveObject {
    public int intVar;
    public short shortVar;
    public long longVar;
    public float floatVar;
    public double doubleVar;

    public boolean boolVar;
    public char charVar;
    public byte byteVar;

    public String stringVar;

    private final static int FINAL_VAR = 33;
}
