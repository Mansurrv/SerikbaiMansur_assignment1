package org.example;

import java.util.Scanner;

public class MergeSort{

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] array = new int[n];
        int[] buffer = new int[n];

        for(int i=0; i<n; i++){
            array[i] = sc.nextInt();
        }

        mergeSort(array, buffer, 0, array.length-1);

        for(int j=0; j<n; j++){
            System.out.println(array[j] + " ");
        }

        sc.close();
    }

    private static void mergeSort(int[] array, int[] buffer, int low, int high){

        if (high - low + 1 <= 10){
            insertionSort(array, low, high);
            return;
        }

        if (low >= high){
            return;
        }


        int mid = low + (high - low) / 2;

        mergeSort(array, buffer, low, mid);
        mergeSort(array, buffer, mid+1, high);
        print(array, buffer, low, mid, high);

    }

    private static void print(int[] array, int[] buffer, int low, int mid, int high){

        for (int i=low; i<=high; i++){
            buffer[i] = array[i];
        }

        int i=low;
        int j=mid+1;
        int k=low;

        while(i <= mid && j <= high){
            if (buffer[i] <= buffer[j]){
                array[k] = buffer[i];
                i++;
            }
            else{
                array[k] = buffer[j];
                j++;
            }
            k++;
        }

        while (i <= mid){
            array[k] = buffer[i];
            i++;
            k++;
        }

        while (j <= high){
            array[k] = buffer[j];
            j++;
            k++;
        }
    }

    private static void insertionSort(int[] array, int low, int high){
        for (int i=low+1; i<=high; i++){
            int key = array[i];
            int j=i-1;

            while (j>=low && array[j]>key){
                array[j+1] = array[j];
                j = j - 1;
            }

            array[j+1] = key;
        }
    }
}