package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
@State(Scope.Thread)

public class SelectVsSortBenchmark {

    @Param({"100", "1000", "10000"})
    private int size;

    @Param({"1", "size/2", "size"})
    private String kType;

    private int[] data;
    private int k;

    @Setup(Level.Iteration)
    public void setup() {
        Random random = new Random(42);
        data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }

        switch (kType) {
            case "1": k = 1; break;
            case "size/2": k = size / 2; break;
            case "size": k = size; break;
            default: k = 1;
        }
    }

    @Benchmark
    public int benchmarkSelect() {
        int[] copy = Arrays.copyOf(data, data.length);
        return DeterministicSelect.select(copy, k);
    }

    @Benchmark
    public int benchmarkMergeSort() {
        int[] copy = Arrays.copyOf(data, data.length);
        int[] buffer = new int[copy.length];
        MergeSort.mergeSort(copy, buffer, 0, copy.length - 1);
        return copy[k - 1];
    }

    @Benchmark
    public int benchmarkQuickSort() {
        int[] copy = Arrays.copyOf(data, data.length);
        QuickSort.quickSort(copy, 0, copy.length - 1);
        return copy[k - 1];
    }

    @Benchmark
    public int benchmarkArraysSort() {
        int[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return copy[k - 1];
    }
}
