package ie.gmit.sw;

import java.util.Scanner;

/**
 * Services class provides a variety of methods that will be used throughout the
 * program
 */
public class Services {

	static Scanner scanner = new Scanner(System.in);

	/**
	 * Method to display a string to console and return a string input
	 * 
	 * @return a user entered String
	 * @param String
	 *            Input is a string containing the required input
	 */
	public static String getInputString(String input) {
		String output = "";

		System.out.println(input);
		output = setFileName(scanner.next());
		return output;
	}// getStringInput

	/**
	 * Method to display a string to console and return an integer
	 * 
	 * @return a user entered Integer
	 * @param input
	 *            is a string containing the required input
	 */
	public static int getInputInt(String input) {
		int output;

		System.out.println(input);
		output = scanner.nextInt();

		return output;
	}// getStringInput

	public static String showMainMenu = "Document Similarity APi Main Menu\n1: Start Similarity Check\n2: Exit system";

	/**
	 * @param filename
	 *            the file name that needs to be validated
	 * @return The validated file name
	 */
	public static String setFileName(String fileName) {
		/*
		 * check if fileName has a .txt extension if not append it to the
		 * fileName
		 */
		String ext = ".txt";
		if (!fileName.contains(ext)) {
			return fileName + ext;
		}

		return fileName;
	}
}
