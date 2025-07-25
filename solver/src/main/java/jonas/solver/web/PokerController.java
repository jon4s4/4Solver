package jonas.solver.web;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jonas.solver.domain.model.Action;
import jonas.solver.domain.model.Player;
import jonas.solver.domain.model.Position;
import jonas.solver.domain.service.GameService;

@Controller
@SessionAttributes("allPlayers")
public class PokerController {
    private final GameService gameService;
    
    public PokerController(GameService gameService) {
        this.gameService = gameService;
    }

    @ModelAttribute("allPlayers")
    public List<Player> allPositions(){
        return gameService.getPlayers();
    }

    @GetMapping("/")
    public String index(){
        
        return "hello";
    }

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model){
        model.addAttribute("name", token.getPrincipal().getName());
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        return "profile";
    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("format", "");
        model.addAttribute("solution", "");
        model.addAttribute("players", 0);

        return "main";
    }

    @GetMapping("/table/format")
    public String changeFormat(@RequestParam(value = "format", required = false) String format,
                                @RequestParam(value = "solution", required = false) String solution,
                                @RequestParam(value= "players", required = false) Integer players,
                                Model model){
        
        model.addAttribute("format", format);
        model.addAttribute("solution", solution);
        model.addAttribute("players", players);

        return "main";
    }

    @PostMapping("/main/action")
    public String makeAction(@ModelAttribute("allPlayers") List<Player> allPlayers, @RequestParam("action") String action,
    @RequestParam("stack") int stack, @RequestParam("position") String positionName, Model model){
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
                allPlayers.get(i).setAction(Action.FOLD);
            }
        }
        model.addAttribute("allPlayers", allPlayers);

        for(Player p: allPlayers){
            System.out.println("Action: " + p.getAction());
            System.out.println("Position: " + p.getPosition());
        }


        return "main";
    }

}
