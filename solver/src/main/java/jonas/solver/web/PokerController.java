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

import jonas.solver.domain.model.Player;
import jonas.solver.domain.service.GameService;

@Controller
@SessionAttributes("allPlayers")
public class PokerController {
    private final GameService gameService;
    
    public PokerController(GameService gameService) {
        this.gameService = gameService;
    }

    @ModelAttribute("allPlayers")
    public List<Player> allPlayers(){
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
        model.addAttribute("currentPlayer", gameService.getCurrentPlayer());
        model.addAttribute("allPlayers", gameService.getPlayers());

        return "mainPage";
    }


    @GetMapping("/table/format")
    public String changeFormat(@RequestParam(value = "format", required = false) String format,
                                @RequestParam(value = "solution", required = false) String solution,
                                @RequestParam(value= "players", required = false) Integer players,
                                Model model){
        
        model.addAttribute("format", format);
        model.addAttribute("solution", solution);
        model.addAttribute("players", players);

        return "mainPage";
    }

    @PostMapping("/main/action")
    public String makeAction(@RequestParam(value = "action", required=false) String action,
    /*@RequestParam("stack") int stack */ @RequestParam("position") String positionName, Model model){ // TODO: handle label-button click correctly

        gameService.processAction(positionName, action); // complete processing stack

        model.addAttribute("allPlayers", gameService.getPlayers());
        model.addAttribute("currentPlayer", gameService.getCurrentPlayer());

        return "mainPage";
    }

    @PostMapping("/main/reset")
    public String resetActions(){
        gameService.resetActions();

        return "mainPage";
    }

    @GetMapping("/vier")
    public String test(){
        return "test";
    }

}
