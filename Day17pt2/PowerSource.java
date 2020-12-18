import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class PowerSource {

	public static HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > >  >points = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > >();
	public static int lowest = 0;
	public static int highest = 0;
	public static int zSize = 0;
	public static int wSize = 0;
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		int currentx = 0;
		while(inputFile.hasNextLine()) {
			String lineOfFile = inputFile.nextLine();
			points.put(currentx, new HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > >());
			for(int y = 0; y < lineOfFile.length(); y++) {
				points.get(currentx).put(y, new HashMap<Integer, HashMap<Integer, Boolean> >());
				points.get(currentx).get(y).put(0, new HashMap<Integer, Boolean>());
				if(lineOfFile.substring(y, y+1).equals("#")) {
					points.get(currentx).get(y).get(0).put(0, true);
				} else {
					points.get(currentx).get(y).get(0).put(0, false);
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
	
	public static boolean getPosition(int x, int y, int z, int w) {
		boolean position = false;
		
		if(points.containsKey(x) && points.get(x).containsKey(y) && points.get(x).get(y).containsKey(z) && points.get(x).get(y).get(z).containsKey(w) && points.get(x).get(y).get(z).get(w) == true) {
			position = true;
		}
		return position;
	}
	
	public static boolean processPoint(int x, int y, int z, int w) {
		int surroundingActive = 0;
		for(int tempX = x-1; tempX <= x+1; tempX++) {
			for(int tempY = y-1; tempY <= y+1; tempY++) {
				for(int tempZ = z-1; tempZ <= z+1; tempZ++) {
					for(int tempW = w-1; tempW <= w+1; tempW++) {
						if((tempX != x || tempY != y || tempZ != z || tempW != w) && getPosition(tempX,tempY,tempZ, tempW)) {
							surroundingActive++;
						}
					}
				}
			}
		}
		boolean newPosition = false;
		if(getPosition(x, y, z, w) && (surroundingActive == 2 || surroundingActive == 3)) {
			newPosition = true;
		} else if (!getPosition(x, y, z, w) && surroundingActive == 3) {
			newPosition = true;
		}
		return newPosition;
	}
	
	public static HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > > processAllKnownPoints() {
		HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > > nextPosition = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > > >();
		lowest--;
		highest++;
		zSize++;
		wSize++;
		for(int x = lowest; x <= highest; x++) {
			nextPosition.put(x, new HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean> > >());
			for(int y = lowest; y <= highest; y++) {
				nextPosition.get(x).put(y, new HashMap<Integer, HashMap<Integer, Boolean> >());
				for(int z = 0-zSize; z <= zSize; z++) {
					nextPosition.get(x).get(y).put(z, new HashMap<Integer, Boolean>());
					for(int w = 0-wSize; w <= wSize; w++) {
						System.out.println(x + " " + y + " " + z + " " + w);
						nextPosition.get(x).get(y).get(z).put(w, processPoint(x,y,z,w));
					}
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
					for(int w = 0-wSize; w <= wSize; w++) {
						if(getPosition(x, y, z, w)) {
							sum++;
						}
					}
				}
			}
		}
		return sum;
	}
	
}