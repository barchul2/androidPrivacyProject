package identifierCode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner; 
import java.util.ArrayList;
import java.io.File; 
import java.io.FileWriter;

 
public class implementWord {

	public static void main(String[] args) throws FileNotFoundException {
		
		String fileName ="/Users/brandonarchuleta/Desktop/FinalAllData.txt"; 
		
		//Open and Scan the file 
		java.io.File file = new java.io.File(fileName);
		Scanner input = new Scanner(file); 
		
		//Declare an arrayList of type Word that will hold the word objects 
		ArrayList<Word> arrayOfWords = new ArrayList<Word>(); 
		ArrayList<Word> whiteList = new ArrayList<Word>(); 
		
		
		while(input.hasNext()) {
			//Read in the values for identifier, privacySenstivie, and sensitivityScore.
			//Convert sensitivityScore to an integer. 
			String identifier = input.next().strip();  
			String privacySensitive = input.next(); 
			String sensitivityScore = input.next(); 
			int sensitivityScoreValue = Integer.parseInt(sensitivityScore.strip()); 
		
			
			//Check to see if any objects have been created. If not, take the first word and make an object. 
			if (arrayOfWords.size() == 0) {
				arrayOfWords.add(createNewWord(identifier, sensitivityScoreValue)); 
				int objectFound = objectIsFound(arrayOfWords,arrayOfWords.size(),identifier); 
				isPrivacySensitive(privacySensitive,arrayOfWords,objectFound);
				
			}else {
				
			int objectFound = objectIsFound(arrayOfWords,arrayOfWords.size(),identifier); 
			//Test to see if the object was found.
			if (objectFound != -1) {
				isPrivacySensitive(privacySensitive,arrayOfWords,objectFound); 
				
			}else {
				
				Word test = createNewWord(identifier, sensitivityScoreValue); 
				arrayOfWords.add(test); 
				isPrivacySensitive(privacySensitive,test);
				
				}
	
			}
		}
		
		input.close(); 
		
		double threashHold = 1; 
		
		//ThreashHold for determining and acceptable yes/(yes+no) ratio
		whiteList(arrayOfWords,threashHold, whiteList); 
		
		//print white list 
		printWhiteList(whiteList); 
		
		
		//Write Results of the white list to a file 
		
		String desiredFileToWrite = "results.txt"; 
		
		try {
			
			File myNewFile = new File(desiredFileToWrite); 
			if(myNewFile.createNewFile()) {
				
				System.out.println("File Creation Success"); 
				writeToFile(whiteList,desiredFileToWrite); 
				
			}else {
				
				System.out.println("File Already exists. Overwriting File."); 
				writeToFile(whiteList,desiredFileToWrite); 
			}
			
		}catch(IOException e){
			
			System.out.println("An Error Occured"); 
			e.printStackTrace();
		}	
		
		
		
	}//end of main method
	
		
	
	static Word createNewWord(String identifier, int sensitivityScore) {
		
		Word newWord = new Word(); 
		newWord.identifier = identifier; 
		newWord.SensitivityScore.add(sensitivityScore); 
		
		return newWord; 
		
	}
	
	
	static int objectIsFound(ArrayList<Word> arrayOfWords, int size, String identifier){
		
		int returnValue = -1; 
		for (int x = 0; x < size; x++){ 
			if (arrayOfWords.get(x).identifier.strip().equals(identifier)){
				returnValue = x; 
			}
		}
		
		return returnValue; 
	}
	
	static void isPrivacySensitive(String privacySensitive, ArrayList <Word> arrayOfWords, int objectFound) {
		
		if (privacySensitive.toUpperCase().equals("YES")) {
			arrayOfWords.get(objectFound).numberYes+=1; 
		}else {
			arrayOfWords.get(objectFound).numberNo+=1;
		}
		
	}
	
	static void isPrivacySensitive(String privacySensitive, Word myWord) {
		
		if (privacySensitive.toUpperCase().equals("YES")) {
			 myWord.numberYes+=1; 
		}else {
			 myWord.numberNo+=1;
		}
	}
	
	static void whiteList(ArrayList <Word> arrayOfWords, double threashHold, ArrayList <Word> whiteList) {
		
		for (int x = 0; x < arrayOfWords.size(); x++) {
			
			double computeRatio = (double)arrayOfWords.get(x).getNumberYes()/(arrayOfWords.get(x).getNumberYes() + arrayOfWords.get(x).getNumberNo());
			 
			
			//if the number of yes/ (yes/no) >= threshold
			if (computeRatio >= threashHold) {
				whiteList.add(arrayOfWords.get(x)); 
			}
		}
	}
	
	
	static void printWhiteList(ArrayList <Word> whiteList) {
		
		System.out.println("WhiteList");
		System.out.println("---------"); 
		for (int x = 0; x < whiteList.size(); x++) {
			System.out.println(whiteList.get(x).identifier); 
			//System.out.println("Number of Yes: " + whiteList.get(x).numberYes); 
			//System.out.println("Number of No: " + whiteList.get(x).numberNo);
			
		}
		
	}
	
	
	static void writeToFile(ArrayList <Word> whiteList, String fileToWrite) {
		
		//Write the white list Results to the file 
				try {
					
					FileWriter myWrite = new FileWriter(fileToWrite); 
					
					for(int x = 0; x < whiteList.size(); x++) {
						myWrite.write((whiteList.get(x).getWordIdentifier() + '\n')); 
						
					}
					
					myWrite.close(); 
				}catch(IOException e){
					
					e.printStackTrace(); 
					
				
				}
	}
}//end of implementWord class 

