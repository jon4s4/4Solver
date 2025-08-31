package jonas.solver.application;

import jonas.solver.domain.model.Action;

public class HandRange {
    double ev;
    String action;
    
    public HandRange(double ev, String action) {
        this.ev = ev;
        this.action = action;
    }

    public double getEv() {
        return ev;
    }
    public void setEv(double ev) {
        this.ev = ev;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

}
