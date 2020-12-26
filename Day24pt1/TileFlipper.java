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
		System.out.println("Total number of files that are left black: " + blackTiles.size());
	}
}