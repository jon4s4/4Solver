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
            allPlayers.add(new Player(position, null, 100));
        }
    }

    public List<Player> getPlayers(){
        return allPlayers;
    }

    public Player getPlayerAtI(int index){
        return allPlayers.get(index);
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

    public void processAction(String positionName, String action, int stack){
        // If player does Action twice, wrong stackSize might be set to all other players
        int selectedIndex = -1;
        // side effect on selectedIndex
        selectedIndex = setPlayerAction(positionName, action, selectedIndex);
        selectedIndex = setFoldToSkippedPlayers(selectedIndex);
        selectedIndex = revokeActionsOfFollowingPlayers(selectedIndex, 100);

        currentPlayerIndex = selectedIndex;
        if(currentPlayerIndex >= allPlayers.size()) {
            currentPlayerIndex = allPlayers.size() - 1;
        }
    }

    private int revokeActionsOfFollowingPlayers(int selectedIndex, int stack) {
        for(int i = selectedIndex + 1; i < allPlayers.size(); i++){
            Player p = getPlayerAtI(i);
            if(p.getAction() == Action.RAISE || p.getAction() == Action.ALLIN){
                raisesInRound--;
                p.resetChips(stack);
            }
            p.resetChips(stack);
            p.setAction(null);
        }
        return selectedIndex;
    }

    private int setFoldToSkippedPlayers(int selectedIndex) {
        for(int i = 0; i < selectedIndex; i++){
            Player p = getPlayerAtI(i);
            if(p.getAction() == null) {
                p.setAction(Action.FOLD);
            }
        }
        return selectedIndex;
    }

    private int setPlayerAction(String positionName, String action, int selectedIndex) {
        Position position = Position.valueOf(positionName.toUpperCase());

        for(int i = 0; i < allPlayers.size(); i++){
            Player p = getPlayerAtI(i);
            if(p.getPosition() == position){
                selectedIndex = i;
                if (action == null) return selectedIndex;
                p.setAction(Action.valueOf(action.toUpperCase()));
                switch(p.getAction()){
                    case RAISE -> {p.raise(raisesInRound); raisesInRound++;}
                    case ALLIN -> {p.allin(); raisesInRound++;}
                    case CALL -> p.call(raisesInRound);
                    case FOLD -> p.fold();
                }
                return selectedIndex;
            }
        }
        return selectedIndex;
    }

    public Player getPlayerByPosition(String position) {
        for(Player p: allPlayers){
            if (p.getPosition().name() == position.toUpperCase()) { // maybe bug here
                return p;
            }
        }
        return null;
    }

}
