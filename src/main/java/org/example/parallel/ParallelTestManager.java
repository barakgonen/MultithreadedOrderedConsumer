package org.example.parallel;

import org.example.Data;
import org.example.DataGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class ParallelTestManager {
    private int numberOfMessages;
    private int numberOfWorkerThreads;

    public ParallelTestManager(int numberOfMessages, int numberOfWorkerThreads) {
        this.numberOfMessages = numberOfMessages;
        this.numberOfWorkerThreads = numberOfWorkerThreads;
    }

    public long run() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorService handlerExecutorService = Executors.newCachedThreadPool();
        TransferQueue<Data> incomingData = new LinkedTransferQueue<>();
        TransferQueue<Data> dataToProcess = new LinkedTransferQueue<>();
        TransferQueue<Future<Data>> waitingData = new LinkedTransferQueue<>();
        DataGenerator<Data> dataDataGenerator = new DataGenerator<>(incomingData, false, numberOfMessages);
        DataConsumer<Data> consumer = new DataConsumer<>(incomingData, dataToProcess);
        DataHandler<Data, Future<Data>> dataDataHandler = new DataHandler(dataToProcess, waitingData, handlerExecutorService);

        DataPublisher<Future<Data>> dataPublisher = new DataPublisher<>(waitingData, numberOfMessages);

        executorService.submit(dataDataGenerator);
        executorService.submit(consumer);
        executorService.submit(dataDataHandler);
        Future<?> lastFuture = executorService.submit(dataPublisher);
        long startTimeMillis = System.currentTimeMillis();

        while (!lastFuture.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long endTimeMillis = System.currentTimeMillis();
        executorService.shutdown();
        handlerExecutorService.shutdown();

        return endTimeMillis - startTimeMillis;
    }
}
