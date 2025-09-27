package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class DeterministicSelect {
    public static int select(int[] array, int k){
        if (array == null || k <= 0 || k > array.length){
            throw new IllegalArgumentException("Erroneous selection");
        }
        return select_helper(array, 0, array.length-1, k-1);
    }

    public static int select_helper(int[] array, int low, int high, int k){
        if (high - low + 1 <= 10){
            insertionSort(array, low, high);
            return array[k];
        }

        int pivot = mOfm(array, low, high);
        int pivot_idx = partition(array, low, high, pivot);

        if (k==pivot_idx){
            return array[k];
        }
        else if (k<pivot_idx){
            return select_helper(array, low, pivot_idx-1, k);
        }
        else{
            return select_helper(array, pivot_idx+1, high, k);
        }
    }

    private static int mOfm(int[] array, int low, int high){
        int n = high - low + 1;
        int num_groups = (n+4)/5;
        int[] m = new int[num_groups];

        for(int i=0; i<num_groups; i++){
            int g_low = low+i*5;
            int g_high = Math.min(g_low+4,high);
            insertionSort(array, g_low, g_high);
            m[i] = array[(g_low+g_high)/2];
        }

        return select_helper(m, 0, m.length-1, (m.length-1)/2);
    }

    private static void insertionSort(int[] array, int low, int high){
        for (int i=low+1; i<=high; i++){
            int key = array[i];
            int j = i-1;
            while (j >= low && array[j] > key){
                array[j+1] = array[j];
                j-=1;
            }
            array[j+1] = key;
        }
    }

    private static int partition(int[] array, int low, int high, int pivot) {
        int pivotIndex = -1;

        for (int i = low; i <= high; i++) {
            if (array[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }

        swap(array, pivotIndex, high);

        int i = low;
        int j = high - 1;

        while (true) {

            while (i <= high && array[i] < pivot) {
                i++;
            }
            while (j >= low && array[j] >= pivot) {
                j--;
            }

            if (i < j) {
                swap(array, i, j);
            }
            else {
                break;
            }
        }

        swap(array, i, high);
        return i;
    }

    protected static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        System.out.print("Enter k-th lowest element of array that you wanna know: ");
        int k = sc.nextInt();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }

        int res = select(array, k);
        System.out.println(Arrays.toString(array));
        System.out.println("The " + k + "-th lowest element is: " + res);

        sc.close();
    }
}
