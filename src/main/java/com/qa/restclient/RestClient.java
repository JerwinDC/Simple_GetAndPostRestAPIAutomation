package com.qa.restclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.qa.base.TestBase;

public class RestClient extends TestBase {

	// Get Method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
		return closeableHttpResponse;

	}

	public int getStatusCode(CloseableHttpResponse closeableHttpResponse) {
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

		return statusCode;

	}

	public JSONObject getAndExportJson(CloseableHttpResponse closeableHttpResponse) {
		JSONObject parseJson = null;
		// Get JSON
		String jsonResponse;
		try {
			jsonResponse = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			parseJson = new JSONObject(jsonResponse);
			exportJson(parseJson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parseJson;
	}
	
	public HashMap<String, String> getHeaders(CloseableHttpResponse closeableHttpResponse) {
        HashMap<String, String> Headers = new HashMap<String, String>();
		
		//Get Headers
		Header[] AllHeaders = closeableHttpResponse.getAllHeaders();
		for(Header header: AllHeaders) {
			Headers.put(header.getName(), header.getValue());
		}
		
		return Headers;
	}
	
	public CloseableHttpResponse get(String url, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
		
		for(Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpGet.addHeader(entry.getKey(),entry.getValue());
		}
		
		return closeableHttpResponse;

	}
	
	//Post Method
	
	public CloseableHttpResponse post(String url, String stringEntity, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url); //for post method
		httpPost.setEntity(new StringEntity(stringEntity)); // for payload
		
		for(Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(),entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost); //to execute post request
		
		return closeableHttpResponse;
	}

}
