import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFiles {

    //Method extracts keywords from each file.
    public static void read(File fileOne, File fileTwo, ArrayList<String> fileOneList, ArrayList fileTwoList) throws FileNotFoundException {

        Scanner fileOneScanner = new Scanner(fileOne);;
        Scanner fileTwoScanner = new Scanner(fileTwo);

        fileOneScanner.useDelimiter("}");
        fileTwoScanner.useDelimiter("}");

        String fileOneContents = new String();
        String fileTwoContents = new String();

        //Read in file input.
        while(fileOneScanner.hasNext()){

            fileOneContents = fileOneScanner.next();

            //Class containing method to extract keyword and add keywords to list.
            KeyWordExtraction.extract(fileOneContents,fileOneList);

        }

        while(fileTwoScanner.hasNext()){

            fileTwoContents = fileTwoScanner.next();

            //Class containing method to extract keyword and add keywords to list.
            KeyWordExtraction.extract(fileTwoContents,fileTwoList);

        }


    }//end of method
}
