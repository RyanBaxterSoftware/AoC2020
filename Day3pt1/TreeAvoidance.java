import java.lang.*;
import java.util.*;
import java.io.*;

class TreeAvoidance {
	public static void main(String[] args) throws FileNotFoundException{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		ArrayList<String> rows = new ArrayList<String>();
		while(inputFile.hasNextLine()) {
			rows.add(inputFile.nextLine());
		}
		
		int slopeDown = 1;
		int slopeRight = 3;
		int column = 0;
		int row = 0;
		int treesHit = 0;
		
		// It is assumed all rows will be the same length across.
		// It is assumed the top left starting square will be empty.
		System.out.println(rows.get(0));
		for(int x = 1; x < rows.size(); x++) {
			if((x-row)%slopeDown == 0) { //determine if this is a row that will have a point marked
				String currentRow = rows.get(x);
				column += slopeRight;
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
		System.out.println("We experienced " + treesHit + " number of tree hits");
	}
}