/* This class accepts a location as a constructor parameter. When constructed, 
 * this class will construct two additional objects that store and return 
 * current weather and 7 day forecast weather information.
 */

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.UnirestException;

public class Weather {
	
	private String location;
	private String city;
	private CurrWeather currWeather;
	private ForecastDay[] forecastArray;
	private String weatherResponse;
	private int responseStatus;
	
	public Weather(String location1, String location2) 
			throws UnirestException, ParseException, FileNotFoundException {
		
		location = location1 + "," + location2;
		city = setCityName(location1);
		
		// Constructs WeatherResponse class to obtain weather data from API
		WeatherResponse response = new WeatherResponse(location);
		weatherResponse = response.getWeather();
		responseStatus = response.getStatus();
		
		setCurrWeather();
		setForecast();
	}
	
	/* Constructs an object that will store and return information about the 
	 * current weather. 
	 */
	private void setCurrWeather() throws ParseException {
		
		// Checks if API response is valid to avoid exceptions
		if (responseStatus == 200) {
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject 
					= (JSONObject) jsonParser.parse(weatherResponse);
			
			// Gets "current" weather data from JSON object
			String weather = jsonObject.get("current").toString();
			currWeather = new CurrWeather(weather);			
		}
		
	}
	
	/* Constructs an object that will store and return information about the
	 * weather forecast for the next 7 days.
	 */
	@SuppressWarnings("unchecked")
	private void setForecast() throws ParseException {
		
		/* Initializes array to size 8 since the weather forecast includes 8 
		 * days (current day and 7 forecast days).
		 */
		forecastArray = new ForecastDay[8];
		
		// Checks if API response is valid to avoid exceptions
		if (responseStatus == 200) {
			
			// Constructs objects from json-simple library to parse JSON Strings
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject 
					= (JSONObject) jsonParser.parse(weatherResponse);
			
			// Gets "daily" forecast weather data from JSON object.
			JSONArray jsonArray = (JSONArray) jsonObject.get("daily");			
			Iterator<JSONObject> itr = jsonArray.iterator();
			
			// Stores a Forecast day object in the array
			int index = 0;
			while (itr.hasNext()) {
				String weather = itr.next().toString();
				forecastArray[index] = new ForecastDay(weather);	
				index++;
			}
			
		}
		
	}
	
	/* Formats the chosen city name so that it follows proper syntax for nouns.
	 * For example, "SAlt LakE citY" will be returned as "Salt Lake City".
	 */
	@SuppressWarnings("resource")
	public String setCityName(String cityName) {		
		Scanner name = new Scanner(cityName);
		String result = "";
			
		while (name.hasNext()) {
			String next = name.next();
			result += next.substring(0, 1).toUpperCase() 
					+ next.substring(1).toLowerCase() + " ";
		}
		
		return result;
	}
	
	// Returns String containing current weather information
	public String currentToString() {
		return "\t" + "Location: " + city + "\n" + currWeather.toString();
	}
	
	// Returns String containing 7 day forecast weather information
	public String forecastToString() {
		
		// Uses Java Calendar class to get current date
		Calendar cal = Calendar.getInstance();
		
		String result = "\t" + "Today:" + "\n" 
				+ forecastArray[0].toString() + "\n";	
		
		for (int i = 1; i < forecastArray.length; i++) {
			cal.add(Calendar.DATE, 1);
			String date = cal.getTime().toString().substring(0, 10);
			
			result += "\t" + date + ": " + "\n" 
					+ forecastArray[i].toString() + "\n";
		}
		
		return "\t" + "Location: " + city + "\n" + "\n" + result;
	}
	
	// Returns numerical API status
	public int getStatus() {
		return responseStatus;
	}
	
}
