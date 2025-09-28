# DAA Assignment 1 — Sorting and Selection Algorithms

This repository contains implementations of classic divide-and-conquer algorithms: **MergeSort**, **QuickSort**, **Deterministic Select (Median-of-Medians)**, and **Closest Pair of Points in 2D**.  
The goal is to analyze **performance**, **recursion depth**, and **operation counts**.

---

## Architecture Notes

- **DepthTracker** monitors the recursion depth to prevent stack overflow.  
- **Metrics** counts comparisons, swaps, and other operations for analysis.  
- QuickSort and Select use **smaller-first recursion** to keep stack depth bounded (~O(log n) typical).  
- MergeSort uses a **reusable buffer** and **small-n cutoff** (InsertionSort) for efficiency.

---

## Recurrence Analysis

### MergeSort
- **Recurrence:** T(n) = 2T(n/2) + Θ(n)  
- **Master Theorem Case 2:** a=2, b=2, f(n)=Θ(n)  
- **Result:** T(n) = Θ(n log n)

### QuickSort (Randomized)
- **Average-case recurrence:** T(n) = T(k) + T(n−k−1) + Θ(n), k ≈ n/2  
- **Intuition:** smaller-first recursion bounds stack depth; expected Θ(n log n), worst-case Θ(n²)  
- **Tail recursion** avoids deep recursion on large partitions

### Deterministic Select (Median-of-Medians)
- **Recurrence:** T(n) = T(n/5) + T(7n/10) + Θ(n)  
- **Akra–Bazzi intuition:** linear-time pivot guarantees T(n) = Θ(n)

### Closest Pair of Points (2D)
- **Recurrence:** T(n) = 2T(n/2) + Θ(n) for divide-and-conquer + strip check  
- **Master Theorem Case 2:** T(n) = Θ(n log n)

---

## Benchmark Results

### Time, Recursion Depth, Comparisons, and Swaps

| Array Size | QuickSort Time (ms) | QuickSort Depth | QuickSort Comparisons | QuickSort Swaps | MergeSort Time (ms) | MergeSort Depth | MergeSort Comparisons | MergeSort Swaps | Select Time (ms) | Select Depth | Select Comparisons | Select Swaps | Median Value |
|------------|--------------------|----------------|---------------------|----------------|--------------------|----------------|---------------------|----------------|-----------------|--------------|------------------|--------------|--------------|
| 1000       | 1                  | 7              | 10910               | 6337           | 1                  | 10             | 8686                | 9976           | 1               | 5            | 5798             | 4633         | 5101         |
| 5000       | 1                  | 8              | 72570               | 42435          | 0                  | 13             | 55246               | 61808          | 1               | 6            | 31121            | 24100        | 24199        |
| 10000      | 1                  | 9              | 149247              | 87382          | 7                  | 14             | 120392              | 133616         | 3               | 6            | 64160            | 49696        | 50768        |

---

## Observations

- QuickSort and MergeSort show **expected logarithmic recursion depth growth**.  
- Linear-time Select has **lower recursion depth** and **fewer swaps** than QuickSort on average.  
- Constant factors (buffer reuse, small-n cutoffs) noticeably affect measured times.  
- Measured times align reasonably well with theoretical predictions.

---
