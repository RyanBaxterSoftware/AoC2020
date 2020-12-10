import java.lang.*;
import java.util.*;
import java.io.*;

public class NumberCruncher {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		int position = 0;
		int preambleSize = 25;
		ArrayList<Long> preamble = new ArrayList<Long>();
		boolean nonSumFound = false;
		while(inputFile.hasNextLine() && !nonSumFound) {
			String line = inputFile.nextLine();
			Long lineLong = Long.parseLong(line);
			System.out.println("The line is " + line);
			if(position < preambleSize) {
				preamble.add(lineLong);
			} else {
				long thisNum = lineLong;
				if(!isItASum(thisNum, preamble)) {
					nonSumFound = true;
					System.out.println("The number found that is not a sum is " + thisNum);
				}
				preamble.remove(0);
				preamble.add(lineLong);
			}
			position++;
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
}