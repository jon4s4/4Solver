package jonas.solver;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jonas.solver.application.HandRange;
import jonas.solver.domain.service.RangeService;

@SpringBootApplication
public class SolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolverApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(){
		return args -> {
			RangeService service = new RangeService();
			Map<String, HandRange> hands = service.loadRanges("utg_vs_bb.csv");
			List<String> ranks = List.of("A","K","Q","J","T","9","8","7","6","5","4","3","2");
			for(int i = 0; i < ranks.size(); i++){
				for(int j = 0; j < ranks.size(); j++){
					String hi = (i <= j ? ranks.get(i) : ranks.get(j));
					String lo = (i <= j ? ranks.get(j) : ranks.get(i));
					String hand = (i < j ? hi+lo+"s" : hi+lo+"o");
					System.out.println("hand: " + hand);
				}
				System.out.println("loaded Hand: " + hands.get("AKs"));
			}

		};
	}

}
