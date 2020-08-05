
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class WeatherResponse {
	private HttpResponse<String> apiCurrWeather;
	private HttpResponse<String> apiForecastWeather;
	private static final String urlCurrent = "http://api.openweathermap.org/data/2.5/weather";
	private static final String urlForecast = "http://api.openweathermap.org/data/2.5/forecast/daily";
	private static final String apiKey = "98b9c50a1756b6f643df39a665fcf18b";
	
	public WeatherResponse(String location) 
			throws UnirestException {
		
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
		throws UnirestException {
		
		HttpResponse<String> response = Unirest.get(urlForecast)
				.queryString("q", query)
				.queryString("cnt", 7)
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
