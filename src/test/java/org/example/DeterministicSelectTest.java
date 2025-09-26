package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeterministicSelectTest {

    private final Random random = new Random();

    private int getExpectedKth(int[] array, int k) {
        int[] sortedCopy = array.clone();
        Arrays.sort(sortedCopy);
        return sortedCopy[k - 1];
    }

    private int[] copyAndScramble(int[] original) {
        int[] copy = original.clone();
        for (int i = 0; i < copy.length; i++) {
            int swapIndex = random.nextInt(copy.length);
            // Предполагается, что DeterministicSelect.swap существует
            // DeterministicSelect.swap(copy, i, swapIndex);
            // Оставляем это здесь как заглушку, т.к. этот метод не используется в тестах N_XXX.
        }
        return copy;
    }

    // Унифицированный вспомогательный метод
    private void runMultipleTrialsTest(int N, int numTrials) {
        System.out.println("Запуск теста: N=" + N + ", numTrials=" + numTrials);
        int range = 2 * N;
        for (int i = 0; i < numTrials; i++) {
            int[] original = new int[N];
            for (int j = 0; j < N; j++) {
                original[j] = random.nextInt(range);
            }

            int k = random.nextInt(N) + 1;

            int expected = getExpectedKth(original, k);

            int[] selectArray = original.clone();
            int actual = DeterministicSelect.select(selectArray, k);

            assertEquals(expected, actual,
                    "Испытание #" + i + " (N=" + N + ", k=" + k +
                            ") не совпал. Ожидалось: " + expected + ", Получено: " + actual);
        }
    }

    @Test
    void validateAgainstArraysSortN_500_Trials() {
        runMultipleTrialsTest(500, 100);
    }

    @Test
    void validateAgainstArraysSortN_1000_Trials() {
        runMultipleTrialsTest(1000, 100);
    }

    @Test
    void validateAgainstArraysSortN_1500_Trials() {
        runMultipleTrialsTest(1500, 100);
    }

    @Test
    void validateAgainstArraysSortN_2000_Trials() {
        runMultipleTrialsTest(2000, 100);
    }

    @Test
    void validateAgainstArraysSortN_2500_Trials() {
        runMultipleTrialsTest(2500, 100);
    }

    @Test
    void validateAgainstArraysSortN_3000_Trials() {
        runMultipleTrialsTest(3000, 100);
    }

    @Test
    void validateAgainstArraysSortN_3500_Trials() {
        runMultipleTrialsTest(3500, 100);
    }

    @Test
    void validateAgainstArraysSortN_4000_Trials() {
        runMultipleTrialsTest(4000, 100);
    }

    @Test
    void validateAgainstArraysSortN_4500_Trials() {
        runMultipleTrialsTest(4500, 100);
    }

    @Test
    void validateAgainstArraysSortN_5000_Trials() {
        runMultipleTrialsTest(5000, 100);
    }

    @Test
    void testSmallArrayBaseCase() {
        int[] array = {5, 2, 8, 1, 9};
        assertEquals(1, DeterministicSelect.select(array.clone(), 1), "k=1 (min)");
        assertEquals(5, DeterministicSelect.select(array.clone(), 3), "k=3 (median)");
        assertEquals(9, DeterministicSelect.select(array.clone(), 5), "k=5 (max)");
    }

    @Test
    void testArrayWithDuplicates() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        assertEquals(1, DeterministicSelect.select(array.clone(), 2), "k=2 (1)");
        assertEquals(3, DeterministicSelect.select(array.clone(), 4), "k=4 (3)");
        assertEquals(9, DeterministicSelect.select(array.clone(), 10), "k=10 (max)");
    }

    @Test
    void testSortedArrayAdversarial() {
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i + 1;
        }
        assertEquals(50, DeterministicSelect.select(array.clone(), 50), "Median of sorted array");
        assertEquals(1, DeterministicSelect.select(array.clone(), 1), "Min of sorted array");
    }

    @Test
    void testNegativeNumbers() {
        int[] array = {-10, -5, 0, 5, 10};
        assertEquals(-5, DeterministicSelect.select(array.clone(), 2), "Negative numbers");
    }

    @Test
    void testBoundaryKValues() {
        int[] array = {10, 20, 30, 40, 50};
        assertEquals(10, DeterministicSelect.select(array.clone(), 1), "k=1 (Min)");
        assertEquals(50, DeterministicSelect.select(array.clone(), 5), "k=N (Max)");
    }

    @Test
    void testInvalidKZero() {
        int[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> {
            DeterministicSelect.select(array, 0);
        }, "K=0 должен вызывать исключение.");
    }

    @Test
    void testInvalidKTooLarge() {
        int[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> {
            DeterministicSelect.select(array, 4);
        }, "K > N должен вызывать исключение.");
    }
}