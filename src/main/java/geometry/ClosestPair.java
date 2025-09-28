package geometry;

import util.DepthTracker;
import util.Metrics;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

public class ClosestPair {

    public double closestDistance(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;

        double[][] arr = new double[pts.length][2];
        for (int i = 0; i < pts.length; i++) {
            arr[i][0] = pts[i].x;
            arr[i][1] = pts[i].y;
        }

        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();

        return ClosestPair.closestPair(arr, depth, metrics);
    }

        public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }


    public static double closestPair(double[][] pts, DepthTracker depth, Metrics metrics) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;

        Point[] points = new Point[pts.length];
        for (int i = 0; i < pts.length; i++) {
            points[i] = new Point(pts[i][0], pts[i][1]);
        }

        Point[] byX = points.clone();
        Arrays.sort(byX, (a, b) -> Double.compare(a.x, b.x));
        Point[] byY = points.clone();
        Arrays.sort(byY, (a, b) -> Double.compare(a.y, b.y));

        double sq = closestRec(byX, byY, depth, metrics);
        return Math.sqrt(sq);
    }

    private static double closestRec(Point[] byX, Point[] byY, DepthTracker depth, Metrics metrics) {
        int n = byX.length;
        if (n <= 3) {
            return bruteForceSq(byX, metrics);
        }

        depth.enterRecursion();

        int mid = n / 2;
        Point midPoint = byX[mid];
        double midX = midPoint.x;

        // left and right byX
        Point[] leftByX = Arrays.copyOfRange(byX, 0, mid);
        Point[] rightByX = Arrays.copyOfRange(byX, mid, n);

        HashSet<Point> leftSet = new HashSet<>();
        for (Point p : leftByX) leftSet.add(p);

        Point[] leftByY = new Point[leftByX.length];
        Point[] rightByY = new Point[rightByX.length];
        int li = 0, ri = 0;
        for (Point p : byY) {
            if (leftSet.contains(p)) leftByY[li++] = p;
            else rightByY[ri++] = p;
        }

        double dl = closestRec(leftByX, leftByY, depth, metrics);
        double dr = closestRec(rightByX, rightByY, depth, metrics);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[byY.length];
        int stripCnt = 0;
        for (Point p : byY) {
            double dx = p.x - midX;
            metrics.incComparisons();
            if (dx * dx < d) strip[stripCnt++] = p;
        }

        for (int i = 0; i < stripCnt; i++) {
            Point a = strip[i];
            for (int j = i + 1; j < stripCnt && j <= i + 7; j++) {
                Point b = strip[j];
                metrics.incComparisons();
                double dy = b.y - a.y;
                if (dy * dy >= d) break;
                double dx = b.x - a.x;
                double sq = dx * dx + dy * dy;
                if (sq < d) d = sq;
            }

        }
        depth.exitRecursion();

        return d;
    }

    private static double bruteForceSq(Point[] a, Metrics metrics) {
        double best = Double.POSITIVE_INFINITY;
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                metrics.incComparisons();
                double dx = a[i].x - a[j].x;
                double dy = a[i].y - a[j].y;
                double sq = dx * dx + dy * dy;
                if (sq < best) best = sq;
            }
        }
        return best;
    }
}
