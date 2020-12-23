import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class PuzzleProcesser {
	public static HashMap<Integer, List<String>> tiles = new HashMap<Integer, List<String>>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));

		boolean nextTile = true;
		String currentTileName = "";
		List<String> currentTileLines = new ArrayList<String>();
		while(inputFile.hasNextLine()) {
			String lineToProcess = inputFile.nextLine();
			if (lineToProcess.equals("")) {
				nextTile = true;
				tiles.put(Integer.parseInt(currentTileName), currentTileLines);
				currentTileLines = new ArrayList<String>();
			} else if(nextTile == true) {
				// get the name
				currentTileName = lineToProcess.substring(5, 9);
				nextTile = false;
			} else {
				// get contents
				currentTileLines.add(lineToProcess);
			}
		}
		tiles.put(Integer.parseInt(currentTileName), currentTileLines);
				
		System.out.println(tiles);
		
		HashMap<Integer, List<String>> sides = new HashMap<Integer, List<String>>();
		Iterator<Integer> iteratorOfTiles = tiles.keySet().iterator();
		List<Integer> tileList = new ArrayList<Integer>();
		List<Integer> corners = new ArrayList<Integer>();
		while(iteratorOfTiles.hasNext()) {
			int tileNum = iteratorOfTiles.next();
			tileList.add(tileNum);
			System.out.println("Tile : " + tileNum);
			sides.put(tileNum, getSides(tileNum));
		}
		for(int x = 0; x < tileList.size(); x++) {
			int unmatchedSides = 4;
			List<String> thisTilesSides = sides.get(tileList.get(x));
			boolean sideMatched = false;
			for(int y = 0; y < thisTilesSides.size(); y++ ) {
				sideMatched = false;
				String sideToMatch = reverseString(thisTilesSides.get(y));
				List<String> sidesForOtherTiles;
				for(int z = 0; z < tileList.size(); z++) {
					if(z != x) {
						sidesForOtherTiles = sides.get(tileList.get(z));
						
						System.out.println("Comparing " + sideToMatch + " with Tile " + tileList.get(z) + " " + sidesForOtherTiles);
						if(sidesForOtherTiles.contains(sideToMatch) || sidesForOtherTiles.contains(reverseString(sideToMatch))){
							sideMatched = true;
							System.out.println(sideToMatch + " on tile " + tileList.get(x) + " is matched to " + tileList.get(z));
						}
					} else {
						
					}
				}
				if(sideMatched) {
					unmatchedSides--;
				}
			}
			System.out.println("Tile " + tileList.get(x) + " has " + unmatchedSides + " unmatched sides");
			if(unmatchedSides == 2) {
				corners.add(tileList.get(x));
			}
		}
		System.out.println("The corners are " + corners);
		Long sum = 1L;
		for(int x = 0; x < corners.size(); x++) {
			sum *= corners.get(x);
		}
		System.out.println(sum);
	}
	
	// Get a list of sides, going clockwise, starting with the right side.
	public static List<String> getSides(int tileNum) {
		ArrayList<String> sides = new ArrayList<String>();
		String side = "";
		List<String> tile = tiles.get(tileNum);
		for(int x = 0; x < tile.size(); x++) {
			System.out.println(tile.get(x));
			side += tile.get(x).substring(tile.get(x).length()-1);
		}
		System.out.println("");
		System.out.println("Sides are as follows:");
		System.out.println(side);
		sides.add(side);
		String tempSide = tile.get(tile.size() -1);
		side = reverseString(tempSide);
		sides.add(side);
		System.out.println(side);
		side = "";
		for(int x = tile.size() - 1; x >= 0; x--) {
			side += tile.get(x).substring(0,1);
		}
		System.out.println(side);
		sides.add(side);
		
		sides.add(tile.get(0));
		System.out.println(tile.get(0));
		System.out.println("");
		return sides;
	}
	
	public static String reverseString(String input) {
		String reversedString = "";
		while(input.length() > 0) {
			reversedString += input.substring(input.length() -1);
			input = input.substring(0, input.length()-1);
		}
		return reversedString;
	}
}