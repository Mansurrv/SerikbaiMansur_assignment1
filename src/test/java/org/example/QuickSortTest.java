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

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        testSorting(input, expected, "Сортировка пустого массива.");
    }

    @Test
    void testSingleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        testSorting(input, expected, "Сортировка массива из одного элемента.");
    }

    @Test
    void testRandomSmallArray() {
        int[] input = {8, 1, 5, 9, 3, 6, 2, 7, 4, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        testSorting(input, expected, "Сортировка небольшого случайного массива.");
    }

    @Test
    void testArrayWithDuplicatesAndNegatives() {
        int[] input = {4, -2, 4, 0, -1, 3, 5, -2, 3, 5};
        int[] expected = {-2, -2, -1, 0, 3, 3, 4, 4, 5, 5};
        testSorting(input, expected, "Сортировка массива с дубликатами и отрицательными числами.");
    }

    @Test
    void testAlreadySortedArrayAdversarial() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        testSorting(input, expected, "Сортировка уже отсортированного массива.");
    }

    @Test
    void testReverseSortedArrayAdversarial() {
        int[] input = {12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        testSorting(input, expected, "Сортировка обратно отсортированного массива.");
    }

    @Test
    void testStackDepthOnLargeAdversarialArray() {
        int N = 100_000;
        int[] input = new int[N];

        for (int i = 0; i < N; i++) {
            input[i] = i;
        }

        int[] expected = input.clone();

        testSorting(input, expected, "Сортировка большого массива, StackOverflow check.");
    }
}