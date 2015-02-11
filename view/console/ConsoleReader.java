package ch.zhaw.mas8i.pendlersupport.view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.mas8i.pendlersupport.Log;
import ch.zhaw.mas8i.pendlersupport.controller.TransportData;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;

/**
 * Static helper class for accessing System.in and request
 * a specific input type.
 */
public class ConsoleReader {
	
	/**
	 * Forces the user to enter an integer.
	 * Asks again, until the input is valid.
	 * 
	 * @param  title Description of the input value
	 * @return The requested integer
	 */
	public static int getIntegerInput(String title) {
		return getIntegerInput(title, 0);
	}
	
	/**
	 * Forces the user to enter an integer.
	 * Asks again, until the input is a valid
	 * integer and between 0 and limit.
	 * 
	 * @param  title Description of the input value
	 * @param  limit The input must be between 0 and limit
	 * @return The requested integer
	 */
	public static int getIntegerInput(String title, int limit) {
		Log.info("Asking user to enter an integer (limit " + limit + "): " + title);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int integer;
		
		while (true) {
			System.out.print(title + ": ");
			
			try {
				integer = Integer.parseInt(br.readLine());
				if (limit != 0 && (integer >= limit || integer < 0)) {
					throw new NumberFormatException();
				}
				break;
			}
			catch (NumberFormatException | IOException e) {
				System.out.println("    Error, please try again.");
				Log.info(e.getMessage());
			}
		}
		
		return integer;
	}
	
	/**
	 * Forces the user to enter a boolean (represented
	 * as YES and NO). Asks again, until the input is
	 * valid.
	 * 
	 * @param  title Description of the input value (question to answer with YES/NO)
	 * @param  selection Default value
	 * @return The requested boolean
	 */
	public static boolean getBooleanInput(String title, boolean selection) {
		Log.info("Asking the user to enter a boolean (default " + (selection ? "YES" : "NO") + "): " + title);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		
		while (true) {
			System.out.print(title + " (" + (selection ? "YES/no" : "yes/NO") + ")? ");
			
			try {
				input = br.readLine();
				if (input.isEmpty()) {
					return selection;
				}
				else if (input.equalsIgnoreCase("yes")) {
					return true;
				}
				else if (input.equalsIgnoreCase("no")) {
					return false;
				}
				
				throw new IOException();
			}
			catch (IOException e) {
				System.out.println("    Error, please try again (type \"yes\" or \"no\").");
				Log.warning(e.getMessage());
			}
		}
	}
	
	/**
	 * Forces the user to enter a valid location. First, he
	 * needs to enter a string, this string will be queried
	 * with TransportData and if there are more then one result,
	 * they will be displayed to the user so that he can select
	 * the wanted.
	 * 
	 * @param data TransportData object
	 * @param title Description of the location
	 * @param optional Is empty also valid?
	 * @return A new location or - if optional is true - null
	 */
	public static Location getLocationInput(TransportData data, String title, boolean optional) {
		Log.info("Asking user to enter a location" + (optional ? " (optional)" : "") + ": " + title);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String locationName;
		
		while (true) {
			System.out.print("  * " + title +" location" + (optional ? " (optional)" : "") + ": ");
			
			try {
				locationName = br.readLine();
				break;
			}
			catch (IOException e) {
				System.out.println("    Error, please try again.");
				Log.info(e.getMessage());
			}
		}
		
		if (locationName.isEmpty() && optional) {
			return null;
		}
		
		List<Location> locations = new ArrayList<>();
		for(int i = 0; i <= 3; i++) {
			try {
				locations = data.getPossibleLocationList(locationName);
				break;
			}
			catch (TransportDataException e) {
				// Tries 3 times (unstable Internet connection), after that, returns an empty list
				Log.warning(e.getMessage());
			}
		}
		
		if (locations.size() == 1) {
			return locations.get(0);
		}
		
		System.out.println("    0) Type a new location");
		for(int i = 1; i <= locations.size(); i++) {
			System.out.println("    " + i + ") " + locations.get(i - 1));
		}
		
		int index = getIntegerInput("    Select number", locations.size() + 1);
		
		if (index == 0) {
			return getLocationInput(data, title, optional);
		}
		
		return locations.get(index - 1);
	}
}
