package se.tedahlberg.monty_hall;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import se.tedahlberg.monty_hall.controller.TreasureController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MontyHallApplicationTests {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";

	@LocalServerPort
	private int port;

	@Autowired
	private TreasureController treasureController;

	private TestRestTemplate restTemplate = new TestRestTemplate();
	private HttpHeaders headers = new HttpHeaders();

	private void println(String message) {
		System.out.println("[" + ANSI_GREEN + "TEST" + ANSI_RESET + "] " + message);
	}

	private String createUrl(String path) {
		return "http://localhost:" + port + path;
	}

	private String calculateTreasureFindingPercentage(Boolean trustJack) {
		HttpEntity<Boolean> entity = new HttpEntity<Boolean>(null, headers);

		int numberOfTries = 1000;
		int numberOfTreasures = 0;

		for (int i = 0; i < numberOfTries; i++) {
			ResponseEntity<Boolean> response = restTemplate.exchange(createUrl(
					"/treasure/find?trustJack=" + trustJack), HttpMethod.GET, entity, Boolean.class);

			final Boolean foundTreasure = response.getBody();

			if (foundTreasure) {
				numberOfTreasures++;
			}
		}

		double percentage = (double) numberOfTreasures / numberOfTries * 100;
		return String.format("%.2f", percentage) + "%";
	}

	@Test
	@Order(1)
	void contextLoads() {
		println("Context loads");
		assertThat(treasureController).isNotNull();
	}

	@Test
	@Order(2)
	void percentageOfTreasuresWhenTrustingJack() {
		println("Percentage of treasures found when trusting Jack");

		String percentage = calculateTreasureFindingPercentage(true);

		println("Precentage of treasures found: " + percentage);
	}

	@Test
	@Order(3)
	void percentageOfTreasuresWhenNotTrustingJack() {
		println("Percentage of treasures found when not trusting Jack");

		String percentage = calculateTreasureFindingPercentage(false);

		println("Precentage of treasures found: " + percentage);
	}
}
