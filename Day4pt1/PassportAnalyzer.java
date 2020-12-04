package Day4pt1;
import java.lang.*;
import java.util.*;
import java.io.*;

class PassportAnalyzer {
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
		
		for(int x = 0; x < passports.size(); x++) {
			Passport currentPassport = new Passport();
			System.out.println(passports.get(x));
			currentPassport.consumeInputString(passports.get(x));
		}
	}
}