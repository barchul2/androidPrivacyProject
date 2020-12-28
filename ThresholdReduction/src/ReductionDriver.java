import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class ReductionDriver {

    public static void main(String[] args) {

        //File object. Contains paths of two files.
        File fileOne = new File("/Users/brandonarchuleta/Library/Mobile Documents/com~apple~CloudDocs/ResearchProjects/AndroidPrivacy/RawResultFIles/myResults/results_L73S55/COMICS/results-com.marvel.unlimited.apk");
        File fileTwo = new File("/Users/brandonarchuleta/Library/Mobile Documents/com~apple~CloudDocs/ResearchProjects/AndroidPrivacy/RawResultFIles/myResults/results_L73S55/COMICS/results-net.manga.geek.mangamaster.apk");

        //ArrayLists where file keyword are written.
        ArrayList<String> fileOneList = new ArrayList<>();
        ArrayList<String> fileTwoList = new ArrayList<>();

        //List to hold the different keywords.
        ArrayList<String> differentKeyWordsList = new ArrayList<>();

        //Call to ReadFiles class and ReadFiles method
        try {

            ReadFiles.read(fileOne, fileTwo, fileOneList,fileTwoList);

        } catch (FileNotFoundException e) {
            System.out.println("One of the files entered into fileOne and fileTwo cannot be determined.");
            e.printStackTrace();
        }

        //Call KeywordComparison class and compare static method to find keywords that are different.
        KeywordComparison.compare(fileOneList,fileTwoList,differentKeyWordsList);

        print(differentKeyWordsList);

    }

    public static void print(ArrayList<String> list){

        for(String item: list){
            System.out.println(item);
        }
    }

}
