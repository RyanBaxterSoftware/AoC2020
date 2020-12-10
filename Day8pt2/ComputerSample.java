import java.lang.*;
import java.util.*;
import java.io.*;

public class ComputerSample {
	
	public static void main(String[] args) throws Exception{
		int acc = 0;
		ArrayList<Integer> previousSteps = new ArrayList<Integer>();
		ArrayList<Integer> jumpSteps = new ArrayList<Integer>();
		ArrayList<Integer> nopSteps = new ArrayList<Integer>();
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		int position = 0;
		while(inputFile.hasNextLine()) {
			String rule = inputFile.nextLine();
			computerLines.put(position, rule);
			position++;
		}
		final int maxPosition = position;
		
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
				jumpSteps.add(currentPos);
				currentPos += workingValue;
			} else if (command[0].equals("nop")) {
				nopSteps.add(currentPos);
				currentPos++;
			} else {
				System.out.println("WHAT?? unknown command " + command[0]);
			}
		}
		System.out.println("We've repeated a line!");
		
		boolean answerFound = false;
		System.out.println("The following lines need to be updated: " + jumpSteps);
		for(int x = 0; (x < jumpSteps.size()) && !answerFound; x++) {
			acc = 0;
			String thisCommand = computerLines.get(jumpSteps.get(x));
			String[] separatedCommand = split(thisCommand, " ");
			computerLines.put(jumpSteps.get(x), "nop " + separatedCommand[1]);
			System.out.println("Changing from jmp to nop at line " + jumpSteps.get(x));
			
			 currentPos = 0;
			// Run the current line
			previousSteps = new ArrayList<Integer>();
			while(!previousSteps.contains(currentPos) && currentPos < computerLines.size()) {
				previousSteps.add(currentPos);
				String lineToRun = computerLines.get(currentPos);
				String[] command = split(lineToRun, " ");
				int workingValue = Integer.parseInt(command[1]);
				if(command[0].equals("acc")) {
					acc += workingValue;
					currentPos++;
					System.out.println("accumulating. Stepping on");
				} else if (command[0].equals("jmp")) {
					currentPos += workingValue;
					System.out.println("jump up " + workingValue);
				} else if (command[0].equals("nop")) {
					currentPos++;
					System.out.println("nop. Stepping on");
				} else {
					System.out.println("WHAT?? unknown command " + command[0]);
				}
			}
			if(currentPos == computerLines.size()) {
				System.out.println("Hey!! We hit it :3 Acc is " + acc);
				answerFound = true;
			}
			System.out.println("Error occured. Stepping on.");
			computerLines.put(jumpSteps.get(x), "jmp " + separatedCommand[1]);
			System.out.println("restoring line");
		}
		for(int x = 0; (x < nopSteps.size()) && !answerFound; x++) {
			acc = 0;
			String thisCommand = computerLines.get(nopSteps.get(x));
			String[] separatedCommand = split(thisCommand, " ");
			computerLines.put(x, "jmp " + separatedCommand[1]);
		}
		
		System.out.println("Value of accumulator is " + acc);
	}
	
	public static Hashtable<Integer, String> computerLines = new Hashtable<Integer, String>();
	
	//A method that will split a string into two strings around the first occurence of a character.
	public static String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+c.length()).trim();
		}
		else {
			responseString[0] = inputString;
		}
		return responseString;
	}
	
}