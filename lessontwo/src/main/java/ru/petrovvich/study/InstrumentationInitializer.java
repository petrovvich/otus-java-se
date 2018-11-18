package ru.petrovvich.study;

import java.lang.instrument.Instrumentation;

public class InstrumentationInitializer {

    private static volatile Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static void getObjectSize(Object objectToMeasure) {
        if (instrumentation == null) {
            throw new IllegalStateException("To correctly start an app, please add VM option " +
                    "-javaagent:\"{path_to_jar}/InstrumentationAgent.jar\"");
        }
        Long size = instrumentation.getObjectSize(objectToMeasure);

        System.out.println("For object with type: " + objectToMeasure.getClass() + " size is: " + size + " bytes");
    }
}