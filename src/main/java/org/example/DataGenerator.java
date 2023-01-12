package org.example;

import java.util.UUID;
import java.util.concurrent.TransferQueue;

public class DataGenerator<T> implements Runnable {
    private TransferQueue<T> incomingData;
    private boolean shouldStopRunning;
    private long numberOfMessages;

    public DataGenerator(TransferQueue<T> incomingData, boolean shouldStopRunning, long numberOfMessages) {
        this.incomingData = incomingData;
        this.shouldStopRunning = shouldStopRunning;
        this.numberOfMessages = numberOfMessages;

    }

    @Override
    public void run() {
        long counter = 1;
        while (counter < this.numberOfMessages) {
            Data data = new Data(counter, UUID.randomUUID().toString(), getStringInLength(counter));
            try {
                incomingData.put((T) data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        Data data = new Data(0L, UUID.randomUUID().toString(), getStringInLength(counter));
        try {
            incomingData.put((T) data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getStringInLength(long length) {
        return "";
    }
}
