package jonas.solver.application;

public class RangeEntry {
    private String position;
    private String hand;
    private String action;

    public RangeEntry(String position, String hand, String action) {
        this.position = position;
        this.hand = hand;
        this.action = action;
    }

    public String getPosition() { return position; }
    public String getHand() { return hand; }
    public String getAction() { return action; }
}
