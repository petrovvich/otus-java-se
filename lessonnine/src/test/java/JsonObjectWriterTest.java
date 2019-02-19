import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import writer.JsonObjectWriter;
import writer.objects.ArraysObject;
import writer.objects.CollectionObject;
import writer.objects.ComplexObject;
import writer.objects.PrimitiveObject;

import java.util.*;

public class JsonObjectWriterTest {

    @Test
    public void nullTest() {
        Assert.assertEquals(new GsonBuilder().create().toJson(null), JsonObjectWriter.toJson(null));
    }

    @Test
    public void primitiveObjectTest() {
        PrimitiveObject input = getRandomPrimitiveObject();
        Assert.assertEquals(new GsonBuilder().create().toJson(input), JsonObjectWriter.toJson(input));
    }

    @Test
    public void testComplexObject() {
        ComplexObject input = getComplexObject();
        Assert.assertEquals(new GsonBuilder().create().toJson(input), JsonObjectWriter.toJson(input));
    }

    @Test
    public void testCollectionObject() {
        CollectionObject input = getCollectionObject();
        Assert.assertEquals(new GsonBuilder().create().toJson(input), JsonObjectWriter.toJson(input));
    }

    @Test
    public void testArraysObject() {
        ArraysObject input = getArraysObject();
        Assert.assertEquals(new GsonBuilder().create().toJson(input), JsonObjectWriter.toJson(input));
    }

    private PrimitiveObject getRandomPrimitiveObject() {
        PrimitiveObject primitiveObject = new PrimitiveObject();

        Random rand = new Random();

        primitiveObject.setIntVar(rand.nextInt(10000));
        primitiveObject.setShortVar((short) rand.nextInt(1 << 15));
        primitiveObject.setLongVar(rand.nextLong());
        primitiveObject.setDoubleVar(rand.nextDouble());
        primitiveObject.setFloatVar(rand.nextFloat());
        primitiveObject.setBoolVar(rand.nextBoolean());
        primitiveObject.setByteVar((byte) rand.nextInt(255));
        primitiveObject.setCharVar((char) (rand.nextInt(26) + 'a'));
        primitiveObject.setStringVar("JSON");

        return primitiveObject;
    }

    private ComplexObject getComplexObject() {
        ComplexObject result = new ComplexObject();

        result.setIntVar(1456);
        result.setTransientStringTest("This is public transient String transientStringTest for ComplexObject");
        result.setPrimitiveTestObject(getRandomPrimitiveObject());
        Set<PrimitiveObject> objects = new HashSet<>();
        for (int i = 0; i < 12; i++) {
            objects.add(getRandomPrimitiveObject());
        }
        result.setPrimitiveTestObjectSet(objects);

        return result;
    }

    private CollectionObject getCollectionObject() {
        CollectionObject result = new CollectionObject();

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 210; i++) {
            integers.add(new Random().nextInt());
        }
        result.setIntegerList(integers);

        Set<String> strings = new HashSet<>();
        for (int i = 0; i < 56; i++) {
            strings.add(String.valueOf(new Random().nextInt()));
        }
        result.setStringSet(strings);

        Map<Integer, String> stringStringMap = new HashMap<>();
        for (int i = 0; i < 156; i++) {
            stringStringMap.put(new Random().nextInt(), String.valueOf(new Random().nextFloat()));
        }
        result.setTestMap(stringStringMap);
        return result;
    }

    private ArraysObject getArraysObject() {
        ArraysObject result = new ArraysObject();

        int[] integers = new int[10];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = new Random().nextInt();
        }
        result.setIntArrayVar(integers);

        float[] floats = new float[156];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = new Random().nextFloat();
        }
        result.setFloatArrayVar(floats);

        boolean[] booleans = new boolean[2665];
        for (int i = 0; i < booleans.length; i++) {
            booleans[i] = new Random().nextBoolean();
        }
        result.setBoolArrayVar(booleans);
        return result;
    }
}
