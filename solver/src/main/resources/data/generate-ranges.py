import csv
import os
import random

ranks = ["A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"]
actions = ["FOLD", "CALL", "RAISE"]

OUTPUT_DIR = "ranges"

def generate_starting_hands():
    """Erzeuge alle 169 Start-Hand-Kategorien (Paare, Suited, Offsuit)."""
    hands = []

    for i, r1 in enumerate(ranks):
        for j, r2 in enumerate(ranks):
            if i == j:
                # Paare
                hands.append(r1 + r2)
            elif i < j:
                # Beispiel: AK -> einmal suited, einmal offsuit
                hands.append(r1 + r2 + "s")  # suited
                hands.append(r1 + r2 + "o")  # offsuit

    return hands

def generate_csv(filename):
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    filepath = os.path.join(OUTPUT_DIR, filename)

    hands = generate_starting_hands()

    with open(filepath, mode="w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow(["Hand", "EV", "Action"])  # Header

        for hand in hands:
            ev = round(random.uniform(-2.0, 5.0), 2)
            action = random.choice(actions)
            writer.writerow([hand, ev, action])

    print(f"Datei erstellt: {filepath}")

if __name__ == "__main__":
    matchups = ["utg_vs_bb", "utg_vs_sb", "utg_vs_btn", "btn_vs_bb"]

    for matchup in matchups:
        generate_csv(f"{matchup}.csv")
