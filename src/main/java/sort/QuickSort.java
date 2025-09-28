package sort;

import util.ArraysUtil;
import util.DepthTracker;
import util.Metrics;

import java.util.Random;

public class QuickSort {
    private final Random random = new Random();

    public static void sort(int[] arr) {
        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, depth, metrics);
    }

    public static void sort(int[] array, DepthTracker depth, Metrics metrics) {
        if (array == null || array.length < 2) return;
        ArraysUtil.shuffle(array);
        quickSort(array, 0, array.length - 1, metrics, depth);
    }

    private static void quickSort(int[] arr, int left, int right, Metrics metrics, DepthTracker depth) {
        while (left < right) {
            depth.enterRecursion();
            int pivotIndex = partition(arr, left, right, metrics);
            if (pivotIndex - left < right - pivotIndex) {
                quickSort(arr, left, pivotIndex - 1, metrics, depth);
                left = pivotIndex + 1; // хвост делаем итеративно
            } else {
                quickSort(arr, pivotIndex + 1, right, metrics, depth);
                right = pivotIndex - 1;
            }
            depth.exitRecursion();
        }
    }

    private static int partition(int[] arr, int left, int right, Metrics metrics) {
        int pivotIndex = left + ArraysUtil.random.nextInt(right - left + 1);
        int pivotValue = arr[pivotIndex];

        ArraysUtil.swap(arr, pivotIndex, right);
        metrics.incSwaps();

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.incComparisons();
            if (arr[i] < pivotValue) {
                ArraysUtil.swap(arr, i, storeIndex);
                metrics.incSwaps();
                storeIndex++;
            }
        }
        ArraysUtil.swap(arr, storeIndex, right);
        metrics.incSwaps();
        return storeIndex;
    }

}
