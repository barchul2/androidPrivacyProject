package identifierCode;
import java.util.ArrayList;

public class Word {
	
	String identifier; 
	int numberYes = 0; 
	int numberNo = 0; 
	ArrayList<Integer> SensitivityScore = new ArrayList<Integer>(); 
	
	//getter to return the identifier 
	public String getWordIdentifier() {
		
		return this.identifier; 
		
	}
	
	
	public int getNumberYes() {
		
		return this.numberYes; 
	}
	
	public int getNumberNo() {
		
		return this.numberNo; 
	}
	
}


