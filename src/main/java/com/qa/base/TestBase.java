package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestBase {
	public Properties prop;
	public static int HTTP_STATUS_CODE_200 = 200;
	public static int HTTP_STATUS_CODE_201 = 201;
	public static int HTTP_STATUS_CODE_404 = 404;
	public static int HTTP_STATUS_CODE_301 = 301;
	public static int HTTP_STATUS_CODE_500 = 500;

	public TestBase() {
		prop = new Properties();
		String configPath = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";
		try {
			FileInputStream file = new FileInputStream(configPath);
			prop.load(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportJson(JSONObject jsonObject) {
		Date date = new Date();
		String fileName = date.toString().replaceAll(" ", "_").replaceAll(":", "-");
		String reportPath = System.getProperty("user.dir") + "//Reports//" + fileName + "_output.json";

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			objectMapper.writeValue(new File(reportPath), jsonObject.toMap());
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getValueByJPath(JSONObject responsejson, String jpath) {
		Object obj = responsejson;
		for (String s : jpath.split("/")) {
			if (!s.isEmpty()) {
				if (s.contains("[") && s.contains("]")) {
					String key = s.split("\\[")[0];
					int index = Integer.parseInt(s.split("\\[")[1].replace("]", ""));
					obj = ((JSONArray) ((JSONObject) obj).get(key)).get(index);
				} else {
					obj = ((JSONObject) obj).get(s);
				}

				if (obj instanceof JSONArray) {
					continue;
				} else if (!(obj instanceof JSONObject)) {
					return obj.toString();
				}
			} else {
				return "Data not found, please recheck the json path";
			}
		}
		return obj.toString();
	}

}
