import java.lang.*;
import java.util.*;
import java.io.*;

public class AdapterOrderer {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		ArrayList<Integer> allValues = new ArrayList<Integer>();
		while(inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			int lineInt = Integer.parseInt(line);
			allValues.add(lineInt);
		}
		ArrayList<Integer> sortedList = order(allValues);
		System.out.println(sortedList);
		int previousNum = 0;
		ArrayList<Integer> oneJump = new ArrayList<Integer>();
		ArrayList<Integer> twoJump = new ArrayList<Integer>();
		ArrayList<Integer> threeJump = new ArrayList<Integer>();
		boolean continuing = true;
		for(int x = 0; x < sortedList.size() && continuing; x++) {
			if(sortedList.get(x) - previousNum == 1) {
				oneJump.add(sortedList.get(x));
				System.out.println(sortedList.get(x) + " and " + previousNum + " have a difference of one ");
			} else if(sortedList.get(x) - previousNum == 2) {
				twoJump.add(sortedList.get(x));
				System.out.println(sortedList.get(x) + " and " + previousNum + " have a difference of two ");
			} else if(sortedList.get(x) - previousNum == 3) {
				threeJump.add(sortedList.get(x));
				System.out.println(sortedList.get(x) + " and " + previousNum + " have a difference of three ");
			} else {
				continuing = false;
			}
			previousNum = sortedList.get(x);
		}
		System.out.println("We have " + oneJump.size() + " one jumps and " + (threeJump.size()+1) + " three jumps so multiplied that is " + (oneJump.size() * (threeJump.size()+1)));
		
	}
	
	public static ArrayList<Integer> order(ArrayList<Integer> listToSort) {
		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		if(listToSort.size() <= 1) {
			for(int x = 0; x < listToSort.size(); x++) {
				sortedList.add(listToSort.get(x));
			}
		} else {
			ArrayList<Integer> firstHalf = new ArrayList<Integer>();
			ArrayList<Integer> secondHalf = new ArrayList<Integer>();
			for(int x = 0; x < listToSort.size(); x++) {
				if(x%2 == 0) {
					firstHalf.add(listToSort.get(x));
				} else {
					secondHalf.add(listToSort.get(x));
				}
			}
			firstHalf = order(firstHalf);
			secondHalf = order(secondHalf);
			while(firstHalf.size() != 0 && secondHalf.size() != 0) {
				if(firstHalf.get(0) < secondHalf.get(0)) {
					sortedList.add(firstHalf.get(0));
					firstHalf.remove(0);
				} else {
					sortedList.add(secondHalf.get(0));
					secondHalf.remove(0);
				}
			}
			while(firstHalf.size() != 0) {
				sortedList.add(firstHalf.get(0));
				firstHalf.remove(0);
			}
			while(secondHalf.size() != 0) {
				sortedList.add(secondHalf.get(0));
				secondHalf.remove(0);
			}
		}
		return sortedList;
	}
}