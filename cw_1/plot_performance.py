#!/usr/bin/env python3
"""Create performance graphs from the Maven sorting-benchmark output.

Example:
    python plot_benchmarks.py \
        --ascending ascending.txt --descending descending.txt --random random.txt \
        --csv
"""

from __future__ import annotations

import argparse
import re
from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from matplotlib.ticker import FuncFormatter, NullLocator

ANSI_PATTERN = re.compile(r"\x1B\[[0-9;]*[A-Za-z]")

RESULT_PATTERN = re.compile(
    r"^\s*(?P<algorithm>[^|]+?)\s*\|\s*"
    r"(?P<size>\d+)\s*\|\s*(?P<time>[+-]?(?:\d+(?:[.,]\d+)?|[.,]\d+)"
    r"(?:[eE][+-]?\d+)?)\s*$"
)

MARKERS = "osD^v<>p*h"


# --------------------------------------------------------------------------- #
# Parsing
# --------------------------------------------------------------------------- #

def read_results(path: Path) -> pd.DataFrame:
    """Parse one Maven output file.

    Rows with a negative time (the harness prints -1 when a measurement fails)
    are kept with a status explaining why, based on the log line printed just
    before the result: "timeout" or "stack overflow".
    """
    rows = []
    pending_reason = None

    text = path.read_text(encoding="utf-8", errors="replace")
    for raw_line in text.splitlines():
        line = ANSI_PATTERN.sub("", raw_line)

        if "StackOverflow" in line:
            pending_reason = "stack overflow"
            continue
        if "TIMEOUT" in line:
            pending_reason = "timeout"
            continue

        match = RESULT_PATTERN.match(line)
        if match is None:
            continue

        time_value = float(match.group("time").replace(",", "."))
        if time_value >= 0:
            status = "ok"
        else:
            status = pending_reason or "failed"
        pending_reason = None

        rows.append(
            {
                "algorithm": match.group("algorithm").strip(),
                "size": int(match.group("size")),
                "time_ns": time_value if time_value >= 0 else np.nan,
                "status": status,
            }
        )

    if not rows:
        raise ValueError(f"No benchmark rows found in {path}")

    return pd.DataFrame(rows)


def curves_of(data: pd.DataFrame) -> pd.DataFrame:
    """Successful measurements only, one row per (algorithm, size), in ms."""
    ok = data[data["status"] == "ok"]
    curves = ok.groupby(["algorithm", "size"], as_index=False, sort=False)[
        "time_ns"
    ].median()
    curves["time_ms"] = curves["time_ns"] / 1e6
    return curves


def failure_notes(data: pd.DataFrame) -> dict[str, str]:
    """Short description of missing results per algorithm."""
    notes = {}
    failed = data[data["status"] != "ok"].sort_values("size")
    for algorithm, group in failed.groupby("algorithm", sort=False):
        reasons = " / ".join(dict.fromkeys(group["status"]))
        notes[algorithm] = f"no result from {int(group['size'].min())} ({reasons})"
    return notes


# --------------------------------------------------------------------------- #
# Plotting
# --------------------------------------------------------------------------- #

def build_styles(algorithms: list[str]) -> dict[str, tuple]:
    """One fixed colour and marker per algorithm, shared by every graph."""
    cmap = plt.get_cmap("tab10")
    return {
        name: (cmap(i % 10), MARKERS[i % len(MARKERS)])
        for i, name in enumerate(algorithms)
    }


