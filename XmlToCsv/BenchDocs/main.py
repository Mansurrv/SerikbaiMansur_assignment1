import pandas as pd
import matplotlib.pyplot as plt
import json

try:
    with open('/home/mansur/IdeaProjects/untitled/resultsBench.csv', 'r') as f:
        data = json.load(f)
except FileNotFoundError:
    print("file 'resultsBench.csv' not found.")
    exit()
except json.JSONDecodeError:
    print("error 'resultsBench.csv'.")
    exit()

processed_data = []
for entry in data:
    benchmark_name = entry.get('benchmark', 'N/A').split('.')[-1] 
    mode = entry.get('mode', 'N/A')
    score = entry.get('primaryMetric', {}).get('score')
    score_error = entry.get('primaryMetric', {}).get('scoreError')
    size = entry.get('params', {}).get('size')
    
    try:
        size_numeric = int(size)
    except (ValueError, TypeError):
        size_numeric = size
    
    if score is not None:
        processed_data.append({
            'benchmark': benchmark_name,
            'mode': mode,
            'size': size_numeric,
            'score': score,
            'score_error': score_error
        })

df = pd.DataFrame(processed_data)

df_plot = df[df['mode'] == 'avgt'].copy()

df_plot.dropna(subset=['benchmark', 'size', 'score'], inplace=True)

df_plot.sort_values(by=['size', 'benchmark'], inplace=True)

plt.figure(figsize=(12, 6))

benchmarks = df_plot['benchmark'].unique()
x_positions = pd.factorize(df_plot['size'].astype(str))[0]
sizes = df_plot['size'].astype(str).unique()
size_to_pos = {size: pos for pos, size in enumerate(sizes)}

width = 0.8 / len(benchmarks) 

for i, bench in enumerate(benchmarks):
    subset = df_plot[df_plot['benchmark'] == bench]
    x_pos_bench = [size_to_pos[str(s)] + i * width - (len(benchmarks) - 1) * width / 2 for s in subset['size']]
    
    plt.bar(
        x_pos_bench, 
        subset['score'], 
        yerr=subset['score_error'], 
        capsize=5, 
        width=width, 
        label=bench
    )

plt.yscale('log')

plt.title('Comparison of average completion time of benchmarks')
plt.xlabel('Input data size')
plt.ylabel('Average execution time')
plt.xticks(
    ticks=range(len(sizes)), 
    labels=sizes, 
    rotation=45, 
    ha='right' 
)
plt.legend(title='Бенчмарк')
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.tight_layout()

plt.show()