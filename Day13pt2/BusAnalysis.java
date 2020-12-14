import java.lang.*;
import java.util.*;
import java.io.*;

public class BusAnalysis {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		if(inputFile.hasNextLine()) {
			inputFile.nextLine();
		}
		String allBusSchedules = "";
		if(inputFile.hasNextLine()) {
			allBusSchedules = inputFile.nextLine();
		}
		
		String remainingBuses = allBusSchedules;
		ArrayList<Long> buses = new ArrayList<Long>();
		do {
			String[] splitResults = split(remainingBuses, ",");
			if(splitResults[0].equals("x")) {
				buses.add(0L);
			} else {
				buses.add(Long.parseLong(splitResults[0]));
			}
			remainingBuses = splitResults[1];
		} while (remainingBuses != null);
		System.out.println(buses);
		
		Long firstOccuranceOfAll = 0L;
		
		Long lcm = buses.get(0);
		for(int x = 1; x < buses.size(); x++ ) {
			if(buses.get(x) > 0) {
				System.out.println("Now processing " + buses.get(x));
				
				while(((firstOccuranceOfAll + x) % buses.get(x)) != 0)
				{
					firstOccuranceOfAll += lcm;
				}
				lcm *= buses.get(x);
			}
			System.out.println("First occurence is now " + firstOccuranceOfAll);
		}
		System.out.println("First occurance of all of these in order is " + firstOccuranceOfAll.toString());
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