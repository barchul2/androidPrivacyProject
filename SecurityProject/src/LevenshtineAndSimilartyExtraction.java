
import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevenshtineAndSimilartyExtraction {

	public static void main(String[] args) throws FileNotFoundException {

		// The directory with the results files
		File[] directory = new File("/Users/brandonarchuleta/Desktop/Results").listFiles();
		ArrayList<ResultsFile> resultsList = new ArrayList<>();
		readFiles(directory, resultsList);

		//printResults(resultsList);
		
		System.out.println(resultsList.get(2).levenshtine_Threshold); 

	}

	public static void readFiles(File[] directory, ArrayList<ResultsFile> resultsList) throws FileNotFoundException {

		// Iterate through the array of files in the directory
		for (int x = 0; x < directory.length; x++) {

			if (directory[x].isDirectory()) {

				// Recursive call to traverse the directory looking for the specified files
				readFiles(directory[x].listFiles(), resultsList);

			} else {

				String pattern = "results.*";
				int count = 0;

				if (Pattern.matches(pattern, directory[x].getName())) {

					resultsList.add(new ResultsFile(directory[x].getName(), directory[x].getParent()));

					traverseFile(directory[x], resultsList.get(count));
					count++;

				}
			}

		}

	}// end of method

	public static void traverseFile(File myFile, ResultsFile resultsObject) throws FileNotFoundException {

		// String object that reads in an entire files text. Not a single line.
		String fileContents;

		Matcher simalarityTokenMatcher;
		Matcher androidAppTokenMatcher;

		// Matcher the thresholds at the beginning of each file.
		Matcher levenshteinThresholdMatcher;
		Matcher sematicThresholdMatcher;

		Scanner input = new Scanner(myFile);

		// Create a Regex pattern to identify the two SPT and AAT (android app token).
		Pattern simalarityToken = Pattern.compile("SIMILAR_PRIVACY_RELATED_TOKEN.[\": ]*.[a-z]*");
		Pattern androidAppToken = Pattern.compile("ANDROID_APP_TOKEN.[\": ]*.[a-z]*");

		// TODO add threshold capabilities.
		Pattern levenshteinThreshold = Pattern.compile("levenshtein_threshold.[ \":.]*.[0-9]*.[. ]*[0-9]*");
		Pattern sematicThreshold = Pattern.compile("sematic_threshold.[ \":.]*.[0-9]*.[. ]*[0-9]*");

		while (input.hasNext()) {

			// Read in all the data in the file as a single string.
			fileContents = input.nextLine();

			levenshteinThresholdMatcher = levenshteinThreshold.matcher(fileContents);
			sematicThresholdMatcher = sematicThreshold.matcher(fileContents);

			// Using compiled regex, identify create matcher to identify matches
			simalarityTokenMatcher = simalarityToken.matcher(fileContents);
			androidAppTokenMatcher = androidAppToken.matcher(fileContents);

			// Extract each token and then extract keywords.
			while (simalarityTokenMatcher.find()) {
				resultsObject.similarPrivacyRelatedTokens.add(simalarityTokenMatcher.group(0).substring(33));
			}

			// Extract each token and then extract keywords.
			while (androidAppTokenMatcher.find()) {
				resultsObject.androidAppToken.add(androidAppTokenMatcher.group(0).substring(21)); 
			}

			while (levenshteinThresholdMatcher.find()) {
				resultsObject.setLevenshtine_Threshold(Double.parseDouble(levenshteinThresholdMatcher.group(0).substring(24))); 
				levenshteinThresholdMatcher.reset(); 
			}

			while (sematicThresholdMatcher.find()) {
				// Add the levenshtine threshold and simlarity threshold to the object for
				// reference.
				resultsObject.setSemantic_Threshold(Double.parseDouble(sematicThresholdMatcher.group(0).substring(20)));
				sematicThresholdMatcher.reset(); 
			}
		

		} // end of input while loop

		// Close scanner
		input.close();

	}

	public static void printResults(ArrayList<ResultsFile> resultsList) {

		System.out.println("RESULTS FOR RESULT FILES IN DIRECTORY");
		System.out.println("-------------------------------------\n\n");

		for (int x = 0; x < resultsList.size(); x++) {

			// print out the information for each object
			System.out.println("FileName: " + resultsList.get(x).getFileName());
			System.out.println("FilePath: " + resultsList.get(x).getParentDirectory());
			System.out.println("levenshtine_Threshold:   " + resultsList.get(x).levenshtine_Threshold);
			System.out.println("semantic_Threshold:   " + resultsList.get(x).semantic_Threshold);

		} // end of outside for loop

	}

}// end of class

class ResultsFile {

	double levenshtine_Threshold;
	double semantic_Threshold;

	private String parentDirectory;
	private String fileName;
	ArrayList<String> similarPrivacyRelatedTokens;
	ArrayList<String> androidAppToken;

	ResultsFile(String fileName, String parentDirectory) {

		this.fileName = fileName;
		this.parentDirectory = parentDirectory;
		similarPrivacyRelatedTokens = new ArrayList<String>();
		androidAppToken = new ArrayList<String>();
	}

	public String getParentDirectory() {

		return parentDirectory;
	}

	public String getFileName() {

		return fileName;
	}
	
	public void setLevenshtine_Threshold(double thresh) {
		levenshtine_Threshold = thresh; 
	}
	
	public void setSemantic_Threshold(double thresh) {
		semantic_Threshold = thresh; 
	}


}
