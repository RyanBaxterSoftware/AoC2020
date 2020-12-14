import java.lang.*;
import java.util.*;
import java.io.*;

public class InstructionImprover {

	public static ArrayList<String> directions = new ArrayList<String>();
	public static int whichDirection = 0;
	public static int vertical = 0;
	public static int horizontal = 0;
	
	
	public static void main(String[] args) throws Exception{
		directions.add("E");
		directions.add("S");
		directions.add("W");
		directions.add("N");
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		while(inputFile.hasNextLine()) {
			String instruction = inputFile.nextLine();
			String command = instruction.substring(0,1);
			int amount = Integer.parseInt(instruction.substring(1));
			if(command.equals("N")) {
				vertical += amount;
			} else if(command.equals("S")) {
				vertical -= amount;
			} else if(command.equals("E")) {
				horizontal += amount;
			} else if(command.equals("W")) {
				horizontal -= amount;
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
			whichDirection += howManyTurns;
			while(whichDirection > 3) {
				whichDirection -= 4;
			}
		} else if (direction.equals("L")) {
			whichDirection -= howManyTurns;
			while(whichDirection < 0) {
				whichDirection += 4;
			}
		}
		System.out.println("turned " + howManyTurns + " and now facing " + directions.get(whichDirection));
	}
	
	public static void forward(int amount) {
		String command = directions.get(whichDirection);
		if(command.equals("N")) {
			vertical += amount;
		} else if(command.equals("S")) {
			vertical -= amount;
		} else if(command.equals("E")) {
			horizontal += amount;
		} else if(command.equals("W")) {
			horizontal -= amount;
		}
	}
}