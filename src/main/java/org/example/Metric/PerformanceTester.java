package org.example.Metric;
import org.example.Metric.Point;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PerformanceTester {

    private static final int R = 10;

    public static double measureTimeMs(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

    public static void main(String[] args) {
        String header = "N,QuickSort_ms,MergeSort_ms,Select_ms,PairOfPoints_ms";
        MetricLogger logger = new MetricLogger("performance_results.csv", header);

        int[] dataSizes = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};

        System.out.println(" JVM (JIT compilation)...");
        runTests(logger, new int[]{1000}, 5, true);

        System.out.println("Start up (R=" + R + " )...");

        runTests(logger, dataSizes, R, false);

        logger.close();
        System.out.println("\nMetrics collection is complete. Results in performance_results.csv");
    }

    private static void runTests(MetricLogger logger, int[] dataSizes, int repetitions, boolean isWarmup) {

        for (int N : dataSizes) {
            if (!isWarmup) {
                System.out.print("  Test N = " + N + "...");
            }

            int[] randomArray = generateRandomArray(N);
            List<Point> randomPoints = generateRandomPoints(N);

            double avgQuickSortTime = 0;
            double avgMergeSortTime = 0;
            double avgSelectTime = 0;
            double avgPairOfPointsTime = 0;

            for (int r = 0; r < repetitions; r++) {

                int[] arrQS = randomArray.clone();
                avgQuickSortTime += measureTimeMs(() -> SorterADS.quickSort(arrQS, 0, N - 1));

                int[] arrMS = randomArray.clone();
                int[] buffer = new int[N];
                avgMergeSortTime += measureTimeMs(() -> SorterADS.mergeSort(arrMS, buffer, 0, N - 1));

                int[] arrDS = randomArray.clone();
                avgSelectTime += measureTimeMs(() -> DeterministicSelect.select(arrDS, N / 2));

                List<Point> pointsCopy = new ArrayList<>(randomPoints);
                avgPairOfPointsTime += measureTimeMs(() -> PairOfPoints.findClosestPair(pointsCopy));
            }

            avgQuickSortTime /= repetitions;
            avgMergeSortTime /= repetitions;
            avgSelectTime /= repetitions;
            avgPairOfPointsTime /= repetitions;

            if (!isWarmup) {
                String dataLine = String.format("%d,%.4f,%.4f,%.4f,%.4f",
                        N,
                        avgQuickSortTime,
                        avgMergeSortTime,
                        avgSelectTime,
                        avgPairOfPointsTime);
                logger.log(dataLine);
                System.out.println(" Готово.");
            }
        }
    }

    private static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000000);
        }
        return arr;
    }

    private static List<Point> generateRandomPoints(int n) {
        List<Point> points = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            points.add(new Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000));
        }
        return points;
    }
}