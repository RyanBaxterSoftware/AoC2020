import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class TicketAnalyzer {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		String lineToProcess = inputFile.nextLine(); // Assuming a valid file is passed in.
		// This order is done to check the empty lines in the while loops.
		ArrayList<Integer> allValuesThatAreValid = new ArrayList<Integer>();
		while(!lineToProcess.equals("") && inputFile.hasNextLine()) {
			String[] ruleSplit = split(lineToProcess, ": ");
			String[] rangeSplit = split(ruleSplit[1], " or ");
			String[] firstRange = split(rangeSplit[0], "-");
			String[] secondRange = split(rangeSplit[1], "-");
			
			for(int startingVal = Integer.parseInt(firstRange[0]); startingVal <= Integer.parseInt(firstRange[1]);startingVal++) {
				allValuesThatAreValid.add(startingVal);
				//System.out.println("Adding " + startingVal);
			}
			for(int startingVal = Integer.parseInt(secondRange[0]); startingVal <= Integer.parseInt(secondRange[1]);startingVal++) {
				allValuesThatAreValid.add(startingVal);
				//System.out.println("Adding " + startingVal);
			}
			
			// process rules
			lineToProcess = inputFile.nextLine();
		}
		inputFile.nextLine(); // "your ticket"
		lineToProcess = "nonEmptyString";
		while(!lineToProcess.equals("") && inputFile.hasNextLine()) {
			lineToProcess = inputFile.nextLine();
			System.out.println(lineToProcess);
			// skip over our ticket for now.
		}
		inputFile.nextLine(); // "nearby tickets"
		int sumOfInvalid = 0;
		System.out.println(allValuesThatAreValid);
		while(inputFile.hasNextLine()) {
			lineToProcess = inputFile.nextLine();
			// process all other tickets
			String splitResults[] = split(lineToProcess, ",");
			System.out.println(splitResults[0] + " " + splitResults[1]);
			int currentInteger = Integer.parseInt(splitResults[0]);
			if(!allValuesThatAreValid.contains(currentInteger)) {
				System.out.println(currentInteger + " was not found in the list");
				sumOfInvalid += currentInteger;
			} else {
				System.out.println(currentInteger + " was found in the list");
			}
			while(splitResults[1] != null) {
				splitResults = split(splitResults[1], ",");
				currentInteger = Integer.parseInt(splitResults[0]);
				if(!allValuesThatAreValid.contains(currentInteger)) {
					System.out.println(currentInteger + " was not found in the list");
					sumOfInvalid += currentInteger;
				} else {
					System.out.println(currentInteger + " was found in the list");
				}
			}
		}
		System.out.println("The total of all values found outside of any rules is " + sumOfInvalid);
		
	}
	
	//A method that will split a string into two strings around the first occurence of a character.
	public static String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		//System.out.println("Looking for " + c + " in the string " + inputString);
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+c.length()).trim();
		}
		else {
			responseString[0] = inputString;
			//System.out.println("didn't find " + c + " in line " + inputString);
		}
		return responseString;
	}
}