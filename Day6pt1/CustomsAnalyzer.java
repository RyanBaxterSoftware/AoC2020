import java.lang.*;
import java.util.*;
import java.io.*;

public class CustomsAnalyzer {
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		int totalAnswers = 0;
		
		String currentGroup = "";
		String currentAnswers = "";
		int groupSum = 0;
		while(inputFile.hasNextLine()) {
			currentAnswers = inputFile.nextLine();
			groupSum = 0;
			if(!currentAnswers.trim().equals("")) {
				System.out.println("Found contents " + currentAnswers);
				currentGroup += currentAnswers;
			} else {
				System.out.println("Found a blank line. Total line is " +currentGroup);
				for(int x = 0; x < currentGroup.length(); x++ ) {
					System.out.println("The current question (" + currentGroup.substring(x,x+1) + ") is being compared to the previous questions (" + currentGroup.substring(0,x) + ")");
					System.out.println("The remaining line is " + currentGroup.substring(x+1));
					if(!currentGroup.substring(0,x).contains(currentGroup.substring(x,x+1))) {
						System.out.println("It's unique! up the total");
						groupSum++;
					}
				}
				currentGroup = "";
				totalAnswers += groupSum;
				System.out.println("adding " + groupSum + " for a new total of " + totalAnswers);
				groupSum = 0;
			}
		}
		if(!currentGroup.equals("")) {
			System.out.println("Final group being processed of " +currentGroup);
				for(int x = 0; x < currentGroup.length(); x++ ) {
					System.out.println("The current question (" + currentGroup.substring(x,x+1) + ") is being compared to the previous questions (" + currentGroup.substring(0,x) + ")");
					System.out.println("The remaining line is " + currentGroup.substring(x+1));
					if(!currentGroup.substring(0,x).contains(currentGroup.substring(x,x+1))) {
						groupSum++;
					}
				}
				currentGroup = "";
				totalAnswers += groupSum;
				groupSum = 0;
		}
		System.out.println("The total number of all groups yes answers is " + totalAnswers);
	}
}