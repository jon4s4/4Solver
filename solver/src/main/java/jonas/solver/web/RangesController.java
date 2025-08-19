package jonas.solver.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.service.annotation.GetExchange;

import jonas.solver.application.RangeEntry;
import jonas.solver.domain.service.RangeService;

@Controller
public class RangesController {
    private final RangeService rangeService;

    public RangesController(RangeService rangeService) {
        this.rangeService = rangeService;
    }
    
    @GetMapping("/ranges")
    public String showRanges(Model model){
        List<RangeEntry> ranges = rangeService.loadRanges(); 
        model.addAttribute("ranges", ranges);
        
        return "ranges";
    }
}
