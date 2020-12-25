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
		final int max = 1000000;

        final Cup[] cupMap = new Cup[max + 1];

        Cup head = new Cup(cupsAround.get(0));
        cupMap[head.value] = head;
        Cup tail = head;

        for (int i = 1; i < cupsAround.size(); i++) {
            final Cup c = new Cup(cupsAround.get(i));
            cupMap[c.value] = c;
            c.next = head;
            tail.next = c;
            tail = c;
        }

        for (int i = Collections.max(cupsAround) + 1; i <= max; i++) {
            final Cup c = new Cup(i);
            cupMap[c.value] = c;
            c.next = head;
            tail.next = c;
            tail = c;
        }

        for (int i = 0; i < 10000000; i++) {
            final Cup current = head;
            final Cup c1 = current.next;
            final Cup c3 = c1.next.next;

            head.next = c3.next;

            int targetIndex = current.value == 1 ? max : current.value - 1;
            while (targetIndex == c1.value || targetIndex == c1.next.value || targetIndex == c3.value) {
                targetIndex--;
                targetIndex = targetIndex < 1 ? max : targetIndex;
            }

            final Cup target = cupMap[targetIndex];

            c3.next = target.next;
            target.next = c1;
            tail = current;
            head = current.next;
        }

        final Cup one = cupMap[1];
        System.out.println((long)one.next.value * one.next.next.value);
    }

    private static class Cup {
        public final int value;

        public Cup next;

        public Cup(int value) {
            this.value = value;
        }
    }
}