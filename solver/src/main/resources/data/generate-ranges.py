import csv
import os

ranks = ["A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"]

OUTPUT_DIR = "ranges"

def generate_starting_hands():
    hands = []
    for i, r1 in enumerate(ranks):
        for j, r2 in enumerate(ranks):
            if i == j:
                hands.append(r1 + r2)
            elif i < j:
                hands.append(r1 + r2 + "s")
                hands.append(r1 + r2 + "o")
    return hands

def classify_hand(hand):
    """Einfache Logik für Action + EV anhand der Hand."""
    premiums = {"AA","KK","QQ","JJ","TT","AKs","AQs","AKo"}
    midpairs = {"99","88","77","66"}
    broadways = {"KQs","QJs","JTs","T9s","KQo"}

    if hand in premiums:
        return round(3.0 + (hash(hand) % 20) / 10, 2), "RAISE"
    elif hand in midpairs or hand in broadways:
        return round((hash(hand) % 25) / 10, 2), "CALL"
    else:
        return round(-2.0 + (hash(hand) % 15) / 10, 2), "FOLD"

def generate_csv(filename):
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    filepath = os.path.join(OUTPUT_DIR, filename)

    hands = generate_starting_hands()

    with open(filepath, mode="w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow(["Hand", "EV", "Action"])

        for hand in hands:
            ev, action = classify_hand(hand)
            writer.writerow([hand, ev, action])

    print(f"Datei erstellt: {filepath}")

if __name__ == "__main__":
    matchups = ["utg_vs_utg1", "utg_vs_hj", "utg_vs_lj", "utg_vs_co", "utg_vs_btn", "utg_vs_sb", "utg_vs_bb",
                "utg1_vs_hj", "utg1_vs_lj", "utg1_vs_co", "utg1_vs_btn", "utg1_vs_sb", "utg1_vs_bb",
                "hj_vs_lj", "hj_vs_co", "hj_vs_btn", "hj_vs_sb", "hj_vs_bb",
                "lj_vs_co", "lj_vs_btn", "lj_vs_sb", "lj_vs_bb",
                "co_vs_btn", "co_vs_sb", "co_vs_bb",
                "btn_vs_sb", "btn_vs_bb", 
                "sb_vs_bb"]
    for matchup in matchups:
        generate_csv(f"{matchup}.csv")
