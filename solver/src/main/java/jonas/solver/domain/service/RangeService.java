package jonas.solver.domain.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jonas.solver.application.HandRange;
import jonas.solver.domain.model.Action;

@Service
public class RangeService {
    static final List<String> RANKS = List.of("A","K","Q","J","T","9","8","7","6","5","4","3","2");


    public Map<String, HandRange> loadRanges(String fileName) {
        Map<String, HandRange> hands = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/ranges/" + fileName)))) {

            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                String[] parts = line.split(",");
                String key = parts[0];
                hands.put(key, new HandRange(Double.parseDouble(parts[1]), Action.valueOf(parts[2])));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hands;
    }

    public Map<String, HandRange> loadInitRanges() {
        Map<String, HandRange> hands = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/ev_output.csv")))) {

            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                String[] parts = line.split(",");
        
                double evFold =  Double.parseDouble(parts[2]);
                double evCall =  Double.parseDouble(parts[3]);
                double evRaise = Double.parseDouble(parts[4]);

                String key = parts[0];
                HandRange hr = setBestAction(evFold, evCall, evRaise);
                hands.put(key, hr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hands;
    }

    private HandRange setBestAction(double evFold, double evCall, double evRaise) {
        double tmp = Math.max(evCall, evRaise);
        double maxEv = Math.max(tmp, evFold);
        if (maxEv == evRaise) {
            return new HandRange(maxEv, Action.RAISE);
        }else if(maxEv == evCall){
            return new HandRange(maxEv, Action.CALL);
        }else{
            return new HandRange(maxEv, Action.FOLD);
        }
    }

}