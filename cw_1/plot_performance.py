#!/usr/bin/env python3

import argparse
import re
from pathlib import Path

import matplotlib.pyplot as plt
import pandas as pd


RESULT_PATTERN = re.compile(
    r"^\s*(?P<algorithm>.+?)\s*\|\s*"
    r"(?P<size>\d+)\s*\|\s*(?P<time>[+-]?(?:\d+(?:[.,]\d+)?|[.,]\d+)"
    r"(?:[eE][+-]?\d+)?)\s*$"
)


def read_results(path: Path) -> pd.DataFrame:
    rows = []

    for line in path.read_text(encoding="utf-8").splitlines():
        match = RESULT_PATTERN.match(line)
        if match is None:
            continue

        time_text = match.group("time").replace(",", ".")
        time_value = float(time_text)

        if time_value >= 0:
            rows.append(
                {
                    "algorithm": match.group("algorithm").strip(),
                    "size": int(match.group("size")),
                    "time_ns": time_value,
                }
            )

    if not rows:
        raise ValueError(f"No benchmark rows found in {path}")

    return pd.DataFrame(rows)


def create_graph(path: Path, title: str, output: Path) -> None:
    data = read_results(path)

    figure, axis = plt.subplots(figsize=(11, 7))

    for algorithm, values in data.groupby("algorithm"):
        values = values.sort_values("size")
        axis.plot(
            values["size"],
            values["time_ns"],
            marker="o",
            linewidth=1.5,
            label=algorithm,
        )

    axis.set_title(title)
    axis.set_xlabel("Input size")
    axis.set_ylabel("Average time [ns]")
    axis.set_yscale("log")
    axis.grid(True, which="both", linestyle="--", alpha=0.35)
    axis.legend()
    figure.tight_layout()
    figure.savefig(output, dpi=200)
    plt.close(figure)


def main() -> None:
    parser = argparse.ArgumentParser(
        description="Create logarithmic performance graphs from Maven benchmark output."
    )
    parser.add_argument("--ascending", type=Path)
    parser.add_argument("--descending", type=Path)
    parser.add_argument("--random", dest="random_data", type=Path)
    parser.add_argument(
        "--output-dir",
        type=Path,
        default=Path("performance_graphs"),
    )
    args = parser.parse_args()

    inputs = [
        ("ascending", args.ascending, "Sorting performance - ascending data"),
        ("descending", args.descending, "Sorting performance - descending data"),
        ("random", args.random_data, "Sorting performance - random data"),
    ]

    selected_inputs = [(name, path, title) for name, path, title in inputs if path]
    if not selected_inputs:
        parser.error("provide at least one of --ascending, --descending, or --random")

    args.output_dir.mkdir(parents=True, exist_ok=True)

    for name, input_path, title in selected_inputs:
        output_path = args.output_dir / f"{name}.png"
        create_graph(input_path, title, output_path)
        print(f"Created {output_path}")


if __name__ == "__main__":
    main()
