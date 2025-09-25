package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

    private void testSorting(int[] input, int[] expected){
        int[] buffer = new int[input.length];

        MergeSort.mergeSort(input, buffer, 0, input.length-1);
    }
}