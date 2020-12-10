import java.lang.*;
import java.util.*;
import java.io.*;

public class ComputerSample {
	
	public static void main(String[] args) throws Exception{
		int acc = 0;
		ArrayList<Integer> previousSteps = new ArrayList<Integer>();
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		int position = 0;
		while(inputFile.hasNextLine()) {
			String rule = inputFile.nextLine();
			computerLines.put(position, rule);
			position++;
		}
		
		int currentPos = 0;
		// Run the current line
		while(!previousSteps.contains(currentPos)) {
			previousSteps.add(currentPos);
			String lineToRun = computerLines.get(currentPos);
			String[] command = split(lineToRun, " ");
			int workingValue = Integer.parseInt(command[1]);
			if(command[0].equals("acc")) {
				acc += workingValue;
				currentPos++;
			} else if (command[0].equals("jmp")) {
				currentPos += workingValue;
			} else if (command[0].equals("nop")) {
				currentPos++;
			} else {
				System.out.println("WHAT?? unknown command " + command[0]);
			}
		}
		System.out.println("We've repeated a line!");
		
		System.out.println("Value of accumulator is " + acc);
	}
	
	public static Dictionary<Integer, String> computerLines = new Hashtable<Integer, String>();
	
	//A method that will split a string into two strings around the first occurence of a character.
	public static String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		System.out.println("Looking for " + c + " in the string " + inputString);
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+c.length()).trim();
			System.out.println("Found results of " + responseString[0] + " and " + responseString[1]);
		}
		else {
			responseString[0] = inputString;
			System.out.println("didn't find " + c + " in line " + inputString);
		}
		return responseString;
	}
	
}