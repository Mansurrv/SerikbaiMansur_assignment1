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
    void validateAgainstBruteForceMultipleTrials() {
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
    void testLargeRandomSetPerformance() {
        int N = 10000;
        double range = 10000.0;

        List<Point> points = generateRandomPoints(N, range);

        long startTime = System.currentTimeMillis();
        double minDistance = PairOfPoints.findClosestPair(points);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue(minDistance != Double.MAX_VALUE, "Не удалось найти минимальное расстояние в большом наборе.");

        System.out.println("Duration for N=" + N + " points: " + duration + " ms");
        assertTrue(duration < 1000, "Слишком долгое выполнение для O(n log n) с N=" + N + ".");
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
}