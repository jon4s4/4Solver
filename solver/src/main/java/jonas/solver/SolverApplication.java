package jonas.solver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
			String hand = "AA";
			System.out.println(hands.get(hand));
		};
	}

}
