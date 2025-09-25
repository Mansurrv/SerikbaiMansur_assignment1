package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuickSortTest {

    private void testSorting(int[] input, int[] expected) {
        QuickSort.quickSort(input, 0, input.length - 1);

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
        int[] input = {42};
        int[] expected = {42};
        testSorting(input, expected);
    }

    @Test
    void testAlreadySortedArrayAdversarial() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        testSorting(input, expected);
    }

    @Test
    void testReverseSortedArrayAdversarial() {
        int[] input = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        testSorting(input, expected);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] input = {4, 2, 4, 1, 3, 1, 5, 2, 3, 5};
        int[] expected = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5};
        testSorting(input, expected);
    }        // Пустой массив

    @Test
    void testRandomLargeArrayCorrectness() {
        int N = 5000;
        int[] input = new int[N];
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            input[i] = random.nextInt(N * 2);
        }

        int[] expected = input.clone();
        Arrays.sort(expected);

        testSorting(input, expected);
    }

    @Test
    void testArrayWithNegativeAndZero() {
        int[] input = {-1, 5, -10, 0, 8, -5};
        int[] expected = {-10, -5, -1, 0, 5, 8};
        testSorting(input, expected);
    }
}