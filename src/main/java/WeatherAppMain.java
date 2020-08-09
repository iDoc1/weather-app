import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import kong.unirest.UnirestException;

public class WeatherAppMain {

	public static void main(String[] args) 
			throws IOException, ParseException {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Choose an option:");
		System.out.println("Option 1: Current Weather");
		System.out.println("Option 2: 7 Day Forecast");
		System.out.print("Type an option number: ");
		
		String weatherFormat = input.nextLine();
		
		while (!weatherFormat.equals("1") && !weatherFormat.equals("2")) {
			System.out.println();
			System.out.print("Invalid input. Type an option number: ");
			weatherFormat = input.nextLine();
		}
		
		System.out.println();
		System.out.println("Choose an option:");
		System.out.println("Option 1: Get weather by city/state (US only)");
		System.out.println("Option 2: Get weather by city/country");
		System.out.println("Option 3: Get weather by current location");
		System.out.print("Type an option number: ");
		
		String locationFormat = input.nextLine();
		
		while (!locationFormat.equals("1") && 
				!locationFormat.equals("2") && !locationFormat.equals("3")) {
			System.out.println();
			System.out.print("Invalid input. Type an option number:");
			locationFormat = input.nextLine();
		}
		
		System.out.println();
		
		if (locationFormat.equals("1") || locationFormat.equals("2")) {
			System.out.print("Enter city name: ");
			String city = input.nextLine();
			
			if (locationFormat.equals("1")) {
				String state = inputState(input);
								
				printWeatherData(city, state, weatherFormat);
			} else {
				String country = inputCountry(input);
				
				printWeatherData(city, country, weatherFormat);	
			}
			
		} else {
			UserLocation currLocation = new UserLocation();
			System.out.println("\tIP address: " + currLocation.getIpAdress());
			
			printWeatherData(currLocation.getCity(), 
				currLocation.getState() + "," + currLocation.getCountry(), weatherFormat);			
		}

	}
	
	/**
	 * Prints weather data using the city and state/country name as parameters
	 * and calling the CurrWeather or Forecast class depending on which 
	 * option the user chose
	 */
	public static void printWeatherData(String location1, 
						String location2, String weatherFormat) 
							throws UnirestException, ParseException {
		
		Weather weather = new Weather(location1, location2);
		
		if (weatherFormat.equals("1") && weather.getStatus() == 200) {			
			System.out.println(weather.currentToString());
		} else if (weatherFormat.equals("2") && weather.getStatus() == 200) {				
			System.out.println(weather.forecastToString());
		} else {
			System.out.println();
			System.out.println("Location not found");
		}
			
	}
	
	/**Prompts user to enter state name and checks the Country_Names file
	 * to ensure that entered state name is valid
	 */
	public static String inputState(Scanner input) 
			throws FileNotFoundException {
		
		Scanner fileInput = new Scanner(new File("src/main/resources/State_Names.txt"));		
		ArrayList<String> stateNames = new ArrayList<String>();
		
		while (fileInput.hasNext()) {
			stateNames.add(fileInput.next().toLowerCase());
		}
		
		System.out.print("Enter US state name or symbol: ");
		String state = input.nextLine();
		
		while (!stateNames.contains(state)) {
			System.out.println("Not a valid US state name");
			System.out.println();
			System.out.print("Enter US state name or symbol: ");
			state = input.nextLine();			
		}
		
		return state + ",us";
	}
	
	/**Prompts user to enter country name and checks the Country_Names file
	 * to ensure that entered country name is valid
	 */
	public static String inputCountry(Scanner input) 
			throws FileNotFoundException {
		
		Scanner fileInput = new Scanner(new File("src/main/resources/Country_Names.txt"));		
		ArrayList<String> countryNames = new ArrayList<String>();
		
		while (fileInput.hasNext()) {
			countryNames.add(fileInput.next().toLowerCase());
		}
		
		System.out.print("Enter country name or symbol: ");
		String country = input.nextLine();
		
		while (!countryNames.contains(country)) {
			System.out.println("Not a valid country name");
			System.out.println();
			System.out.print("Enter country name or symbol: ");
			country = input.nextLine();
		}
		
		return country;
	}
	
}
