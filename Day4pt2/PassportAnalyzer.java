package Day4pt2;
import java.lang.*;
import java.util.*;
import java.io.*;

public class PassportAnalyzer {
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		ArrayList<String> passports = new ArrayList<String>();
		String currentPassportText = "";
		while(inputFile.hasNextLine()) {
			String currentLine = inputFile.nextLine();
			if(currentLine.equals("")) {
				passports.add(currentPassportText);
				currentPassportText = "";
			}
			else {
				currentPassportText += currentLine + " ";
			}
		}
		int validPassports = 0;
		for(int x = 0; x < passports.size(); x++) {
			Passport currentPassport = new Passport();
			System.out.println(passports.get(x));
			currentPassport.consumeInputString(passports.get(x));
			String missingFieldsForPassport = currentPassport.validateFields();
			if (missingFieldsForPassport.equals("")) {
				System.out.println("This passport has all required fields! It is valid.");
				validPassports++;
			} else if (missingFieldsForPassport.equals("cid")) {
				System.out.println("This passport is incorrect in the CID field, but we'll let it slide for now ;3");
				validPassports++;
			} else {
				System.out.println("This passport is invalid as it is incorrect in the following fields: " + missingFieldsForPassport);
			}
			System.out.println("Running total: " + validPassports);
		}
		
		System.out.println("The total number of valid passports found is " + validPassports);
	}
}