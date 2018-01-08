package ie.gmit.sw;

import java.util.Scanner;

/*
 * Services class provides a variety of methods that will be used throughout the program
 *  */
public class Services {
	
	static Scanner scanner = new Scanner(System.in);

	/*
	 * Method to display a string to console and return a string input
	 */
	public static String getInputString(String input) {

		String output = "";

		System.out.println(input);
		output = scanner.next();

		return output;
	}// getStringInput

	/*
	 * Method to display a string to console and return an integer
	 */
	public static int getInputInt(String input) {
		int output;
		
		System.out.println(input);
		output = scanner.nextInt();
		
		return output;
	}// getStringInput
	
	public static String showMainMenu ="Document Similarity APi Main Menu\n1: Start Similarity Check\n2: Exit system";
	
}
