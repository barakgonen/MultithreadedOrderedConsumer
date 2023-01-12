package org.example.parallel;

import org.example.Data;

import java.util.concurrent.TransferQueue;

public class DataConsumer<T> implements Runnable {
    private TransferQueue<T> incomingData;
    private TransferQueue<T> dataToProcess;

    public DataConsumer(TransferQueue<T> incomingData, TransferQueue<T> dataToProcess) {
        this.incomingData = incomingData;
        this.dataToProcess = dataToProcess;
    }

    @Override
    public void run() {
        boolean shouldContinue = true;
        while (shouldContinue) {
            T data = incomingData.poll();
            if (data != null) {
                try {
                    dataToProcess.put(data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (((Data)data).getId() == 0) {
                    shouldContinue = false;
                }
            }
        }
    }
}
