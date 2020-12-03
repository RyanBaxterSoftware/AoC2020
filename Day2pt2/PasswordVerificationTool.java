import java.lang.*;
import java.util.*;
import java.io.*;

class PasswordVerificationTool {
	public static void main(String[] args) throws FileNotFoundException{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		int totalPasses = 0;
		while(inputFile.hasNextLine()) {
			String fullLine = inputFile.nextLine();
			String[] portions = split(fullLine, ":");
			
			String rule = portions[0];
			String range = split(rule, " ")[0];
			String specifiedCharacter = split(rule, " ")[1];
			int firstVal = Integer.parseInt(split(range, "-")[0]);
			int secondVal = Integer.parseInt(split(range, "-")[1]);
			String password = portions[1];
			System.out.print(fullLine + " ");
			if((password.substring(firstVal-1, firstVal).equals(specifiedCharacter) || password.substring(secondVal-1, secondVal).equals(specifiedCharacter)) && !(password.substring(secondVal-1, secondVal).equals(password.substring(firstVal-1, firstVal)))){
				System.out.println("has passed");
				totalPasses++;
			}
			else {
				System.out.println("has failed");
			}
			System.out.println("compared values: specified " + specifiedCharacter + " and found " + password.substring(firstVal-1, firstVal) + " and " + password.substring(secondVal-1, secondVal));
		}
		System.out.println("Total number of valid passwords: " + totalPasses);
	}	
	
	//A method that will split a string into two strings around the first occurence of a character.
	public static String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+1).trim();
		}
		else {
			responseString[0] = inputString;
		}
		return responseString;
	}
}	