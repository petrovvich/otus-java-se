package ru.petrovich.study;

import ru.petrovich.study.test.runner.TestRunner;

public class Main {

    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();

        testRunner.initByPackageName("ru.petrovich.study");

        testRunner.initByFullPackageName("ru.petrovich.study.TestingClass");
    }
}
