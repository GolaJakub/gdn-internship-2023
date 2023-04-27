package com.gola_jakub.FinanceApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FinanceApiApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}


	@Test
	public void testGetAverageExchangeRateBody() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		String currencyCode = "eur";
		String date = "2023-04-25";
		String url = String.format("http://localhost:8080/average?currencyCode=%s&date=%s", currencyCode, date);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("rate: 4.598");
	}

	@Test
	public void testGetMajorDifferenceBody() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		String currencyCode = "aud";
		String lastQuotations = "250";
		String url = String.format("http://localhost:8080/majorDifference?currencyCode=%s&lastQuotations=%s", currencyCode, lastQuotations);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("difference: 0.0663");
	}

	@Test
	public void testGetMinMaxExchangeRateBody() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		String currencyCode = "usd";
		String lastQuotations = "5";
		String url = String.format("http://localhost:8080/minMaxAverage?currencyCode=%s&lastQuotations=%s", currencyCode, lastQuotations);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("Minimal average exchange rate: 4.1697");
		assertThat(response.getBody()).contains("maximum average exchange rate: 4.2097");
	}

}
