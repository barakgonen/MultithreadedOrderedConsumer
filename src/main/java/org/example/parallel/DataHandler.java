package org.example.parallel;

import org.example.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TransferQueue;

public class DataHandler<T, F extends Future<?>> implements Runnable {

    private TransferQueue<T> incomingData;
    private TransferQueue<F> dataToProcess;
    private ExecutorService executorService;

    public DataHandler(TransferQueue<T> incomingData, TransferQueue<F> dataToProcess, ExecutorService executorService) {
        this.incomingData = incomingData;
        this.dataToProcess = dataToProcess;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        boolean shouldContinue = true;
        while (shouldContinue) {
            T data = incomingData.poll();
            if (data != null) {
                try {
                    dataToProcess.put((F) executorService.submit(new DataTranslator(data)));
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
