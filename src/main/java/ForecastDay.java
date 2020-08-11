/* This class takes a String in JSON format as a parameter, then parses the
 * string and returns the weather data contained in the String. The input
 * constructor parameter must be in JSON format and should contain weather
 * data for a single day in the forecast.
 */

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ForecastDay {	

	private String tempMax;
	private String tempMin;
	private String humidity;
	private String windSpeed;	
	private String precipType;
	private String precipAmount;
	private String description;
	private String dayWeather;
	
	//Parameter contains weather data for a single day in the forecast.
	public ForecastDay(String dayWeather) 
			throws ParseException {
		
		this.dayWeather = dayWeather;
		
		tempMax = parseJson("temp", "max");
		tempMin = parseJson("temp", "min");
		humidity = parseJson("humidity", null);
		windSpeed = parseJson("wind_speed", null);
		setPrecip();
		description = parseJsonArray("weather", "description");		
	}
	
	/* Parses dayWeather JSONObject to find value of given key using the JSON
	 * String output by the Forecast class.
	 */	
	public String parseJson(String key1, String key2) 
			throws ParseException {		
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(dayWeather);
		
		//IF statements account for JSON values that have either 1 or 2 keys
		if (key2 == null) {
			return jsonObject.get(key1).toString();
		} else {
			JSONObject object = (JSONObject) jsonObject.get(key1);
			return object.get(key2).toString();
		}
	}
	
	/* Parses the JSON array to find the value of the given key pairs using the
	 * JSON String passed by the Forecast class.
	 */	
	@SuppressWarnings("unchecked")
	public String parseJsonArray(String key1, String key2) 
			throws ParseException {
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(dayWeather);
		
		JSONArray jsonArray = (JSONArray) jsonObject.get(key1);
		Iterator<JSONObject> itr = jsonArray.iterator();
		String result = "";
		
		while (itr.hasNext()) {
			JSONObject object = itr.next();
			result = object.get(key2).toString();
		}

		return result;	
	}
	
	public void setPrecip() 
			throws ParseException {
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(dayWeather);
		
		if (jsonObject.containsKey("rain")) {
			precipType = "rain";
			precipAmount = jsonObject.get("rain").toString();
		} else if (jsonObject.containsKey("snow")) {
			precipType = "snow";
			precipAmount = jsonObject.get("snow").toString();
		} else {
			precipType = "no precip expected";
			precipAmount = "0";
		}
	}
	
	public String getTempMax() {
		return tempMax;
	}
	
	public String getTempMin() {
		return tempMin;
	}
	
	public String getHumidity() {
		return humidity;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}
	
	public String getPrecipType() {
		return precipType;
	}
	
	public String getPrecipAmount() {
		return precipAmount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return  "\t" + "Max temp: " + tempMax + "\u00B0F" + "\n"
				+ "\t" + "Min temp: " + tempMin + "\u00B0F" + "\n"
				+ "\t" + "Humidity: " + humidity + "%" + "\n"
				+ "\t" + "Wind speed: " + windSpeed + " mph" + "\n"	
				+ "\t" + "Precip type: " + precipType + "\n"
				+ "\t" + "Precip amount: " + precipAmount + " mm" + "\n"
				+ "\t" + "Description: " + description + "\n";
	}
}
