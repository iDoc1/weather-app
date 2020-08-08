
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.UnirestException;

public class CurrWeather {

	private String temp;
	private String windSpeed;
	private String humidity;
	private String description;	
	private String currWeather;
	
	public CurrWeather(String currWeather) 
			throws UnirestException, ParseException {
		
		this.currWeather = currWeather;
		
		temp = parseJson("temp", null);
		windSpeed = parseJson("wind_speed", null);
		humidity = parseJson("humidity", null);
		description = parseJsonArray("weather", "description");
	}
	
	/**
	 * This method parses the JSON formatted String output by the WeatherClient class
	 * and returns a String signifying the value of the given key(s)
	 */
	private String parseJson(String key1, String key2) 
			throws ParseException {

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(currWeather);
		
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
	@SuppressWarnings("unchecked")
	private String parseJsonArray(String key1, String key2) 
			throws ParseException {
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(currWeather);
		
		JSONArray jsonArray = (JSONArray) jsonObject.get(key1);
		Iterator<JSONObject> itr = jsonArray.iterator();
		String result = "";
		
		while (itr.hasNext()) {
			JSONObject object = itr.next();
			result = object.get(key2).toString();
		}
		
		return result;	
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
		return description;
	}
	
	public String toString() {		
		return "\t" +  "Current Temp: " + temp + "\u00B0F" + "\n"
				+ "\t" + "Wind speed: " + windSpeed + " mph" + "\n"
				+ "\t" + "Humidity: " + humidity + "%" + "\n"
				+ "\t" + "Description: " + description;
	}
}
