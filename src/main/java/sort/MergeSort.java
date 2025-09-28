package sort;

import util.DepthTracker;
import util.Metrics;

public class MergeSort {

    public static void sort(int[] arr) {
        DepthTracker depth = new DepthTracker();
        Metrics metrics = new Metrics();
        sort(arr, depth, metrics);
    }

    public static void sort(int[] array, DepthTracker depth, Metrics metrics) {
        if (array == null || array.length < 2) return;
        mergeSort(array, new int[array.length], 0, array.length - 1, depth , metrics);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, DepthTracker depth, Metrics metrics) {
        if (left >= right) return;
        depth.enterRecursion();

        int mid = (left + right) / 2;
        mergeSort(arr, buffer, left, mid, depth, metrics);
        mergeSort(arr, buffer, mid + 1, right, depth, metrics);
        merge(arr, buffer, left, mid, right, metrics);
        depth.exitRecursion();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.incComparisons();
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }

        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        for (int p = left; p <= right; p++) {
            arr[p] = buffer[p];
            metrics.incSwaps();
        }
    }
}
