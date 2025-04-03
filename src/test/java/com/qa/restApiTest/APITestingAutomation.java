package com.qa.restApiTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.users.User;

public class APITestingAutomation extends TestBase {
	TestBase testBase;
	String url;
	String apiURL;
	String serviceURL;

	@BeforeMethod
	public void beforeMethod() {
		testBase = new TestBase();
		serviceURL = prop.getProperty("serviceURL");
		apiURL = prop.getProperty("apiURL");
		url = serviceURL + apiURL;
	}

	// @Test
	public void getAPITest() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient();

		CloseableHttpResponse closeableHttpResponse;
		closeableHttpResponse = restClient.get(url);

		// Get Status Code
		int actualStatusCode = restClient.getStatusCode(closeableHttpResponse);
		System.out.println("Status code-->" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, HTTP_STATUS_CODE_200);

		// Get parseJson
		JSONObject parseJson = restClient.getAndExportJson(closeableHttpResponse);
		System.out.println("Parse json data-->" + parseJson);

		String actualtotalPages = getValueByJPath(parseJson, "total_pages");
		System.out.println("Total number of pages-->" + actualtotalPages);
		Assert.assertEquals(actualtotalPages, "2");

		String expectedID = prop.getProperty("id");
		String expectedemail = prop.getProperty("email");
		String expectedfirst_name = prop.getProperty("first_name");
		String expectedlast_name = prop.getProperty("last_name");
		String expectedavatar = prop.getProperty("avatar");

		String actualID = getValueByJPath(parseJson, "data[0]/id");
		String actualemail = getValueByJPath(parseJson, "data[0]/email");
		String actualfirst_name = getValueByJPath(parseJson, "data[0]/first_name");
		String actuallast_name = getValueByJPath(parseJson, "data[0]/last_name");
		String actualavatar = getValueByJPath(parseJson, "data[0]/avatar");

		System.out.println("Actual ID-->" + actualID);
		System.out.println("Actual Email-->" + actualemail);
		System.out.println("Actual First Name-->" + actualfirst_name);
		System.out.println("Actual Last Name-->" + actuallast_name);
		System.out.println("Actual Avatar-->" + actualavatar);

		Assert.assertEquals(actualID, expectedID);
		Assert.assertEquals(actualemail, expectedemail);
		Assert.assertEquals(actualfirst_name, expectedfirst_name);
		Assert.assertEquals(actuallast_name, expectedlast_name);
		Assert.assertEquals(actualavatar, expectedavatar);

		// Get headers
		HashMap<String, String> headers = restClient.getHeaders(closeableHttpResponse);
		System.out.println("Response headers-->" + headers);

	}

	// @Test
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient();

		CloseableHttpResponse closeableHttpResponse;
		HashMap<String, String> headerMap = new HashMap<String, String>();

		headerMap.put("Content-type", "application/json");
		headerMap.put("username", "test");
		headerMap.put("password", "@test123");
		headerMap.put("token-key", "token123");

		closeableHttpResponse = restClient.get(url, headerMap);

		// Get Status Code
		int actualStatusCode = restClient.getStatusCode(closeableHttpResponse);
		System.out.println("Status code-->" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, HTTP_STATUS_CODE_200);

		// Get parseJson
		JSONObject parseJson = restClient.getAndExportJson(closeableHttpResponse);
		System.out.println("Parse json data-->" + parseJson);

		String actualtotalPages = getValueByJPath(parseJson, "total_pages");
		System.out.println("Total number of pages-->" + actualtotalPages);
		Assert.assertEquals(actualtotalPages, "2");

		String expectedID = prop.getProperty("id");
		String expectedemail = prop.getProperty("email");
		String expectedfirst_name = prop.getProperty("first_name");
		String expectedlast_name = prop.getProperty("last_name");
		String expectedavatar = prop.getProperty("avatar");

		String actualID = getValueByJPath(parseJson, "data[0]/id");
		String actualemail = getValueByJPath(parseJson, "data[0]/email");
		String actualfirst_name = getValueByJPath(parseJson, "data[0]/first_name");
		String actuallast_name = getValueByJPath(parseJson, "data[0]/last_name");
		String actualavatar = getValueByJPath(parseJson, "data[0]/avatar");

		System.out.println("Actual ID-->" + actualID);
		System.out.println("Actual Email-->" + actualemail);
		System.out.println("Actual First Name-->" + actualfirst_name);
		System.out.println("Actual Last Name-->" + actuallast_name);
		System.out.println("Actual Avatar-->" + actualavatar);

		Assert.assertEquals(actualID, expectedID);
		Assert.assertEquals(actualemail, expectedemail);
		Assert.assertEquals(actualfirst_name, expectedfirst_name);
		Assert.assertEquals(actuallast_name, expectedlast_name);
		Assert.assertEquals(actualavatar, expectedavatar);

		// Get headers
		HashMap<String, String> headers = restClient.getHeaders(closeableHttpResponse);
		System.out.println("Response headers-->" + headers);
	}

	@Test
	public void postApiTest() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient();

		CloseableHttpResponse closeableHttpResponse;
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		String name = prop.getProperty("name");
		String job = prop.getProperty("job");
		User userObj = new User(name, job);

		String jsonPath = System.getProperty("user.dir") + prop.getProperty("jsonPath");
		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(new File(jsonPath), userObj);

		String jsonEntity = mapper.writeValueAsString(userObj);
		System.out.println("json Entity:" + jsonEntity);

		closeableHttpResponse = restClient.post(url, jsonEntity, headerMap);
		int actualStatusCode = restClient.getStatusCode(closeableHttpResponse);
		System.out.println("Status code-->" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, HTTP_STATUS_CODE_201);

		String responseJsonString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject parseJson = new JSONObject(responseJsonString);
        exportJson(parseJson);
		System.out.println("Parse json data-->" + parseJson);

		User userResponseObject = mapper.readValue(responseJsonString, User.class);

		System.out.println("json response name-->" + userResponseObject.getName());
		System.out.println("json response job-->" + userResponseObject.getJob());
		System.out.println("json response id-->" + userResponseObject.getId());
		System.out.println("json response createAt-->" + userResponseObject.getCreatedAt());

		Assert.assertEquals(userObj.getName(), userResponseObject.getName());
		Assert.assertEquals(userObj.getJob(), userResponseObject.getJob());

	}
}
