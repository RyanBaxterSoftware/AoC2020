import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class MemoryProcessor {
	
	public static ArrayList<BigInteger> memorySpots = new ArrayList<BigInteger>();
	public static ArrayList<BigInteger> memoryValues = new ArrayList<BigInteger>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		String mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
		while(inputFile.hasNextLine()) {
			String lineToProcess = inputFile.nextLine();
			String[] splitResult = split(lineToProcess, " = ");
			if(splitResult[0].equals("mask")) {
				mask = splitResult[1];
			} else {
				String[] memoryResult = split(splitResult[0], "[");
				int position = Integer.parseInt(memoryResult[1].substring(0, memoryResult[1].length() - 1));
				int value = Integer.parseInt(splitResult[1]);
				saveValue(mask, position, value);
			}
		}
		BigInteger sum = new BigInteger("0", 10);
		for(int x = 0; x < memoryValues.size(); x++) {
			sum = sum.add((memoryValues.get(x)));
		}
		System.out.println(sum);
	}
	
	public static void saveValue(String mask, int number, int value) {
		String numberAsBinary = Integer.toBinaryString(number);
		ArrayList<String> allDestinations = new ArrayList<String>();
		allDestinations.add("");
		while(numberAsBinary.length() < 36) {
			numberAsBinary = "0" + numberAsBinary;
		}
		System.out.println("About to pass the following through mask");
		System.out.println(numberAsBinary);
		System.out.println(mask);
		for(int x = (numberAsBinary.length()-1); x >= 0; x--) {
			String currentMaskValue = mask.substring(x, x+1);
			String foundValue = "";
			int currentDestinationCount = allDestinations.size();
			for(int y = 0; y < currentDestinationCount; y++)
			{
				if(currentMaskValue.equals("1")) {
					allDestinations.set(y, mask.substring(x, x+1) + allDestinations.get(y));
				} else if (currentMaskValue.equals("0")) {
					allDestinations.set(y, numberAsBinary.substring(x, x+1) + allDestinations.get(y));
				} else if (currentMaskValue.equals("X")) {
					allDestinations.add("1" + allDestinations.get(y));
					allDestinations.set(y, "0" + allDestinations.get(y));
				}
			}
		}
		for(int x = 0; x < allDestinations.size(); x++) {
			System.out.println(allDestinations.get(x));
			BigInteger position = new BigInteger(allDestinations.get(x), 2);
			if(memorySpots.contains(position)) {
				int whereToOverwrite = memorySpots.indexOf(position);
				memoryValues.set(whereToOverwrite, BigInteger.valueOf(value));
			} else {
				memorySpots.add(position);
				memoryValues.add(BigInteger.valueOf(value));
			}
		}
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