package jonas.solver.web;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import jonas.solver.domain.model.Position;
import jonas.solver.domain.service.GameService;

@Controller
public class PokerController {
    private final GameService gameService;
    
    public PokerController(GameService gameService) {
        this.gameService = gameService;
    }

    @ModelAttribute("positionen")
    public List<Position> allPositions(){
        return gameService.getPositionen();
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

}
