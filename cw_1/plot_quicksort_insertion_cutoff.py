import matplotlib.pyplot as plt


RESULT_FILE = "QuickSortInsertionCutoff_performance.txt"
OUTPUT_FILE = "QuickSortInsertionCutoff.png"


cutoffs = []
times = []

with open(RESULT_FILE, encoding="utf-8") as file:
    next(file)

    for line in file:
        cutoff, time = line.split("|")
        cutoffs.append(int(cutoff.strip()))
        times.append(int(time.strip()) / 1_000_000)

lowest_index = times.index(min(times))
lowest_cutoff = cutoffs[lowest_index]
lowest_time = times[lowest_index]

plt.plot(cutoffs, times, marker="o")
plt.scatter(
    lowest_cutoff,
    lowest_time,
    color="red",
    edgecolors="black",
    zorder=3,
    label=f"lowest time: cutoff = {lowest_cutoff}",
)

plt.xlabel("Insertion sort cutoff")
plt.ylabel("Average sorting time [ms]")
plt.title("QuickSort with insertion sort for small subproblems")
plt.grid(True)
plt.legend()
plt.savefig(OUTPUT_FILE, dpi=200, bbox_inches="tight")
plt.show()
