# generate_ev.py
import eval7
import itertools
import csv
import math

RANKS = ["A","K","Q","J","T","9","8","7","6","5","4","3","2"]

# erzeugt die 169-Kategorien
def generate_hands():
    hands = []
    for i,r1 in enumerate(RANKS):
        for j,r2 in enumerate(RANKS):
            if i == j:
                hands.append(r1+r2)
            elif i < j:
                hands.append(r1+r2+'s')
                hands.append(r1+r2+'o')
    # remove duplicates and keep order (should be 169)
    seen = []
    for h in hands:
        if h not in seen:
            seen.append(h)
    return seen

# Hilfsfunktionen: expandiere Kategorie in konkrete Combos (z.B. AKs -> AhKh, AhKd, ...)
def expand_category(cat):
    # Return list of 2-card combos, each combo as tuple of two eval7.Card
    ranks = ['2','3','4','5','6','7','8','9','T','J','Q','K','A']
    suits = ['h','d','c','s']
    # map our rank letter to eval7 format (we'll use same letters)
    # build full deck
    deck = [r+s for r in list("AKQJT98765432") for s in suits]
    # helper to generate combos
    combos = []
    if len(cat) == 2: # pair e.g. "AA"
        r = cat[0]
        cards = [r+s for s in suits]
        for i in range(len(cards)):
            for j in range(i+1,len(cards)):
                combos.append((eval7.Card(cards[i]), eval7.Card(cards[j])))
    else:
        r1 = cat[0]; r2 = cat[1]; suf = cat[2].lower()
        if suf == 's':
            # suited: same suit, different ranks
            for s in suits:
                combos.append((eval7.Card(r1+s), eval7.Card(r2+s)))
        else:
            # offsuit: different suits
            for s1 in suits:
                for s2 in suits:
                    if s1 == s2: continue
                    combos.append((eval7.Card(r1+s1), eval7.Card(r2+s2)))
    return combos

# Equity: simulate many random deals with remaining deck
def equity_vs_range(my_hand_cat, opp_range_cats, trials=2000):
    # expand my hand to combos and pick a representative combo sampling
    my_combos = expand_category(my_hand_cat)
    # expand opponent range combos (flatten)
    opp_combos = []
    for cat, weight in opp_range_cats.items():
        combos = expand_category(cat)
        # weight could be frequency (0..1). For now, we treat weight as probability per category.
        # We'll append combos multiple times proportional to weight (approx)
        count = max(1, int(round(weight * 100)))  # heuristic
        for _ in range(count):
            opp_combos.extend(combos)
    if len(opp_combos) == 0:
        raise ValueError("Opponent range empty")

    wins = 0
    ties = 0
    total = 0

    for t in range(trials):
        # choose random specific combos for hero and villain while avoiding card collisions
        import random
        # pick hero combo
        hero_combo = random.choice(my_combos)
        # build deck excluding hero cards
        used = {str(hero_combo[0]), str(hero_combo[1])}
        # pick villain combo from opp_combos until no overlap
        attempts = 0
        while True:
            opp_combo = random.choice(opp_combos)
            attempts += 1
            if str(opp_combo[0]) not in used and str(opp_combo[1]) not in used:
                break
            if attempts > 1000:
                # fallback: choose any distinct random two cards
                deck = [c for c in eval7.Deck().cards if str(c) not in used]
                opp_combo = (deck[0], deck[1])
                break

        # simulate random board
        deck = eval7.Deck()
        # remove hero and opp cards
        deck.cards = [c for c in deck.cards if str(c) not in {str(hero_combo[0]), str(hero_combo[1]), str(opp_combo[0]), str(opp_combo[1])}]
        board = []
        # draw 5 board cards
        for _ in range(5):
            board.append(deck.deal(1)[0])
        # evaluate
        hscore = eval7.evaluate(board + [hero_combo[0], hero_combo[1]])
        oscore = eval7.evaluate(board + [opp_combo[0], opp_combo[1]])
        if hscore > oscore:
            wins += 1
        elif hscore == oscore:
            ties += 1
        total += 1

    eq = (wins + ties * 0.5) / total
    return eq

def compute_ev_for_hand(hand_cat, opp_range_cats, P=1.5, call_cost=1.0, raise_extra=2.0, opp_fold_prob=0.4):
    e = equity_vs_range(hand_cat, opp_range_cats, trials=1000)
    # EV fold = 0 (relative)
    EV_fold = 0.0
    # EV call approximation
    EV_call = e * (P + call_cost) - call_cost
    # EV raise
    # Wenn Gegner foldet, du gewinnst Pot P
    # Wenn Gegner calls, pot grows by raise_extra (hero invest raise_extra)
    EV_if_called = e * (P + raise_extra) - raise_extra
    EV_raise = opp_fold_prob * P + (1 - opp_fold_prob) * EV_if_called
    return {'hand': hand_cat, 'equity': round(e,4), 'EV_fold': round(EV_fold,4),
            'EV_call': round(EV_call,4), 'EV_raise': round(EV_raise,4)}

# Beispiel: einfache Gegner-Range (dictionary category -> weight)
# weight: relative frequency (0..1) of including this category in their range
SAMPLE_RANGE = {
    '22': 0.02, '33': 0.02, '44': 0.02, '55': 0.03, '66': 0.04, '77': 0.04,
    '88': 0.05, '99': 0.06, 'TT': 0.07, 'JJ': 0.08, 'QQ': 0.08, 'KK': 0.06, 'AA': 0.05,
    'AKs': 0.02, 'AQs': 0.03, 'AJs': 0.03, 'KQs': 0.02,
    'AKo': 0.02, 'AQo': 0.03, 'AJo': 0.02,
    # rest assumed zero
}

if __name__ == '__main__':
    ALL_HANDS = generate_hands()
    out = []
    for h in ALL_HANDS:
        res = compute_ev_for_hand(h, SAMPLE_RANGE, P=1.5, call_cost=1.0, raise_extra=2.0, opp_fold_prob=0.35)
        out.append(res)
        print(res['hand'], res['equity'], res['EV_call'], res['EV_raise'])
    # write CSV
    with open('ev_output.csv','w',newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['Hand','Equity','EV_fold','EV_call','EV_raise'])
        for r in out:
            writer.writerow([r['hand'], r['equity'], r['EV_fold'], r['EV_call'], r['EV_raise']])
    print("Done")
