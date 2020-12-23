import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class ConquestGame {
	
	public static ArrayDeque<Integer> deck1 = new ArrayDeque<Integer>();
	public static ArrayDeque<Integer> deck2 = new ArrayDeque<Integer>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));

		boolean secondDeck = false;
		while(inputFile.hasNextLine()) {
			String lineToProcess = inputFile.nextLine();
			if(lineToProcess.equals("")) {
				secondDeck = true;
			}
			if(!secondDeck) {
				if(!lineToProcess.equals("Player 1:")){
					deck1.add(Integer.parseInt(lineToProcess));
				}
			} else {
				if(!lineToProcess.equals("") && !lineToProcess.equals("Player 2:")) {
					deck2.add(Integer.parseInt(lineToProcess));
				}
			}
		}
		
		System.out.println(deck1);
		System.out.println(deck2);
		while(deck1.size() > 0 && deck2.size() > 0) {
			Integer card1 = deck1.remove();
			Integer card2 = deck2.remove();
			if(card1 > card2) {
				deck1.add(card1);
				deck1.add(card2);
			} else {
				deck2.add(card2);
				deck2.add(card1);
			}
		}
		System.out.println(deck1);
		System.out.println(deck2);
		
		ArrayDeque<Integer> deckWinner = new ArrayDeque<Integer>();
		if(deck1.size() > 0) {
			deckWinner = deck1;
		} else {
			deckWinner = deck2;
		}
		Long score = 0L;
		while(deckWinner.size() > 0) {
			int size = deckWinner.size();
			int cardHere = deckWinner.remove();
			score += size * cardHere;
		}
		System.out.println(score);
	}
}