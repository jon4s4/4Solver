package jonas.solver.domain.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jonas.solver.application.RangeEntry;

@Service
public class RangeService {

    public List<RangeEntry> loadRanges(String fileName) {
        List<RangeEntry> ranges = new ArrayList<>();

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
                ranges.add(new RangeEntry(parts[0], parts[1], Double.parseDouble(parts[2])));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ranges;
    }
}