import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class HackerMan {
	
	public static Long publicKeyCard = 1526110L;
	public static Long publicKeyDoor = 20175123L;
	
	public static void main(String[] args) throws Exception{
		Long subjectNumber = 7L;
		//get the loop size 
		int loopSizeCard = 0;
		Long value = 1L;
		while(value.compareTo(publicKeyCard) != 0) {
			value *= subjectNumber;
			value = value%20201227;
			loopSizeCard++;
		}
		System.out.println("We got a loop size of " + loopSizeCard + " for the card");
		Long encryptionKeyValue = 1L;
		for(int x = 0; x < loopSizeCard; x++) {
			encryptionKeyValue *= publicKeyDoor;
			encryptionKeyValue = encryptionKeyValue%20201227;
		}
		System.out.println("The final encryption key is " + encryptionKeyValue);
	}
}