def plot_dataset(
    axis,
    data: pd.DataFrame,
    styles: dict[str, tuple],
    title: str,
    ylim: tuple[float, float],
    guides: bool,
    notes_in_legend: bool,
    log_x: bool,
) -> None:
    curves = curves_of(data)
    notes = failure_notes(data)
    sizes = sorted(data["size"].unique())

    for algorithm in styles:
        values = curves[curves["algorithm"] == algorithm].sort_values("size")
        if values.empty:
            continue

        label = algorithm
        if notes_in_legend and algorithm in notes:
            label += f"  [{notes[algorithm]}]"

        color, marker = styles[algorithm]
        axis.plot(
            values["size"],
            values["time_ms"],
            color=color,
            marker=marker,
            markersize=5,
            linewidth=1.6,
            label=label,
        )

    if guides and not curves.empty:
        # Reference growth rates, anchored at the fastest point of the smallest size.
        x = np.array(sizes, dtype=float)
        at_start = curves[curves["size"] == sizes[0]]["time_ms"]
        y0 = (at_start if not at_start.empty else curves["time_ms"]).min()
        x0 = x[0]
        n_log_n = y0 * (x * np.log2(x)) / (x0 * np.log2(x0))
        n_squared = y0 * (x / x0) ** 2
        axis.plot(x, n_log_n, color="0.55", linestyle=":", linewidth=1.3,
                  label=r"reference: $n \log n$")
        axis.plot(x, n_squared, color="0.30", linestyle="--", linewidth=1.0,
                  label=r"reference: $n^2$")

    axis.set_title(title)
    axis.set_xlabel("Input size n")
    if log_x:
        axis.set_xscale("log", base=2)
        axis.set_xticks(sizes)
        axis.xaxis.set_minor_locator(NullLocator())
        axis.tick_params(axis="x", labelrotation=45)
    else:
        axis.set_xlim(0, max(sizes) * 1.03)
    axis.xaxis.set_major_formatter(FuncFormatter(lambda v, _: f"{int(v):,}".replace(",", " ")))

    axis.set_yscale("log")
    axis.set_ylim(*ylim)
    axis.yaxis.set_major_formatter(FuncFormatter(lambda v, _: f"{v:g}"))
    axis.grid(True, which="both", linestyle="--", alpha=0.3)


def save_single(
    name: str,
    data: pd.DataFrame,
    title: str,
    styles: dict[str, tuple],
    ylim: tuple[float, float],
    guides: bool,
    log_x: bool,
    output: Path,
    dpi: int,
) -> None:
    figure, axis = plt.subplots(figsize=(12, 6.5), constrained_layout=True)
    plot_dataset(axis, data, styles, title, ylim, guides, True, log_x)
    axis.set_ylabel("Average time per sort [ms]")
    axis.legend(loc="lower right", fontsize=8, framealpha=0.9)
    figure.savefig(output, dpi=dpi, bbox_inches="tight")
    plt.close(figure)


def save_combined(
    datasets: list[tuple[str, pd.DataFrame, str]],
    styles: dict[str, tuple],
    ylim: tuple[float, float],
    guides: bool,
    log_x: bool,
    output: Path,
    dpi: int,
) -> None:
    count = len(datasets)
    figure, axes = plt.subplots(
        1, count, figsize=(5.6 * count, 6.8), sharey=True, constrained_layout=True
    )
    axes = np.atleast_1d(axes)

    for axis, (_, data, title) in zip(axes, datasets):
        plot_dataset(axis, data, styles, title, ylim, guides, False, log_x)

        notes = failure_notes(data)
        if notes:
            text = "\n".join(f"{name}: {note}" for name, note in notes.items())
            axis.text(
                0.0, -0.22, text, transform=axis.transAxes, va="top", ha="left",
                fontsize=7, color="0.25",
            )

    axes[0].set_ylabel("Average time per sort [ms]")
    handles, labels = axes[0].get_legend_handles_labels()
    figure.legend(
        handles, labels, loc="outside lower right", ncol=min(len(labels), 6),
        fontsize=9, frameon=False,
    )
    figure.savefig(output, dpi=dpi, bbox_inches="tight")
    plt.close(figure)


