package ru.petrovich.study.test.initializer;

import ru.petrovich.study.test.interfaces.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestInitializer {



    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initByFullPackageName("ru.petrovich.study.TestingClass");
        initByPackageName("ru.petrovich.study");
    }

    public static void initByPackageName(String s) {

    }


    public static void initByFullPackageName(String className) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        Class<?> clazz = Class.forName(className);

        Constructor<?> classConstructor = clazz.getConstructor();

        Object o = classConstructor.newInstance();

        List<Method> methodList = Arrays.asList(o.getClass().getDeclaredMethods());

        runTestMethods(o, methodList, "Before");

        runTestMethods(o, methodList, "Test");

        runTestMethods(o, methodList, "After");
    }

    private static void runTestMethods(Object o, List<Method> methodList, String annotationName) throws InvocationTargetException, IllegalAccessException {
        for (Method m : methodList) {
            if (checkMethodAnnotations(m, annotationName)) {
                String result = (String) m.invoke(o);
                if (result != null)
                    System.out.println(result);
            }
        }
    }

    private static boolean checkMethodAnnotations(Method method, String annotationName) {
        List<Annotation> annotations = Arrays.asList(method.getDeclaredAnnotations());

        List<String> annotationNames = new ArrayList<>();

        annotations.forEach(a -> annotationNames.add(a.annotationType().getSimpleName().toUpperCase()));
//        Subsidiary logging
//        annotationNames.forEach(a->System.out.println("For method: " + method.getName() + ", " +
//                "find annotation with name: " + a));
        return annotationNames.contains(annotationName.toUpperCase());
    }
}