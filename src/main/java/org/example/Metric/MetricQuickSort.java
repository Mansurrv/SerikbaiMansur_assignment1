package org.example.Metric;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MetricQuickSort {
    private static final Random rand = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) array[i] = sc.nextInt();

        Metrics metrics = new Metrics();
        CsvLogger logger = new CsvLogger("results.csv");

        long start = System.nanoTime();
        quickSort(array, 0, n-1, metrics);
        long end = System.nanoTime();

        long timeMs = (end - start) / 1_000_000;

        logger.log("QuickSort", n, metrics, timeMs);

        System.out.println(Arrays.toString(array));
        System.out.println("Comparisons: " + metrics.getComparisons());
        System.out.println("Swaps: " + metrics.getSwaps());
        System.out.println("Max recursion depth: " + metrics.getMaxDepth());
    }

    protected static void quickSort(int[] arr, int low, int high, Metrics metrics) {
        metrics.enterRecursion();

        while (low < high) {
            int pivotIndex = partition(arr, low, high, metrics);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1, metrics);
                low = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, high, metrics);
                high = pivotIndex - 1;
            }
        }

        metrics.exitRecursion();
    }

    private static int partition(int[] arr, int low, int high, Metrics metrics) {
        int pivotIndex = low + rand.nextInt(high - low + 1);
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, high, metrics);

        int i = low;
        for (int j = low; j < high; j++) {
            metrics.incComparisons();
            if (arr[j] < pivot) {
                swap(arr, i, j, metrics);
                i++;
            }
        }
        swap(arr, i, high, metrics);
        return i;
    }

    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        metrics.incSwaps();
    }
}