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
        currentPlayerIndex = 0;
    }

    public void processAction(String positionName, String action, int stack){
        int selectedIndex = -1;
        // side effect on selectedIndex
        selectedIndex = setPlayerAction(positionName, action, selectedIndex);
        selectedIndex = setFoldToSkippedPlayers(selectedIndex);
        selectedIndex = revokeActionsOfFollowingPlayers(selectedIndex);

        currentPlayerIndex = selectedIndex + 1;
        if(currentPlayerIndex >= allPlayers.size()) {
            currentPlayerIndex = allPlayers.size() - 1;
        }
    }

    private int revokeActionsOfFollowingPlayers(int selectedIndex) {
        for(int i = selectedIndex + 1; i < allPlayers.size(); i++){
            Player p = allPlayers.get(i);
            p.setAction(null);
        }
        return selectedIndex;
    }

    private int setFoldToSkippedPlayers(int selectedIndex) {
        for(int i = 0; i < selectedIndex; i++){
            Player p = allPlayers.get(i);
            if(p.getAction() == null) {
                p.setAction(Action.FOLD);
            }
        }
        return selectedIndex;
    }

    private int setPlayerAction(String positionName, String action, int selectedIndex) {
        Position position = Position.valueOf(positionName.toUpperCase());

        for(int i = 0; i < allPlayers.size(); i++){
            Player p = allPlayers.get(i);
            if(p.getPosition() == position){
                selectedIndex = i;
                p.setAction(Action.valueOf(action.toUpperCase()));
                switch(p.getAction()){
                    case RAISE -> p.raise();
                    case ALLIN -> p.allin();
                    case CALL -> p.call();
                    case FOLD -> p.fold();
                }
                break;
            }
        }
        return selectedIndex;
    }

}
