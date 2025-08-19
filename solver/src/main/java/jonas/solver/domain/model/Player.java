package jonas.solver.domain.model;

public class Player {
    Action action;
    String hand;
    Position position;
    int stackSize;

    public Player(Position position, Action action, int stack) {
        this.position = position;
        this.action = action;
        this.stackSize = stack;
    }

    public Action getAction() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }
    public String getHand() {
        return hand;
    }
    public void setHand(String hand) {
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

    public void raise(int raisesInRound){
        double amount = switch (raisesInRound){
            case 0 -> 2.3;
            case 1 -> 8.0;
            case 2 -> 20.0; 
            default -> 20.0;
        };
        if(stackSize > amount){
            stackSize -= amount;
        }else{
            throw new IllegalArgumentException("Nicht genug Chips für einen Allin");
        }
    }

    public void allin() {
        stackSize = 0;
    }

    public void call(int raisesInRound) {
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
    
    public void resetChips(int defaultStack){
        this.stackSize = defaultStack;
    }

}
