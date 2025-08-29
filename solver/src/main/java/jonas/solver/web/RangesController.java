package jonas.solver.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jonas.solver.application.RangeEntry;
import jonas.solver.domain.service.RangeService;

@Controller
public class RangesController {
    private final RangeService rangeService;

    public RangesController(RangeService rangeService) {
        this.rangeService = rangeService;
    }
    
    @GetMapping("/ranges/{filename}")
    public String showRanges(Model model, @PathVariable("filename") String filename){
        List<RangeEntry> ranges = rangeService.loadRanges(filename + ".csv"); 
        model.addAttribute("ranges", ranges);
        model.addAttribute("scenario", filename);
        
        return "ranges";
    }
}
