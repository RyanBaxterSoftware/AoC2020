package Day4pt1;
import java.lang.*;
import java.util.*;
import java.io.*;

public class Passport {
	public String cid = "";
	public String byr = "";
	public String iyr = "";
	public String eyr = "";
	public String hgt = "";
	public String hcl = "";
	public String ecl = "";
	public String pid = "";
	
	public Passport() {}
	
	public void consumeInputString(String fieldsAsAString) throws Exception {
		ArrayList<String> allEntries = new ArrayList<String>();
		String remainingElements = fieldsAsAString;
		do {
			String[] splitting = split(remainingElements, " ");
			allEntries.add(splitting[0]);
			remainingElements = splitting[1];
		} while (!remainingElements.equals(""));
		for(int y = 0; y < allEntries.size(); y++) {
			String[] splittingResults = split(allEntries.get(y), ":");
			if(splittingResults[0].equals("cid")) {
				cid = splittingResults[1];
			} else if(splittingResults[0].equals("pid")) {
				pid = splittingResults[1];
			} else if(splittingResults[0].equals("byr")) {
				byr = splittingResults[1];
			} else if(splittingResults[0].equals("iyr")) {
				iyr = splittingResults[1];
			} else if(splittingResults[0].equals("eyr")) {
				eyr = splittingResults[1];
			} else if(splittingResults[0].equals("hgt")) {
				hgt = splittingResults[1];
			} else if(splittingResults[0].equals("hcl")) {
				hcl = splittingResults[1];
			} else if(splittingResults[0].equals("ecl")) {
				ecl = splittingResults[1];
			} else {
				System.out.println("Error! Not recognized type. " + allEntries.get(y));
				System.in.read();
			}// otherwise info not recognized
		}
	}
	
	public String missingFields() {
		String fields = "";
		if(cid.equals("")) {
			fields += "cid,";
		}
		if(pid.equals("")) {
			fields += "pid,";
		}
		if(byr.equals("")) {
			fields += "byr,";
		}
		if(iyr.equals("")) {
			fields += "iyr,";
		}
		if(eyr.equals("")) {
			fields += "eyr,";
		}
		if(hgt.equals("")) {
			fields += "hgt,";
		}
		if(hcl.equals("")) {
			fields += "hcl,";
		}
		if(ecl.equals("")) {
			fields += "ecl,";
		}
		
		if(!fields.equals("")) {
			fields = fields.substring(0, fields.length()-1);
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