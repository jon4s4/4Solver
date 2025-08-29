package jonas.solver.application;

public class RangeEntry {
    private String hand;
    private String action;
    private double ev;

    public RangeEntry(String hand, double ev, String action) {
        this.hand = hand;
        this.ev = ev;
        this.action = action;
    }

    public String getHand() { return hand; }
    public double getEv() { return ev; }
    public String getAction() { return action; }
}

