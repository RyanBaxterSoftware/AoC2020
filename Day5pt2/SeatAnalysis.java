import java.lang.*;
import java.util.*;
import java.io.*;

class SeatAnalysis {
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		
		ArrayList<Integer> seatIds = new ArrayList<Integer>();	
		while(inputFile.hasNextLine()) {
			String currentRow = inputFile.nextLine();
			String rowDesignation = currentRow.substring(0, 7);
			int rowRangeLow = 0;
			int rowRangeHigh = 127;
			for(int y = 0; y < rowDesignation.length(); y++) {
				double halfPoint = ((rowRangeLow + rowRangeHigh)/2)+.5;
				if(rowDesignation.substring(y,y+1).equals("F")) {
					rowRangeHigh = (int) Math.floor(halfPoint);
				} else if (rowDesignation.substring(y,y+1).equals("B")) {
					rowRangeLow = (int) Math.ceil(halfPoint);
				}
			}
			int row = rowRangeLow;
			String columnDesignation = currentRow.substring(7);
			int columnRangeLow = 0;
			int columnRangeHigh = 7;
			for(int y = 0; y < columnDesignation.length(); y++) {
				double halfPoint = ((columnRangeLow + columnRangeHigh)/2)+.5;
				if(columnDesignation.substring(y,y+1).equals("L")) {
					columnRangeHigh = (int)Math.floor(halfPoint);
				} else if (columnDesignation.substring(y,y+1).equals("R")) {
					columnRangeLow = (int)Math.ceil(halfPoint);
				}
			}
			int column = columnRangeLow;
			
			if(columnRangeLow != columnRangeHigh || rowRangeLow != rowRangeHigh) {
				System.out.println("Something went wrong. ColumnRange: " + columnRangeLow + ":" + columnRangeHigh + " and the row range is " + rowRangeLow + ":" + rowRangeHigh);
				System.in.read();
			}
			
			int seatID = (row * 8) + column;
			seatIds.add(seatID);
			
			
		}
		for(int x = 0; x < 931;x++) {
			if(!seatIds.contains(x) && seatIds.contains(x+1) && seatIds.contains(x-1)) {
				System.out.println("There is a missing seat (that exists) of " + x);
			}
		}
	}
}