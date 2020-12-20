import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class ErrorChecking {
	
	public static HashMap<Integer, ArrayList<String> > rules = new HashMap<Integer, ArrayList<String> >();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		boolean noBlankFound = true;
		while(inputFile.hasNextLine() && noBlankFound) {
			String lineToProcess = inputFile.nextLine();
			if(!lineToProcess.equals("")) {
				String[] splitResults = split(lineToProcess, ": ");
				String[] splitRules = split(splitResults[1], " | ");
				ArrayList<String> allRules = new ArrayList<String>();
				allRules.add(splitRules[0]);
				if(splitRules[1] != null) {
					allRules.add(splitRules[1]);
				}
				rules.put(Integer.parseInt(splitResults[0]), allRules);
			} else {
				noBlankFound = false;
			}
		}
		System.out.println(rules);
		ArrayList<String> validMessage = getAllValues(0);
		int matches = 0;
		while(inputFile.hasNextLine()) {
			String lineToProcess = inputFile.nextLine();
			if(validMessage.contains(lineToProcess)) {
				matches++;
			}
		}
		System.out.println("Total lines that match: " + matches);
	}
	
	public static ArrayList<String> getAllValues(int rule) {
		ArrayList<String> resultsHere = new ArrayList<String>();
		ArrayList<String> thisRuleResult = rules.get(rule);
		for(int x = 0; x < thisRuleResult.size(); x++) {
			String tempResultAddition = "";
			System.out.println(thisRuleResult.get(x));
			if(thisRuleResult.get(x).equals("\"a\"")) {
				resultsHere.add("a");
			} else if (thisRuleResult.get(x).equals("\"b\"")) {
				resultsHere.add("b");
			} else {
				String[] splitRule = split(thisRuleResult.get(x), " ");
				ArrayList<String> ruleResult = getAllValues(Integer.parseInt(splitRule[0]));
				ArrayList<String> ruleResult2 = new ArrayList<String>();
				if(splitRule[1] != null) {
					ruleResult2 = getAllValues(Integer.parseInt(splitRule[1]));
				}
				for(int y = 0; y < ruleResult.size(); y++) {
					String answer = ruleResult.get(y);
					if(ruleResult2.size() > 0) {
						for(int z = 0; z < ruleResult2.size(); z++) {
							resultsHere.add(answer + ruleResult2.get(z));
						}
					} else {
						resultsHere.add(answer);
					}
				}
			}
		}
		return resultsHere;
	}
	
	public static boolean doesStringMatchRules(String input) {
		return false;
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