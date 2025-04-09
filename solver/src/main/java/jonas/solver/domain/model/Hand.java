package jonas.solver.domain.model;

import java.util.Map;

public class Hand {
    Map<String, Color> firstCard;
    Map<String, Color> secondCard;
    
    public Hand(Map<String, Color> firstCard, Map<String, Color> secondCard) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }
    public Map<String, Color> getfirstCard() {
        return firstCard;
    }
    public void setfirstCard(Map<String, Color> firstCard) {
        this.firstCard = firstCard;
    }
    public Map<String, Color> getsecondCard() {
        return secondCard;
    }
    public void setsecondCard(Map<String, Color> secondCard) {
        this.secondCard = secondCard;
    }

    
}
