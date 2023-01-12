package org.example.parallel;

import java.util.Random;
import java.util.concurrent.Callable;

public class DataTranslator<T> implements Callable {
    private T data;

    public DataTranslator(T data) {
        this.data = data;
    }

    @Override
    public Object call() throws Exception {
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
