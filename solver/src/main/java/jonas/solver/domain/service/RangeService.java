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
                String key = canonical(parts[0]);
                hands.put(key, new HandRange(Double.parseDouble(parts[1]), parts[2]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hands;
    }

    private String canonical(String hand) {
        hand = hand.trim().toUpperCase(); // AKS, AKo, AA
        if (hand.length() == 2) return hand; // Paare
        
        String r1 = hand.substring(0,1);
        String r2 = hand.substring(1,2);
        char suffix = hand.charAt(2); // S oder O

        int i1 = RANKS.indexOf(r1);
        int i2 = RANKS.indexOf(r2);

        String hi = (i1 <= i2) ? r1 : r2;
        String lo = (i1 <= i2) ? r2 : r1;

        return hi + lo + suffix; // z.B. immer AKs statt KAs
    }

}