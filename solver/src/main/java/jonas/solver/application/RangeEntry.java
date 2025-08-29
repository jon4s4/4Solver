package jonas.solver.application;

public class RangeEntry {
    private String hand;
    private String action;
    private double ev;

    public RangeEntry(String hand, String action, double ev) {
        this.hand = hand;
        this.action = action;
        this.ev = ev;
    }

    public String getHand() { return hand; }
    public String getAction() { return action; }
    public double getEv() { return ev; }
}

