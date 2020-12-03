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
			int lowVal = Integer.parseInt(split(range, "-")[0]);
			int highVal = Integer.parseInt(split(range, "-")[1]);
			String password = portions[1];
			int specifiedCharacterOccurences = 0;
			for(int x = 0; x < password.length(); x++) {
				if(password.substring(x, x+1).equals(specifiedCharacter)) {
					specifiedCharacterOccurences++;
				}
			}
			System.out.print(fullLine + " ");
			if(lowVal <= specifiedCharacterOccurences && specifiedCharacterOccurences <= highVal)
			{
				System.out.print("has passed");
				totalPasses++;
			}
			else {
				System.out.print("has failed");
			}
			System.out.println(" (" + specifiedCharacterOccurences + " occurences) and needed between " + lowVal + " and " + highVal);
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