import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class PuzzleProcesser {
	public static HashMap<Integer, List<String>> tiles = new HashMap<Integer, List<String>>();
	
	public static HashMap<Integer, List<Integer>> matches = new HashMap<Integer, List<Integer>>();
	public static HashMap<Integer, List<String>> sides = new HashMap<Integer, List<String>>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));

		List<List<Integer> > allTiles = new ArrayList< List<Integer> >();
		List<String> resultImage = new ArrayList<String>();

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
			ArrayList<Integer> thisTileMatches = new ArrayList<Integer>();
			for(int y = 0; y < thisTilesSides.size(); y++ ) {
				thisTileMatches.add(0);
				sideMatched = false;
				String sideToMatch = reverseString(thisTilesSides.get(y));
				List<String> sidesForOtherTiles;
				for(int z = 0; z < tileList.size(); z++) {
					if(z != x) {
						sidesForOtherTiles = sides.get(tileList.get(z));
						
						System.out.println("Comparing " + sideToMatch + " with Tile " + tileList.get(z) + " " + sidesForOtherTiles);
						if(sidesForOtherTiles.contains(sideToMatch) || sidesForOtherTiles.contains(reverseString(sideToMatch))){
							sideMatched = true;
							thisTileMatches.set(y, tileList.get(z));
							System.out.println(sideToMatch + " on tile " + tileList.get(x) + " is matched to " + tileList.get(z));
							
						}
					} else {
						
					}
				}
				if(sideMatched) {
					unmatchedSides--;
				}
			}
			matches.put(tileList.get(x), thisTileMatches);
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
		
		System.out.println(matches);
		
		// add the top row
		List<String> foundTiles = new ArrayList<String>();
		List<Integer> tileMatches = new ArrayList<Integer>();
		int firstTileNum = -1;
		for(int x = 0; x < tileList.size(); x++) {
			tileMatches = matches.get(tileList.get(x));
			if(tileMatches.get(2) == 0 && tileMatches.get(3) == 0) {
				foundTiles = tiles.get(tileList.get(x));
				firstTileNum = tileList.get(x);
			}
		}
		System.out.println("First tile is " + firstTileNum);
		boolean nextTileExists = true;
		int previousTileNum = 0;
		int currentTileNum = firstTileNum;
		int nextTileToGet = matches.get(currentTileNum).get(0);
		
		List<Integer> previousRow = new ArrayList<Integer>();
		
		boolean anotherLineExists = true;
		while(nextTileExists) {
			System.out.println("About to compare " + currentTileNum + " to " + nextTileToGet);
			while(matches.get(nextTileToGet).get(2) != currentTileNum) {
				rotatePiece(nextTileToGet);
			}
			if(matches.get(nextTileToGet).get(3) != 0) {
				flipPiece(nextTileToGet);
			}
			previousRow.add(currentTileNum);
			previousTileNum = currentTileNum;
			currentTileNum = nextTileToGet;
			nextTileToGet = matches.get(nextTileToGet).get(0);
			if(nextTileToGet == 0) {
				nextTileExists = false;
			}
			if(matches.get(currentTileNum).get(1) == 0) {
				anotherLineExists = false;
			}
		}
		List<Integer> currentRow = new ArrayList<Integer>();
		previousRow.add(currentTileNum);
		allTiles.add(previousRow);
		while(anotherLineExists) {
			nextTileExists = true;
			System.out.println("New line!!");
			int position = 0;previousTileNum = 0;
			System.out.println("About to get the lower value of " + previousRow.get(0) + ": "  + matches.get(previousRow.get(0)));
			currentTileNum = matches.get(previousRow.get(0)).get(1);
			while(!matches.get(currentTileNum).get(3).toString().equals(previousRow.get(0).toString())) {						
				System.out.println("Comparing " + matches.get(currentTileNum).get(3) + " and " + previousRow.get(0) + ".");
				System.out.println(matches.get(currentTileNum).get(3) != previousRow.get(0));
				rotatePiece(currentTileNum);
			}
			if(matches.get(currentTileNum).get(0) == 0) {
				swapPiece(currentTileNum);
			}
			System.out.println(matches.get(currentTileNum));
			while(nextTileExists) {
				nextTileToGet = matches.get(currentTileNum).get(0);
				System.out.println("About to compare " + currentTileNum + " to " + nextTileToGet);
				if(nextTileToGet != 0) {
					while(matches.get(nextTileToGet).get(2) != currentTileNum) {
						rotatePiece(nextTileToGet);
					}
					if(matches.get(nextTileToGet).get(3) != previousRow.get(position)) {
						flipPiece(nextTileToGet);
					}
				}
				currentRow.add(currentTileNum);
				previousTileNum = currentTileNum;
				currentTileNum = nextTileToGet;
				if(nextTileToGet != 0) {
					nextTileToGet = matches.get(nextTileToGet).get(0);
				}
				if(nextTileToGet == 0) {
					nextTileExists = false;
				}
				System.out.println("Next tile is " + nextTileToGet);
				if(matches.get(previousTileNum).get(1) == 0) {
					anotherLineExists = false;
				}
				position++;
			}
			currentRow.add(currentTileNum);
			allTiles.add(currentRow);
			previousRow = currentRow;
			currentRow = new ArrayList<Integer>();
		}
		for(int x = 0; x < allTiles.size(); x++) {
			System.out.println(allTiles.get(x));
		}
		for(int x = 0; x < allTiles.size(); x++) {
			List<String> theseLines = new ArrayList<String>();
			for(int y = 0; y < allTiles.get(x).size(); y++) {
				List<String> tile = tiles.get(allTiles.get(x).get(y));
				System.out.println(allTiles.get(x).get(y) + ": ");
				for(int z = 0; z < tile.size(); z++) {
					System.out.println(tile.get(z));
				}
				for(int z = 1; z < tile.size()-1; z++) {
					if(theseLines.size() < z) {
						theseLines.add("");
					}
					String tileLine = tile.get(z);
					theseLines.set(z-1, theseLines.get(z-1) + tileLine.substring(1, tileLine.length()-1));
					System.out.println("Line is " + tileLine + " and the added amount is " + tileLine.substring(1, tileLine.length()-1));
				}
			}
			resultImage.addAll(theseLines);
		}
		for(int x = 0; x < resultImage.size(); x++) {
			System.out.println(resultImage.get(x));
		}
		System.out.println("");
		boolean monsterFound = false;
		while(!monsterFound) {
			//check for arrangement                   
			//                    #  18
			//	#    ##    ##    ### 0,5,6,11,12,17,18,19
			//   #  #  #  #  #  #    1,4,7,10,13,16
			for(int x = 0; x < resultImage.size()-2; x++) {
				String lineToExamine1 = resultImage.get(x);
				String lineToExamine2 = resultImage.get(x+1);
				String lineToExamine3 = resultImage.get(x+2);
				//System.out.println(lineToExamine1);
				//System.out.println(lineToExamine2);
				//System.out.println(lineToExamine3);
				//System.out.println("");
				for(int y = 0; y < lineToExamine1.length()-19; y++) {
					String monsterParts = "";
					monsterParts += lineToExamine1.substring(y+18, y+19);
					monsterParts += lineToExamine2.substring(y+0,y+1);
					monsterParts += lineToExamine2.substring(y+5,y+6);
					monsterParts += lineToExamine2.substring(y+6,y+7);
					monsterParts += lineToExamine2.substring(y+11,y+12);
					monsterParts += lineToExamine2.substring(y+12,y+13);
					monsterParts += lineToExamine2.substring(y+17,y+18);
					monsterParts += lineToExamine2.substring(y+18,y+19);
					monsterParts += lineToExamine2.substring(y+19,y+20);
					monsterParts += lineToExamine3.substring(y+1,y+2);
					monsterParts += lineToExamine3.substring(y+4,y+5);
					monsterParts += lineToExamine3.substring(y+7,y+8);
					monsterParts += lineToExamine3.substring(y+10,y+11);
					monsterParts += lineToExamine3.substring(y+13,y+14);
					monsterParts += lineToExamine3.substring(y+16,y+17);
					System.out.println("The found values are " + monsterParts);
					if(!monsterParts.contains(".")) {
						monsterFound = true;
						System.out.println("Line number is " + x);
					}
				}
				for(int z = 0; z < resultImage.size(); z++) {
					System.out.println(resultImage.get(z));
				}
				System.out.println("");
			}
			if(!monsterFound) {
				//flip
				List<String> newImage = new ArrayList<String>();
				for(int y = resultImage.size()-1; y >= 0; y--) {
					newImage.add(resultImage.get(y));
				}
				System.out.println("Image was flipped");
				resultImage = newImage;
				for(int y = 0; y < resultImage.size()-2; y++) {
					String lineToExamine1 = resultImage.get(y);
					String lineToExamine2 = resultImage.get(y+1);
					String lineToExamine3 = resultImage.get(y+2);
					//System.out.println(lineToExamine1);
					//System.out.println(lineToExamine2);
					//System.out.println(lineToExamine3);
					//System.out.println("");
					for(int z = 0; z < lineToExamine1.length()-19; z++) {
						String monsterParts = "";
						monsterParts += lineToExamine1.substring(z+18, z+19);
						monsterParts += lineToExamine2.substring(z+0,z+1);
						monsterParts += lineToExamine2.substring(z+5,z+6);
						monsterParts += lineToExamine2.substring(z+6,z+7);
						monsterParts += lineToExamine2.substring(z+11,z+12);
						monsterParts += lineToExamine2.substring(z+12,z+13);
						monsterParts += lineToExamine2.substring(z+17,z+18);
						monsterParts += lineToExamine2.substring(z+18,z+19);
						monsterParts += lineToExamine2.substring(z+19,z+20);
						monsterParts += lineToExamine3.substring(z+1,z+2);
						monsterParts += lineToExamine3.substring(z+4,z+5);
						monsterParts += lineToExamine3.substring(z+7,z+8);
						monsterParts += lineToExamine3.substring(z+10,z+11);
						monsterParts += lineToExamine3.substring(z+13,z+14);
						monsterParts += lineToExamine3.substring(z+16,z+17);
						if(!monsterParts.contains(".")) {
							monsterFound = true;
							System.out.println("Line number is " + y);
						}
					}
				}
				for(int x = 0; x < resultImage.size(); x++) {
					System.out.println(resultImage.get(x));
				}
				System.out.println("");
			}
			
			if(!monsterFound) {						
				//swap
				List<String> newImage = new ArrayList<String>();
				for(int y = 0; y < resultImage.size(); y++) {
					newImage.add(reverseString(resultImage.get(y)));
				}
				
				System.out.println("Image was swapped");
				resultImage = newImage;
				for(int y = 0; y < resultImage.size()-2; y++) {
					String lineToExamine1 = resultImage.get(y);
					String lineToExamine2 = resultImage.get(y+1);
					String lineToExamine3 = resultImage.get(y+2);
					//System.out.println(lineToExamine1);
					//System.out.println(lineToExamine2);
					//System.out.println(lineToExamine3);
					//System.out.println("");
					for(int z = 0; z < lineToExamine1.length()-19; z++) {
						String monsterParts = "";
						monsterParts += lineToExamine1.substring(z+18, z+19);
						monsterParts += lineToExamine2.substring(z+0,z+1);
						monsterParts += lineToExamine2.substring(z+5,z+6);
						monsterParts += lineToExamine2.substring(z+6,z+7);
						monsterParts += lineToExamine2.substring(z+11,z+12);
						monsterParts += lineToExamine2.substring(z+12,z+13);
						monsterParts += lineToExamine2.substring(z+17,z+18);
						monsterParts += lineToExamine2.substring(z+18,z+19);
						monsterParts += lineToExamine2.substring(z+19,z+20);
						monsterParts += lineToExamine3.substring(z+1,z+2);
						monsterParts += lineToExamine3.substring(z+4,z+5);
						monsterParts += lineToExamine3.substring(z+7,z+8);
						monsterParts += lineToExamine3.substring(z+10,z+11);
						monsterParts += lineToExamine3.substring(z+13,z+14);
						monsterParts += lineToExamine3.substring(z+16,z+17);
						if(!monsterParts.contains(".")) {
							monsterFound = true;
							System.out.println("Line number is " + z);
						}
					}
				}
				for(int x = 0; x < resultImage.size(); x++) {
					System.out.println(resultImage.get(x));
				}
				System.out.println("");
			}
			//flip
			if(!monsterFound) {
				List<String> newImage = new ArrayList<String>();
				for(int y = resultImage.size()-1; y >= 0; y--) {
					newImage.add(resultImage.get(y));
				}
				resultImage = newImage;
				
				System.out.println("Image was flipped");
				for(int y = 0; y < resultImage.size()-2; y++) {
					String lineToExamine1 = resultImage.get(y);
					String lineToExamine2 = resultImage.get(y+1);
					String lineToExamine3 = resultImage.get(y+2);
					//System.out.println(lineToExamine1);
					//System.out.println(lineToExamine2);
					//System.out.println(lineToExamine3);
					System.out.println("");
					for(int z = 0; z < lineToExamine1.length()-19; z++) {
						String monsterParts = "";
						monsterParts += lineToExamine1.substring(z+18, z+19);
						monsterParts += lineToExamine2.substring(z+0,z+1);
						monsterParts += lineToExamine2.substring(z+5,z+6);
						monsterParts += lineToExamine2.substring(z+6,z+7);
						monsterParts += lineToExamine2.substring(z+11,z+12);
						monsterParts += lineToExamine2.substring(z+12,z+13);
						monsterParts += lineToExamine2.substring(z+17,z+18);
						monsterParts += lineToExamine2.substring(z+18,z+19);
						monsterParts += lineToExamine2.substring(z+19,z+20);
						monsterParts += lineToExamine3.substring(z+1,z+2);
						monsterParts += lineToExamine3.substring(z+4,z+5);
						monsterParts += lineToExamine3.substring(z+7,z+8);
						monsterParts += lineToExamine3.substring(z+10,z+11);
						monsterParts += lineToExamine3.substring(z+13,z+14);
						monsterParts += lineToExamine3.substring(z+16,z+17);
						if(!monsterParts.contains(".")) {
							monsterFound = true;
							System.out.println("Line number is " + y);
						}
					}
				}
				for(int x = 0; x < resultImage.size(); x++) {
					System.out.println(resultImage.get(x));
				}
				System.out.println("");
			}
				//rotate
			if(!monsterFound) {
				List<String> newImage = new ArrayList<String>();
				for(int x = 0; x < resultImage.get(0).length(); x++) {
					newImage.add("");
				}
				for(int x = resultImage.size()-1; x >= 0; x--) {
					for(int y = 0; y < resultImage.get(x).length(); y++) {
						newImage.set(y, newImage.get(y) + resultImage.get(x).substring(y, y+1));
					}
				}
				resultImage = newImage;
				
				for(int x = 0; x < resultImage.size(); x++) {
					System.out.println(resultImage.get(x));
				}
				
				System.out.println("Image was rotated");
			}
			System.out.println(monsterFound);
		}
		// Now we know the puzzle is oriented correctly.
		int totalSeaMonsters = 0;
		for(int y = 0; y < resultImage.size()-2; y++) {
			String lineToExamine1 = resultImage.get(y);
			String lineToExamine2 = resultImage.get(y+1);
			String lineToExamine3 = resultImage.get(y+2);
			System.out.println(lineToExamine1);
			System.out.println(lineToExamine2);
			System.out.println(lineToExamine3);
			System.out.println("");
			for(int z = 0; z < lineToExamine1.length()-19; z++) {
				String monsterParts = "";
				monsterParts += lineToExamine1.substring(z+18, z+19);
				monsterParts += lineToExamine2.substring(z+0,z+1);
				monsterParts += lineToExamine2.substring(z+5,z+6);
				monsterParts += lineToExamine2.substring(z+6,z+7);
				monsterParts += lineToExamine2.substring(z+11,z+12);
				monsterParts += lineToExamine2.substring(z+12,z+13);
				monsterParts += lineToExamine2.substring(z+17,z+18);
				monsterParts += lineToExamine2.substring(z+18,z+19);
				monsterParts += lineToExamine2.substring(z+19,z+20);
				monsterParts += lineToExamine3.substring(z+1,z+2);
				monsterParts += lineToExamine3.substring(z+4,z+5);
				monsterParts += lineToExamine3.substring(z+7,z+8);
				monsterParts += lineToExamine3.substring(z+10,z+11);
				monsterParts += lineToExamine3.substring(z+13,z+14);
				monsterParts += lineToExamine3.substring(z+16,z+17);
				if(!monsterParts.contains(".")) {
					System.out.println("Line number is " + y);
					totalSeaMonsters++;
				}
			}
		}
		int monsterParts = totalSeaMonsters*15;
		int totalHash = 0;
		for(int x = 0; x < resultImage.size(); x++) {
			for(int y = 0; y < resultImage.get(x).length(); y++) {
				if(resultImage.get(x).substring(y, y+1).equals("#")) {
					totalHash++;
				}
			}
		}
		System.out.println("We found " + totalSeaMonsters + " and " + totalHash + " so the final number of water turmoil is " + (totalHash - monsterParts));
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
	
	public static void rotatePiece(int inputTile) {
		List<String> existingTile = tiles.get(inputTile);
		List<String> newTile = new ArrayList<String>();
		for(int x = 0; x < existingTile.get(0).length(); x++) {
			newTile.add("");
		}
		for(int x = existingTile.size()-1; x >= 0; x--) {
			for(int y = 0; y < existingTile.get(x).length(); y++) {
				newTile.set(y, newTile.get(y) + existingTile.get(x).substring(y, y+1));
			}
		}
		tiles.put(inputTile, newTile);
		ArrayList<Integer> newMatches = new ArrayList<Integer>();
		newMatches.add(matches.get(inputTile).get(3));
		newMatches.add(matches.get(inputTile).get(0));
		newMatches.add(matches.get(inputTile).get(1));
		newMatches.add(matches.get(inputTile).get(2));
		matches.put(inputTile, newMatches);
		
		ArrayList<String> newSides = new ArrayList<String>();
		newSides.add(sides.get(inputTile).get(3));
		newSides.add(sides.get(inputTile).get(3));
		newSides.add(sides.get(inputTile).get(3));
		newSides.add(sides.get(inputTile).get(3));
		sides.put(inputTile, newSides);
										
	}
	
	// vertical
	public static void flipPiece(int inputTile) {
		List<String> existingTile = tiles.get(inputTile);
		List<String> newTile = new ArrayList<String>();
		for(int x = existingTile.size()-1; x >= 0; x--) {
			newTile.add(existingTile.get(x));
		}
		tiles.put(inputTile, newTile);
		ArrayList<Integer> newMatches = new ArrayList<Integer>();
		newMatches.add(matches.get(inputTile).get(0));
		newMatches.add(matches.get(inputTile).get(3));
		newMatches.add(matches.get(inputTile).get(2));
		newMatches.add(matches.get(inputTile).get(1));
		matches.put(inputTile, newMatches);
		// swap 1 and 3
		ArrayList<String> newSides = new ArrayList<String>();
		// swap 1 and 3
		newSides.add(reverseString(sides.get(inputTile).get(0)));
		newSides.add(sides.get(inputTile).get(3));
		newSides.add(reverseString(sides.get(inputTile).get(2)));
		newSides.add(sides.get(inputTile).get(1));
		sides.put(inputTile, newSides);
		// reverse 0 and 2
	}
	
	// horizontal
	public static void swapPiece(int inputTile) {
		List<String> existingTile = tiles.get(inputTile);
		List<String> newTile = new ArrayList<String>();
		for(int x = 0; x < existingTile.size(); x++) {
			newTile.add(reverseString(existingTile.get(x)));
		}
		tiles.put(inputTile, newTile);
		ArrayList<Integer> newMatches = new ArrayList<Integer>();
		newMatches.add(matches.get(inputTile).get(2));
		newMatches.add(matches.get(inputTile).get(1));
		newMatches.add(matches.get(inputTile).get(0));
		newMatches.add(matches.get(inputTile).get(3));
		matches.put(inputTile, newMatches);
		
		// swap 0 and 2
		ArrayList<String> newSides = new ArrayList<String>();
		newSides.add(sides.get(inputTile).get(2));
		newSides.add(reverseString(sides.get(inputTile).get(1)));
		newSides.add(sides.get(inputTile).get(0));
		newSides.add(reverseString(sides.get(inputTile).get(3)));
		sides.put(inputTile, newSides);
		// swap 0 and 2
		// reverse 1 and 3
	}
}