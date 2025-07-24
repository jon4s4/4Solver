package jonas.solver.domain.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import jonas.solver.domain.model.Player;
import jonas.solver.domain.model.Position;

@Service
public class GameService {
    private List<Player> allPlayers = new ArrayList<>();
    // private final List<Position> positionen = new ArrayList<>();
    public GameService() {
        for(Position position: Position.values()){
            allPlayers.add(new Player(position, null));
        }
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

    public List<Player> getPlayers(){
        return allPlayers;
    }
}
