import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class PowerSource {

	public static HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > points = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > >();
	public static int lowest = 0;
	public static int highest = 0;
	public static int zSize = 0;
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		int currentx = 0;
		while(inputFile.hasNextLine()) {
			String lineOfFile = inputFile.nextLine();
			points.put(currentx, new HashMap<Integer, HashMap<Integer, Boolean> >());
			for(int y = 0; y < lineOfFile.length(); y++) {
				points.get(currentx).put(y, new HashMap<Integer, Boolean>());
				if(lineOfFile.substring(y, y+1).equals("#")) {
					points.get(currentx).get(y).put(0, true);
				} else {
					points.get(currentx).get(y).put(0, false);
				}
				if(y > highest) {
					highest = y;
				}
				if(currentx > highest) {
					highest = currentx;
				}
			}
			currentx++;
		}
		for(int x = 0; x < 6; x++) {
			points = processAllKnownPoints();
		}
		System.out.println(howManyActiveCubes());
	}
	
	public static boolean getPosition(int x, int y, int z) {
		boolean position = false;
		
		if(points.containsKey(x) && points.get(x).containsKey(y) && points.get(x).get(y).containsKey(z) && points.get(x).get(y).get(z) == true) {
			position = true;
		}
		return position;
	}
	
	public static boolean processPoint(int x, int y, int z) {
		int surroundingActive = 0;
		for(int tempX = x-1; tempX <= x+1; tempX++) {
			for(int tempY = y-1; tempY <= y+1; tempY++) {
				for(int tempZ = z-1; tempZ <= z+1; tempZ++) {
					if((tempX != x || tempY != y || tempZ != z) && getPosition(tempX,tempY,tempZ)) {
						surroundingActive++;
					}
				}
			}
		}
		boolean newPosition = false;
		if(getPosition(x, y, z) && (surroundingActive == 2 || surroundingActive == 3)) {
			newPosition = true;
		} else if (!getPosition(x, y, z) && surroundingActive == 3) {
			newPosition = true;
		}
		return newPosition;
	}
	
	public static HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > processAllKnownPoints() {
		HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > nextPosition = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > >();
		lowest--;
		highest++;
		zSize++;
		for(int x = lowest; x <= highest; x++) {
			nextPosition.put(x, new HashMap<Integer, HashMap<Integer, Boolean> >());
			for(int y = lowest; y <= highest; y++) {
				nextPosition.get(x).put(y, new HashMap<Integer, Boolean>());
				for(int z = 0-zSize; z <= zSize; z++) {
					nextPosition.get(x).get(y).put(z, processPoint(x,y,z));
				}
			}
		}
		return nextPosition;
	}
	
	public static int howManyActiveCubes() {
		int sum = 0;
		for(int x = lowest; x <= highest; x++) {
			for(int y = lowest; y <= highest; y++) {
				for(int z = 0-zSize; z <= zSize; z++) {
					if(getPosition(x, y, z)) {
						sum++;
					}
				}
			}
		}
		return sum;
	}
	
}