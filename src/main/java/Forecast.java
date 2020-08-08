import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import kong.unirest.HttpResponse;

public class Forecast {
	private String location;
	private String city;
	private ForecastDay[] forecastArray;
	private String weatherForecast;
	private int apiStatus;
	
	public Forecast(String location1, String location2) 
			throws ParseException {
		
		location = location1 + "," + location2;
		city = setCityName(location1);
		
		WeatherResponse forecastResponse = new WeatherResponse(location);
		weatherForecast = forecastResponse.getWeather();
		apiStatus = forecastResponse.getStatus();
		
		forecastArray = new ForecastDay[8];
		setForecastArray("daily");		
	}
	
	@SuppressWarnings("unchecked")
	private void setForecastArray(String key) 
			throws ParseException {
		
		if (apiStatus == 200) {
		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(weatherForecast);
			
			JSONArray jsonArray = (JSONArray) jsonObject.get(key);			
			Iterator<JSONObject> itr = jsonArray.iterator();
			
			int index = 0;
			while (itr.hasNext()) {
				String singleDay = itr.next().toString();
				forecastArray[index] = new ForecastDay(singleDay);	//creates new ForecastDay object in forecast day array	
				index++;
			}
			
		}
	}
	
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
	
	public String getLocation() {
		return location;
	}
	
	public String getCity() {
		return city;
	}
	
	public int getStatus() {
		return apiStatus;
	}
	
	//returns String of forecastArray of 7 day weather forecast data
	public String toString() {
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
	
	
}
