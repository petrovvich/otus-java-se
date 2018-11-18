package ru.petrovvich.study;

import java.util.ArrayList;
import java.util.List;

/**
 * To correctly start an app, please add VM option -javaagent:"{path_to_jar}/InstrumentationAgent.jar"
 */

public class Main {

    public static void main(String[] args) {
        String emptyString = "";
        String string = "Estimating Object Size Using Instrumentation";
        String[] stringArray = { emptyString, string, "com.baeldung" };
        String[] anotherStringArray = new String[100];
        List<String> stringList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(100);
        int maxIntPrimitive = Integer.MAX_VALUE;
        int minIntPrimitive = Integer.MIN_VALUE;
        Integer maxInteger = Integer.MAX_VALUE;
        Integer minInteger = Integer.MIN_VALUE;
        long zeroLong = 0L;
        double zeroDouble = 0.0;
        boolean falseBoolean = false;
        Object object = new Object();

        class EmptyClass {
        }
        EmptyClass emptyClass = new EmptyClass();

        class StringClass {
            public String s;
        }
        StringClass stringClass = new StringClass();

        InstrumentationInitializer.getObjectSize(emptyString);
        InstrumentationInitializer.getObjectSize(string);
        InstrumentationInitializer.getObjectSize(stringArray);
        InstrumentationInitializer.getObjectSize(anotherStringArray);
        InstrumentationInitializer.getObjectSize(stringList);
        InstrumentationInitializer.getObjectSize(stringBuilder);
        InstrumentationInitializer.getObjectSize(maxIntPrimitive);
        InstrumentationInitializer.getObjectSize(minIntPrimitive);
        InstrumentationInitializer.getObjectSize(maxInteger);
        InstrumentationInitializer.getObjectSize(minInteger);
        InstrumentationInitializer.getObjectSize(zeroLong);
        InstrumentationInitializer.getObjectSize(zeroDouble);
        InstrumentationInitializer.getObjectSize(falseBoolean);
        InstrumentationInitializer.getObjectSize(object);
        InstrumentationInitializer.getObjectSize(emptyClass);
        InstrumentationInitializer.getObjectSize(stringClass);
    }
}