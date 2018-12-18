package ru.petrovich.study;

import ru.petrovich.study.test.runner.TestRunner;

public class Main {

    public static void main(String[] args) {
        TestRunner.initByPackageName("ru.petrovich.study");

        TestRunner.initByFullPackageName("ru.petrovich.study.TestingClass");
    }
}
