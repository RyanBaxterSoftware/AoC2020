import java.lang.*;
import java.util.*;
import java.io.*;

public class BusAnalysis {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		int timeToLeave = -1;
		if(inputFile.hasNextLine()) {
			timeToLeave = Integer.parseInt(inputFile.nextLine());
		}
		String allBusSchedules = "";
		if(inputFile.hasNextLine()) {
			allBusSchedules = inputFile.nextLine();
		}
		
		String remainingBuses = allBusSchedules;
		ArrayList<Integer> buses = new ArrayList<Integer>();
		do {
			String[] splitResults = split(remainingBuses, ",");
			if(splitResults[0].equals("x")) {
				buses.add(-1);
			} else {
				buses.add(Integer.parseInt(splitResults[0]));
			}
			remainingBuses = splitResults[1];
		} while (remainingBuses != null);
		System.out.println(buses);
		
		ArrayList<Integer> busTimeOverLeaveTime = new ArrayList<Integer>();
		for(int x = 0; x < buses.size(); x++) {
			if(buses.get(x) <= 0) { // it won't increase in this case
				busTimeOverLeaveTime.add(0);
			} else {
				int recurringValue = 0;
				while(recurringValue < timeToLeave) {
					recurringValue += buses.get(x);
				}
				busTimeOverLeaveTime.add(recurringValue);
			}
		}
		
		int smallestDifference = Integer.MAX_VALUE;
		int occurenceOfSmallestDifference = -1;
		for(int x = 0; x < busTimeOverLeaveTime.size(); x++) {
			int thisDifference = busTimeOverLeaveTime.get(x) - timeToLeave;
			if(thisDifference >= 0 && thisDifference < smallestDifference) {
				smallestDifference = thisDifference;
				occurenceOfSmallestDifference = x;
			}
		}
		
		System.out.println("The closest time to the departure time is " + busTimeOverLeaveTime.get(occurenceOfSmallestDifference) + " and the bus that does that is " + buses.get(occurenceOfSmallestDifference));
		System.out.println("So the multiplied result of the wait time and id is " + (buses.get(occurenceOfSmallestDifference) * smallestDifference));
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