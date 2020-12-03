import java.lang.*;
import java.util.*;
import java.io.*;

class TreeAvoidance {
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		ArrayList<String> rows = new ArrayList<String>();
		while(inputFile.hasNextLine()) {
			rows.add(inputFile.nextLine());
		}
		ArrayList<Integer> slopesDownToCheck = new ArrayList<Integer>(Arrays.asList(1,1,1,1,2));
		ArrayList<Integer> slopesRightToCheck = new ArrayList<Integer>(Arrays.asList(1,3,5,7,1));
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		long sum = 1; // so we can multiply it
		// It is assumed all rows will be the same length across.
		// It is assumed the top left starting square will be empty.
		for(int w = 0; w < slopesDownToCheck.size(); w++)
		{
			int treesHit = 0;
			System.out.println(rows.get(0));
			int column = 0;
			for(int x = 1; x < rows.size(); x++) {
				if((x)%slopesDownToCheck.get(w) == 0) { //determine if this is a row that will have a point marked
					String currentRow = rows.get(x);
					column += slopesRightToCheck.get(w);
					if(column >= currentRow.length()) {
						column = column%currentRow.length();
					}
					String adjustedRow = currentRow.substring(0, column);
					String hitOrNot;
					if(currentRow.substring(column, column+1).equals("#")){
						adjustedRow += "X";
						treesHit++;
						hitOrNot = "HIT";
					}
					else {
						adjustedRow += "O";
						hitOrNot = "MISS";
					}
					adjustedRow += currentRow.substring(column+1) + " at this row we experienced a " + hitOrNot;
					System.out.println(adjustedRow);
				}
				else {
					System.out.println(rows.get(x));
				}
			}
			System.out.println("We experienced " + treesHit + " tree hits");
			sum = sum * treesHit;
			System.out.println("Running total is " + sum);
		}
	}
}