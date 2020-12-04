package Day4pt1;
import java.lang.*;
import java.util.*;
import java.io.*;

class Passport {
	public String cid = "";
	public String byr = "";
	public String iyr = "";
	public String eyr = "";
	public String hgt = "";
	public String hcl = "";
	public String ecl = "";
	public String pid = "";
	
	public Passport() {}
	
	public void consumeInputString(String fieldsAsAString) {
		ArrayList<String> allEntries = new ArrayList<String>();
		String remainingElements = fieldsAsAString;
		do {
			String[] splitting = split(remainingElements, " ");
			allEntries.add(splitting[0]);
			remainingElements = splitting[1];
		} while (!remainingElements.equals(""));
		for(int y = 0; y < allEntries.size(); y++) {
			System.out.println(allEntries.get(y));
		}
	}
	
	public String missingFields() {
		String fields = "";
		if(cid.equals("")) {
			fields += "cid,";
		}
		
		if(!fields.equals("")) {
			fields = fields.substring(0, fields.length());
		}
		System.out.println("Missing the following: " + fields);
		return fields;
	}
	
	
	//A method that will split a string into two strings around the first occurence of a character.
	public String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+1).trim();
		}
		else {
			responseString[0] = inputString;
			responseString[1] = "";
		}
		return responseString;
	}
}