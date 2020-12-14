import java.lang.*;
import java.util.*;
import java.io.*;

public class InstructionImprover {

	public static ArrayList<String> directions = new ArrayList<String>();
	public static int whichDirection = 0;
	public static int vertical = 0;
	public static int horizontal = 0;
	
	public static int[] waypointValues = new int[2];
	public static int[] whichWaypointDirections = new int[2];
	
	
	public static void main(String[] args) throws Exception{
		directions.add("E");
		directions.add("S");
		directions.add("W");
		directions.add("N");
		
		whichWaypointDirections[0] = 0;
		whichWaypointDirections[1] = 3;
		waypointValues[0] = 10;
		waypointValues[1] = 1;
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		while(inputFile.hasNextLine()) {
			String instruction = inputFile.nextLine();
			String command = instruction.substring(0,1);
			int amount = Integer.parseInt(instruction.substring(1));
			if(command.equals("N") || command.equals("S") || command.equals("E") || command.equals("W")) {
				moveWaypoint(command, amount);
			} else if(command.equals("L")|| command.equals("R")) {
				turn(command, amount);
			} else if(command.equals("F")) {
				forward(amount);
			} 
		}
		System.out.println("Total distance is " + horizontal + " east and west and " + vertical + " north and south making it " + (Math.abs(horizontal) + Math.abs(vertical)));
	}
	
	public static void turn(String direction, int amount) {
		int howManyTurns = amount/90;
		if(direction.equals("R")) {
			whichWaypointDirections[0] += howManyTurns;
			while(whichWaypointDirections[0] > 3) {
				whichWaypointDirections[0] -= 4;
			}
			whichWaypointDirections[1] += howManyTurns;
			while(whichWaypointDirections[1] > 3) {
				whichWaypointDirections[1] -= 4;
			}
		} else if (direction.equals("L")) {
			whichWaypointDirections[0] -= howManyTurns;
			while(whichWaypointDirections[0] < 0) {
				whichWaypointDirections[0] += 4;
			}
			whichWaypointDirections[1] -= howManyTurns;
			while(whichWaypointDirections[1] < 0) {
				whichWaypointDirections[1] += 4;
			}
		}
		System.out.println("turned " + howManyTurns + " and waypoint is now at " + directions.get(whichWaypointDirections[0]) + waypointValues[0] + " " + directions.get(whichWaypointDirections[1]) + waypointValues[1]);
	}
	
	public static void forward(int amount) {
		for(int x = 0; x < whichWaypointDirections.length; x++) {
			String command = directions.get(whichWaypointDirections[x]);
			if(command.equals("N")) {
				vertical += waypointValues[x] * amount;
			} else if(command.equals("S")) {
				vertical -= waypointValues[x] * amount;
			} else if(command.equals("E")) {
				horizontal += waypointValues[x] * amount;
			} else if(command.equals("W")) {
				horizontal -= waypointValues[x] * amount;
			}
		}
	}
	
	public static void moveWaypoint(String direction, int distance) {
		if(direction.equals("N")) {
			if(whichWaypointDirections[0] == 3){
				waypointValues[0] += distance;
			} else if (whichWaypointDirections[0] == 1) {
				waypointValues[0] -= distance;
			} else if(whichWaypointDirections[1] == 3){
				waypointValues[1] += distance;
			} else if (whichWaypointDirections[1] == 1) {
				waypointValues[1] -= distance;
			}
		} else if (direction.equals("E")) {
			if(whichWaypointDirections[0] == 0){
				waypointValues[0] += distance;
			} else if (whichWaypointDirections[0] == 2) {
				waypointValues[0] -= distance;
			} else if(whichWaypointDirections[1] == 0){
				waypointValues[1] += distance;
			} else if (whichWaypointDirections[1] == 2) {
				waypointValues[1] -= distance;
			}
		} else if (direction.equals("S")) {
			if(whichWaypointDirections[0] == 3){
				waypointValues[0] -= distance;
			} else if (whichWaypointDirections[0] == 1) {
				waypointValues[0] += distance;
			} else if(whichWaypointDirections[1] == 3){
				waypointValues[1] -= distance;
			} else if (whichWaypointDirections[1] == 1) {
				waypointValues[1] += distance;
			}
		} else if (direction.equals("W")) {
			if(whichWaypointDirections[0] == 2){
				waypointValues[0] += distance;
			} else if (whichWaypointDirections[0] == 0) {
				waypointValues[0] -= distance;
			} else if(whichWaypointDirections[1] == 2){
				waypointValues[1] += distance;
			} else if (whichWaypointDirections[1] == 0) {
				waypointValues[1] -= distance;
			}
		}
	}
}