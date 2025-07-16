package jonas.solver.domain.model;

public class Hand {
    Card firstCard;
    Card secondCard;
    
    public Hand(Card firstCard, Card secondCard) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }
    public Card getFirstCard() {
        return firstCard;
    }
    public Card getSecondCard() {
        return secondCard;
    }
    
}
