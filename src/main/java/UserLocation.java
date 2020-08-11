/* This class uses the bot.whatismyipaddress.com website to obtain the IP
 * address of the current user. The ipapi API is then used to determine
 * the city, state and country of the user which can be returned in the GET
 * methods.
 */

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
	
	// Sets city, state and country fields using the users IP address.
	private void setLocationFields() 
			throws ParseException {
		
		HttpResponse<String> response = 
				Unirest.get(
						"https://ipapi.co/" + ipAddress + "/json/").asString();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject 
				= (JSONObject) jsonParser.parse(response.getBody());
		
		city = jsonObject.get("city").toString();
		state = jsonObject.get("region_code").toString();
		country = jsonObject.get("country").toString();
	}
	
	//Sets the IP field to the users current IPv6 IP address
	private String setIP() 
			throws IOException {
		
		URL url = new URL("http://bot.whatismyipaddress.com");
		BufferedReader readUrl 
				= new BufferedReader(new InputStreamReader(url.openStream()));
		
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
	
	public String toString() {
		return "City: " + city + ", State: " + state + ", "
				+ "Country: " + country;
	}
}
