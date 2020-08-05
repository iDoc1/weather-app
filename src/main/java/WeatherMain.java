import java.util.*;

import org.json.simple.parser.ParseException;

import java.io.*;
import kong.unirest.UnirestException;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public class WeatherMain {

	//public static void main(String[] args) 
			throws UnirestException, IOException, GeoIp2Exception, ParseException {
		
		System.out.println("Follow the prompts to see weather information for any city.\n");		
		System.out.println("Choose one of the following options by entering the option number:");
		System.out.println("\tOption 1: Current weather by city/state (US only)");
		System.out.println("\tOption 2: Current weather by city/country");
		System.out.println("\tOption 3: Current weather by your current location");
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
		
		Weather current = new Weather(location);
		
		if (current.getApiStatus() == 200) {
			System.out.println("\tLocation: " + current.getLocation());
			System.out.println("\tCurrent conditions: " + current.getConditions());
			System.out.println("\tCurrent temperature: " + current.getTemp() + "\u00B0" + "F");
			System.out.println("\tCurrent humidity: " + current.getHumidity() + "%");
			System.out.println("\tCurrent wind speed: " + current.getWindSpeed() + " mph");				
		} else {
			System.out.println("\tlocation not found");
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
