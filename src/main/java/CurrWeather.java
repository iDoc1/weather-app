/* This class accepts a JSON formatted String as a parameter and parses the 
 * String so the client program can obtain current weather information. 
 */

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
	
	/* Parses dayWeather JSONObject to find value of given key using the JSON
	 * String output by the WeatherResponse class.
	 */	
	private String parseJson(String key1, String key2) 
			throws ParseException {

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(currWeather);
		
		// IF statements account for JSON values that have either 1 or 2 keys
		if (key2 == null) {
			return jsonObject.get(key1).toString();
		} else {
			JSONObject object = (JSONObject) jsonObject.get(key1);
			return object.get(key2).toString();
		}
	}
	
	/* Parses the JSON array to find the value of the given key pairs using the
	 * JSON String passed by the WeatherResponse class.
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
		return "\t" +  "Temperature: " + temp + "\u00B0F" + "\n"
				+ "\t" + "Wind speed: " + windSpeed + " mph" + "\n"
				+ "\t" + "Humidity: " + humidity + "%" + "\n"
				+ "\t" + "Description: " + description;
	}
}
