package jonas.solver.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import jonas.solver.domain.model.Action;
import jonas.solver.domain.model.Player;
import jonas.solver.domain.model.Position;

@Service
public class GameService {
    private List<Player> allPlayers = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private int raisesInRound = 0;

    public GameService() {
        for(Position position: Position.values()){
            allPlayers.add(new Player(position, null, 100)); // 100 default StackSize, TODO: change if custom amount is implemented
        }
    }

    public List<Player> getPlayers(){
        return allPlayers;
    }

    public Player getPlayerAtI(int index){
        return allPlayers.get(index);
    }

    public Player getPlayerByPosition(String position) {
        for(int i = 0; i < allPlayers.size(); i++){
            if(getPlayerAtI(i).getPosition().name().equalsIgnoreCase(position)){
                currentPlayerIndex = i;
                return getPlayerAtI(i);
            }
        }
        return null;
    }

    public Player getCurrentPlayer(){
        return allPlayers.get(currentPlayerIndex);
    }

    public List<Position> getPositions(){
        Position[] positions = Position.values();
        Vector<Position> allPositions = new Vector<>();
        for(Position p: positions){
            allPositions.addLast(p);
        }
        return allPositions;
    }

    public void resetActions(){
        for(Player p: allPlayers){
            p.setAction(null);
        }
        raisesInRound = 0;
        currentPlayerIndex = 0;
    }

    public void processAction(String positionName, String action /*, int stack */){
        Player targetPlayer = getPlayerByPosition(positionName);

        setPlayerAction(targetPlayer, action);
        setFoldToSkippedPlayers();
        revokeActionsOfFollowingPlayers(100);

        if(currentPlayerIndex >= allPlayers.size()) {
            return; // TODO: handle correctly
        }
    }

    private void revokeActionsOfFollowingPlayers(int stack) {
        for(int i = currentPlayerIndex + 1; i < allPlayers.size(); i++){
            Player p = getPlayerAtI(i);
            if(p.getAction() == Action.RAISE || p.getAction() == Action.ALLIN){
                raisesInRound--;
                p.resetChips(stack);
            }
            p.resetChips(stack);
            p.setAction(null);
        }
    }

    private void setFoldToSkippedPlayers() {
        for(int i = 0; i < currentPlayerIndex; i++){
            Player p = getPlayerAtI(i);
            if(p.getAction() == null) {
                p.setAction(Action.FOLD);
            }
        }
    }

    private void setPlayerAction(Player targetPlayer, String action) {
        Position position = targetPlayer.getPosition();
        if(targetPlayer.getAction() != null){
            if(targetPlayer.getAction() == Action.ALLIN || targetPlayer.getAction() == Action.RAISE) raisesInRound--;
            targetPlayer.resetChips(100);
        }

        for(int i = 0; i < allPlayers.size(); i++){
            Player p = getPlayerAtI(i);
            if(p.getPosition() == position){
                currentPlayerIndex = i;
                if (action == null) return;
                p.setAction(Action.valueOf(action.toUpperCase()));
                switch(p.getAction()){
                    case RAISE -> {p.raise(raisesInRound); raisesInRound++;}
                    case ALLIN -> {p.allin(); raisesInRound++;}
                    case CALL -> p.call(raisesInRound);
                    case FOLD -> p.fold();
                }
            }
        }
    }

}
