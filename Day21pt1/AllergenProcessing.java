import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

public class AllergenProcessing{
	
	public static ArrayList<String> allWords = new ArrayList<String>();
	public static HashMap<String, List<String>> possibleAllergens = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> ingredientCount = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws Exception{
		String fileLocation = System.getProperty("user.dir") + "\\" + args[0];
		Scanner inputFile = new Scanner(new FileReader(fileLocation));

		while(inputFile.hasNextLine()) {
			String lineToProcess = inputFile.nextLine();
			String[] splitResult = split(lineToProcess, " (contains ");
			ArrayList<String> theseValues = new ArrayList<String>();
			String ingredientsString = splitResult[0];
			while(ingredientsString != null) {
				String[] gettingIngredients = split(ingredientsString, " ");
				ingredientsString = gettingIngredients[1];
				theseValues.add(gettingIngredients[0]);
			}
			ArrayList<String> theseAllergens = new ArrayList<String>();
			String remainingAllergensString = splitResult[1];
			while(remainingAllergensString != null) {
				String[] gettingAllergens = split(remainingAllergensString, ", ");
				remainingAllergensString = gettingAllergens[1];
				theseAllergens.add(gettingAllergens[0]);
			}
			String lastAllergen = theseAllergens.get(theseAllergens.size()-1);
			lastAllergen = lastAllergen.substring(0, lastAllergen.length()-1);
			System.out.println(lastAllergen);
			theseAllergens.set(theseAllergens.size()-1, lastAllergen);
			System.out.println(theseValues);
			System.out.println(theseAllergens);
			for(int x = 0; x < theseAllergens.size(); x++) {
				String currentAllergy = theseAllergens.get(x);
				if(!possibleAllergens.containsKey(currentAllergy)){
					ArrayList<String> valuesCopy = new ArrayList<String>();
					valuesCopy.addAll(theseValues);
					possibleAllergens.put(currentAllergy, valuesCopy);
				}
				System.out.println(possibleAllergens.get(currentAllergy) + " is the possibilities for " + currentAllergy);
				List<String> thisAllergen = new ArrayList<String>();
				thisAllergen = possibleAllergens.get(currentAllergy);
				for(int y = 0; y < thisAllergen.size(); y++) {
					if(!theseValues.contains(thisAllergen.get(y))){
						System.out.println(thisAllergen.get(y) + " does not appear in this line, so it can't be the allergen for " + currentAllergy);
						thisAllergen.remove(y);
						y--;
					}
				}
			}
			for(int x = 0; x < theseValues.size(); x++) {
				if(!allWords.contains(theseValues.get(x))){
					allWords.add(theseValues.get(x));
				} 				
				if(ingredientCount.containsKey(theseValues.get(x))) {
					ingredientCount.put(theseValues.get(x), ingredientCount.get(theseValues.get(x))+1);
				} else {
					ingredientCount.put(theseValues.get(x), 1);
				}
			}
			
		}
		
		System.out.println(possibleAllergens);
		System.out.println(allWords);
		Iterator<String> allergies = possibleAllergens.keySet().iterator();
		while(allergies.hasNext()) {
			List<String> allIngredients = possibleAllergens.get(allergies.next());
			for(int x = 0; x < allIngredients.size(); x++) {
				if(allWords.contains(allIngredients.get(x))) {
					allWords.remove(allIngredients.get(x));
				}
			}
		}
		int totalCount = 0;
		for(int x = 0; x < allWords.size(); x++) {
			totalCount += ingredientCount.get(allWords.get(x));
		}
		System.out.println(totalCount);
		
		allergies = possibleAllergens.keySet().iterator();
		List<String> allergens = new ArrayList<String>();
		while(allergies.hasNext()) {
			allergens.add(allergies.next());
		}
		System.out.println(allergens);
		Collections.sort(allergens);
		System.out.println(allergens);
		
		boolean allHaveOne = false;
		while(!allHaveOne) {
			allHaveOne = true;
			for(int x = 0; x < allergens.size(); x++) {
				if(possibleAllergens.get(allergens.get(x)).size() == 1) {
					for(int y = 0; y < allergens.size(); y++) {
						if(y != x) {
							List<String> theseIngredients = possibleAllergens.get(allergens.get(y));
							theseIngredients.remove(possibleAllergens.get(allergens.get(x)).get(0));
						}
					}
				} else {
					allHaveOne = false;
				}
			}
		}
		System.out.println(possibleAllergens);
		String list = "";
		for(int x = 0; x < allergens.size(); x++) {
			List<String> theseAllergyList = possibleAllergens.get(allergens.get(x));
			Collections.sort(theseAllergyList);
			for(int y = 0; y < theseAllergyList.size(); y++) {
				list += theseAllergyList.get(y) + ",";
			}
		}
		System.out.println(list);
		// make a list of all words that aren't possible allergens
	}
	
	//A method that will split a string into two strings around the first occurence of a character.
	public static String[] split(String inputString, String c) {
		String[] responseString = new String[2];
		//System.out.println("Looking for " + c + " in the string " + inputString);
		int occurenceSpot = inputString.indexOf(c);
		if(occurenceSpot != -1) {
			responseString[0] = inputString.substring(0, occurenceSpot).trim();
			responseString[1] = inputString.substring(occurenceSpot+c.length()).trim();
		}
		else {
			responseString[0] = inputString;
			//System.out.println("didn't find " + c + " in line " + inputString);
		}
		return responseString;
	}
}