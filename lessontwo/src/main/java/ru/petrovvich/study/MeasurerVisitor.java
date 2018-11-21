package ru.petrovvich.study;

public interface MeasurerVisitor {

    void visit(int i);

    void visit(char c);

    void visit(short s);

    void visit(byte by);

    void visit(long l);

    void visit(boolean bool);

    void visit(double d);

    void visit(float f);

    void visit(Object object);
}
