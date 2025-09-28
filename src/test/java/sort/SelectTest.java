package sort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SelectTest {

    @Test
    void testSingleElement() {
        int[] arr = {42};
        assertEquals(42, Select.select(arr.clone(), 0));
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {7, 7, 7, 7};
        for (int i = 0; i < arr.length; i++) {
            assertEquals(7, Select.select(arr.clone(), i));
        }
    }

    @Test
    void testArrayWithDuplicates() {
        int[] arr = {5, 2, 5, 2, 5};
        assertEquals(2, Select.select(arr.clone(), 1));
        assertEquals(5, Select.select(arr.clone(), 3));
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(1, Select.select(arr.clone(), 0));
        assertEquals(3, Select.select(arr.clone(), 2));
        assertEquals(5, Select.select(arr.clone(), 4));
    }

    @Test
    void testReverseArray() {
        int[] arr = {5, 4, 3, 2, 1};
        assertEquals(1, Select.select(arr.clone(), 0));
        assertEquals(3, Select.select(arr.clone(), 2));
        assertEquals(5, Select.select(arr.clone(), 4));
    }

    @Test
    void testEmptyArrayThrows() {
        int[] arr = {};
        assertThrows(IllegalArgumentException.class, () -> Select.select(arr.clone(), 0));
    }
}
