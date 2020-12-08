import java.lang.*;
import java.util.*;
import java.io.*;

public class CustomsLuggage5000 {
	
	public static Dictionary<String, ArrayList<String> > ruleBook = new Hashtable<String, ArrayList<String> >();
		
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		while(inputFile.hasNextLine()) {
			String rule = inputFile.nextLine();
			
			String[] rulesSet = split(rule, " contain ");
			String key = rulesSet[0];
			String entries = rulesSet[1];
			ArrayList<String> entriesList = new ArrayList<String>();
			do {
				String[] tempSolutions = split(entries, ", ");
				entriesList.add(tempSolutions[0].trim());
				entries = tempSolutions[1];
			} while (entries != null);	
			ruleBook.put(key, entriesList);
		}
		
		System.out.println(ruleBook);
	}
	
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
	
	// recursive method that returns a list of all bags in that bag.
	public static String processBag(String bagName) {
		String listOfBags = "";
		ArrayList<String> resultBags = ruleBook.get(bagName);
		for(int x = 0; x < resultBags.size(); x++) {
			listOfBags += resultBags.get(x);
			listOfBags += processBag(resultBags);
		}
		return listOfBags;
	}
}