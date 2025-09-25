package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class QuickSort {

    public static void quickSort(int[] arr, int low, int high){

        while (low < high){
            int pivot = partition(arr, low, high);

            if (pivot - low < high - pivot){
                quickSort(arr, low, pivot - 1);
                low = pivot + 1;
            }
            else{
                quickSort(arr, pivot + 1, high);
                high = pivot - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high){

        Random rd = new Random();
        int rd_idx = rd.nextInt(high - low + 1) + low;
        int tem_p = arr[low];
        arr[low] = arr[rd_idx];
        arr[rd_idx] = tem_p;

        int pivot = arr[low];
        int left = low + 1;
        int right = high;

        while (true){
            while (left<=right && arr[left]<=pivot){
                left++;
            }
            while (right>=left && arr[right]>=pivot){
                right--;
            }
            if (right<left){
                break;
            }
            else{
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
        int temp = arr[low];
        arr[low] = arr[right];
        arr[right] = temp;
        return right;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n];
        for (int i=0; i<n; i++){
            array[i] = sc.nextInt();
        }
        quickSort(array,  0, array.length - 1);
        System.out.println(Arrays.toString(array));
        sc.close();
    }
}