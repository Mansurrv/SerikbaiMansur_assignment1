package org.example.Metric;
import org.example.Metric.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

class PairOfPoints {
    public static double findClosestPair(List<Point> points) {
        if (points == null || points.size() < 2) {
            return Double.MAX_VALUE;
        }
        Collections.sort(points, Comparator.comparingDouble(p -> p.x));
        return recursiveFind(points, 0, points.size() - 1);
    }

    private static double recursiveFind(List<Point> points, int low, int high) {
        int n = high - low + 1;

        if (n <= 3) {
            double minDistance = Double.MAX_VALUE;
            for (int i = low; i <= high; i++) {
                for (int j = i + 1; j <= high; j++) {
                    minDistance = Math.min(minDistance, points.get(i).distance(points.get(j)));
                }
            }
            return minDistance;
        }

        int mid = low + (high - low) / 2;
        Point midPoint = points.get(mid);

        double d1 = recursiveFind(points, low, mid);
        double d2 = recursiveFind(points, mid + 1, high);
        double minDistance = Math.min(d1, d2);

        List<Point> strip = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (Math.abs(points.get(i).x - midPoint.x) < minDistance) {
                strip.add(points.get(i));
            }
        }

        return Math.min(minDistance, stripClosest(strip, minDistance));
    }

    private static double stripClosest(List<Point> strip, double minDistance) {
        Collections.sort(strip, Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < minDistance; j++) {
                minDistance = Math.min(minDistance, strip.get(i).distance(strip.get(j)));
            }
        }

        return minDistance;
    }
}