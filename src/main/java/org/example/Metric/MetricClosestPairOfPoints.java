package org.example.Metric;

import java.util.*;

public class MetricClosestPairOfPoints {
    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Point[] points = new Point[n];
        for (int i=0; i<n; i++) {
            points[i] = new Point(sc.nextDouble(), sc.nextDouble());
        }

        Metrics metrics = new Metrics();
        CsvLogger logger = new CsvLogger("results.csv");

        long start = System.nanoTime();
        double minDist = closestPair(points, metrics);
        long end = System.nanoTime();

        long timeMs = (end - start) / 1_000_000;

        logger.log("ClosestPair", n, metrics, timeMs);

        System.out.println("Closest distance = " + minDist);
        System.out.println("Comparisons: " + metrics.getComparisons());
        System.out.println("Swaps: " + metrics.getSwaps());
        System.out.println("Max recursion depth: " + metrics.getMaxDepth());
    }

    public static double closestPair(Point[] points, Metrics metrics) {
        Point[] pointsSortedByX = points.clone();
        Arrays.sort(pointsSortedByX, Comparator.comparingDouble(p -> p.x));
        Point[] pointsSortedByY = points.clone();
        Arrays.sort(pointsSortedByY, Comparator.comparingDouble(p -> p.y));
        return closestPairRec(pointsSortedByX, pointsSortedByY, metrics);
    }

    private static double closestPairRec(Point[] Px, Point[] Py, Metrics metrics) {
        metrics.enterRecursion();
        int n = Px.length;
        if (n <= 3) {
            double min = bruteForce(Px, metrics);
            metrics.exitRecursion();
            return min;
        }

        int mid = n/2;
        Point midPoint = Px[mid];

        Point[] Qx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid, n);

        List<Point> Qy = new ArrayList<>();
        List<Point> Ry = new ArrayList<>();
        for (Point p : Py) {
            if (p.x <= midPoint.x) Qy.add(p); else Ry.add(p);
        }

        double d1 = closestPairRec(Qx, Qy.toArray(new Point[0]), metrics);
        double d2 = closestPairRec(Rx, Ry.toArray(new Point[0]), metrics);

        double d = Math.min(d1, d2);

        List<Point> strip = new ArrayList<>();
        for (Point p : Py) {
            if (Math.abs(p.x - midPoint.x) < d) strip.add(p);
        }

        double stripMin = stripClosest(strip, d, metrics);

        metrics.exitRecursion();
        return Math.min(d, stripMin);
    }

    private static double bruteForce(Point[] points, Metrics metrics) {
        double min = Double.POSITIVE_INFINITY;
        for (int i=0; i<points.length; i++) {
            for (int j=i+1; j<points.length; j++) {
                metrics.incComparisons();
                double dist = distance(points[i], points[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double stripClosest(List<Point> strip, double d, Metrics metrics) {
        double min = d;
        for (int i=0; i<strip.size(); i++) {
            for (int j=i+1; j<strip.size() && (strip.get(j).y - strip.get(i).y) < min; j++) {
                metrics.incComparisons();
                double dist = distance(strip.get(i), strip.get(j));
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}