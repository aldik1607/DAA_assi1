package sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    public void testSortBasic() {
        int[] arr = {9, 3, 7, 1, 5};
        int[] expected = {1, 3, 5, 7, 9};

        QuickSort sorter = new QuickSort();
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        QuickSort sorter = new QuickSort();
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSingleElement() {
        int[] arr = {42};
        int[] expected = {42};

        QuickSort sorter = new QuickSort();
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
    }
}
