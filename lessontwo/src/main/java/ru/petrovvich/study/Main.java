package ru.petrovvich.study;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        MeasurerVisitor measurerVisitor = new MeasurerVisitorImpl();

        measurerVisitor.visit(1);
        measurerVisitor.visit('1');
        measurerVisitor.visit(1L);
        measurerVisitor.visit(2.0);
        measurerVisitor.visit(1.5f);
        measurerVisitor.visit((short) 2);
        measurerVisitor.visit((byte) 1.2);
        measurerVisitor.visit(true);

        System.out.println("String char: " + Measurer.measure(() -> new String(new char[0])));
        System.out.println("String byte: " + Measurer.measure(() -> new String(new byte[0])));
        System.out.println("Boolean object size is: " + Measurer.measure(() -> Boolean.FALSE));
        //about 48 bytes
        System.out.println("HashMap object size is: " + Measurer.measure((Supplier<HashMap>) HashMap::new));
        //about 32 bytes
        System.out.println("LinkedList object size is: " + Measurer.measure((Supplier<LinkedList>) LinkedList::new));
        //about 64 bytes
        System.out.println("TreeSet object size is: " + Measurer.measure((Supplier<TreeSet>) TreeSet::new));

    }
}