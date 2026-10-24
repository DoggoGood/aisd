import matplotlib.pyplot as plt


DATA = {
    "modular_5101": {
        7: 3, 8: 6, 9: 17, 10: 31, 11: 66, 12: 106, 13: 151,
        14: 227, 15: 282, 16: 379, 17: 398, 18: 440, 19: 498,
        20: 435, 21: 417, 22: 377, 23: 317, 24: 267, 25: 216,
        26: 136, 27: 120, 28: 74, 29: 54, 30: 35, 31: 21,
        32: 15, 33: 7, 34: 1, 35: 3, 36: 2,
    },
    "modular_2097169": {
        0: 1999496, 1: 95387, 2: 2245, 3: 41, 6: 1,
    },
    "multiplicative_5101": {
        6: 1, 7: 1, 8: 9, 9: 13, 10: 47, 11: 68, 12: 106,
        13: 164, 14: 205, 15: 298, 16: 363, 17: 415, 18: 459,
        19: 438, 20: 461, 21: 418, 22: 388, 23: 313, 24: 238,
        25: 202, 26: 144, 27: 96, 28: 84, 29: 56, 30: 41,
        31: 32, 32: 14, 33: 15, 34: 7, 35: 2, 36: 2, 37: 1,
    },
    "multiplicative_2097169": {
        0: 1999488, 1: 95395, 2: 2256, 3: 27, 4: 3,
    },
}

TITLES = {
    "modular_5101": "Haszowanie modularne, rozmiar tablicy 5101",
    "modular_2097169": "Haszowanie modularne, rozmiar tablicy 2097169",
    "multiplicative_5101": "Haszowanie przez mnożenie, rozmiar tablicy 5101",
    "multiplicative_2097169": "Haszowanie przez mnożenie, rozmiar tablicy 2097169",
}


for name, values in DATA.items():
    lengths = list(values)
    counts = list(values.values())

    plt.figure(figsize=(8, 4.5))
    plt.bar(lengths, counts, width=0.8, color="#4472C4", edgecolor="black")
    plt.xlabel("Długość łańcucha")
    plt.ylabel("Liczba łańcuchów")
    plt.title(TITLES[name])
    plt.grid(axis="y", alpha=0.3)
    plt.tight_layout()
    plt.savefig(f"images/task2_{name}.png", dpi=200)
    plt.close()
