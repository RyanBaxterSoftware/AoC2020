import java.lang.*;
import java.util.*;
import java.io.*;

class Find2020 {
	public static void main(String[] args) throws FileNotFoundException{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));
		
		ArrayList<Integer> allNums = new ArrayList<Integer>();
		while(inputFile.hasNextLine()) {
			allNums.add(Integer.parseInt(inputFile.nextLine()));
		}
		
		boolean found = false;
		int multSum = -1;
		for(int x = 0; x < allNums.size() && !found;x++){
			for(int y = 0; y < allNums.size() && !found; y++) {
				if(x != y && allNums.get(x) + allNums.get(y) == 2020){
					found = true;
					multSum = allNums.get(x) * allNums.get(y);
				}
			}
			if(!found) {
				System.out.println(allNums.get(x) + " sum not found");
			}
			else {
				System.out.println(allNums.get(x) + " sum was found");
				System.out.println("Sum is " + multSum);
			}
		}
	}
}