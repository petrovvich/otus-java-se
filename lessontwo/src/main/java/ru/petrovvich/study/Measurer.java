package ru.petrovvich.study;

import java.util.function.Supplier;

class Measurer {
    private final static int ARRAY_SIZE = 30_000;

    static <T> long measure(Supplier<T> objectGetter)  {
        Object[] array = new Object[ARRAY_SIZE];
        long mem1 = getMem();

        for (int i = 0; i < array.length; i++) {
            array[i] = objectGetter.get();
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("Element size: " + size + " len:" + array.length);
        return size;
    }

    public static long getMem() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static long measureChar() {
        long mem1 = getMem();
        char[] array = new char[ARRAY_SIZE];
        System.out.println("after char: " + getMem());

        for (int i = 0; i < array.length; i++) {
            array[i] += 'i';
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("char size: " + size + " len:" + array.length);
        return size;
    }
}