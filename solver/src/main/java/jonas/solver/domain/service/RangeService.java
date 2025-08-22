package jonas.solver.domain.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jonas.solver.application.RangeEntry;

@Service
public class RangeService {
    public List<RangeEntry> loadRanges(){
        List<RangeEntry> ranges = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
            getClass().getResourceAsStream("/data/ranges.csv")
        ))){
            String line;
            boolean first = true;
            while ((line = bufferedReader.readLine()) != null){
                if(first){
                    first = false;
                    continue;
                }
                String[] parts = line.split(",");
                ranges.add(new RangeEntry(parts[0], parts[1], parts[2]));
            }
            for(RangeEntry re: ranges){
                System.out.println("Action: " + re.getAction());
                System.out.println("Hand: " + re.getHand());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ranges;
    }

    
}
