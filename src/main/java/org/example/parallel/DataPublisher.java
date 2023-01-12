package org.example.parallel;

import org.example.Data;
import org.example.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TransferQueue;

public class DataPublisher<F extends Future<?>> implements Runnable {
    private TransferQueue<F> incomingData;
    private int numberOfMessagesForTest;

    public DataPublisher(TransferQueue<F> incomingData, int numberOfMessagesForTest) {
        this.incomingData = incomingData;
        this.numberOfMessagesForTest = numberOfMessagesForTest;
    }

    @Override
    public void run() {
        Long prevId = 0L;
        int counter = 0;
        while (counter < numberOfMessagesForTest) {
            F parsedData = incomingData.poll();
            if (parsedData != null) {
                try {
                    Object obj = parsedData.get();
                    // Casting
                    Data d = (Data)obj;
                    if (!Util.isMessageInOrder(prevId, d.getId())) {
                        System.out.println("ERRORR!!! not in order!!!!!");
                    }
                    counter++;
                    prevId = d.getId();
                    if (counter % 10000 == 0) {
                        System.out.println("parallel: handled: " + counter + ", messages so far");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
