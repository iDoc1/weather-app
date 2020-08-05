//test comment

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kong.unirest.HttpResponse;

//Subclass of Weather. Eliminates wind speed field, adds precip type and quantity fields
//Overloads weather class constructor

public class ForecastDay {	
	private String tempMax;
	private String tempMin;
	private String humidity;
	private String conditions;	
	private String precipType;
	private String precipAmount;
	private JSONObject dayWeather;
	private int dayCount;
	
	public ForecastDay(JSONObject jsonDayWeather) {
		dayWeather = jsonDayWeather;
		
		humidity = parseJson("humidity", null);
		
	}
	
	//partse dayWeather JSONObject to find value of given key
	public String parseJson(String key1, String key2) {		
		
		if (key2 == null) {
			return dayWeather.get(key1).toString();
		} else {
			JSONObject object = (JSONObject) dayWeather.get(key1);
			return object.get(key2).toString();
		}
	}
	
	//prints individual forecast for this instance's day
	public String toString() {
		return humidity;
	}
}
