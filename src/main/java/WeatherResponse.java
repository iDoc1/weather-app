/*
 * This class utilizes the OpenWeatherMap "One Call" API. Data is requested
 * by providing the API with geographic coordinates. The API responds with 
 * weather data in JSON format. 
 */

import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class WeatherResponse {

	private HttpResponse<String> apiWeather;
	private static final String url = "https://api.openweathermap.org/data/2.5/onecall";
	private static final String apiKey = "98b9c50a1756b6f643df39a665fcf18b";
	
	public WeatherResponse(String location) 
			throws UnirestException, ParseException {
		
		apiWeather = callWeather(location);
	}
	
	/* This method calls first calls a Geocode class to get latitude and longitude
	 * for a given location, then calls the OpenWeatherMap API to obtain 7 day 
	 * forecast and current weather. 
	 */
	private HttpResponse<String> callWeather(String query) 
		throws UnirestException, ParseException {

		Geocode coords = new Geocode(query);
		
		/* Constructs an HttpResponse object using the Unirest library and the
		 * OpenWeatherMap API
		 */
		HttpResponse<String> response = Unirest.get(url)
				.queryString("lat", coords.getLat())
				.queryString("lon", coords.getLon())
				.queryString("exclude", "hourly,minutely")
				.queryString("appid", apiKey)
				.queryString("units", "imperial")
				.asString();

		return response;
	}
	
	private getApiKey( ) {
		//get API key from API_KEY folder
	}
		
	public String getWeather(){
		return apiWeather.getBody();
	}
	
	public int getStatus() {
		return apiWeather.getStatus();
	}
	
}