def print_summary(name: str, data: pd.DataFrame) -> None:
    """Best / worst algorithm on valid measurements at the largest comparable size."""
    curves = curves_of(data)
    total = data["algorithm"].nunique()
    finished = curves.groupby("size")["algorithm"].nunique()
    complete = finished[finished == total]

    cases = [
        ("largest size where ALL algorithms finished",
         int(complete.index.max()) if not complete.empty else None),
        ("largest size with any valid result", int(curves["size"].max())),
    ]

    print(f"\n[{name}]")
    for label, size in cases:
        if size is None:
            print(f"  {label}: none")
            continue
        ranking = curves[curves["size"] == size].sort_values("time_ns")
        print(f"  {label}: n = {size} ({len(ranking)}/{total} algorithms)")
        print(f"    best : {ranking.iloc[0]['algorithm']}")
        print(f"    worst: {ranking.iloc[-1]['algorithm']}")
        for _, row in ranking.iterrows():
            print(f"      {row['algorithm']:<30} {row['time_ms']:10.3f} ms")


def save_csv(data: pd.DataFrame, output: Path) -> None:
    """Wide table (algorithm x size, ns); failed measurements are left empty."""
    curves = curves_of(data)
    table = curves.pivot(index="algorithm", columns="size", values="time_ns")
    order = list(dict.fromkeys(data["algorithm"]))
    table.reindex(order).round(0).to_csv(output)


# --------------------------------------------------------------------------- #
# CLI
# --------------------------------------------------------------------------- #

def main() -> None:
    parser = argparse.ArgumentParser(
        description="Create performance graphs (log scale on the y axis) from Maven benchmark output."
    )
    parser.add_argument("--ascending", type=Path)
    parser.add_argument("--descending", type=Path)
    parser.add_argument("--random", dest="random_data", type=Path)
    parser.add_argument("--output-dir", type=Path, default=Path("performance_graphs"))
    parser.add_argument("--format", default="png", choices=["png", "svg", "pdf"])
    parser.add_argument("--dpi", type=int, default=200)
    parser.add_argument("--no-combined", action="store_true",
                        help="do not create the side-by-side overview graph")
    parser.add_argument("--guides", action="store_true",
                        help="draw n log n and n^2 reference lines")
    parser.add_argument("--log-x", action="store_true",
                        help="also use a log2 x axis (default: linear x, log y only)")
    parser.add_argument("--independent-y", action="store_true",
                        help="scale each graph separately instead of sharing the y axis")
    parser.add_argument("--csv", action="store_true",
                        help="also write the results as CSV tables")
    args = parser.parse_args()

    inputs = [
        ("ascending", args.ascending, "Ascending data"),
        ("descending", args.descending, "Descending data"),
        ("random", args.random_data, "Random data"),
    ]
    selected = [(name, path, title) for name, path, title in inputs if path]
    if not selected:
        parser.error("provide at least one of --ascending, --descending, or --random")

    datasets = [(name, read_results(path), title) for name, path, title in selected]

    algorithms = list(dict.fromkeys(
        algorithm for _, data, _ in datasets for algorithm in data["algorithm"]
    ))
    styles = build_styles(algorithms)

    all_times = pd.concat([curves_of(data)["time_ms"] for _, data, _ in datasets])
    shared_ylim = (all_times.min() / 1.6, all_times.max() * 1.6)

    args.output_dir.mkdir(parents=True, exist_ok=True)
    guides = args.guides

    for name, data, title in datasets:
        ylim = shared_ylim
        if args.independent_y:
            times = curves_of(data)["time_ms"]
            ylim = (times.min() / 1.6, times.max() * 1.6)

        output = args.output_dir / f"{name}.{args.format}"
        save_single(name, data, f"Sorting performance - {title.lower()}",
                    styles, ylim, guides, args.log_x, output, args.dpi)
        print(f"Created {output}")
        print_summary(name, data)

        if args.csv:
            csv_path = args.output_dir / f"{name}.csv"
            save_csv(data, csv_path)
            print(f"Created {csv_path}")

    if len(datasets) > 1 and not args.no_combined:
        output = args.output_dir / f"combined.{args.format}"
        save_combined(datasets, styles, shared_ylim, guides, args.log_x, output, args.dpi)
        print(f"Created {output}")


if __name__ == "__main__":
    main()
