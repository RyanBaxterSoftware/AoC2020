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
		ArrayDeque<Integer>[] resultDecks = processGame(deck1, deck2, 1);
		deck1 = resultDecks[0];
		deck2 = resultDecks[1];
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
	
	public static ArrayDeque<Integer>[] processGame(ArrayDeque<Integer> deck1, ArrayDeque<Integer> deck2, int depth) {
		// make copy of both decks
		// run game
		ArrayList<String> previousRepresentations = new ArrayList<String>();
		
		boolean reoccured = false;
		while(deck1.size() > 0 && deck2.size() > 0) { 
			String deckRep = deck1.toString() + "/" + deck2.toString();
			if(depth < 5) {System.out.println(deckRep + " of line " + depth);}
			Integer card1 = deck1.remove();
			Integer card2 = deck2.remove();
			if(previousRepresentations.contains(deckRep)) {
				deck2 = new ArrayDeque<Integer>(); // player 2 loses.
			} else { 
				previousRepresentations.add(deckRep);
			/*
			
			
			
			
			
			
			*/
				if(card1 <= deck1.size() && card2 <= deck2.size()) {
					ArrayDeque<Integer> copyDeck1 = new ArrayDeque<Integer>();
					ArrayDeque<Integer> returnDeck = new ArrayDeque<Integer>();
					while(deck1.size() > 0) {
						int tempSpot = deck1.remove();
						if(copyDeck1.size() < card1) {
							copyDeck1.add(tempSpot);
						}
						returnDeck.add(tempSpot);
					}
					while(returnDeck.size() > 0) {
						deck1.add(returnDeck.remove());
					}
					ArrayDeque<Integer> copyDeck2 = new ArrayDeque<Integer>();
					while(deck2.size() > 0) {
						int tempSpot = deck2.remove();
						if(copyDeck2.size() < card2) {
							copyDeck2.add(tempSpot);
						}
						returnDeck.add(tempSpot);
					}
					while(returnDeck.size() > 0) {
						deck2.add(returnDeck.remove());
					}
					ArrayDeque<Integer>[] resultDecks = processGame(copyDeck1, copyDeck2, depth+1);
					/*
					
					
					
					*/
					System.out.println("The result solution is as follows");
					System.out.println(resultDecks[0]);
					System.out.println(resultDecks[1]);
					if(resultDecks[0].size() == 0) {
						deck2.add(card2);
						deck2.add(card1);
					} else {
						deck1.add(card1);
						deck1.add(card2);
					}
				} else if(card1 > card2) {
					deck1.add(card1);
					deck1.add(card2);
				} else {
					deck2.add(card2);
					deck2.add(card1);
				}
			}
		}
		ArrayDeque<Integer>[] bothDecks = new ArrayDeque[2];
		bothDecks[0] = deck1;
		bothDecks[1] = deck2;
		return bothDecks;
	}
}