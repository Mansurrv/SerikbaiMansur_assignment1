1. Architecture notes

1.1. Quick Sort

Recursion depth   --->   Controlled by optimizing tail of recursion on the larger partition. Shortly the recursive call is always directed to the smaller of the tw partitions, and the larger partition is processed in an iterative while loop.

Supporting element   --->   A randomized anchor element that is selected from a random index in the range from low to high is used, which provides the expected complexity of O(n logn) and guarantees that the worst case of O(n^2) is extremely unlikely

Limit   --->   This architecture limits the recursion depth to O(logn), preventing stack overflow even on very large, already sorted, or reverse sorted arrays

