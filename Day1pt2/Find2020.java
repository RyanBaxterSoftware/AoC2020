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
				for(int z = 0; z < allNums.size() && !found; z++) {
					if(x != z && x != y && y != z && allNums.get(x) + allNums.get(y) + allNums.get(z) == 2020){
						found = true;
						multSum = allNums.get(x) * allNums.get(y) * allNums.get(z);
					}
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