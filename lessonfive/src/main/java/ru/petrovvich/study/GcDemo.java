package ru.petrovvich.study;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
-Xms200m
-Xmx200m
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xloggc:./lessonfive/gc_logs/log.log
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./lessonfive/heap_dumps/
-agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
-XX:+UseConcMarkSweepGC
*/

/*
-XX:+UseG1GC
-XX:+UseSerialGC
-XX:+UseParallelGC
-XX:+UseConcMarkSweepGC
-XX:+UnlockExperimentalVMOptions -XX:+UseZGC
 */

/*
1)
    default, time: 80 sec
2)
    -XX:MaxGCPauseMillis=100000, time: 69 sec
3)
    -XX:MaxGCPauseMillis=10, time: 82 sec
4)
disable concurrent remembered set:
    -XX:-G1UseAdaptiveConcRefinement
    -XX:G1ConcRefinementGreenZone=2G
    -XX:G1ConcRefinementThreads=0
                                    time: 56 sec
5)
-Xms2048m
-Xmx2048m
    time: 92 sec
6)
-Xms256m
-Xmx256m
    time: 58 sec
*/

public class GcDemo {

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        int size = 5 * 1000 * 1000;
        //int loopCounter = 100;
        int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.petrovvich.study:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();

        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        AtomicLong totalDuration = new AtomicLong(0L);
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                System.out.println("---------------- GC called: " + notification.getSequenceNumber() + " time ----------------");
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();

                    long duration = info.getGcInfo().getDuration();

                    totalDuration.addAndGet(duration);

                    long averageDuration = getAverageDuration(gcbean.getCollectionCount(), totalDuration);



                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    System.out.println("Average duration is: " + averageDuration);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static long getAverageDuration(long count, AtomicLong duration) {
        return duration.intValue()/count;
    }

}
