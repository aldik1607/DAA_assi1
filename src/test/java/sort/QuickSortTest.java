package sort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testSingleElementArray() {
        int[] arr = {42};
        int[] expected = {42};
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {7, 7, 7, 7};
        int[] expected = {7, 7, 7, 7};
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] arr = {5, 2, 5, 2, 5};
        int[] expected = {2, 2, 5, 5, 5};
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testReverseArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
