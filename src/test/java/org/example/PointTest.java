package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointTest {

    private double bruteForceClosestPair(List<Point> points) {
        if (points == null || points.size() < 2) {
            return Double.MAX_VALUE;
        }

        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dist = points.get(i).distance(points.get(j));
                minDistance = Math.min(minDistance, dist);
            }
        }
        return minDistance;
    }

    private List<Point> generateRandomPoints(int count, double range) {
        List<Point> points = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * range;
            double y = random.nextDouble() * range;
            points.add(new Point(x, y));
        }
        return points;
    }

    // Вспомогательный метод для тестирования корректности большого набора
    private void runCorrectnessTest(int N, int numTrials) {
        double range = 1000.0;
        for (int i = 0; i < numTrials; i++) {
            List<Point> points = generateRandomPoints(N, range);

            double expected = bruteForceClosestPair(points);
            double actual = PairOfPoints.findClosestPair(points);

            assertEquals(expected, actual, 1e-9,
                    "N=" + N + ", Испытание #" + i + " не совпало. Ожидалось: " + expected + ", Получено: " + actual);
        }
    }

    // --- Исходные тесты ---

    @Test
    void testBasicExample() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 6));
        points.add(new Point(2, 3));
        points.add(new Point(3, 7));
        points.add(new Point(4, 4));
        points.add(new Point(6, 8));
        points.add(new Point(7, 1));
        points.add(new Point(8, 5));
        points.add(new Point(9, 2));

        double expected = bruteForceClosestPair(points);
        double actual = PairOfPoints.findClosestPair(points);

        assertEquals(expected, actual, 1e-9, "Основной пример не соответствует ожидаемому результату.");
    }

    @Test
    void testBoundaryConditions() {
        assertEquals(Double.MAX_VALUE, PairOfPoints.findClosestPair(new ArrayList<>()),
                "Пустой список должен вернуть MAX_VALUE.");

        List<Point> twoPoints = new ArrayList<>();
        twoPoints.add(new Point(0, 0));
        twoPoints.add(new Point(3, 4));
        assertEquals(5.0, PairOfPoints.findClosestPair(twoPoints), 1e-9,
                "Ошибка при двух точках.");

        List<Point> threePoints = new ArrayList<>();
        threePoints.add(new Point(1, 1));
        threePoints.add(new Point(10, 10));
        threePoints.add(new Point(1, 1.000000001));
        assertEquals(0.000000001, PairOfPoints.findClosestPair(threePoints), 1e-9,
                "Ошибка в базовом случае.");
    }

    @Test
    void validateAgainstBruteForceMultipleTrialsN_100() {
        // Оставим исходный тест N=100 для быстрого сравнения
        int numTrials = 100;
        int N = 100;
        double range = 1000.0;

        for (int i = 0; i < numTrials; i++) {
            List<Point> points = generateRandomPoints(N, range);

            double expected = bruteForceClosestPair(points);
            double actual = PairOfPoints.findClosestPair(points);

            assertEquals(expected, actual, 1e-9,
                    "O(n log n) результат не совпал с O(n^2) для набора #" + i);
        }
    }

    @Test
    void testIdenticalPoints() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(5, 5));
        points.add(new Point(10, 10));
        points.add(new Point(5, 5));
        points.add(new Point(20, 20));

        assertEquals(0.0, PairOfPoints.findClosestPair(points), 1e-9, "Расстояние между совпадающими точками должно быть 0.");
    }

    // --- НОВЫЕ ТЕСТЫ КОРРЕКТНОСТИ (N = 500 до 5000 с шагом 500) ---
    // Для этих размеров мы можем себе позволить сравнение с O(n^2) Brute Force.

    @Test
    void validateAgainstBruteForceN_500_Trials() {
        runCorrectnessTest(500, 50);
    }

    @Test
    void validateAgainstBruteForceN_1000_Trials() {
        runCorrectnessTest(1000, 20);
    }

    @Test
    void validateAgainstBruteForceN_1500_Trials() {
        runCorrectnessTest(1500, 10);
    }

    @Test
    void validateAgainstBruteForceN_2000_Trials() {
        runCorrectnessTest(2000, 5);
    }

    @Test
    void validateAgainstBruteForceN_2500_Trials() {
        runCorrectnessTest(2500, 3);
    }

    @Test
    void validateAgainstBruteForceN_3000_Trials() {
        runCorrectnessTest(3000, 3);
    }

    @Test
    void validateAgainstBruteForceN_3500_Trials() {
        runCorrectnessTest(3500, 2);
    }

    @Test
    void validateAgainstBruteForceN_4000_Trials() {
        runCorrectnessTest(4000, 2);
    }

    @Test
    void validateAgainstBruteForceN_4500_Trials() {
        runCorrectnessTest(4500, 1);
    }

    @Test
    void validateAgainstBruteForceN_5000_Trials() {
        runCorrectnessTest(5000, 1);
    }
}