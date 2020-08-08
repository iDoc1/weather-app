/*
 * This class uses the OpenWeatherMap Current Weather API to find latitude
 * and longitude given a specific city name. Most Geocoding API's cost $$. This
 * class serves as a workaround by using OpenWeatherMap's current weather response, 
 * but only provides geographic coordinates accurate to two decimal places. 
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class Geocode {
	private String lat;
	private String lon;
	private static final String url = "https://api.openweathermap.org/data/2.5/weather";
	private static final String apiKey = "98b9c50a1756b6f643df39a665fcf18b";
	
	public Geocode(String location) 
			throws ParseException {
		
		setLatLon(location);
	}
	
	private void setLatLon(String location) 
			throws ParseException {
		
		HttpResponse<String> response = Unirest.get(url)
				.queryString("q", location)
				.queryString("appid", apiKey)
				.asString();
		
		int apiStatus = response.getStatus();
		
		if (apiStatus == 200) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
			
			JSONObject coord = (JSONObject) jsonObject.get("coord");		
			lat = coord.get("lat").toString();
			lon = coord.get("lon").toString();
		}
	}
	
	public String getLat() {
		return lat;
	}
	
	public String getLon() {
		return lon;
	}
}
