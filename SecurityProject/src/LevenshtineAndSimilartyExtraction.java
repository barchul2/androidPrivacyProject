
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
		String line;
		Matcher m;
		Scanner input = new Scanner(myFile);
		Pattern p = Pattern.compile("SIMILAR_PRIVACY_RELATED_TOKEN.[\": ]*.[a-z]*");

		// TODO add regex for rest of data

		while (input.hasNext()) {

			line = input.nextLine();
			m = p.matcher(line);

			while (m.find()) {

				resultsObject.similarPrivacyRelatedTokens.add(m.group(0));

			}

		}

		input.close();

	}

}// end of class

class ResultsFile {

	private String parentDirectory;
	private String fileName;
	ArrayList<String> similarPrivacyRelatedTokens;

	ResultsFile(String fileName, String parentDirectory) {

		this.fileName = fileName;
		this.parentDirectory = parentDirectory;
		similarPrivacyRelatedTokens = new ArrayList<String>();
	}

	public String getParentDirectory() {

		return parentDirectory;
	}

	public String getFileName() {

		return fileName;
	}

}
