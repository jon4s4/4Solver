import csv
import random
import os

ranks = ["A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"]
suits = ["h", "d", "c", "s"]  

actions = ["FOLD", "CALL", "RAISE"]

OUTPUT_DIR = "ranges"

def generate_random_hand():
    deck = [r + s for r in ranks for s in suits]
    card1 = random.choice(deck)
    deck.remove(card1)
    card2 = random.choice(deck)
    return card1, card2

def generate_csv(filename, num_hands=100):
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    filepath = os.path.join(OUTPUT_DIR, filename)

    with open(filepath, mode="w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow(["Hand", "EV", "Action"])

        for _ in range(num_hands):
            hand = generate_random_hand()
            ev = round(random.uniform(-2.0, 5.0), 2)
            action = random.choice(actions)
            writer.writerow([f"{hand[0]} {hand[1]}", ev, action])

    print(f"Datei erstellt: {filepath}")

if __name__ == "__main__":
    
    matchups = ["utg_vs_bb", "utg_vs_sb", "utg_vs_btn", "btn_vs_bb"]

    for matchup in matchups:
        generate_csv(f"{matchup}.csv", num_hands=200)
