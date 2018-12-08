package ru.petrovvich.study;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyArrayListTest {

    List<String> myList;
    List<String> myCopyList;

    public MyArrayListTest(){}

    @Before
    public void setUp() throws Exception {
        myList = new MyArrayList<>(Arrays.asList("Leningrad", "Stambul", "Ekaterinburg", "Seoul"));
        myCopyList = new MyArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        myList = null;
        myCopyList = null;
    }

    @Test
    public void testSize() {
        assertEquals(4, myList.size());
        assertEquals(0, myCopyList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(myCopyList.isEmpty());
        assertFalse(myList.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(myList.contains("Leningrad"));
    }


    @Test
    public void testToArray() {
        Object[] myArray = myList.toArray();
        assertEquals("Leningrad", myArray[0]);
        assertEquals("Stambul", myArray[1]);
        assertEquals("Ekaterinburg", myArray[2]);
        assertEquals("Seoul", myArray[3]);
    }

    @Test
    public void testToArrayTArray() {
        String[] myArray = myList.toArray(new String[0]);
        assertEquals("Leningrad", myArray[0]);
        assertEquals("Stambul", myArray[1]);
        assertEquals("Ekaterinburg", myArray[2]);
        assertEquals("Seoul", myArray[3]);
    }

    @Test
    public void testAddE() {
        assertEquals(4, myList.size());
        myList.add("five");
        assertEquals(5, myList.size());
        assertEquals("five", myList.get(4));
    }

    @Test
    public void testRemoveObject() {
        assertEquals("Stambul", myList.get(1));
        myList.remove("Stambul");
        assertEquals("Ekaterinburg", myList.get(1));
    }

    @Test
    public void testContainsAll() {
        myCopyList.add("Leningrad");
        myCopyList.add("Ekaterinburg");
        assertTrue(myList.containsAll(myCopyList));
    }

    @Test
    public void testAddAllCollectionOfQextendsE() {
        myCopyList.add("five");
        myCopyList.add("six");
        myList.addAll(myCopyList);
        assertEquals(6, myList.size());
        assertEquals("six", myList.get(5));
    }

    @Test
    public void testAddAllIntCollectionOfQextendsE() {
        myCopyList.add("one_and_a_half");
        myCopyList.add("one_three_quarters");
        myList.addAll(1, myCopyList);
        assertEquals(6, myList.size());
        assertEquals(1, myList.indexOf("one_and_a_half"));
        assertEquals("Stambul", myList.get(3));
    }

    @Test
    public void testRemoveAll() {
        myCopyList.add("Leningrad");
        myCopyList.add("Ekaterinburg");
        assertTrue(myList.removeAll(myCopyList));
        assertEquals(2, myList.size());
        assertEquals("Stambul", myList.get(0));
        assertEquals("Seoul", myList.get(1));
    }

    @Test
    public void testRetainAll() {
        myCopyList.add("Leningrad");
        myCopyList.add("Ekaterinburg");
        assertTrue(myList.retainAll(myCopyList));
        assertEquals(2, myList.size());
        assertEquals("Leningrad", myList.get(0));
        assertEquals("Ekaterinburg", myList.get(1));
    }

    @Test
    public void testClear() {
        assertEquals(4, myList.size());
        myList.clear();
        assertEquals(0, myList.size());
    }

    @Test
    public void testGet() {
        assertEquals("Leningrad", myList.get(0));
        assertEquals("Stambul", myList.get(1));
    }

    @Test
    public void testSet() {
        assertEquals("Leningrad", myList.get(0));
        myList.set(0, "ten");
        assertEquals("ten", myList.get(0));
    }

    @Test
    public void testAddIntE() {
        assertEquals(4, myList.size());
        myList.add(0, "five");
        assertEquals(5, myList.size());
        assertEquals("five", myList.get(0));
    }

    @Test
    public void testRemoveInt() {
        assertEquals(4, myList.size());
        myList.remove(0);
        assertEquals(3, myList.size());
        assertEquals("Stambul", myList.get(0));
    }

    @Test
    public void testIndexOf() {

        assertEquals(0, myList.indexOf("Leningrad"));
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(3, myList.lastIndexOf("Seoul"));
        myList.add("Seoul");
        assertEquals(4, myList.lastIndexOf("Seoul"));
    }

}