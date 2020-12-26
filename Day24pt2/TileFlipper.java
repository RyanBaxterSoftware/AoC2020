import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class TileFlipper {
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		ArrayList<String> blackTiles = new ArrayList<String>();
		while(inputFile.hasNextLine()) {
			String tileLocation = inputFile.nextLine();
			Double x = 0D;
			Double y = 0D;
			while(tileLocation.length() > 0) {
				if(tileLocation.substring(0,1).equals("e")) {
					x++;
					tileLocation = tileLocation.substring(1);
				} else if(tileLocation.substring(0,1).equals("w")) {
					x--;
					tileLocation = tileLocation.substring(1);
				} else if(tileLocation.substring(0,2).equals("se")) {
					x += .5;
					y--;
					tileLocation = tileLocation.substring(2);
				} else if(tileLocation.substring(0,2).equals("sw")) {
					x -= .5;
					y--;
					tileLocation = tileLocation.substring(2);
				} else if(tileLocation.substring(0,2).equals("ne")) {
					x += .5; 
					y++;
					tileLocation = tileLocation.substring(2);
				} else if(tileLocation.substring(0,2).equals("nw")) {
					x -= .5;
					y++;
					tileLocation = tileLocation.substring(2);
				} else {
					System.out.println("ERROR, NON-rECOGNIZED STRING: " + tileLocation);
				}
			}
			String thisTile = x+","+y;
			System.out.println(thisTile);
			if(blackTiles.contains(thisTile)){
				blackTiles.remove(thisTile);
			} else {
				blackTiles.add(thisTile);
			}
		}
		System.out.println(blackTiles);
		
		for(int x = 0; x < 100; x++) {
			
			ArrayList<String> tilesToCheck = new ArrayList<String>(); // ideally it should be converted to tile objects
			for(int y = 0; y < blackTiles.size(); y++) {
				// add every black tile and every adjacent tile
				String[] bothValues = split(blackTiles.get(y), ",");
				if(!tilesToCheck.contains(blackTiles.get(y))) {
					tilesToCheck.add(blackTiles.get(y));
				}
				List<String> surrounding = getSurroundingHexes(blackTiles.get(y));
				for(int z = 0; z < surrounding.size(); z++) {
					if(!tilesToCheck.contains(surrounding.get(z))) {
						tilesToCheck.add(surrounding.get(z));
					}
				}
			}
			ArrayList<String> newBlackTiles = new ArrayList<String>();
			for(int y = 0; y < tilesToCheck.size(); y++) {
				List<String> surrounding = getSurroundingHexes(tilesToCheck.get(y));
				int howManySurroundingBlackTiles = 0;
				for(int z = 0; z < surrounding.size(); z++) {
					if(blackTiles.contains(surrounding.get(z))){ 
						howManySurroundingBlackTiles++;
					}
				}
				if(blackTiles.contains(tilesToCheck.get(y))) {
					// check for black tile requirements
					if((howManySurroundingBlackTiles == 1 || howManySurroundingBlackTiles == 2) && !newBlackTiles.contains(tilesToCheck.get(y))){
						newBlackTiles.add(tilesToCheck.get(y));
					}
				} else {
					// check for white tile requirements
					if(howManySurroundingBlackTiles == 2 && !newBlackTiles.contains(tilesToCheck.get(y))) {
						newBlackTiles.add(tilesToCheck.get(y));
					}
				}
			}
			blackTiles = newBlackTiles;
			System.out.println(x + ": Total number of files that are left black: " + blackTiles.size());
		}
		System.out.println("Total number of files that are left black: " + blackTiles.size());
	}
	
	public static List<String> getSurroundingHexes(String position) {
		/* all connected hexes are 
		 y   x
		 0   +1
		 0   -1
		 +1  +.5
		 +1  -.5
		 -1  +.5
		 -1  -.5
		*/
		String[] bothValues = split(position, ",");
		Double xPos = Double.parseDouble(bothValues[0]);
		Double yPos = Double.parseDouble(bothValues[1]);
		List<String> surroundingHexes = new ArrayList<String>();
		surroundingHexes.add((xPos+1)+","+(yPos));
		surroundingHexes.add((xPos-1)+","+(yPos));
		surroundingHexes.add((xPos+.5)+","+(yPos+1));
		surroundingHexes.add((xPos-.5)+","+(yPos+1));
		surroundingHexes.add((xPos+.5)+","+(yPos-1));
		surroundingHexes.add((xPos-.5)+","+(yPos-1));
		return surroundingHexes;
	}
	
	//A method that will split a string into two strings around the first occurence of a character.
	public static String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		//System.out.println("Looking for " + c + " in the string " + inputString);
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+c.length()).trim();
		}
		else {
			responseString[0] = inputString;
			//System.out.println("didn't find " + c + " in line " + inputString);
		}
		return responseString;
	}
}