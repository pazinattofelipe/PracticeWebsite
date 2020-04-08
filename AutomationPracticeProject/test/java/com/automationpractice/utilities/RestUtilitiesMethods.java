package com.automationpractice.utilities;

import java.net.URI;
import java.util.List;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.jayway.restassured.path.json.JsonPath;

public class RestUtilitiesMethods {

	public static ResponseEntity<String> runRestRequest(String baseUrl) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
		return response;
	}

	public static Boolean runDeleteRequest(String baseUrl) {
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			restTemplate.delete(baseUrl, String.class);
			return true;

		} catch (Exception ex) {

			return false;
		}
	}

	public static List<String> returnListMovies(ResponseEntity<String> responseSteps) {
		return JsonPath.from(responseSteps.getBody()).get("results.title");
	}
}
