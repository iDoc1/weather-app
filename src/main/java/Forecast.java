//test comment

import kong.unirest.HttpResponse;

//Subclass of Weather. Eliminates wind speed field, adds precip type and quantity fields
//Overloads weather class constructor

public class Forecast {
	private String locationName;
	private String temp;
	private String humidity;
	private
	private String conditions;	
	private HttpResponse<String> currWeather;
	private int apiStatus;
}
