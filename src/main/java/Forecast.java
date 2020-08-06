import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;

public class Forecast {
	private String timezone;
	private ForecastDay[] forecastArray;
	private HttpResponse<String> weatherForecast;
	private int apiStatus;
	
	public Forecast(String location) 
			throws ParseException {
		
		WeatherResponse forecastResponse = new WeatherResponse(location);
		weatherForecast = forecastResponse.getForecastWeather();
		apiStatus = weatherForecast.getStatus();
		
		forecastArray = new ForecastDay[8];
		setForecastArray("daily");
		timezone = parseJson("timezone", null);
		
	}
	
	/**
	 * Parses JSON input from API for single or double layer keys. Example:
	 * single: key1:value , double: key1:{key2:value,key_:value,key_:value...}
	 */
	private String parseJson(String key1, String key2) 
			throws ParseException {
		
		if (apiStatus != 200) {
			return null;
		}
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(weatherForecast.getBody());
		
		if (key2 == null) {
			return jsonObject.get(key1).toString();
		} else {
			JSONObject object = (JSONObject) jsonObject.get(key1);
			return object.get(key2).toString();
		}
		
	}
	
	private void setForecastArray(String key) 
			throws ParseException {
		
		/*if (apiStatus != 200) {
			return null;
		}*/
		//System.out.println(weatherForecast.toString());
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(weatherForecast.getBody());
		
		JSONArray jsonArray = (JSONArray) jsonObject.get(key);
		Iterator<JSONObject> itr = jsonArray.iterator();
		
		int index = 0;
		while (itr.hasNext()) {
			JSONObject object = itr.next();
			forecastArray[index] = new ForecastDay(object);	//creates new ForecastDay object in forecast day array	
			index++;
		}		
	}
	
	public int getApiStatus() {
		return apiStatus;
	}
	
	//returns String of forecastArray of 7 day weather forecast data
	public String toString() {
		String result = "\t" + "Timezone: " + timezone + "\n";
		result += "\t" + "Today: " + "\n" + forecastArray[0].toString() + "\n";	
		
		for (int i = 1; i < forecastArray.length; i++) {
			result += "\t" + "Day" + i + ": " + "\n" + forecastArray[i].toString() + "\n";
		}
		
		return result;
	}
}
