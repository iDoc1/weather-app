import java.io.*;
import java.net.InetAddress;
import java.net.URL;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class UserLocation {
	private CityResponse response;
	private String city;
	private String country;
	private String ipAddress;
	private static final String databaseFile = "src/main/resources/GeoLite2-City.mmdb";
	
	public UserLocation() 
			throws IOException, GeoIp2Exception {
		ipAddress = setIP();
		response = setResponse();		
		
		
		city = response.getCity().getName();
		country = response.getCountry().getName();
	}
	
	private CityResponse setResponse() 
			throws IOException, GeoIp2Exception {
		File database = new File(databaseFile);
		DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
		
		InetAddress address = InetAddress.getByName(ipAddress);
		return dbReader.city(address);
	}
	
	private String setIP() throws IOException {
		URL url = new URL("http://bot.whatismyipaddress.com");
		BufferedReader readUrl = 
				new BufferedReader(new InputStreamReader(url.openStream()));
		
		return readUrl.readLine();
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getIpAdress() {
		return ipAddress;
	}
}
