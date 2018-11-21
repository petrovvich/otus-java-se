package ru.petrovvich.study;

import static ru.petrovvich.study.Measurer.getMem;

public class MeasurerVisitorImpl implements MeasurerVisitor {

    private final static int ARRAY_SIZE = 300000;

    @Override
    public void visit(int i) {
        long mem1 = getMem();
        int[] array = new int[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += i;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("int size: " + size + " len: " + array.length);

        System.out.println("Measure int: " + size);
    }

    @Override
    public void visit(char c) {
        long mem1 = getMem();
        char[] array = new char[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += c;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("char size: " + size + " len: " + array.length);

        System.out.println("Measure char: " + size);
    }

    @Override
    public void visit(short s) {
        long mem1 = getMem();
        short[] array = new short[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += s;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("short size: " + size + " len: " + array.length);

        System.out.println("Measure short: " + size);
    }

    @Override
    public void visit(byte by) {
        long mem1 = getMem();
        byte[] array = new byte[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += by;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("byte size: " + size + " len: " + array.length);

        System.out.println("Measure byte: " + size);
    }

    @Override
    public void visit(long l) {
        long mem1 = getMem();
        long[] array = new long[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += l;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("long size: " + size + " len: " + array.length);

        System.out.println("Measure long: " + size);
    }

    @Override
    public void visit(boolean bool) {
        long mem1 = getMem();
        boolean[] array = new boolean[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            if (z%2 == 0) {
                array[z] = true;
            }
            array[z] = false;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("boolean size: " + size + " len: " + array.length);

        System.out.println("Measure boolean: " + size);
    }

    @Override
    public void visit(double d) {
        long mem1 = getMem();
        double[] array = new double[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += d;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("double size: " + size + " len: " + array.length);

        System.out.println("Measure double: " + size);
    }

    @Override
    public void visit(float f) {
        long mem1 = getMem();
        float[] array = new float[ARRAY_SIZE];

        for (int z = 0; z < array.length; z++) {
            array[z] += f;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("float size: " + size + " len: " + array.length);

        System.out.println("Measure float: " + size);
    }

    @Override
    public void visit(Object object) {

    }
}
