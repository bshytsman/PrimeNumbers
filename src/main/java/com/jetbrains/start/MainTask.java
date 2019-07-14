package com.jetbrains.start;

import com.jetbrains.tasks.PrimeNumbersTask;

import static com.jetbrains.utils.MyLogger.trace;

public class MainTask {

    public static void main(String[] args) {
        trace("Prime Numbers.");
        trace();

        final PrimeNumbersTask task = new PrimeNumbersTask();
        task.doStudy();
    }
}
