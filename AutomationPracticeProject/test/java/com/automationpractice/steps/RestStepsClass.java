package com.automationpractice.steps;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;

import com.automationpractice.utilities.RestUtilitiesMethods;
import com.jayway.restassured.path.json.JsonPath;


public class RestStepsClass extends Steps {
	
	final static String BASE_URL = "https://swapi.co";
	final static String PEOPLE = "/api/people/";
	final static String VEHICLES = "/api/vehicles/";
	final static String FILMS = "/api/films/";
	
	ResponseEntity<String> responseSteps = null;
	String URI = null;
	Boolean isResponseFound = false;

	@Given("I create a get request to search for $choice")
	public void createRequestUri(@Named("choice") String choice) {
		
		switch (choice) {
		case "PEOPLE":
			URI = BASE_URL + PEOPLE;
			break;
			
		case "VEHICLES":
			URI = BASE_URL + VEHICLES;
			break;
			
		case "FILMS":
			URI = BASE_URL + FILMS;
			break;
			
		default:
			break;
		}
	}
	
	@When("I search for $valueSearch")
	public void sendSearchRequest(@Named("valueSearch") String valueSearch) {
		String nextPage = URI;
		
		do {
			ResponseEntity<String> response = RestUtilitiesMethods.runRestRequest(nextPage);
			
			List<String> namesJson = JsonPath.from(response.getBody()).get("results.name");
			
			for (String string : namesJson) {
				if (string.contains(valueSearch)) {
					isResponseFound = true;
					break;
				}
			}
			
			nextPage = JsonPath.from(response.getBody()).get("next");
			
		} while (nextPage!=null);
		
	}
	

	@When("I send a request")
	public void sendRequest() {
		responseSteps = RestUtilitiesMethods.runRestRequest(URI);
	}
	
	@Then("my search specification is found in the response")
	public void validateResultFound() {
		Assert.assertTrue("Result has not been found", isResponseFound);
	}
	
	@Then("my response returns code $code")
	public void validateResponseCode(@Named("code") int code) {
		Assert.assertEquals("Incorrect response code returned. Received: " + responseSteps.getStatusCodeValue() +
							" but expected: ", code, responseSteps.getStatusCodeValue());
	}
	
	@Then("my response returns list $listValues")
	public void validateListResponse(@Named("listValues") List<String> listValues) {
		List<String> moviesFound = RestUtilitiesMethods.returnListMovies(responseSteps);
		
		Assert.assertEquals("List of movies selected not found. Found the following: " + moviesFound,
							listValues, moviesFound);
	}
}
