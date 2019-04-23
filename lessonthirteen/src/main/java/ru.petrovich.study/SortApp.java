package ru.petrovich.study;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortApp {

    private static Logger log = LoggerFactory.getLogger(SortApp.class);

    public static void main(String[] args) throws Exception {

        SortingService sortingService = new SortingService(1000000, 4, 20);
        log.info("Start sorting array from main with params size: {}, number of threads: {}, number of repeating: {}", 1000000, 4, 20);
        sortingService.sort();
        log.info("Finish sorting array from main");
    }
}