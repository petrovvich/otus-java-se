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

        measurerVisitor.visit(() -> new String(new char[0]));

        measurerVisitor.visit(() ->new String(new byte[0]));
        measurerVisitor.visit(() ->Boolean.FALSE);
        //about 48 bytes
        measurerVisitor.visit(((Supplier<HashMap>) HashMap::new));
        //about 32 bytes
        measurerVisitor.visit(((Supplier<LinkedList>) LinkedList::new));
        //about 64 bytes
        measurerVisitor.visit(((Supplier<TreeSet>) TreeSet::new));

    }
}