import java.lang.*;
import java.util.*;
import java.io.*;

public class SeatAnalysis {
	
	public static ArrayList<String> seats = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		System.out.println("this".substring(0,4));
		while(inputFile.hasNextLine()) {
			seats.add(inputFile.nextLine());
		}
		boolean wasChange = true;
		while(wasChange) {
			wasChange = false;
			ArrayList<String> nextIterationOfSeats = new ArrayList<String>();
			for(int x = 0; x < seats.size();x++) {
				String theseSeats = seats.get(x);
				String newSeats = "";
				for(int y = 0; y < theseSeats.length(); y++) {
					String newSeat = processSeat(x, y);
					newSeats += newSeat;
				}
				if(!seats.get(x).equals(newSeats)){
					wasChange = true;
				}
				nextIterationOfSeats.add(newSeats);
			}
			for(int x = 0; x < nextIterationOfSeats.size(); x++) {
				System.out.println(nextIterationOfSeats.get(x));
			}
			System.out.println("");
			seats = nextIterationOfSeats;
		}
		int totalSeatsTaken = 0;
		for(int x = 0; x < seats.size(); x++) {
			String thisRow = seats.get(x);
			for (int y = 0; y < thisRow.length(); y++) {
				String seatHere = "";
				if(y == thisRow.length()-1){
					seatHere = thisRow.substring(y);
				} else {
					seatHere = thisRow.substring(y, y+1);
				}
				if(seatHere.equals("#")){
					totalSeatsTaken++;
				}
			}
		}
		System.out.println("Total number of taken seats is " + totalSeatsTaken);
	}
	
	public static String processSeat(int x, int y) {
		String thisSeatRow = seats.get(x);
		String thisSeat = "";
		if(y != thisSeatRow.length()-1) {
			thisSeat = thisSeatRow.substring(y, y+1);
		} else {
			thisSeat = thisSeatRow.substring(y);
		}
		ArrayList<String> surroundingSeats = getAllSurroundingPoints(x, y);
		//System.out.println(thisSeat + " has the surrounding seats " + surroundingSeats);
		if(thisSeat.equals("#")) {
			int occupiedCount = 0;
			for(int z = 0; z < surroundingSeats.size(); z++) {
				if(surroundingSeats.get(z).equals("#")) {
					occupiedCount++;
				}
			}
			if(occupiedCount >= 4) {
				thisSeat = "L";
			}
		} else if (thisSeat.equals("L")) {
			int occupiedCount = 0;
			for(int z = 0; z < surroundingSeats.size(); z++) {
				if(surroundingSeats.get(z).equals("#")) {
					occupiedCount++;
				}
			}
			if(occupiedCount == 0) {
				thisSeat = "#";
			}
		} else if (thisSeat.equals(".")) {
			
		} else {
			System.out.println("Unknown character found");
		}
		return thisSeat;
	}
	
	// x is the line, y is the distance into it
	public static ArrayList<String> getAllSurroundingPoints(int x, int y) {
		String previousLine = "";
		if(x > 0) {
			previousLine = seats.get(x-1);
		}
		String currentLine = seats.get(x);
		String nextLine = "";
		if(x < seats.size()-1) {
			nextLine = seats.get(x+1);
		}
		//System.out.println(previousLine);
		//System.out.println(currentLine);
		//System.out.println(nextLine);
		ArrayList<String> seatList = new ArrayList<String>();
		if(y > 0) {
			if(!previousLine.equals("")) {
				seatList.add(previousLine.substring(y-1, y)); //up left
				//System.out.println("Adding position " + (x-1) + "," + (y-1));
			}
			seatList.add(currentLine.substring(y-1, y)); //left
			//System.out.println("Adding position " + x + "," + (y-1));
			if(!nextLine.equals("")) {
				seatList.add(nextLine.substring(y-1, y)); // bottom left
				//System.out.println("Adding position " + (x+1) + "," + (y-1));
			}
		}
		if(y < currentLine.length()-1) {
			if(!previousLine.equals("")) {
				seatList.add(previousLine.substring(y+1, y+2)); // up right
				//System.out.println("Adding position " + (x-1) + "," + (y+1));
			}
			seatList.add(currentLine.substring(y+1, y+2)); // right
			//System.out.println("Adding position " + x + "," + (y+1));
			if(!nextLine.equals("")) {
				seatList.add(nextLine.substring(y+1, y+2)); // bottom right
				//System.out.println("Adding position " + (x+1) + "," + (y+1));
			}
		}
		if(!previousLine.equals("")) {
			seatList.add(previousLine.substring(y, y+1));
			//System.out.println("Adding position " + (x-1) + "," + y);
		}
		if(!nextLine.equals("")) {
			seatList.add(nextLine.substring(y, y+1));
			//System.out.println("Adding position " + (x+1) + "," + y);
		}
		return seatList;
	}
	
	public static String getFirstDirection(int x, int y, String direction) {
		int currentX = x;
		int currentY = y;
		String foundSeat = "."; //If we don't find a seat, it is the same as finding floor
		if(direction.equals("NorthWest")){
			String foundRow = seats.get(currentX);
			while(currentY > 0 && currentX > 0 && foundSeat.equals(".")) {
				currentY--;
				currentX--;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("North")){
			while(currentX >= 0 && foundSeat.equals(".")) {
				currentX--;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("NorthEast")){
			String foundRow = seats.get(currentX); //here, only used for length
			while(currentY < foundRow.length() && currentX >= 0 && foundSeat.equals(".")){
				currentX--;
				currentY++;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("East")){
			String foundRow = seats.get(currentX);
			while(currentY < foundRow.length() && foundSeat.equals(".")) {
				currentY++;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("SouthEast")){
			String foundRow = seats.get(currentX);
			while(currentX < seats.size() && currentY < foundRow.length() && foundSeat.equals(".")) {
				currentX++;
				currentY++;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("South")){
			while(currentX < seats.size() && foundSeat.equals(".")) {
				currentX++;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("SouthWest")){
			String foundRow = seats.get(currentX);
			while(currentX < seats.size() && currentY > 0 && foundSeat.equals(".")) {
				currentX++;
				currentY--;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		} else if(direction.equals("West")){
			String foundRow = seats.get(currentX);
			while(currentY > 0 && foundSeat.equals(".")) {
				currentY--;
				foundSeat = seats.get(currentX).substring(y, y+1);
			}
		}
		return foundSeat;
	}
}