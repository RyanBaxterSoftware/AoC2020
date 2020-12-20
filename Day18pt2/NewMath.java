import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class NewMath {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));

		Long sum = 0L;
		while(inputFile.hasNextLine()) {
			String lineToProcess = inputFile.nextLine();
			Long answer = processTotal(lineToProcess); 
			sum += answer;
			System.out.println("We have added in the value of " + answer);
		}
		System.out.println(sum);
	}
	
	public static Long processTotal(String parenthesisMath) {
		Long total = 0L;
		//System.out.println("processing " + parenthesisMath);
		
		ArrayList<Long> allValues = new ArrayList<Long>();
		ArrayList<String> signs = new ArrayList<String>();
		
		String[] firstNumber = getNextIntAndRemainder(parenthesisMath);
		total += Long.parseLong(firstNumber[0]);
		System.out.println("First number found is " + firstNumber[0] + " out of " + parenthesisMath);
		parenthesisMath = firstNumber[1];
		allValues.add(Long.parseLong(firstNumber[0]));
		while(parenthesisMath != null) {
			String[] splitForSign = split(parenthesisMath, " ");
			String mathSymbol = splitForSign[0];
			signs.add(mathSymbol);
			String[] nextNumSplit = getNextIntAndRemainder(splitForSign[1]);
			allValues.add(Long.parseLong(nextNumSplit[0]));
			parenthesisMath = nextNumSplit[1];
		}
		for(int x = 0; x < allValues.size(); x++) {
			System.out.print(allValues.get(x) + ",");
			if(signs.size() > x) {
				System.out.print(signs.get(x));
			}
		}
		System.out.println("");
		for(int x = 0; x < signs.size(); x++) {
			if(signs.get(x).equals("+")) {
				Long tempAnswer = allValues.get(x) + allValues.get(x+1);
				allValues.remove(x);
				allValues.set(x, tempAnswer);
				signs.remove(x);
				x--;
			}
		}
		for(int x = 0; x < allValues.size(); x++) {
			System.out.print(allValues.get(x) + ",");
			if(signs.size() > x) {
				System.out.print(signs.get(x));
			}
		}
		System.out.println("");
		Long finalAnswer = 1L;
		for(int x = 0; x < allValues.size(); x++) {
			finalAnswer *= allValues.get(x);
		}
		return finalAnswer;
	}
	
	public static String[] getNextIntAndRemainder(String input) {
		String[] results = new String[2];
		if(input != null) {
			String total = "";
			String remainder = "";
			String[] splitResults = split(input, " ");
			if(splitResults[0].substring(0,1).equals("(")) {
				int numberOfOpenParens = 1;
				int closedParen = -1;
				for(int x = 1; x < input.length() && numberOfOpenParens > 0; x++) {
					if(input.substring(x, x+1).equals("(")) {
						numberOfOpenParens += 1;
					} else if (input.substring(x, x+1).equals(")")) {
						numberOfOpenParens -= 1;
						if(numberOfOpenParens == 0) {
							closedParen = x;
						}
					}
				}
				total = Long.toString(processTotal(input.substring(1, closedParen)));
				//System.out.println(input + " is being split at " + (closedParen+2));
				if(input.length() >= closedParen+2) {
					remainder = input.substring(closedParen+2);
				} else {
					remainder = null;
				}
				//System.out.println("That gives us a remaining line of " + remainder);
			} else {
				total = splitResults[0];
				remainder = splitResults[1];
			}
			results[0] = total;
			results[1] = remainder;
		} else {
			results[0] = null;
			results[1] = null;
		} 
		return results;
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