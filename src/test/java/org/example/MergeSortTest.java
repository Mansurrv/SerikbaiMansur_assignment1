package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

    private void testSorting(int[] input, int[] expected) {
        int[] buffer = new int[input.length];

        MergeSort.mergeSort(input, buffer, 0, input.length - 1);

        assertArrayEquals(expected, input, "Массив не отсортирован правильно.");
    }

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        testSorting(input, expected);
    }

    @Test
    void testSingleElementArray() {
        int[] input = {5};
        int[] expected = {5};
        testSorting(input, expected);
    }

    @Test
    void testAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        testSorting(input, expected);
    }

    @Test
    void testReverseSortedArray() {
        int[] input = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        testSorting(input, expected);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] input = {4, 2, 4, 1, 3, 1, 5, 2, 3, 5};
        int[] expected = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
        testSorting(input, expected);
    }

    @Test
    void testArrayWithNegativeNumbers() {
        int[] input = {-5, 0, -10, 3, 2};
        int[] expected = {-10, -5, 0, 2, 3};
        testSorting(input, expected);
    }

    @Test
    void testArraySmallerThanCutoff() {
        int[] input = {8, 1, 5, 9, 3, 6};
        int[] expected = {1, 3, 5, 6, 8, 9};
        testSorting(input, expected);
    }

    @Test
    void testArrayLargerThanCutoff() {
        int[] input = {100, 5, 99, 10, 4, 88, 1, 20, 3, 77, 2, 30, 0};
        int[] expected = {0, 1, 2, 3, 4, 5, 10, 20, 30, 77, 88, 99, 100};
        testSorting(input, expected);
    }

    @Test
    void testArrayWithMaxValues() {
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;

        int[] input = {max, 5, min, 0, max, -10};
        int[] expected = {min, -10, 0, 5, max, max};

        testSorting(input, expected);
    }

    @Test
    void testLargeRandomArrayCorrectness() {

        int N = 10000;
        int[] input = new int[N];
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < N; i++) {
            input[i] = random.nextInt();
        }

        int[] expected = input.clone();
        java.util.Arrays.sort(expected);

        testSorting(input, expected);
    }
}