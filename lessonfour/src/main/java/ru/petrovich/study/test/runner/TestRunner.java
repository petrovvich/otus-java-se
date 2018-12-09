package ru.petrovich.study.test.runner;

import ru.petrovich.study.test.interfaces.After;
import ru.petrovich.study.test.interfaces.Before;
import ru.petrovich.study.test.interfaces.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    /**
     * Another package-name test runner. Search classes in package and delegate their to base runner.
     *
     * @param packageName with test class, ex. <b>java.lang<b/>
     */
    public static void initByPackageName(String packageName) {
        List<Class> classes = Arrays.asList(getClasses(packageName));

        classes.forEach(c -> {
            initByFullPackageName(c.getName());
        });
    }

    /**
     * Base test runner. Search annotated methods and run each of their.
     *
     * @param packageName full class name ex. <b>java.lang.String<b/>
     */
    public static void initByFullPackageName(String packageName) {
        System.out.println("\r\n" + "Input parameter is: " + packageName);

        Class<?> clazz = null;
        try {
            clazz = Class.forName(packageName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (clazz != null && (clazz.isInterface() || clazz.isAnnotation())) {
            return;
        }

        Constructor<?> classConstructor = null;
        try {
            assert clazz != null;
            classConstructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Object o = getTestClassObject(classConstructor);

        assert o != null;
        List<Method> allMethodList = Arrays.asList(o.getClass().getDeclaredMethods());

        if (allMethodList.isEmpty()) {
            return;
        }

        if (findByAnnotation(allMethodList, Test.class).size() <= 0) {
            return;
        }

        List<Method> test = findByAnnotation(allMethodList, Test.class);

        for (Method m : test) {
            o = getTestClassObject(classConstructor);
            runTestMethods(o, findByAnnotation(allMethodList, Before.class), Before.class);
            runSingleMethod(o, m);
            runTestMethods(o, findByAnnotation(allMethodList, After.class), After.class);
        }
    }

    private static Object getTestClassObject(Constructor<?> classConstructor) {
        Object o = null;
        try {
            assert classConstructor != null;
            o = classConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Running methods of testing class.
     *
     * @param o              object to invoke annotated methods
     * @param methodList     list of annotated methods
     * @param annotationName current annotation to run
     */
    private static void runTestMethods(Object o, List<Method> methodList, Class annotationName) {
        for (Method m : methodList) {
            if (checkMethodAnnotations(m, annotationName)) {
                try {
                    m.invoke(o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Running single method of testing class.
     *
     * @param o      object to invoke annotated methods
     * @param method method to run
     */
    private static void runSingleMethod(Object o, Method method) {
        try {
            method.invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find methods annotated by annotation
     *
     * @param methodList     list methods to find by
     * @param annotationName annotation to find
     * @return list of methods annotated by this annotation
     */
    private static List<Method> findByAnnotation(List<Method> methodList, Class annotationName) {
        return methodList.stream().filter(m -> checkMethodAnnotations(m, annotationName)).collect(Collectors.toList());

    }

    /**
     * Checker of existing test annotation in method
     *
     * @param method          method to check on annotation exists
     * @param annotationClass annotation to find in this method
     * @return method annotated by this anotation or not
     */
    private static boolean checkMethodAnnotations(Method method, Class annotationClass) {
        return method.getAnnotation(annotationClass) != null;
    }

    /**
     * Finder of classes in directory. This need to find test runner
     *
     * @param packageName package to find test class
     * @return array of classes in package
     */
    private static Class[] getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (Object directory : dirs) {
            try {
                classes.addAll(findClasses((File) directory, packageName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (Class[]) classes.toArray(new Class[0]);
    }

    /**
     * Finder class files in file system in subdirectories.
     *
     * @param directory   to find classes
     * @param packageName subdirectory
     * @return list of files
     */
    private static List findClasses(File directory, String packageName) {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}