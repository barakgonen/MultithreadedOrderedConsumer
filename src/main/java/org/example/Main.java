package org.example;

import org.example.parallel.DataConsumer;
import org.example.parallel.DataHandler;
import org.example.parallel.DataPublisher;
import org.example.parallel.ParallelTestManager;
import org.example.singlethread.SingleThreadedTestManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test begins! we will try to send messages to a parallel consumer and to single threaded consumer and measure the time taken for each method");
//        ArrayList<Integer> numberOfMessages = new ArrayList<>(Arrays.asList(1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000));
        ArrayList<Integer> numberOfMessages = new ArrayList<>(Arrays.asList(10,
                                                                            100,
                                                                            1000,
                                                                            10000
//                                                                            100000
//                                                                            1000000
//                                                                            10000000
//                                                                            100000000
                ));
        TreeMap<Integer, Long> sizeToDurationParallel = new TreeMap<>();
        TreeMap<Integer, Long> sizeToDurationSingleThread = new TreeMap<>();

        numberOfMessages.forEach(integer -> {
            ParallelTestManager parallelTestManager = new ParallelTestManager(integer, 30);
            sizeToDurationParallel.put(integer, parallelTestManager.run());
        });

        System.out.println("==================================");
        numberOfMessages.forEach(integer -> {
            SingleThreadedTestManager singleThreadedTestManager = new SingleThreadedTestManager(integer);
            singleThreadedTestManager.run();
            System.out.println("ROUND: " + integer + ", has completed!");
            sizeToDurationSingleThread.put(integer, singleThreadedTestManager.run());
        });

        ResultsWriter parallelResultsWriter = new ResultsWriter("parallel", sizeToDurationParallel);
        parallelResultsWriter.write();
        ResultsWriter singleThreadedResultsWriter = new ResultsWriter("singleThreaded", sizeToDurationSingleThread);
        singleThreadedResultsWriter.write();

    }
}