package org.example.Metric;

import java.io.FileWriter;
import java.io.IOException;

public class CsvLogger {
    private final String fileName;

    public CsvLogger(String fileName) {
        this.fileName = fileName;

        // при каждом запуске создаём новый файл и пишем заголовок
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("Algorithm,Size,Comparisons,Swaps,MaxDepth,TimeMs\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String algorithm, int size, Metrics metrics, long timeMs) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(algorithm + "," +
                    size + "," +
                    metrics.getComparisons() + "," +
                    metrics.getSwaps() + "," +
                    metrics.getMaxDepth() + "," +
                    timeMs + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}