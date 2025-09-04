package jonas.solver.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jonas.solver.application.HandRange;
import jonas.solver.domain.service.RangeService;

@Controller
public class RangesController {
    private final RangeService rangeService;

    public RangesController(RangeService rangeService) {
        this.rangeService = rangeService;
    }

    @ModelAttribute("ranks")
    public List<String> getRanks(){
        List<String> ranks = List.of("A","K","Q","J","T","9","8","7","6","5","4","3","2");

        return ranks;
    }
    
    @GetMapping("ranges")
    public String rangesInit(Model model){
        Map<String, HandRange> hands = rangeService.loadRanges("utg_vs_bb.csv");
        System.out.println("Geladene Keys: " + hands.keySet());
        model.addAttribute("hands", hands);
        model.addAttribute("matchup", "utg_vs_bb");

        return "ranges";
    }

    @GetMapping("/ranges/{filename}")
    public String showRanges(Model model, @PathVariable("filename") String filename){
        Map<String, HandRange> hands = rangeService.loadRanges(filename + ".csv");
        model.addAttribute("hands", hands);
        model.addAttribute("matchup", filename);
        return "ranges";
    }

    @GetMapping("/ranges/default")
    public String showDefault(Model model){
        Map<String, HandRange> hands = rangeService.loadInitRanges();
        model.addAttribute("hands", hands);
        model.addAttribute("matchup", "default matchup");

        return "ranges";
    }
}
