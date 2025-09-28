package cli;

import geometry.ClosestPair;
import sort.MergeSort;
import sort.QuickSort;
import sort.Select;
import util.CSVWriter;
import util.DepthTracker;
import util.Metrics;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Main {
    private static final String USAGE = """
            Usage:
              --algo <mergesort|quicksort|select|closest|all>
              --n <size>
              --trials <t>
              --out <file.csv>
              [--seed <seed>]
              [--k <k>]
            """;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }

        // defaults
        String algo = null;
        int n = 10000;
        int trials = 5;
        long seed = System.currentTimeMillis();
        String out = "results.csv";
        int k = -1;

        // simple args parsing
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo" -> algo = args[++i];
                case "--n" -> n = Integer.parseInt(args[++i]);
                case "--trials" -> trials = Integer.parseInt(args[++i]);
                case "--seed" -> seed = Long.parseLong(args[++i]);
                case "--out" -> out = args[++i];
                case "--k" -> k = Integer.parseInt(args[++i]);
                default -> {
                    System.out.println("Unknown arg: " + args[i]);
                    System.out.println(USAGE);
                    return;
                }
            }
        }
        if (algo == null) {
            System.out.println("Please provide --algo.");
            return;
        }
        if (k < 0) k = n / 2;

        System.out.printf(Locale.ROOT,
                "Algo=%s n=%d trials=%d seed=%d k=%d out=%s%n",
                algo, n, trials, seed, k, out);

        CSVWriter csv = new CSVWriter(out);
        csv.write("header",
                "algo,n,trial,time_ns,verified,depth,comparisons,swaps,notes");

        Random rnd = new Random(seed);

        if (algo.equals("mergesort") || algo.equals("all")) {
            runSortTrials("mergesort", n, trials, rnd, csv, Main::runMergeSortOnce);
        }
        if (algo.equals("quicksort") || algo.equals("all")) {
            runSortTrials("quicksort", n, trials, rnd, csv, Main::runQuickSortOnce);
        }
        if (algo.equals("select") || algo.equals("all")) {
            runSelectTrials("select", n, k, trials, rnd, csv, Main::runSelectOnce);
        }
        if (algo.equals("closest") || algo.equals("all")) {
            runClosestTrials("closest", n, trials, rnd, csv, Main::runClosestOnce);
        }

        System.out.println("Done. Results -> " + out);
    }

    /* ---------------------- per-algo run implementations ---------------------- */

    private static void runMergeSortOnce(String name, int[] base, int trial, CSVWriter csv) {
        int[] arr = base.clone();
        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();

        long start = System.nanoTime();
        MergeSort.sort(arr, depth, metrics); // нужно добавить параметры в MergeSort
        long end = System.nanoTime();

        boolean ok = isSorted(arr);
        writeCsv(csv, name, base.length, trial, end - start, ok,
                depth.getMaxDepth(), metrics.getComparisons(), metrics.getSwaps(), "");
    }

    private static void runQuickSortOnce(String name, int[] base, int trial, CSVWriter csv) {
        int[] arr = base.clone();
        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();

        long start = System.nanoTime();
        QuickSort.sort(arr, depth, metrics);
        long end = System.nanoTime();

        boolean ok = isSorted(arr);
        writeCsv(csv, name, base.length, trial, end - start, ok,
                depth.getMaxDepth(), metrics.getComparisons(), metrics.getSwaps(), "");
    }

    private static void runSelectOnce(String name, int[] base, int k, int trial, CSVWriter csv) {
        int[] arr = base.clone();
        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();

        long start = System.nanoTime();
        int result = Select.select(arr, k, depth, metrics);
        long end = System.nanoTime();

        int[] sorted = base.clone();
        Arrays.sort(sorted);
        boolean ok = (result == sorted[k]);

        writeCsv(csv, name, base.length, trial, end - start, ok,
                depth.getMaxDepth(), metrics.getComparisons(), metrics.getSwaps(),
                "select_k=" + k);
    }

    private static void runClosestOnce(String name, double[][] points, int trial, CSVWriter csv) {
        double[][] pts = Arrays.stream(points).map(double[]::clone).toArray(double[][]::new);
        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();

        long start = System.nanoTime();
        double dist = ClosestPair.closestPair(pts, depth, metrics);
        long end = System.nanoTime();

        boolean verified = true;
        if (pts.length <= 2000) {
            double brute = bruteForceClosest(pts);
            verified = Math.abs(dist - brute) < 1e-9;
        }

        writeCsv(csv, name, pts.length, trial, end - start, verified,
                depth.getMaxDepth(), metrics.getComparisons(), metrics.getSwaps(),
                "distance=" + dist);
    }

    /* ---------------------- trial orchestrators ---------------------- */

    private static void runSortTrials(String name, int n, int trials, Random rnd,
                                      CSVWriter csv, SortRunner runner) {
        for (int t = 0; t < trials; t++) {
            int[] base = randomIntArray(n, rnd);
            runner.run(name, base, t, csv);
        }
    }

    private static void runSelectTrials(String name, int n, int k, int trials, Random rnd,
                                        CSVWriter csv, SelectRunner runner) {
        for (int t = 0; t < trials; t++) {
            int[] base = randomIntArray(n, rnd);
            runner.run(name, base, k, t, csv);
        }
    }

    private static void runClosestTrials(String name, int n, int trials, Random rnd,
                                         CSVWriter csv, ClosestRunner runner) {
        for (int t = 0; t < trials; t++) {
            double[][] pts = randomPoints(n, rnd);
            runner.run(name, pts, t, csv);
        }
    }

    /* ---------------------- utils ---------------------- */

    private static void writeCsv(CSVWriter csv, String algo, int n, int trial, long timeNs,
                                 boolean verified, long depth, long comparisons, long swaps,
                                 String notes) {
        String data = String.format(Locale.ROOT,
                "%s,%d,%d,%d,%s,%d,%d,%d,%s",
                algo, n, trial, timeNs, verified ? "1" : "0",
                depth, comparisons, swaps, notes);
        try {
            csv.write("", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] randomIntArray(int n, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        return a;
    }

    private static double[][] randomPoints(int n, Random rnd) {
        double[][] p = new double[n][2];
        for (int i = 0; i < n; i++) {
            p[i][0] = rnd.nextDouble();
            p[i][1] = rnd.nextDouble();
        }
        return p;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }

    private static double bruteForceClosest(double[][] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i][0] - pts[j][0];
                double dy = pts[i][1] - pts[j][1];
                double d = Math.hypot(dx, dy);
                if (d < best) best = d;
            }
        }
        return best;
    }

    /* ---------------------- functional interfaces ---------------------- */

    @FunctionalInterface
    private interface SortRunner {
        void run(String name, int[] base, int trial, CSVWriter csv);
    }

    @FunctionalInterface
    private interface SelectRunner {
        void run(String name, int[] base, int k, int trial, CSVWriter csv);
    }

    @FunctionalInterface
    private interface ClosestRunner {
        void run(String name, double[][] points, int trial, CSVWriter csv);
    }
}
