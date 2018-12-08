package ru.petrovich.study.test.initializer;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class TestInitializer {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        initByFullPackageName("ru.petrovich.study.TestingClass");
        initByPackageName("ru.petrovich.study");
    }

    /**
     * Another package-name test runner. Search classes in package and delegate their to base runner.
     *
     * @param packageName with test class, ex. <b>java.lang<b/>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void initByPackageName(String packageName) throws IOException, ClassNotFoundException {
        List<Class> classes = Arrays.asList(getClasses(packageName));

        classes.forEach(c -> {
            try {
                initByFullPackageName(c.getName());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Base test runner. Search annotated methods and run each of their.
     *
     * @param className full class name ex. <b>java.lang.String<b/>
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void initByFullPackageName(String className) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        System.out.println("\r\n" + "Input parameter is: " + className);
        Class<?> clazz = Class.forName(className);

        if (clazz.isInterface() || clazz.isAnnotation()) {
            return;
        }

        Constructor<?> classConstructor = clazz.getConstructor();

        Object o = classConstructor.newInstance();

        List<Method> methodList = Arrays.asList(o.getClass().getDeclaredMethods());

        runTestMethods(o, methodList, "Before");

        runTestMethods(o, methodList, "Test");

        runTestMethods(o, methodList, "After");
    }

    /**
     * Running methods of testing class.
     *
     * @param o              object to invoke annotated methods
     * @param methodList     list of annotated methods
     * @param annotationName current annotation to run
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void runTestMethods(Object o, List<Method> methodList, String annotationName) throws InvocationTargetException, IllegalAccessException {
        for (Method m : methodList) {
            if (checkMethodAnnotations(m, annotationName)) {
                String result = (String) m.invoke(o);
                if (result != null)
                    System.out.println(result);
            }
        }
    }

    /**
     * Checker of existing test annotation in method
     *
     * @param method         method to check on annotation exists
     * @param annotationName annotation to check
     * @return method annotated by this anotation or not
     */
    private static boolean checkMethodAnnotations(Method method, String annotationName) {
        List<Annotation> annotations = Arrays.asList(method.getDeclaredAnnotations());

        List<String> annotationNames = new ArrayList<>();

        annotations.forEach(a -> annotationNames.add(a.annotationType().getSimpleName().toUpperCase()));
        return annotationNames.contains(annotationName.toUpperCase());
    }

    /**
     * Finder of classes in directory. This need to find test initializer
     *
     * @param packageName package to find test class
     * @return array of classes in package
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (Object directory : dirs) {
            classes.addAll(findClasses((File) directory, packageName));
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }

    /**
     * Finder class files in file system in subdirectories.
     *
     * @param directory   to find classes
     * @param packageName subdirectory
     * @return list of files
     * @throws ClassNotFoundException
     */
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
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
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}