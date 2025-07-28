package jonas.solver.domain.model;

public class Player {
    Action action;
    Hand hand;
    Position position;
    int stackSize;
    int raisesInRound;

    public Player(Position position, Action action, int stack) {
        this.position = position;
        this.action = action;
        this.stackSize = stack;
    }

    public int getRaisesInRound() {
        return raisesInRound;
    }

    public void setRaisesInRound(int raisesInRound) {
        this.raisesInRound = raisesInRound;
    }

    public Action getAction() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }
    public Hand getHand() {
        return hand;
    }
    public void setHand(Hand hand) {
        this.hand = hand;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public void raise(){
        double amount = switch (raisesInRound){
            case 0 -> 2.3;
            case 1 -> 8.0;
            case 2 -> 20.0;
            default -> stackSize;
        };
        if(stackSize > amount){
            stackSize -= amount;
            raisesInRound++;
        }else{
            throw new IllegalArgumentException("Nicht genug Chips für einen Allin");
        }
    }

    public void allin() {
        stackSize = 0;
    }

    public void call() {
        double amount = switch (raisesInRound){
            case 0 -> 1;
            case 1 -> 2.3;
            case 2 -> 8;
            case 3 -> 20;
            default -> stackSize;
        };
        if (stackSize > amount) {
            stackSize -= amount;
        }
    }

    public void fold() {
    }
    

}
