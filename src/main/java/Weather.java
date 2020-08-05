//comment

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

public class Weather {
	private String locationName;
	private String temp;
	private String windSpeed;
	private String humidity;
	private String conditions;	
	private HttpResponse<String> currWeather;
	private int apiStatus;
	
	public Weather(String location) 
			throws UnirestException, ParseException {
		
		WeatherResponse current = new WeatherResponse(location);
		currWeather = current.getCurrentWeather();
		apiStatus = currWeather.getStatus();
		
		locationName = parseJson("name", null);
		temp = parseJson("main", "temp");
		windSpeed = parseJson("wind", "speed");
		humidity = parseJson("main", "humidity");
		conditions = parseJsonArray("weather", "description");
	}
	
	/**
	 * This method parses the JSON formatted String output by the WeatherClient class
	 * and returns a String signifying the value of the given key(s)
	 */
	private String parseJson(String key1, String key2) 
			throws ParseException {
		if (apiStatus != 200) {
			return null;
		}
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(currWeather.getBody());
		
		if (key2 == null) {
			return jsonObject.get(key1).toString();
		} else {
			JSONObject object = (JSONObject) jsonObject.get(key1);
			return object.get(key2).toString();
		}
	}
	
	/**
	 * /**
	 * This method parses the JSON formatted array output by the WeatherClient
	 * and returns a String signifying the value of the given keys
	 */
	 
	private String parseJsonArray(String key1, String key2) 
			throws ParseException {
		if (apiStatus != 200) {
			return null;
		}
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(currWeather.getBody());
		
		JSONArray jsonArray = (JSONArray) jsonObject.get(key1);
		Iterator<JSONObject> itr = jsonArray.iterator();
		String result = "";
		
		while (itr.hasNext()) {
			JSONObject object = itr.next();
			result = object.get(key2).toString();
		}
		
		return result;	
	}
	
	public String getLocation() {
		return locationName;
	}
	
	public int getApiStatus() {
		return apiStatus;
	}
	
	public String getTemp() {
		return temp;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}
	
	public String getHumidity() {
		return humidity;
	}
		
	public String getConditions() {
		return conditions;
	}
}
