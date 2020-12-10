import java.lang.*;
import java.util.*;
import java.io.*;

public class NumberCruncher {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		// previous solution was 26796446
		// Clearing work for simplicity
		
		long sumToFind = 26796446;
		ArrayList<Long> allValues = new ArrayList<Long>();
		boolean sumFound = false;
		while(inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			Long lineLong = Long.parseLong(line);
			allValues.add(lineLong);
		}
		System.out.println(allValues);
		for(int x = 0; x < allValues.size() && !sumFound; x++) {
			long sum = 0;
			ArrayList<Long> allNumsInRange = new ArrayList<Long>();
			for(int y = 0; y + x < allValues.size() && sum < sumToFind; y++) {
				sum += allValues.get(x+y);
				allNumsInRange.add(allValues.get(x+y));
			}
			if(sum == sumToFind) {
				System.out.println("Found the range! Here they are: " + allNumsInRange);
				long lowNum = lowestVal(allNumsInRange);
				long highNum = highestVal(allNumsInRange);
				long total = lowNum + highNum;
				sumFound = true;
				System.out.println("Low num is " + lowNum + " and high num is " + highNum + " so their sum is " + total);
			}
			System.out.println("Found sum of " + sum + " from " + allNumsInRange);
		}
	}
	
	public static boolean isItASum(Long testNum, ArrayList<Long> preamble) {
		boolean found = false;
		System.out.println("About to test " + testNum + " against " + preamble);
		for(int x = 0; x < preamble.size() && !found; x++) {
			for(int y = x+1; y < preamble.size() && !found; y++) {
				if(testNum == preamble.get(x) + preamble.get(y)) {
					found = true;
					System.out.println(testNum + " is a sum of " + preamble.get(x) + " and " + preamble.get(y));
				}
			}
		}
		return found;
	}
	
	public static Long lowestVal(ArrayList<Long> nums) {
		Long lowestNum  = Long.MAX_VALUE;
		for(int x = 0; x < nums.size(); x++) {
			if(nums.get(x) < lowestNum) {
				lowestNum = nums.get(x);
			}
		}
		return lowestNum;
	}
	
	public static Long highestVal(ArrayList<Long> nums) {
		Long highestNum  = (long)-1;
		for(int x = 0; x < nums.size(); x++) {
			if(nums.get(x) > highestNum) {
				highestNum = nums.get(x);
			}
		}
		return highestNum;
	}
}