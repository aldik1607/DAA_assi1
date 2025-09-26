package sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    @Test
    public void testSortBasic() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        int[] expected = {1, 2, 5, 5, 6, 9};

        MergeSort sorter = new MergeSort();
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        MergeSort sorter = new MergeSort();
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSingleElement() {
        int[] arr = {42};
        int[] expected = {42};

        MergeSort sorter = new MergeSort();
        sorter.sort(arr);

        assertArrayEquals(expected, arr);
    }
}
