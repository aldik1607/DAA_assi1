package geometry;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosestPairTest {

    private final Random rnd = new Random();

    private double bruteClosest(ClosestPair.Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double dist = Math.sqrt(dx*dx + dy*dy);
                if (dist < best) best = dist;
            }
        }
        return best;
    }

    @Test
    void testSmallRandom() {
        ClosestPair cp = new ClosestPair();
        for (int t = 0; t < 200; t++) {
            int n = 1 + rnd.nextInt(100); // небольшие массивы
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) pts[i] = new ClosestPair.Point(rnd.nextDouble()*1000, rnd.nextDouble()*1000);

            double expected = (n < 2) ? Double.POSITIVE_INFINITY : bruteClosest(pts);
            double actual = cp.closestDistance(pts);
            if (Double.isInfinite(expected)) {
                assertEquals(Double.POSITIVE_INFINITY, actual);
            } else {
                assertEquals(expected, actual, 1e-9);
            }
        }
    }

    @Test
    void testValidateAgainstBruteForBiggerN() {
        ClosestPair cp = new ClosestPair();
        // несколько тестов с n up to 2000 (проверка на малых кол-вo для корректности)
        for (int t = 0; t < 10; t++) {
            int n = 500 + rnd.nextInt(1501); // 500..2000
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) pts[i] = new ClosestPair.Point(rnd.nextDouble()*10000, rnd.nextDouble()*10000);

            double brute = bruteClosest(pts); // O(n^2) but n<=2000 so ok for test
            double alg = cp.closestDistance(pts);
            assertEquals(brute, alg, 1e-8);
        }
    }
}
