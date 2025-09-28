# DAA Assignment 1 — Sorting and Selection Algorithms

This repository contains implementations of classic divide-and-conquer algorithms: MergeSort, QuickSort, Deterministic Select (Median-of-Medians), and Closest Pair of Points in 2D. The goal is to analyze performance, recursion depth, and operation counts.

## Architecture Notes

All algorithms use **safe recursion patterns**:

- **DepthTracker** monitors the recursion depth to prevent stack overflow.  
- **Metrics** counts comparisons, swaps, and other operations for analysis.  
- Recursion in **QuickSort** and **Select** is done on the smaller partition to keep stack depth bounded (~O(log n) typical).  
- MergeSort uses a **reusable buffer** and small-n cutoff (InsertionSort) for efficiency.

## Recurrence Analysis

### MergeSort

Recurrence: `T(n) = 2T(n/2) + Θ(n)`  

Master Theorem Case 2: `a=2, b=2, f(n)=Θ(n)`  

Result: `T(n) = Θ(n log n)`  

### QuickSort (Randomized)

Average-case recurrence: `T(n) = T(k) + T(n−k−1) + Θ(n)`, k ≈ n/2  

Intuition: smaller-first recursion bounds stack depth; expected `Θ(n log n)`, worst-case `Θ(n^2)`  

Tail recursion avoids deep recursion on large partitions  

### Deterministic Select (Median-of-Medians)

Recurrence: `T(n) = T(n/5) + T(7n/10) + Θ(n)`  

Akra–Bazzi intuition: linear-time pivot guarantees `T(n) = Θ(n)`  

### Closest Pair of Points (2D)

Recurrence: `T(n) = 2T(n/2) + Θ(n)` for divide-and-conquer + strip check  

Master Theorem Case 2 → `T(n) = Θ(n log n)`  


**Observations:**  
- QuickSort and MergeSort show expected logarithmic recursion depth growth.  
- Linear-time Select has lower depth but fewer swaps than QuickSort on average.  
- Constant factors (buffer reuse, small-n cutoffs) noticeably affect measured times.
