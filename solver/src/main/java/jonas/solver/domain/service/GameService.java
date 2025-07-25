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
        // handle out of bounds exception after action of BB
        // handle next Actions after BB done and Flop coming

        Position position = Position.valueOf(positionName.toUpperCase());
        int selectedIndex = -1;

        for(int i = 0; i < allPlayers.size(); i++){
            Player p = allPlayers.get(i);
            if(p.getPosition() == position){
                selectedIndex = i;
                p.setAction(Action.valueOf(action.toUpperCase()));
            }
        }
        for(int i = 0; i < selectedIndex; i++){
            if(allPlayers.get(i).getAction() == null){
                currentPlayerIndex++;
                allPlayers.get(i).setAction(Action.FOLD);
            }
        }
        currentPlayerIndex++;
        if(currentPlayerIndex >= allPlayers.size()) currentPlayerIndex--; // temp fix
    }
}
