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
            allPlayers.add(new Player(position, null));
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

    public void processAction(String positionName, String action){
        Position position = Position.valueOf(positionName.toUpperCase());
        int selectedIndex = -1;

        for(int i = 0; i < allPlayers.size(); i++){
            Player p = allPlayers.get(i);
            if(p.getPosition() == position){
                selectedIndex = i;
                p.setAction(Action.valueOf(action.toUpperCase()));
                break;
            }
        }

        for(int i = 0; i < selectedIndex; i++){
            Player p = allPlayers.get(i);
            if(p.getAction() == null) {
                p.setAction(Action.FOLD);
            }
        }

        for(int i = selectedIndex + 1; i < allPlayers.size(); i++){
            Player p = allPlayers.get(i);
            p.setAction(null);
        }

        currentPlayerIndex = selectedIndex + 1;
        if(currentPlayerIndex >= allPlayers.size()) {
            currentPlayerIndex = allPlayers.size() - 1;
        }
    }

}
