package com.github.tavi.rep_portal;

import java.util.ResourceBundle;

import org.junit.jupiter.api.*;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ApiTests {

	private static String BASE_URI;
	private static RequestSpecification requestSpecification;
	
	
	@BeforeAll
	public static void setup() {
		ResourceBundle rb = ResourceBundle.getBundle("report-portal");
		BASE_URI = rb.getString("API_BASE_URI");
		requestSpecification = RestAssured.given()
				.baseUri(BASE_URI)
				.header("Authorization", String.format("Bearer %s", rb.getString("API_KEY")));
	}
	
	@Test
	@DisplayName("Successful dashboard creation")
	public void positiveDashboardCreation() {
		String body = "{ \"name\" : \"Test dashboard 1\", \"description\" : \"Successfully created dashboard\" }";
		
		int id = createDashboard(body, 201);
		
		Assertions.assertEquals(200, dashboardGetRequestStatus(id));
		Assertions.assertTrue(deleteDashboard(id));
	}
	
	@Test
	@DisplayName("Could not create, dashboard name missing")
	public void negativeDashboardCreation() {
		String body = "{ \"description\" : \"Cannot create this dashboard\" }";
		
		Integer id = createDashboard(body, 400);
		
		Assertions.assertNull(id);
	}
	
	/**
	 * Try to create a dash-board through API
	 * @param body JSON body of the request
	 * @param expectedStatus The HTTP response status will be asserted to this value
	 * @return ID of the newly created dash-board (on success) OR {@code null} (on failure)
	 */
	@Step("Create dashboard, get its id")
	private Integer createDashboard(String body, int expectedStatus) {
		return requestSpecification
	            .header("Content-Type", "application/json")
				.body(body)
				.when()
				.post()
				.then()
				.assertThat()
				.statusCode(expectedStatus)
				.extract().path("id");
	}
	
	/**
	 * Deletes dash-board by its ID
	 * @param id ID of dash-board
	 * @return True if deleted successfully
	 */
	@Step("Delete dashboard, get status")
	private boolean deleteDashboard(int id) {
		return requestSpecification
				.delete(String.format("/%d", id))
				.statusCode() == 200;
	}
	
	/**
	 * GET request to API to retrieve the information about dash-board
	 * @param id ID of dash-board
	 * @return HTTP Status Code of the operation
	 */
	@Step("Try to get dashboad by id")
	private int dashboardGetRequestStatus(int id) {
		return requestSpecification
				.get(String.format("/%d", id))
				.statusCode();
	}
	
}
