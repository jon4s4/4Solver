package jonas.solver.domain.model;

public class String {
    Card firstCard;
    Card secondCard;
    
    public String(Card firstCard, Card secondCard) {
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
