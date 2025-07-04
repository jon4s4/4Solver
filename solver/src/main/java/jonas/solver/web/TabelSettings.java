package jonas.solver.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jonas.solver.domain.model.Position;

@Controller
@RequestMapping("/table")
public class TabelSettings {
    @ModelAttribute("Positionen")
    public List<Position> allePositions(){
        return List.of(Position.UTG, Position.LJ, Position.HJ, Position.CO,
        Position.BTN, Position.SB, Position.BB);
    }
    
    @GetMapping("/format")
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
