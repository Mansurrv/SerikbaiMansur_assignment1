package org.example.MetricsOfAlgorithms;

import java.util.Scanner;

public class MetricDeterministicSelect {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) array[i] = sc.nextInt();

        Metrics metrics = new Metrics();
        CsvLogger logger = new CsvLogger("results.csv");

        long start = System.nanoTime();
        int result = deterministicSelect(array, 0, n-1, k, metrics);
        long end = System.nanoTime();

        long timeMs = (end - start) / 1_000_000;

        logger.log("DeterministicSelect", n, metrics, timeMs);

        System.out.println("k-th element = " + result);
        System.out.println("Comparisons: " + metrics.getComparisons());
        System.out.println("Swaps: " + metrics.getSwaps());
        System.out.println("Max recursion depth: " + metrics.getMaxDepth());
    }

    protected static int deterministicSelect(int[] arr, int left, int right, int k, Metrics metrics) {

        while (true) {
            if (left == right) return arr[left];

            int pivotIndex = medianOfMedians(arr, left, right, metrics);
            pivotIndex = partition(arr, left, right, pivotIndex, metrics);

            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex, Metrics metrics) {

        int pivotValue = arr[pivotIndex];

        swap(arr, pivotIndex, right, metrics);

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            metrics.incComparisons();
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i, metrics);
                storeIndex++;
            }
        }
        swap(arr, right, storeIndex, metrics);
        return storeIndex;
    }


    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics) {

        int n = right - left + 1;

        if (n < 5) {
            insertionSort(arr, left, right, metrics);
            return left + n/2;
        }

        int numMedians = 0;

        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i+4, right);
            insertionSort(arr, i, subRight, metrics);
            int medianIndex = i + (subRight-i)/2;
            swap(arr, left+numMedians, medianIndex, metrics);
            numMedians++;
        }
        return medianOfMedians(arr, left, left+numMedians-1, metrics);
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left+1; i <= right; i++) {
            int key = arr[i];
            int j = i-1;
            while (j >= left) {
                metrics.incComparisons();
                if (arr[j] > key) {
                    arr[j+1] = arr[j];
                    metrics.incSwaps();
                    j--;
                } else break;
            }
            arr[j+1] = key;
        }
    }

    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        metrics.incSwaps();
    }
}