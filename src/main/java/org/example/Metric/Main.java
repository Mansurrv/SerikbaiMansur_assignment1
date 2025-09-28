package org.example.Metric;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        CsvLogger logger = new CsvLogger("results.csv");
        Random rand = new Random();

        for (int n = 0; n <= 50000; n += 5000) {
            System.out.println("Test input size: " + n);

            int[] baseArray = new int[n];
            for (int i = 0; i < n; i++) {
                baseArray[i] = rand.nextInt(10000);
            }

            int[] arr1 = baseArray.clone();
            int[] buffer = new int[n];
            Metrics m1 = new Metrics();
            long start1 = System.nanoTime();
            MetricMergeSort.mergeSort(arr1, buffer, 0, n - 1, m1);
            long end1 = System.nanoTime();
            logger.log("MergeSort", n, m1, (end1 - start1) / 1_000_000);

            int[] arr2 = baseArray.clone();
            Metrics m2 = new Metrics();
            long start2 = System.nanoTime();
            MetricQuickSort.quickSort(arr2, 0, n - 1, m2);
            long end2 = System.nanoTime();
            logger.log("QuickSort", n, m2, (end2 - start2) / 1_000_000);

            if (n > 0) {
                int[] arr3 = baseArray.clone();
                int k = n / 2;
                Metrics m3 = new Metrics();
                long start3 = System.nanoTime();
                int kth = MetricDeterministicSelect.deterministicSelect(arr3, 0, n - 1, k, m3);
                long end3 = System.nanoTime();
                logger.log("DeterministicSelect", n, m3, (end3 - start3) / 1_000_000);
                System.out.println("Deterministic Select: " + k + "-th element = " + kth);
            }

            MetricClosestPairOfPoints.Point[] points = new MetricClosestPairOfPoints.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new MetricClosestPairOfPoints.Point(
                        rand.nextDouble() * 1000,
                        rand.nextDouble() * 1000
                );
            }
            Metrics m4 = new Metrics();
            long start4 = System.nanoTime();
            double minDist = (n > 1)
                    ? MetricClosestPairOfPoints.closestPair(points, m4)
                    : 0.0;
            long end4 = System.nanoTime();
            logger.log("ClosestPair", n, m4, (end4 - start4) / 1_000_000);
            System.out.println("Closest Pair: minimum distance = " + minDist);
        }

        System.out.println("All results saved in results.csv");
    }
}