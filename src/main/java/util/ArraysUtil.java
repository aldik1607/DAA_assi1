package util;

import java.util.Random;

public class ArraysUtil {
    public static final Random random = new Random();

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void shuffle(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    public static boolean isNullOrEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }
}
