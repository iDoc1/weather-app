import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ForecastTest {

	//public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		/*HttpResponse<String> response = Unirest.get("https://api.openweathermap.org/data/2.5/onecall")
				.queryString("lat", "33.441792")
				.queryString("lon", "-94.037689")
				.queryString("exclude", "current,hourly,minutely")
				.queryString("appid", "98b9c50a1756b6f643df39a665fcf18b")
				.asString();
		
		//System.out.println(response.getBody());
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
		
		JSONArray jsonArray = (JSONArray) jsonObject.get("daily");
		Iterator<JSONObject> itr = jsonArray.iterator();
		
		JSONObject[] forecastArray = new JSONObject[8];
		
		int index = 0;
		while (itr.hasNext()) {
			forecastArray[index] = itr.next();
			index++;
		}
		
		for (int i = 0; i < forecastArray.length; i++) {
			System.out.println(forecastArray[i]);
		}*/
		//Forecast f = new Forecast("Seattle,wa,us");
		
		//f.printForecast();

	//}

}
