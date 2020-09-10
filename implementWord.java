import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

public class implementWord {

	public static void main(String[] args) throws FileNotFoundException {

		// Path of FinalAllData.txt file -Sorry, I was lazy. 
		String fileName = "/Users/brandonarchuleta/Desktop/FinalAllData copy.txt";

		// Open and Scan the file
		java.io.File file = new java.io.File(fileName);
		Scanner input = new Scanner(file);

		// Declare an arrayList of type Word that will hold the word objects
		ArrayList<Word> arrayOfWords = new ArrayList<Word>();
		ArrayList<Word> whiteList = new ArrayList<Word>();

		// while loop to read the attributes into the Word class
		while (input.hasNext()) {

			// Read in the values for identifier, privacySenstivie, and sensitivityScore.
			// Convert sensitivityScore to an integer.
			String identifier = input.next().strip();
			String privacySensitive = input.next().strip();
			String sensitivityScore = input.next().strip();
			int sensitivityScoreValue = Integer.parseInt(sensitivityScore.strip());

			// Check to see if any objects have been created. If not, take the attributes
			// and make an object Then add it to the Word Array List so that we have at least one object
			if (arrayOfWords.size() == 0) {

				// Create the new word and add it to the ArrayList of words.
				arrayOfWords.add(createNewWord(identifier, sensitivityScoreValue));

				// Test to see if the object with the identifier has been created already. We only want one object for each unique identifier.
				int objectFound = objectIsFound(arrayOfWords, arrayOfWords.size(), identifier);

				// Determine if the identifier is privacy sensitive or not based on the privacySensitive attribute.
				isPrivacySensitive(privacySensitive, arrayOfWords, objectFound);

			} else {

				// If our list of objects (Words) already has a object stored. Test to see if the object with the identifier has been created already
				int objectFound = objectIsFound(arrayOfWords, arrayOfWords.size(), identifier);

				// If the object is found, then test to see if its privacy sensitive.
				if (objectFound != -1) {
					isPrivacySensitive(privacySensitive, arrayOfWords, objectFound);

				} else {
					// Create a new object of type word and add it to our list of words.
					Word test = createNewWord(identifier, sensitivityScoreValue);
					arrayOfWords.add(test);
					// Then test to see if its privacy sensitive. If it is, add one to the privacy sensitive attribute of the object
					isPrivacySensitive(privacySensitive, test);

				}
			}
		}

		// Close the scanner as we are no longer reading input.
		input.close();

		// This is the threashHold variable that we can change.
		double threashHold = 1;

		// ThreashHold for determining and acceptable yes/(yes+no) ratio
		// If the privacy sensitive word is above the threasHold, then add it to the whiteList (ArrayList of Word Objects).
		whiteList(arrayOfWords, threashHold, whiteList);

		// print white list
		printWhiteList(whiteList);

		// Write Results of the white list to a file
		String desiredFileToWrite = "results.txt";

		try {

			File myNewFile = new File(desiredFileToWrite);
			if (myNewFile.createNewFile()) {

				System.out.println("File Creation Success");
				writeToFile(whiteList, desiredFileToWrite);

			} else {

				System.out.println("File Already exists. Overwriting File.");
				writeToFile(whiteList, desiredFileToWrite);
			}

		} catch (IOException e) {

			System.out.println("An Error Occured");
			e.printStackTrace();
		}

	}// end of main method

	static Word createNewWord(String identifier, int sensitivityScore) {

		Word newWord = new Word();
		newWord.identifier = identifier;
		newWord.SensitivityScore.add(sensitivityScore);

		return newWord;

	}

	static int objectIsFound(ArrayList<Word> arrayOfWords, int size, String identifier) {

		int returnValue = -1;
		for (int x = 0; x < size; x++) {
			if (arrayOfWords.get(x).identifier.strip().equals(identifier)) {
				returnValue = x;
			}
		}

		return returnValue;
	}

	static void isPrivacySensitive(String privacySensitive, ArrayList<Word> arrayOfWords, int objectFound) {

		if (privacySensitive.toUpperCase().equals("YES")) {
			arrayOfWords.get(objectFound).numberYes += 1;
		} else {
			arrayOfWords.get(objectFound).numberNo += 1;
		}

	}

	static void isPrivacySensitive(String privacySensitive, Word myWord) {

		if (privacySensitive.toUpperCase().equals("YES")) {
			myWord.numberYes += 1;
		} else {
			myWord.numberNo += 1;
		}
	}

	static void whiteList(ArrayList<Word> arrayOfWords, double threashHold, ArrayList<Word> whiteList) {

		for (int x = 0; x < arrayOfWords.size(); x++) {

			double computeRatio = (double) arrayOfWords.get(x).getNumberYes()
					/ (arrayOfWords.get(x).getNumberYes() + arrayOfWords.get(x).getNumberNo());

			// if the number of yes/ (yes/no) >= threshold
			if (computeRatio >= threashHold) {
				whiteList.add(arrayOfWords.get(x));
			}
		}
	}

	static void printWhiteList(ArrayList<Word> whiteList) {

		System.out.println("WhiteList");
		System.out.println("---------");
		for (int x = 0; x < whiteList.size(); x++) {
			System.out.println(whiteList.get(x).identifier);
			// System.out.println("Number of Yes: " + whiteList.get(x).numberYes);
			// System.out.println("Number of No: " + whiteList.get(x).numberNo);

		}

	}

	static void writeToFile(ArrayList<Word> whiteList, String fileToWrite) {

		// Write the white list Results to the file
		try {

			FileWriter myWrite = new FileWriter(fileToWrite);

			for (int x = 0; x < whiteList.size(); x++) {
				myWrite.write((whiteList.get(x).getWordIdentifier() + '\n'));

			}

			myWrite.close();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}// end of implementWord class
