package org.example.MetricsOfAlgorithms;

import java.util.*;

public class CliRunner {
    public static void main(String[] args) {
        Map<String, String> params = parseArgs(args);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String algo = params.getOrDefault("algo", input);
        String outFile = params.getOrDefault("out", "resultsCli.csv");

        CsvLogger logger = new CsvLogger(outFile);
        Random rand = new Random();

        if (params.containsKey("range")) {
            String[] parts = params.get("range").split(":");
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            int step = Integer.parseInt(parts[2]);

            for (int n = start; n <= end; n += step) {
                System.out.println("▶ Range test: n = " + n);
                runAllAlgorithms(n, logger, rand);
            }
        } else if (algo.equalsIgnoreCase("all")) {
            int n = scanner.nextInt();
            runAllAlgorithms(n, logger, rand);
        } else {
            int n = scanner.nextInt();
            runOneAlgorithm(algo, n, logger, rand);
        }

        System.out.println("Все результаты сохранены в " + outFile + " ✅");
    }

    private static void runOneAlgorithm(String algo, int n, CsvLogger logger, Random rand) {
        int[] baseArray = new int[n];
        for (int i = 0; i < n; i++) baseArray[i] = rand.nextInt(10000);

        switch (algo.toLowerCase()) {
            case "mergesort" -> {
                int[] arr = baseArray.clone();
                int[] buffer = new int[n];
                Metrics m = new Metrics();
                long start = System.nanoTime();
                MetricMergeSort.mergeSort(arr, buffer, 0, n - 1, m);
                long end = System.nanoTime();
                logger.log("MergeSort", n, m, (end - start) / 1_000_000);
                System.out.println("MergeSort done");
            }
            case "quicksort" -> {
                int[] arr = baseArray.clone();
                Metrics m = new Metrics();
                long start = System.nanoTime();
                MetricQuickSort.quickSort(arr, 0, n - 1, m);
                long end = System.nanoTime();
                logger.log("QuickSort", n, m, (end - start) / 1_000_000);
                System.out.println("QuickSort done");
            }
            case "select" -> {
                int[] arr = baseArray.clone();
                int k = n / 2;
                Metrics m = new Metrics();
                long start = System.nanoTime();
                int kth = MetricDeterministicSelect.deterministicSelect(arr, 0, n - 1, k, m);
                long end = System.nanoTime();
                logger.log("DeterministicSelect", n, m, (end - start) / 1_000_000);
                System.out.println("Deterministic Select: " + kth);
            }
            case "closest" -> {
                MetricClosestPairOfPoints.Point[] points = new MetricClosestPairOfPoints.Point[n];
                for (int i = 0; i < n; i++) points[i] = new MetricClosestPairOfPoints.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
                Metrics m = new Metrics();
                long start = System.nanoTime();
                double minDist = (n > 1) ? MetricClosestPairOfPoints.closestPair(points, m) : 0.0;
                long end = System.nanoTime();
                logger.log("ClosestPair", n, m, (end - start) / 1_000_000);
                System.out.println("Closest Pair: " + minDist);
            }
            default -> System.out.println("Unknown algo: " + algo);
        }
    }

    private static void runAllAlgorithms(int n, CsvLogger logger, Random rand) {
        int[] baseArray = new int[n];
        for (int i = 0; i < n; i++) baseArray[i] = rand.nextInt(10000);

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
            System.out.println("Deterministic Select: " + k + "-й элемент = " + kth);
        }

        MetricClosestPairOfPoints.Point[] points = new MetricClosestPairOfPoints.Point[n];
        for (int i = 0; i < n; i++) points[i] = new MetricClosestPairOfPoints.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        Metrics m4 = new Metrics();
        long start4 = System.nanoTime();
        double minDist = (n > 1) ? MetricClosestPairOfPoints.closestPair(points, m4) : 0.0;
        long end4 = System.nanoTime();
        logger.log("ClosestPair", n, m4, (end4 - start4) / 1_000_000);

        System.out.println("All algorithms completed for n = " + n);
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--") && arg.contains("=")) {
                String[] parts = arg.substring(2).split("=", 2);
                params.put(parts[0], parts[1]);
            }
        }
        return params;
    }
}
