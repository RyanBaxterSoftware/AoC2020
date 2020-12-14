import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class MemoryProcessor {
	
	public static ArrayList<Integer> memorySpots = new ArrayList<Integer>();
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
				BigInteger result = passThroughMask(mask, Integer.parseInt(splitResult[1]));
				if(memorySpots.contains(position)) {
					int whereToOverwrite = memorySpots.indexOf(position);
					memoryValues.set(whereToOverwrite, result);
				} else {
						memorySpots.add(position);
						memoryValues.add(result);
				}
			}
		}
		BigInteger sum = new BigInteger("0", 10);
		for(int x = 0; x < memoryValues.size(); x++) {
			sum = sum.add(memoryValues.get(x));
		}
		System.out.println(sum.toString());
	}
	
	public static BigInteger passThroughMask(String mask, int number) {
		String numberAsBinary = Integer.toBinaryString(number);
		while(numberAsBinary.length() < 36) {
			numberAsBinary = "0" + numberAsBinary;
		}
		System.out.println("About to pass the following through mask");
		System.out.println(numberAsBinary);
		System.out.println(mask);
		String resultNumberBinary = "";
		for(int x = (numberAsBinary.length()-1); x >= 0; x--) {
			if(!mask.substring(x, x+1).equals("X")) {
				resultNumberBinary = mask.substring(x, x+1) + resultNumberBinary;
			} else {
				resultNumberBinary = numberAsBinary.substring(x, x+1) + resultNumberBinary;
			}
		}
		System.out.println(resultNumberBinary);
		BigInteger resultNumber = new BigInteger(resultNumberBinary, 2);
		System.out.println("Resulting number is " + resultNumber);
		return resultNumber;
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