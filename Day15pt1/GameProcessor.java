import java.lang.*;
import java.util.*;
import java.io.*;

public class GameProcessor {
	
	public static HashMap<Integer, Integer> previousOccurance = new HashMap<Integer, Integer>();
	public static int round;
	
	public static void main(String[] args) throws Exception  {
		round = 1;
		Integer[] startingInputs = { 1,12,0,20,8,16 };
		for(int x = 0; x < startingInputs.length-1; x++) {
			round++;
			previousOccurance.put(startingInputs[x], round);
		}
		int answer = startingInputs[startingInputs.length-1];
		while(round < 30000000) {
			round++;
			if(round%1000==0) {
				System.out.println("Processing turn " + round);
			}
			answer = processAnswer(answer);
		}
		System.out.println(answer);
		
	}
	
	public static int processAnswer(int answer) {
		int currentAnswer = 0;
		if(previousOccurance.containsKey(answer)) {
			currentAnswer = round - previousOccurance.get(answer);
		}
		previousOccurance.put(answer, round);
		return currentAnswer;
	}
}