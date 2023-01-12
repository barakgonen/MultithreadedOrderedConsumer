package org.example.singlethread;

import org.example.Data;
import org.example.DataGenerator;
import org.example.parallel.DataConsumer;
import org.example.parallel.DataHandler;
import org.example.parallel.DataPublisher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class SingleThreadedTestManager {
    private int numberOfMessages;

    public SingleThreadedTestManager(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public long run() {
        boolean shouldStopRunning = false;
        TransferQueue<Data> incomingData = new LinkedTransferQueue<>();
        DataGenerator<Data> dataDataGenerator = new DataGenerator<>(incomingData, shouldStopRunning, this.numberOfMessages);
        SingleThreadedHandler<Data> consumer = new SingleThreadedHandler<>(incomingData, this.numberOfMessages, shouldStopRunning);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(dataDataGenerator);
        Future<?> isFinished = executorService.submit(consumer);
        long startTimeMillis = System.currentTimeMillis();
        while (!isFinished.isDone()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        executorService.shutdownNow();
        return endTimeMillis - startTimeMillis;
    }
}
