package com.jetbrains.tasks;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jetbrains.utils.MyLogger.trace;

public class PrimeNumbersTask {

    private static final String FILE_OUTPUT_NAME = "output/prime-numbers.txt";
    private final List<Long> dividers = new ArrayList<>();
    private final OutputData outputData = new OutputData();
    private final long MAX_CHECK_VALUE = 10_000_000_000L;
//    private final long MAX_CHECK_VALUE = 10_000_000L;
    private final int MAX_DIVIDER = (int) Math.round(Math.sqrt(MAX_CHECK_VALUE)) + 1;

    public void doStudy() {
        createOutputFile();

        for (long i = 1; i <= MAX_CHECK_VALUE; i++) {
            final boolean simple = isPrime(i);
            if (simple) {
                outputData.appendData(i);
                if (outputData.needDisplayData()) {
                    displayData();
                }
            }
        }

        if (!outputData.dataShown) {
            displayData();
        }
    }

    private void displayData() {
        appendOutputFile();
        trace(formatLong(outputData.lastSimpleNumber) + " #" + formatLong(outputData.totalNumsCalculated));
        outputData.resetData();
    }

    private boolean isPrime(long number) {
        if (number == 1) {
            return true;
        }

        boolean prime = true;

        for (final long divider : dividers) {
            if (divider * divider > number) {
                break;
            }
            if (number % divider == 0) {
                prime = false;
                break;
            }
        }

        if (prime && (number <= MAX_DIVIDER)) {
            dividers.add(number);
        }

        return prime;
    }

    private void createOutputFile() {
        try {
            final File file = new File(FILE_OUTPUT_NAME);
            final FileOutputStream out = FileUtils.openOutputStream(file);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendOutputFile() {
        try {
            // Open given file in append mode.
            final BufferedWriter out = new BufferedWriter(
                    new FileWriter(FILE_OUTPUT_NAME, true));

            for (final long number: outputData.buffer) {
                out.write(formatLong(number));
                out.write('\n');
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatLong(final long number) {
        return String.format("%,d", number);
    }

    class OutputData {
        boolean dataShown;
        long lastSimpleNumber;
        long totalNumsCalculated;
        List<Long> buffer;
        LocalDateTime shownTime;

        OutputData() {
            totalNumsCalculated = 0;
            resetData();
        }

        void appendData(final long number) {
            dataShown = false;
            lastSimpleNumber = number;
            totalNumsCalculated++;
            buffer.add(number);
        }

        boolean needDisplayData() {
            if (dataShown) {
                return false;
            }

            final Duration duration = Duration.between(shownTime, LocalDateTime.now());
            return duration.toMillis() > 1000;
        }

        private void resetData() {
            dataShown = true;
            lastSimpleNumber = 0;
            buffer = new ArrayList<>();
            shownTime = LocalDateTime.now();
        }
    }
}
