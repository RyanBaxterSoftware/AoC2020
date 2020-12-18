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

		Map<String, ArrayList<Integer> > allFields = new HashMap<String, ArrayList<Integer> >();
		while(!lineToProcess.equals("") && inputFile.hasNextLine()) {
			String[] ruleSplit = split(lineToProcess, ": ");
			String[] rangeSplit = split(ruleSplit[1], " or ");
			String[] firstRange = split(rangeSplit[0], "-");
			String[] secondRange = split(rangeSplit[1], "-");
			ArrayList<Integer> thisFieldsRange = new ArrayList<Integer>();
			for(int startingVal = Integer.parseInt(firstRange[0]); startingVal <= Integer.parseInt(firstRange[1]);startingVal++) {
				allValuesThatAreValid.add(startingVal);
				thisFieldsRange.add(startingVal);
				//System.out.println("Adding " + startingVal);
			}
			for(int startingVal = Integer.parseInt(secondRange[0]); startingVal <= Integer.parseInt(secondRange[1]);startingVal++) {
				allValuesThatAreValid.add(startingVal);
				thisFieldsRange.add(startingVal);
				//System.out.println("Adding " + startingVal);
			}
			allFields.put(ruleSplit[0], thisFieldsRange);
			
			// process rules
			lineToProcess = inputFile.nextLine();
		}
		inputFile.nextLine(); // "your ticket"
		lineToProcess = "nonEmptyString";
		String ourTicket = "";
		while(!lineToProcess.equals("") && inputFile.hasNextLine()) {
			ourTicket = lineToProcess;
			lineToProcess = inputFile.nextLine();
			System.out.println(lineToProcess);
			// skip over our ticket for now.
		}
		System.out.println("Our ticket is " + ourTicket);
		System.in.read();
		inputFile.nextLine(); // "nearby tickets"
		//System.out.println(allValuesThatAreValid);
		ArrayList<String> validTickets = new ArrayList<String>();
		while(inputFile.hasNextLine()) {
			boolean ticketIsValid = true;
			lineToProcess = inputFile.nextLine();
			// process all other tickets
			String splitResults[] = split(lineToProcess, ",");
			int currentInteger = Integer.parseInt(splitResults[0]);
			if(!allValuesThatAreValid.contains(currentInteger)) {
				System.out.println(currentInteger + " was not found in the list");
				ticketIsValid = false;
			}
			while(splitResults[1] != null) {
				splitResults = split(splitResults[1], ",");
				currentInteger = Integer.parseInt(splitResults[0]);
				if(!allValuesThatAreValid.contains(currentInteger)) {
					System.out.println(currentInteger + " was not found in the list");
					ticketIsValid = false;
				}
			}
			if(ticketIsValid) {
				validTickets.add(lineToProcess);
				System.out.println("Adding " + lineToProcess);
			}
		}
		//System.out.println(allFields);
		Map<Integer, ArrayList<String> > everyPositionPossibility = new HashMap<Integer, ArrayList<String> >();
		for(int x = 0; x < validTickets.size(); x++) {
			String remainingTicket = validTickets.get(x);
			int currentValue = 0;
			while(remainingTicket != null) {
				String[] splitResults = split(remainingTicket, ",");
				remainingTicket = splitResults[1];
				int fieldValue = Integer.parseInt(splitResults[0]);
				currentValue++;
				if(!everyPositionPossibility.containsKey(currentValue)){
					ArrayList<String> possibleFields = new ArrayList<String>();
					possibleFields.addAll(allFields.keySet());
					everyPositionPossibility.put(currentValue, possibleFields);
				}
				ArrayList<String> allPosibilities = everyPositionPossibility.get(currentValue);
				for(int y = 0; y < allPosibilities.size(); y++) {
					//System.out.println(allPosibilities.get(y));
					if(!allFields.get(allPosibilities.get(y)).contains(fieldValue)) {
						System.out.println(fieldValue + " is not in " + allPosibilities.get(y));// + ": " + allFields.get(allPosibilities.get(y)));
						allPosibilities.remove(y);
						y--;
					}
				}
			}
			System.out.println(everyPositionPossibility);
		}
		ArrayList<Integer> finishedPositions = new ArrayList<Integer>();
		for(int x = 1; x <= everyPositionPossibility.size(); x++) {
			ArrayList<String> currentList = everyPositionPossibility.get(x);
			if(currentList.size() == 1 && !finishedPositions.contains(x)) {
				finishedPositions.add(x);
				String foundDefinite = currentList.get(0);
				for(int y = 1; y <= everyPositionPossibility.size(); y++) {
					if(everyPositionPossibility.get(y).contains(foundDefinite) && y != x) {
						everyPositionPossibility.get(y).remove(foundDefinite);
					}
				}
				x = 0;
			}
		}
		
		System.out.println(everyPositionPossibility);
		ArrayList<Integer> positionsWithDeparture = new ArrayList<Integer>();
		for(int x = 1; x <= everyPositionPossibility.size(); x++) { // assumes at this point all spots have exactly one possible field name
			String thisPositionName = everyPositionPossibility.get(x).get(0);
			if(split(thisPositionName, " ")[0].equals("departure")) {
				System.out.println(thisPositionName + " at position " + x + " starts with departure");
				positionsWithDeparture.add(x);
			}
		}
		
		int currentOurTicketSpot = 1;
		Long sum = 1L;
		while(ourTicket != null) {
			String[] splitResults = split(ourTicket, ",");
			if(positionsWithDeparture.contains(currentOurTicketSpot)) {
				System.out.println("Found a departure of " + splitResults[0]);
				sum *= Integer.parseInt(splitResults[0]);
				System.out.println("New result is " + sum);
			}
			ourTicket = splitResults[1];
			currentOurTicketSpot++;
		}
		System.out.println("Total is " + sum);
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