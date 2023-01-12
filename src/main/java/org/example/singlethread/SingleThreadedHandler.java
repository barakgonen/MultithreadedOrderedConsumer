package org.example.singlethread;

import java.util.Random;
import java.util.concurrent.TransferQueue;

public class SingleThreadedHandler<T> implements Runnable {
    private TransferQueue<T> incomingData;
    private int numberOfMessagesToHandle;
    private boolean shouldStopRunning;

    public SingleThreadedHandler(TransferQueue<T> incomingData, int numberOfMessagesToHandle, boolean shouldStopRunning) {
        this.incomingData = incomingData;
        this.numberOfMessagesToHandle = numberOfMessagesToHandle;
        this.shouldStopRunning = shouldStopRunning;
    }

    @Override
    public void run() {
        int numberOfMesagesHandled = 0;
        while (numberOfMesagesHandled < numberOfMessagesToHandle -1) {
            T data = incomingData.poll();
            if (data != null) {
                try {
                    // translate data
                    T convertedData = runValidationsAndConversion(data);
                    String s = convertedData.toString();
                    numberOfMesagesHandled++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.shouldStopRunning = true;
    }

    public T runValidationsAndConversion(T data) throws Exception {
        Thread.sleep(getRandomNumberUsingInts());
        return data;
    }

    public int getRandomNumberUsingInts() {
        Random random = new Random();
        return random.ints(1, 15)
                .findFirst()
                .getAsInt();
    }
}
