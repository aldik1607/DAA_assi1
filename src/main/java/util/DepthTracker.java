package util;

public class DepthTracker {
    private int currentDepth = 0;
    private int maxDepth = 0;

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void reset() {
        currentDepth = 0;
        maxDepth = 0;
    }
}
