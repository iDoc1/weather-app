import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;

public class Forecast {
	private String locationName;
	private ForecastDay[] forecastArray;
	private HttpResponse<String> forecastWeather;
	private int apiStatus;
	
	public Forecast(String location) 
			throws ParseException {
		
		WeatherResponse forecast = new WeatherResponse(location);
		forecastWeather = forecast.getForecastWeather();
		apiStatus = forecastWeather.getStatus();
		
		forecastArray = new ForecastDay[7];
		setForecastArray("daily");
		//locationName = parseJson("city", "name");
		
	}
	
	private String parseJson(String key1, String key2) 
			throws ParseException {
		
		if (apiStatus != 200) {
			return null;
		}
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(forecastWeather.getBody());
		
		JSONObject object = (JSONObject) jsonObject.get(key1);
		return object.get(key2).toString();
		
	}
	
	private void setForecastArray(String key) 
			throws ParseException {
		
		/*if (apiStatus != 200) {
			return null;
		}*/
		System.out.println(forecastWeather.getBody());
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(forecastWeather.getBody());
		
		JSONArray jsonArray = (JSONArray) jsonObject.get("list");
		Iterator<JSONObject> itr = jsonArray.iterator();
		
		int index = 0;
		while (itr.hasNext() && index < forecastArray.length) {
			JSONObject object = itr.next();
			forecastArray[index] = new ForecastDay(object);	//creates new ForecastDay object in forecast day array		
		}		
	}
	
	public void printForecast() {
		//prints forecastArray
		for (int i = 0; i < forecastArray.length; i++) {
			System.out.println(forecastArray[i].toString());
		}
	}
}
