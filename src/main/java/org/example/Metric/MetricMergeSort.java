package org.example.Metric;
import java.util.Arrays;
import java.util.Scanner;

public class MetricMergeSort{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n];
        int[] buffer = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }

        Metrics metrics = new Metrics();
        CsvLogger logger = new CsvLogger("results.csv");

        long start = System.nanoTime();
        mergeSort(array, buffer, 0, n-1, metrics);
        long end = System.nanoTime();

        long timeMs = (end - start) / 1_000_000;

        logger.log("MergeSort", n, metrics, timeMs);

        System.out.println(Arrays.toString(array));
        System.out.println("Comparisons: " + metrics.getComparisons());
        System.out.println("Swaps: " + metrics.getSwaps());
        System.out.println("Max recursion depth: " + metrics.getMaxDepth());
    }


    protected static void mergeSort(int[] array, int[] buffer, int low, int high, Metrics metrics){
        metrics.enterRecursion();

        if (high - low + 1 <= 10){
            insertionSort(array, low, high, metrics);
            metrics.exitRecursion();
            return;
        }

        if (low >= high){
            metrics.exitRecursion();
            return;
        }

        int mid = low + (high - low) / 2;

        mergeSort(array, buffer, low, mid, metrics);
        mergeSort(array, buffer, mid+1, high, metrics);
        merge(array, buffer, low, mid, high, metrics);

        metrics.exitRecursion();
    }


    private static void merge(int[] array, int[] buffer, int low, int mid, int high, Metrics metrics){
        for (int i=low; i<=high; i++){
            buffer[i] = array[i];
        }

        int i=low, j=mid+1, k=low;

        while(i <= mid && j <= high){
            metrics.incComparisons();
            if (buffer[i] <= buffer[j]){
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        while (i <= mid){
            array[k++] = buffer[i++];
        }
        while (j <= high){
            array[k++] = buffer[j++];
        }
    }


    private static void insertionSort(int[] array, int low, int high, Metrics metrics){
        for (int i=low+1; i<=high; i++){
            int key = array[i];
            int j=i-1;

            while (j>=low){
                metrics.incComparisons();
                if (array[j]>key){
                    array[j+1] = array[j];
                    metrics.incSwaps();
                    j--;
                } else break;
            }

            array[j+1] = key;
        }
    }

}