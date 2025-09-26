package geometry;

import java.util.Arrays;
import java.util.HashSet;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public double closestDistance(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] byX = pts.clone();
        Arrays.sort(byX, (a, b) -> Double.compare(a.x, b.x));
        Point[] byY = pts.clone();
        Arrays.sort(byY, (a, b) -> Double.compare(a.y, b.y));
        double sq = closestRec(byX, byY);
        return Math.sqrt(sq);
    }

    private double closestRec(Point[] byX, Point[] byY) {
        int n = byX.length;
        if (n <= 3) {
            return bruteForceSq(byX);
        }

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

        double dl = closestRec(leftByX, leftByY);
        double dr = closestRec(rightByX, rightByY);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[byY.length];
        int stripCnt = 0;
        for (Point p : byY) {
            double dx = p.x - midX;
            if (dx * dx < d) strip[stripCnt++] = p;
        }

        for (int i = 0; i < stripCnt; i++) {
            Point a = strip[i];
            for (int j = i + 1; j < stripCnt && j <= i + 7; j++) {
                Point b = strip[j];
                double dy = b.y - a.y;
                if (dy * dy >= d) break;
                double dx = b.x - a.x;
                double sq = dx * dx + dy * dy;
                if (sq < d) d = sq;
            }
        }

        return d;
    }

    private double bruteForceSq(Point[] a) {
        double best = Double.POSITIVE_INFINITY;
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dx = a[i].x - a[j].x;
                double dy = a[i].y - a[j].y;
                double sq = dx * dx + dy * dy;
                if (sq < best) best = sq;
            }
        }
        return best;
    }
}
