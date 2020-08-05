import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ForecastTest {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		HttpResponse<String> response = Unirest.get("https://api.openweathermap.org/data/2.5/onecall")
				.queryString("lat", "33.441792")
				.queryString("lon", "-94.037689")
				.queryString("exclude", "current,hourly,minutely")
				.queryString("appid", "98b9c50a1756b6f643df39a665fcf18b")
				.asString();
		
		System.out.println(response.getBody());
		
		//Forecast f = new Forecast("Seattle,wa,us");
		
		//f.printForecast();

	}

}
