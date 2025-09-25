package sort;

import java.util.Random;

public class QuickSort {
    private final Random random = new Random();

    public void sort(int[] array) {
        if (array == null || array.length < 2) return;
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] arr, int left, int right) {
        while (left < right) {
            int pivotIndex = partition(arr, left, right);
            // Рекурсивно идём в меньший подмассив
            if (pivotIndex - left < right - pivotIndex) {
                quickSort(arr, left, pivotIndex - 1);
                left = pivotIndex + 1; // хвост делаем итеративно
            } else {
                quickSort(arr, pivotIndex + 1, right);
                right = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] arr, int left, int right) {
        // выбираем случайный pivot
        int pivotIndex = left + random.nextInt(right - left + 1);
        int pivotValue = arr[pivotIndex];

        // swap pivot → right
        swap(arr, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, i, storeIndex);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
