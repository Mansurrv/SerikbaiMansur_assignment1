package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuickSortTest {
    private void testSorting(int[] input, int[] expected, String message) {
        QuickSort.quickSort(input, 0, input.length - 1);
        assertArrayEquals(expected, input, message);
    }



    private void runLargeRandomTest(int N) {
        Random random = new Random();
        int[] input = new int[N];

        for (int i = 0; i < N; i++) {
            input[i] = random.nextInt();
        }

        int[] expected = input.clone();
        Arrays.sort(expected);

        testSorting(input, expected, "Sorting Random Largest Test (N=" + N + ") error.");
    }



    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        testSorting(input, expected, "empty array.");
    }
    @Test
    void testSingleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        testSorting(input, expected, "single element array.");
    }
    @Test
    void testRandomSmallArray() {
        int[] input = {8, 1, 5, 9, 3, 6, 2, 7, 4, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        testSorting(input, expected, "random small array.");
    }
    @Test
    void testArrayWithDuplicatesAndNegatives() {
        int[] input = {4, -2, 4, 0, -1, 3, 5, -2, 3, 5};
        int[] expected = {-2, -2, -1, 0, 3, 3, 4, 4, 5, 5};
        testSorting(input, expected, "array with duplicates and negatives.");
    }
    @Test
    void testAlreadySortedArrayAdversarial() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        testSorting(input, expected, "already sorted array adversarial.");
    }
    @Test
    void testReverseSortedArrayAdversarial() {
        int[] input = {12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        testSorting(input, expected, "reverse sorted array adversarial.");
    }
    @Test
    void testStackDepthOnLargeAdversarialArray() {
        int N = 100_000;
        int[] input = new int[N];
        for (int i = 0; i < N; i++) {
            input[i] = i;
        }
        int[] expected = input.clone();
        testSorting(input, expected, "stack depth on large adversarial.");
    }



    @Test
    void testLargeRandomArrayCorrectnessN_500() {
        runLargeRandomTest(500);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_1000() {
        runLargeRandomTest(1000);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_1500() {
        runLargeRandomTest(1500);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_2000() {
        runLargeRandomTest(2000);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_2500() {
        runLargeRandomTest(2500);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_3000() {
        runLargeRandomTest(3000);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_3500() {
        runLargeRandomTest(3500);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_4000() {
        runLargeRandomTest(4000);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_4500() {
        runLargeRandomTest(4500);
    }
    @Test
    void testLargeRandomArrayCorrectnessN_5000() {
        runLargeRandomTest(5000);
    }
}