package Day4pt2;
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
	
	public String validateFields() {
		String fields = "";
		if(cid.equals("")) { // All other rules are being ignored
			fields += "cid,";
		}
		if(pid.equals("") || pid.length() != 9 || !isNumeric(pid)) {
			fields += "pid,";
		}
		if(byr.equals("") || byr.length() != 4 || !isNumeric(byr) || Integer.parseInt(byr) < 1920 || Integer.parseInt(byr) > 2002) {
			fields += "byr,";
		}
		if(iyr.equals("") || iyr.length() != 4 || !isNumeric(iyr) || Integer.parseInt(iyr) < 2010 || Integer.parseInt(iyr) > 2020) {
			fields += "iyr,";
		}
		if(eyr.equals("") || eyr.length() != 4 || !isNumeric(eyr) || Integer.parseInt(eyr) < 2020 || Integer.parseInt(eyr) > 2030) {
			fields += "eyr,";
		}
		if(!heightApproval(hgt)) {
			fields += "hgt,";
		}
		if(hcl.equals("") || !hcl.substring(0,1).equals("#") || !(hcl.substring(1).matches("[0-9a-zA-Z]+")) || !(hcl.length() == 7)) {
			fields += "hcl,";
		}
		if(ecl.equals("") || !(ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") || ecl.equals("gry") || ecl.equals("grn") || ecl.equals("hzl") || ecl.equals("oth"))) {
			fields += "ecl,";
		}
		
		if(!fields.equals("")) {
			fields = fields.substring(0, fields.length()-1);
		}
		System.out.println("Incorrect info in the following: " + fields);
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
	
	public boolean isNumeric(String inputString) {
		boolean isNumeric = false;
		try {
			Integer.parseInt(inputString);
			isNumeric = true;
		} catch(Exception e) {
			
		}
		return isNumeric;
	}
	
	public boolean heightApproval(String height) {
		boolean heightValid = false;
		//System.out.println("The height value is " + height.substring(height.length()-2));
		if (height.length() >= 2 && height.substring(height.length()-2).equals("cm")) {
			try {
				int number = Integer.parseInt(height.substring(0, height.length()-2));
			    if(number >= 150 && number <= 193) {
					heightValid = true;
				}
			} catch (Exception e) {
				
			}
		} else if (height.length() >= 2 && height.substring(height.length()-2).equals("in")) {
			try {
				int number = Integer.parseInt(height.substring(0, height.length()-2));
				if(number >= 59 && number <= 76) {
					heightValid = true;
				}
			} catch (Exception e) {
				
			}
		} // if neither of these types are present, it won't be valid
		return heightValid;
	}
}