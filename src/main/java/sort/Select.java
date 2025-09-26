package sort;

import util.ArraysUtil;

public class Select {

    public int select(int[] arr, int k) {
        if (ArraysUtil.isNullOrEmpty(arr) || k < 0 || k >= arr.length)
            throw new IllegalArgumentException("Invalid input");
        return deterministicSelect(arr, 0, arr.length - 1, k);
    }

    private int deterministicSelect(int[] arr, int left, int right, int k) {
        while (true) {
            if (left == right) return arr[left];

            int pivotIndex = medianOfMedians(arr, left, right);
            pivotIndex = partition(arr, left, right, pivotIndex);

            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        ArraysUtil.swap(arr, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                ArraysUtil.swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        ArraysUtil.swap(arr, storeIndex, right);
        return storeIndex;
    }

    private int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right);
            return left + n / 2;
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            insertionSort(arr, i, subRight);
            int medianIndex = i + (subRight - i) / 2;
            ArraysUtil.swap(arr, left + numMedians, medianIndex);
            numMedians++;
        }

        int medianValue = deterministicSelect(arr, left, left + numMedians - 1, left + numMedians / 2);

        for (int i = left; i <= right; i++) {
            if (arr[i] == medianValue) {
                return i;
            }
        }
        return left;
    }

    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
