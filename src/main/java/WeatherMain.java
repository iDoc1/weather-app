//add support for geographic coordinates using geocoder api
//get rid of huge database for IP address lookup

import java.util.*;

import org.json.simple.parser.ParseException;

import java.io.*;
import kong.unirest.UnirestException;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public class WeatherMain {

	public static void main(String[] args) 
			throws UnirestException, IOException, GeoIp2Exception, ParseException {
		
		System.out.println("Follow the prompts to see weather information for any city.\n");		
		System.out.println("Choose one of the following options by entering the option number:");
		System.out.println("\tOption 1: Weather by city/state (US only)");
		System.out.println("\tOption 2: Weather by city/country");
		System.out.println("\tOption 3: Weather by your current location");
		System.out.print("\tChoose an option number: ");
		
		Scanner input = new Scanner(System.in);
		String answer = input.nextLine().toLowerCase();
		
		while (!answer.equals("1") && !answer.equals("2") && !answer.equals("3")) {
			System.out.print("\tNot a valid option number. Type number 1, 2 or 3: ");
			answer = input.nextLine().toLowerCase();
		}
		
		System.out.println();
		String location = "";
		
		if (answer.equals("1")) {
			location = cityInUS(input);
			System.out.println();
		} else if (answer.equals("2")) {
			location = cityOutsideUS(input);
			System.out.println();
		} else {
			
			//Constructs new object to return user's current location based on IP address
			UserLocation currLocation = new UserLocation();
			location = getCurrLocation(currLocation);
			System.out.println("\tIP Address: " + currLocation.getIpAdress());
		}
			
		System.out.println("Choose an option to see current weather or 7 day forecast:");
		System.out.println("\tOption 1: Current weather");
		System.out.println("\tOption 2: 7 Day Forecast");
		System.out.print("\tChoose an option number: ");
		
		String queryType = input.nextLine().toLowerCase();
		
		while (!queryType.equals("1") && !queryType.equals("2")) {
			System.out.print("\tNot a valid option number. Type number 1 or 2: ");
			queryType = input.nextLine().toLowerCase();
		}
				
		if (queryType.equals("1")) {
			Weather current = new Weather(location);
			
			if (current.getApiStatus() == 200) {
				System.out.println();
				System.out.println(current.toString());
			} else {
				System.out.println("\tlocation not found");
			}
		} else {
			Forecast forecast = new Forecast(location);
			
			if (forecast.getApiStatus() == 200) {
				System.out.println();
				System.out.println(forecast.toString());
			} else {
				System.out.println("\tlocation not found");
			}
		}
	}
	
	/**
	 * This method asks user for city and state name then returns a String for 
	 * use in the OpenWeatherMap API query
	 */
	public static String cityInUS(Scanner input) 
			throws FileNotFoundException {
		
		System.out.print("\tEnter city name: ");
		String city = input.nextLine();
		
		System.out.print("\tEnter state name: ");
		String state = input.nextLine();
		
		Scanner fileInput = new Scanner(new File("src/main/resources/State_Names.txt"));		
		ArrayList<String> stateNames = new ArrayList<String>();
		
		while (fileInput.hasNext()) {
			stateNames.add(fileInput.next().toLowerCase());
		}
		
		state = checkLocation(stateNames, state, "state");
		return city + "," + state + "," + "us";
	}
	
	/**
	 * This method asks user for city and country name then returns a String for 
	 * use in the OpenWeatherMap API query
	 */
	public static String cityOutsideUS(Scanner input) 
			throws FileNotFoundException {
		System.out.print("\tEnter city name: ");
		String city = input.nextLine();
		
		System.out.print("\tEnter country name: ");
		String country = input.nextLine();
		
		Scanner fileInput = new Scanner(new File("src/main/resources/Country_Names.txt"));		
		ArrayList<String> countryNames = new ArrayList<String>();
		
		while (fileInput.hasNext()) {
			countryNames.add(fileInput.next().toLowerCase());
		}
		
		country = checkLocation(countryNames, country, "country");
		return city + "," + country;
	}
	
	/**
	 * This method ensures that user enters a valid state or country name and returns result.
	 * The locationData parameter contains data from either the Country_Names or 
	 * State_Names text files in the resources repository.
	 */
	private static String checkLocation(ArrayList<String> locationData, 
				String location, String locationType) {
		
		Scanner inputCheck = new Scanner(System.in);
		
		String result = "";
		boolean found = false;
		
		while (!found) {
			if (locationData.contains(location.toLowerCase())) {
				found = true;
				result = location;
			} else {
				System.out.print("\tNot a valid " + locationType + " name. Please enter country name: ");
				location = inputCheck.nextLine();
			}
		}
		return result;
	}
	
	/**
	 * Accepts UserLocation object as parameter returns city and country for weather API query
	 */
	private static String getCurrLocation(UserLocation currLocation)  {		
		String city = currLocation.getCity();
		String country = currLocation.getCountry();
		
		return city + "," + country;
	}
	

}
