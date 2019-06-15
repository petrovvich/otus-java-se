package ru.petrovich.study;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.stream.IntStream;

public class SortApp {

    private static Logger log = LoggerFactory.getLogger(SortApp.class);

    public static void main(String[] args) throws Exception {
        int[] arrayToSort = createArray(100_000);
        SortingService sortingService = new SortingService();
        log.info("Start sorting array from main with params size: {}, number of threads: {}, number of repeating: {}", 1000000, 4, 20);
        sortingService.sortInThreadsSync(arrayToSort, 4);
        log.info("Finish sorting array from main");
    }

    private static int[] createArray(int size) {
        Random rnd = new Random();
        IntStream is = rnd.ints(size);
        return is.toArray();
    }
}