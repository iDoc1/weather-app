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
			throws UnirestException, ParseException {
		
		location = location1 + "," + location2;
		city = setCityName(location1);
			
		WeatherResponse response = new WeatherResponse(location);
		weatherResponse = response.getWeather();
		responseStatus = response.getStatus();
		
		setCurrWeather();
		setForecast();
	}
	
	private void setCurrWeather() 
			throws ParseException {
		
		if (responseStatus == 200) {
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(weatherResponse);
			
			String weather = jsonObject.get("current").toString();
			currWeather = new CurrWeather(weather);			
		}
		
	}
	
	private void setForecast() 
			throws ParseException {
		
		forecastArray = new ForecastDay[8];
		
		if (responseStatus == 200) {
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(weatherResponse);
			
			JSONArray jsonArray = (JSONArray) jsonObject.get("daily");			
			Iterator<JSONObject> itr = jsonArray.iterator();
			
			int index = 0;
			while (itr.hasNext()) {
				String weather = itr.next().toString();
				forecastArray[index] = new ForecastDay(weather);	
				index++;
			}
			
		}
		
	}
	
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
	
	public String currentToString() {
		return currWeather.toString();
	}
	
	public String forecastToString() {
		Calendar cal = Calendar.getInstance();
		
		String result = "\t" + "Location: " + city + "\n" + "\n";
		result += "\t" + "Today:" + "\n" + forecastArray[0].toString() + "\n";	
		
		for (int i = 1; i < forecastArray.length; i++) {
			cal.add(Calendar.DATE, 1);
			String date = cal.getTime().toString().substring(0, 10);
			
			result += "\t" + date + ": " + "\n" + forecastArray[i].toString() + "\n";
		}
		
		return result;
	}
	
	public String toString() {
		return currentToString() + "\n" + forecastArray.toString();
	}
	
	public int getStatus() {
		return responseStatus;
	}
}
