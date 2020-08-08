
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class WeatherResponse {
	private HttpResponse<String> apiCurrWeather;
	private HttpResponse<String> apiForecastWeather;
	private static final String urlCurrent = "http://api.openweathermap.org/data/2.5/weather";
	private static final String urlForecast = "https://api.openweathermap.org/data/2.5/onecall";
	private static final String apiKey = "98b9c50a1756b6f643df39a665fcf18b";
	
	public WeatherResponse(String location) 
			throws UnirestException, ParseException {
		
		apiCurrWeather = callCurrentWeather(location);
		apiForecastWeather = callForecastWeather(location);
	}
		
	private HttpResponse<String> callCurrentWeather(String query) 
			throws UnirestException {
		
		HttpResponse<String> response = Unirest.get(urlCurrent)
				.queryString("q", query)
				.queryString("appid", apiKey)
				.queryString("units", "imperial")
				.asString();
		
		return response;
	}
	
	private HttpResponse<String> callForecastWeather(String query) 
		throws UnirestException, ParseException {

		Geocode coords = new Geocode(query);
		
		HttpResponse<String> response = Unirest.get(urlForecast)
				.queryString("lat", coords.getLat())
				.queryString("lon", coords.getLon())
				.queryString("exclude", "current,hourly,minutely")
				.queryString("appid", apiKey)
				.queryString("units", "imperial")
				.asString();
		
		return response;
	}
	
	public HttpResponse<String> getCurrentWeather() {
		return apiCurrWeather;
	}
	
	public HttpResponse<String> getForecastWeather(){
		return apiForecastWeather;
	}
	
}
