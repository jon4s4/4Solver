package jonas.solver.domain.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jonas.solver.application.HandRange;

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
                hands.put(key, new HandRange(Double.parseDouble(parts[1]), parts[2]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hands;
    }

}