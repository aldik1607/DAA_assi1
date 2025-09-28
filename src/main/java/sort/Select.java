package sort;

import util.ArraysUtil;
import util.DepthTracker;
import util.Metrics;

public class Select {

    public static int select(int[] arr, int k) {
        if (ArraysUtil.isNullOrEmpty(arr) || k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Invalid input");
        }

        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();

        return select(arr, k, depth, metrics);
    }

    public static int select(int[] arr, int k, DepthTracker depth, Metrics metrics) {
        if (ArraysUtil.isNullOrEmpty(arr) || k < 0 || k >= arr.length)
            throw new IllegalArgumentException("Invalid input");
        return deterministicSelect(arr, 0, arr.length - 1, k, depth, metrics);
    }

    private static int deterministicSelect(int[] arr, int left, int right, int k, DepthTracker depth, Metrics metrics) {
        while (true) {
            if (left == right) return arr[left];
            depth.enterRecursion();

            int pivotIndex = medianOfMedians(arr, left, right, metrics, depth);
            pivotIndex = partition(arr, left, right, pivotIndex, metrics);

            if (k == pivotIndex) {
                depth.exitRecursion();
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
            depth.exitRecursion();
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex, Metrics metrics) {
        int pivotValue = arr[pivotIndex];
        ArraysUtil.swap(arr, pivotIndex, right);
        metrics.incSwaps();

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.incComparisons();
            if (arr[i] < pivotValue) {
                ArraysUtil.swap(arr, storeIndex, i);
                metrics.incSwaps();
                storeIndex++;
            }
        }
        ArraysUtil.swap(arr, storeIndex, right);
        metrics.incSwaps();
        return storeIndex;
    }

    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics, DepthTracker depth) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right, metrics);
            return left + n / 2;
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            insertionSort(arr, i, subRight, metrics);
            int medianIndex = i + (subRight - i) / 2;
            ArraysUtil.swap(arr, left + numMedians, medianIndex);
            metrics.incSwaps();
            numMedians++;
        }

        int medianValue = deterministicSelect(arr, left, left + numMedians - 1, left + numMedians / 2, depth, metrics);

        for (int i = left; i <= right; i++) {
            if (arr[i] == medianValue) {
                return i;
            }
        }
        return left;
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                metrics.incComparisons();
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            metrics.incSwaps();
        }
    }
}
