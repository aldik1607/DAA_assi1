
import org.junit.jupiter.api.Test;
import sort.Select;

import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectTest {

    private final Random random = new Random();

    @Test
    void testRandomArrays() {
        Select select = new Select();

        for (int t = 0; t < 100; t++) {
            int n = 1 + random.nextInt(100); // размер массива 1..100
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = random.nextInt(1000);

            int k = random.nextInt(n);
            int expected = Arrays.stream(arr).sorted().toArray()[k];
            int actual = select.select(arr.clone(), k);

            assertEquals(expected, actual, "Failed on array " + Arrays.toString(arr) + " with k=" + k);
        }
    }

    @Test
    void testSmallArray() {
        Select select = new Select();
        int[] arr = {5, 2, 9, 1, 7};

        assertEquals(1, select.select(arr.clone(), 0));
        assertEquals(2, select.select(arr.clone(), 1));
        assertEquals(5, select.select(arr.clone(), 2));
        assertEquals(7, select.select(arr.clone(), 3));
        assertEquals(9, select.select(arr.clone(), 4));
    }

    @Test
    void testArrayWithDuplicates() {
        Select select = new Select();
        int[] arr = {4, 4, 2, 2, 1};

        assertEquals(1, select.select(arr.clone(), 0));
        assertEquals(2, select.select(arr.clone(), 1));
        assertEquals(2, select.select(arr.clone(), 2));
        assertEquals(4, select.select(arr.clone(), 3));
        assertEquals(4, select.select(arr.clone(), 4));
    }
}
