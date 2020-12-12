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
		ArrayList<String> adjacentSeats = new ArrayList<String>();
		String currentSeat = thisSeatRow.substring(y, y+1);
		String newSeat = currentSeat;
		if(!currentSeat.equals(".")) {
			adjacentSeats.add(getFirstDirection(x, y, "NorthWest"));
			adjacentSeats.add(getFirstDirection(x, y, "North"));
			adjacentSeats.add(getFirstDirection(x, y, "NorthEast"));
			adjacentSeats.add(getFirstDirection(x, y, "East"));
			adjacentSeats.add(getFirstDirection(x, y, "SouthEast"));
			adjacentSeats.add(getFirstDirection(x, y, "South"));
			adjacentSeats.add(getFirstDirection(x, y, "SouthWest"));
			adjacentSeats.add(getFirstDirection(x, y, "West"));
			int numOccupiedSeats = 0;
			for(int z = 0; z < adjacentSeats.size(); z++) {
				if(adjacentSeats.get(z).equals("#")){
					numOccupiedSeats++;
				}
			}
			if(currentSeat.equals("L")) {
				if(numOccupiedSeats == 0){
					newSeat = "#";
				}
			} else if (currentSeat.equals("#")) {
				if(numOccupiedSeats >= 5) {
					newSeat = "L";
				}
			}
		}
		return newSeat;
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
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("North")){
			while(currentX > 0 && foundSeat.equals(".")) {
				currentX--;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("NorthEast")){
			String foundRow = seats.get(currentX); //here, only used for length
			while((currentY < foundRow.length()-1) && currentX > 0 && foundSeat.equals(".")){
				currentX--;
				currentY++;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("East")){
			String foundRow = seats.get(currentX);
			while((currentY < foundRow.length()-1) && foundSeat.equals(".")) {
				currentY++;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("SouthEast")){
			String foundRow = seats.get(currentX);
			while((currentX < seats.size()-1) && (currentY < foundRow.length()-1) && foundSeat.equals(".")) {
				currentX++;
				currentY++;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("South")){
			while((currentX < seats.size()-1) && foundSeat.equals(".")) {
				currentX++;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("SouthWest")){
			String foundRow = seats.get(currentX);
			while((currentX < seats.size()-1) && currentY > 0 && foundSeat.equals(".")) {
				currentX++;
				currentY--;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		} else if(direction.equals("West")){
			String foundRow = seats.get(currentX);
			while(currentY > 0 && foundSeat.equals(".")) {
				currentY--;
				foundSeat = seats.get(currentX).substring(currentY, currentY+1);
			}
		}
		return foundSeat;
	}
}