package jonas.solver.domain.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import jonas.solver.domain.model.Position;

@Service
public class GameService {
    // private final Map<Position, Player> players = new LinkedHashMap<>();
    private final List<Position> positionen = new ArrayList<>();
    public GameService() {
        // for(Position position: Position.values()){
        //     players.put(position, new Player(position));
        // }
        for(Position position: Position.values()){
            positionen.add(position);
        }
    }

    public List<Position> getPositionen(){
        return positionen;
    }

    // public Map<Position, Player> getPlayers() {
    //     return players;
    // }
    
    // public void updatePlayer(Position position, Action action, int stack){
    //     Player player = players.get(position);
    //     if(player != null){
    //         player.setAction(action);
    //         player.setStackSize(stack);
    //     }
    // }
}
