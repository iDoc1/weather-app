import java.io.*;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class UserLocation {
	private String city;
	private String state;
	private String country;
	private String ipAddress;
	
	public UserLocation() 
			throws IOException, ParseException {
		
		ipAddress = setIP();
		setLocationFields();
	}
		
	private void setLocationFields() 
			throws ParseException {
		
		HttpResponse<String> response = 
				Unirest.get("https://ipapi.co/" + ipAddress + "/json/").asString();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
		
		city = jsonObject.get("city").toString();
		state = jsonObject.get("region_code").toString();
		country = jsonObject.get("country").toString();
	}
	
	private String setIP() 
			throws IOException {
		
		URL url = new URL("http://bot.whatismyipaddress.com");
		BufferedReader readUrl = 
				new BufferedReader(new InputStreamReader(url.openStream()));
		
		return readUrl.readLine();
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getIpAdress() {
		return ipAddress;
	}
}
