package ru.petrovvich.study;

import java.util.function.Supplier;

public interface MeasurerVisitor {

    void visit(int i);

    void visit(char c);

    void visit(short s);

    void visit(byte by);

    void visit(long l);

    void visit(boolean bool);

    void visit(double d);

    void visit(float f);

    <T> void visit(Supplier<T> objectGetter);
}
