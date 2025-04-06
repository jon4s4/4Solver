package jonas.solver.domain.model;

import java.util.Map;

public class Hand {
    Map<String, Color> firstHand;
    Map<String, Color> secondHand;
    
    public Hand(Map<String, Color> firstHand, Map<String, Color> secondHand) {
        this.firstHand = firstHand;
        this.secondHand = secondHand;
    }
    public Map<String, Color> getFirstHand() {
        return firstHand;
    }
    public void setFirstHand(Map<String, Color> firstHand) {
        this.firstHand = firstHand;
    }
    public Map<String, Color> getSecondHand() {
        return secondHand;
    }
    public void setSecondHand(Map<String, Color> secondHand) {
        this.secondHand = secondHand;
    }

    
}
