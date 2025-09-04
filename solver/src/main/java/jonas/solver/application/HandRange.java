package jonas.solver.application;

import jonas.solver.domain.model.Action;

public class HandRange {
    double ev;
    Action action;
    
    public HandRange(double ev, Action action) {
        this.ev = ev;
        this.action = action;
    }

    public double getEv() {
        return ev;
    }
    public void setEv(double ev) {
        this.ev = ev;
    }
    public Action getAction() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }

}
