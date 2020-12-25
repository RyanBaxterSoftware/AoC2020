import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class CupGame {

/*
   /\
    ( /   @ @    ()
     \  __| |__  /
      -/   "   \-
     /-|       |-\
    / /-\     /-\ \
     / /-`---'-\ \   
      /         \
*/	
	public static void main(String[] args) throws Exception{
		String startingPosition = "685974213";
		ArrayList<Integer> cupsAround = new ArrayList<Integer>();
		for(int x = 0; x < startingPosition.length(); x++) {
			cupsAround.add(Integer.parseInt(startingPosition.substring(x, x+1)));
		}		
		int currentCup = 0;
		ArrayList<Integer> heldCups = new ArrayList<Integer>();
		for(int x = 0; x < 100; x++) {
			
			//pick up the three next cups
			// cups to get
			int whereToGetCupsFrom = currentCup+1;
			int currentCupValue = cupsAround.get(currentCup);
			if(whereToGetCupsFrom >= cupsAround.size()) {
				whereToGetCupsFrom -= cupsAround.size();
			}
			heldCups.add(cupsAround.remove(whereToGetCupsFrom));
			if(whereToGetCupsFrom >= cupsAround.size()) {
				whereToGetCupsFrom -= cupsAround.size();
			}
			heldCups.add(cupsAround.remove(whereToGetCupsFrom));
			if(whereToGetCupsFrom >= cupsAround.size()) {
				whereToGetCupsFrom -= cupsAround.size();
			}
			heldCups.add(cupsAround.remove(whereToGetCupsFrom));
			//System.out.println("New cups is " + cupsAround + " because we're holding " + heldCups);
			int destinationFinding = currentCupValue-1;
			while(!cupsAround.contains(destinationFinding)) {
				destinationFinding--;
				if(destinationFinding < 1) {
					destinationFinding = 9;
				}
			}
			//destination has been found.
			int destinationPosition = cupsAround.indexOf(destinationFinding)+1;
			if(destinationPosition >= cupsAround.size()) {
				destinationPosition -= cupsAround.size();
			}
			cupsAround.add(destinationPosition, heldCups.remove(heldCups.size()-1));
			if(destinationPosition >= cupsAround.size()) {
				destinationPosition -= cupsAround.size();
			}
			cupsAround.add(destinationPosition, heldCups.remove(heldCups.size()-1));
			if(destinationPosition >= cupsAround.size()) {
				destinationPosition -= cupsAround.size();
			}
			cupsAround.add(destinationPosition, heldCups.remove(heldCups.size()-1));
			currentCup = cupsAround.indexOf(currentCupValue); // update the current cup position by value.
			currentCup++;
			if(currentCup >= cupsAround.size()) {
				currentCup -= cupsAround.size();
			}
		}
		System.out.println(cupsAround);
	}
}