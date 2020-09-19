/* This class uses the OpenWeatherMap Current Weather API to find latitude and 
 * longitude given a specific city name. Most Geocoding API's cost $$. This
 * class serves as a workaround by using OpenWeatherMap's Current Weather 
 * API response. As a result it only provides geographic coordinates accurate 
 * to 2 decimal places, as the API only outputs coordinates with this precision. 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class Geocode {
	private String lat;
	private String lon;
	private String apiKey;
	private static final String url 
			= "https:// api.openweathermap.org/data/2.5/weather";
	
	public Geocode(String location) 
			throws ParseException, FileNotFoundException {
		
		apiKey = getApiKey();
		setLatLon(location);
	}
	
	/* Sets latitude and longitude fields using the values from the 
	 * OpenWeatherMap API response.
	 */
	private void setLatLon(String location) 
			throws ParseException {
		
		HttpResponse<String> response = Unirest.get(url)
				.queryString("q", location)
				.queryString("appid", apiKey)
				.asString();
		
		int apiStatus = response.getStatus();
		
		if (apiStatus == 200) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject 
					= (JSONObject) jsonParser.parse(response.getBody());
			
			JSONObject coord = (JSONObject) jsonObject.get("coord");		
			lat = coord.get("lat").toString();
			lon = coord.get("lon").toString();
		}
	}
	
	/* Pulls API key from API_KEY resource file for use with the 
	 * OpenWeatherMap API.
	 */
	private String getApiKey() throws FileNotFoundException {
		Scanner fileInput = new Scanner(
				new File("src/main/resources/API_KEY.txt"));
		
		String key = "";
		while (fileInput.hasNext()) {
			if (fileInput.next().equals("API_KEY:")) {
			
				try {
					key =  fileInput.next();
				} 
				catch (NoSuchElementException ex) {
					System.out.println(
							ex + ": No API key found. See README doc.");
				}
 
			}
		}
		
		return key;
	}
	
	public String getLat() {
		return lat;
	}
	
	public String getLon() {
		return lon;
	}
	
	public String toString() {
		return "Latitude= " + lat + ", Longitude= " + lon;
	}
}
