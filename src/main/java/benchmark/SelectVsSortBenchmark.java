package benchmark;

import sort.MergeSort;
import sort.QuickSort;
import sort.Select;

import util.Metrics;
import util.DepthTracker;

import java.util.Random;

public class SelectVsSortBenchmark {

    private static final Random rand = new Random(123);

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000}; // размеры массивов для теста

        for (int n : sizes) {
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = rand.nextInt(n * 10);

            System.out.println("Array size: " + n);

            // QuickSort
            int[] arrQS = array.clone();
            DepthTracker depthQS = new DepthTracker();
            Metrics metricsQS = new Metrics();
            long startQS = System.nanoTime();
            QuickSort.sort(arrQS, depthQS, metricsQS);
            long endQS = System.nanoTime();
            System.out.println("QuickSort time (ms): " + (endQS - startQS) / 1_000_000);
            System.out.println("QuickSort depth: " + depthQS.getMaxDepth());
            System.out.println("QuickSort comparisons: " + metricsQS.getComparisons());
            System.out.println("QuickSort swaps: " + metricsQS.getSwaps());

            // MergeSort
            int[] arrMS = array.clone();
            DepthTracker depthMS = new DepthTracker();
            Metrics metricsMS = new Metrics();
            long startMS = System.nanoTime();
            MergeSort.sort(arrMS, depthMS, metricsMS);
            long endMS = System.nanoTime();
            System.out.println("MergeSort time (ms): " + (endMS - startMS) / 1_000_000);
            System.out.println("MergeSort depth: " + depthMS.getMaxDepth());
            System.out.println("MergeSort comparisons: " + metricsMS.getComparisons());
            System.out.println("MergeSort swaps: " + metricsMS.getSwaps());

            // Deterministic Select (k = n/2)
            int[] arrSel = array.clone();
            DepthTracker depthSel = new DepthTracker();
            Metrics metricsSel = new Metrics();
            int k = n / 2;
            long startSel = System.nanoTime();
            int median = Select.select(arrSel, k, depthSel, metricsSel);
            long endSel = System.nanoTime();
            System.out.println("Select median time (ms): " + (endSel - startSel) / 1_000_000);
            System.out.println("Select depth: " + depthSel.getMaxDepth());
            System.out.println("Select comparisons: " + metricsSel.getComparisons());
            System.out.println("Select swaps: " + metricsSel.getSwaps());
            System.out.println("Median value: " + median);

            System.out.println("-----------------------------\n");
        }
    }
}
