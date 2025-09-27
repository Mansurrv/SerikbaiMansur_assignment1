package org.example.Metric;

import java.util.Random;

class SorterADS {
    private static void insertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

    public static void mergeSort(int[] array, int[] buffer, int low, int high) {
        if (high - low + 1 <= 10) {
            insertionSort(array, low, high);
            return;
        }
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        mergeSort(array, buffer, low, mid);
        mergeSort(array, buffer, mid + 1, high);
        merge(array, buffer, low, mid, high);
    }

    private static void merge(int[] array, int[] buffer, int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            buffer[i] = array[i];
        }
        int i = low;
        int j = mid + 1;
        int k = low;
        while (i <= mid && j <= high) {
            if (buffer[i] <= buffer[j]) {
                array[k] = buffer[i];
                i++;
            } else {
                array[k] = buffer[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            array[k] = buffer[i];
            i++;
            k++;
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        Random rd = new Random();
        int rd_idx = rd.nextInt(high - low + 1) + low;
        swap(arr, low, rd_idx);
        int pivot = arr[low];
        int left = low + 1;
        int right = high;

        while (true) {
            while (left <= right && arr[left] <= pivot) {
                left++;
            }
            while (right >= left && arr[right] >= pivot) {
                right--;
            }
            if (right < left) {
                break;
            } else {
                swap(arr, left, right);
            }
        }
        swap(arr, low, right);
        return right;
    }

    protected static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}