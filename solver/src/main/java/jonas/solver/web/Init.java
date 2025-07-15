package jonas.solver.web;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jonas.solver.domain.model.Position;

@Controller
public class Init {

    @ModelAttribute("Positionen")
    public List<Position> allePositions(){
        return List.of(Position.UTG,Position.UTG1, Position.LJ, Position.HJ, Position.CO,
        Position.BTN, Position.SB, Position.BB);
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

}
