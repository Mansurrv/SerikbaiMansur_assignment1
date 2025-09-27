package org.example.Metric;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MetricLogger {
    private final String fileName;
    private PrintWriter writer;

    public MetricLogger(String fileName, String header) {
        this.fileName = fileName;
        try {
            this.writer = new PrintWriter(new FileWriter(fileName, false));
            writer.println(header);
            writer.flush();
        } catch (IOException e){
            System.out.println("Error opening file " + fileName);
        }
    }

    public void log(String data) {
        if (writer != null) {
            writer.println(data);
            writer.flush();
        }
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
