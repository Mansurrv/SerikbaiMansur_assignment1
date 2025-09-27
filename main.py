import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

try:
    df = pd.read_csv('performance_results.csv')
except FileNotFoundError:
    exit()

df['N'] = pd.to_numeric(df['N'])

time_columns = ['QuickSort_ms', 'MergeSort_ms', 'Select_ms', 'PairOfPoints_ms']

labels = {
    'QuickSort_ms': 'QuickSort (O(n log n))',
    'MergeSort_ms': 'MergeSort (O(n log n))',
    'Select_ms': 'DeterministicSelect (O(n))',
    'PairOfPoints_ms': 'Closest Pair of Points (O(n log n))'
}

plt.figure(figsize=(12, 7))

for column in time_columns:
    plt.loglog(
        df['N'],
        df[column],
        marker='o',
        linestyle='-',
        label=labels[column]
    )

plt.xlabel('Input Size (N) (log)', fontsize=14)
plt.ylabel('Time complexity (ms) (log)', fontsize=14)
plt.title('Comparison of algorithm performance (Log-Log Scale)', fontsize=16)

plt.grid(True, which="both", ls="--", alpha=0.6)

plt.legend(title="Алгоритм", loc='upper left')

plt.savefig('performance_comparison.png')
plt.show()

max_n_row = df[df['N'] == df['N'].max()].iloc[0]
n_max = max_n_row['N']
time_select = max_n_row['Select_ms']
time_sort = max_n_row['MergeSort_ms']

speedup = time_sort / time_select

print(f"Max (N): {n_max}")
print(f"Time Select (O(n)): {time_select:.4f} мс")
print(f"Time MergeSort (O(n log n)): {time_sort:.4f} мс")
print(f"Acceleration of Select compared to MergeSort when N={n_max}: {speedup:.2f}")