package util;

public class Metrics {
    private long comparisons = 0;
    private long swaps = 0;

    public void incComparisons() {
        comparisons++;
    }

    public void incSwaps() {
        swaps++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
    }
}

