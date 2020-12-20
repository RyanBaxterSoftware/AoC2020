import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class ErrorChecking {
	
	public static HashMap<Integer, String> rules = new HashMap<Integer, String>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		boolean noBlankFound = true;
		while(inputFile.hasNextLine() && noBlankFound) {
			String lineToProcess = inputFile.nextLine();
			if(!lineToProcess.equals("")) {
				String[] splitResults = split(lineToProcess, ": ");
				
				rules.put(Integer.parseInt(splitResults[0]), splitResults[1]);
			} else {
				noBlankFound = false;
			}
		}
		System.out.println(rules);
		List<String> messages = new ArrayList<String>();
		while(inputFile.hasNextLine()) {
			messages.add(inputFile.nextLine());
		}
		part2(rules, messages);
	}
	
	private static void part2(HashMap<Integer, String> rules, List<String> messages) {

        String regex = rules.get(0);
        long prev = 0;
        while (true) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();

            final String pattern = "^" + regex.replaceAll("([ \"])|42|31", "") + "$";

            final long count = messages.stream().filter(a -> a.matches(pattern)).count();
            if (count > 0 && count == prev) {
                System.out.println(count);
                return;
            }
            
            prev = count;
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