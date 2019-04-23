package ru.petrovich.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SortingService {

    private static Logger log = LoggerFactory.getLogger(SortingService.class);
    private int size;
    private int numberOfThreads;
    private int numberOfRepeating;

    private static int[] result;
    private static long sortingTimeMs = 0;

    SortingService(int size, int numOfThreads, int numberOfRepeating) {
        this.size = size;
        this.numberOfThreads = numOfThreads;
        this.numberOfRepeating = numberOfRepeating;
    }

    void sort() throws InterruptedException {
        log.info("Start sorting {} ints", size);

        int[] data = createArray(size);

        for (int i = 1; i <= numberOfThreads; i++) {
            for (int k = 0; k < numberOfRepeating; k++) {
                Thread worker = new Thread(() -> {
                    try {
                        long t1 = System.currentTimeMillis();
                        result = sortInThreadsSync(data, numberOfThreads);
                        long t2 = System.currentTimeMillis();
                        sortingTimeMs += t2 - t1;
                    } catch (Exception e) {
                        log.error("Sorting error ", e);
                    }
                });
                worker.start();
                worker.join();
            }
            sortingTimeMs = sortingTimeMs / numberOfRepeating;
            log.trace("T[{}] - {} ms", numberOfThreads, sortingTimeMs);

            result = null;
            System.gc();
            Thread.sleep(10);
        }
        log.info("Finish sorting {} ints", size);
    }

    int[] sortInThreadsSync(int[] data, int numberOfThreads) throws Exception {

        int dataSize = data.length;

        if (numberOfThreads > dataSize / 2) {
            numberOfThreads = dataSize / 2;
        }
        int partSize = dataSize / numberOfThreads;
        int partStart = 0;

        Thread[] threads = new Thread[numberOfThreads];
        List<int[]> dataParts = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            int partEnd = ((i == numberOfThreads - 1) ? dataSize : (partStart + partSize));

            int[] part = Arrays.copyOfRange(data, partStart, partEnd);
            dataParts.add(part);

            threads[i] = new Thread(new SortingTask(part));
            threads[i].start();

            partStart += partSize;
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

        if (numberOfThreads == 1) {
            return dataParts.get(0);
        } else {
            int[] res = mergeSorted(dataParts.get(0), dataParts.get(1));
            for (int i = 2; i < numberOfThreads; i++) {
                res = mergeSorted(res, dataParts.get(i));
            }
            return res;
        }
    }

    int[] mergeSorted(int[] src1, int[] src2) {
        int[] out = new int[src1.length + src2.length];

        if (src1.length == 0) {
            return Arrays.copyOf(src2, src2.length);
        }
        if (src2.length == 0) {
            return Arrays.copyOf(src1, src1.length);
        }

        int i1 = 0;
        int i2 = 0;

        for (int j = 0; j < out.length; j++) {
            if (src1[i1] < src2[i2]) {
                out[j] = src1[i1];
                i1++;
                if (i1 == src1.length) {
                    System.arraycopy(src2, i2, out, j + 1, src2.length - i2);
                    break;
                }
            } else {
                out[j] = src2[i2];
                i2++;
                if (i2 == src2.length) {
                    System.arraycopy(src1, i1, out, j + 1, src1.length - i1);
                    break;
                }
            }
        }
        return out;
    }

    private static int[] createArray(int size) {
        Random rnd = new Random();
        IntStream is = rnd.ints(size);
        return is.toArray();
    }

    static class SortingTask implements Runnable {

        private int[] data;

        SortingTask(int[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            Arrays.sort(data, 0, data.length);
        }
    }
}
