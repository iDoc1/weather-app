//test comment

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kong.unirest.HttpResponse;

//Subclass of Weather. Eliminates wind speed field, adds precip type and quantity fields
//Overloads weather class constructor

public class ForecastDay {	
	private String tempMax;
	private String tempMin;
	private String humidity;
	private String windSpeed;	
	private String precipType;
	private String precipAmount;
	private String description;
	private JSONObject dailyWeather;
	
	public ForecastDay(JSONObject weatherData) {
		dailyWeather = weatherData;
		
		tempMax = parseJson("temp", "max");
		tempMin = parseJson("temp", "min");
		humidity = parseJson("humidity", null);
		windSpeed = parseJson("wind_speed", null);
		setPrecip();
		description = parseJsonArray("weather", "description");		
	}
	
	//parse dayWeather JSONObject to find value of given key
	public String parseJson(String key1, String key2) {		
		
		if (key2 == null) {
			return dailyWeather.get(key1).toString();
		} else {
			JSONObject object = (JSONObject) dailyWeather.get(key1);
			return object.get(key2).toString();
		}
	}
	
	public String parseJsonArray(String key1, String key2) {
		
		JSONArray jsonArray = (JSONArray) dailyWeather.get(key1);
		Iterator<JSONObject> itr = jsonArray.iterator();
		String result = "";
		
		while (itr.hasNext()) {
			JSONObject object = itr.next();
			result = object.get(key2).toString();
		}

		return result;	
	}
	
	public void setPrecip() {
		if (dailyWeather.containsKey("rain")) {
			precipType = "rain";
			precipAmount = dailyWeather.get("rain").toString();
		} else if (dailyWeather.containsKey("snow")) {
			precipType = "snow";
			precipAmount = dailyWeather.get("snow").toString();
		} else {
			precipType = "no precip expected";
			precipAmount = "0";
		}
	}
	
	public String getTempMax() {
		return tempMax;
	}
	
	public String getTempMin() {
		return tempMin;
	}
	
	public String getHumidity() {
		return humidity;
	}
	
	public String getWindSpeed() {
		return windSpeed;
	}
	
	public String getPrecipType() {
		return precipType;
	}
	
	public String getPrecipAmount() {
		return precipAmount;
	}
	
	public String getDescription() {
		return description;
	}
	
	//prints individual forecast for this instance's day
	public String toString() {
		return  "\t" + "Max temp: " + tempMax + "\n"
				+ "\t" + "Min temp: " + tempMin + "\n"
				+ "\t" + "Humidity: " + humidity + "\n"
				+ "\t" + "Wind speed: " + windSpeed + "\n"	
				+ "\t" + "Precip type: " + precipType + "\n"
				+ "\t" + "Precip amount: " + precipAmount + "\n"
				+ "\t" + "Description: " + description + "\n";
	}
}
