import java.util.ArrayList;

public class KeywordComparison {

    public static void compare(ArrayList<String> fileOne, ArrayList<String> fileTwo, ArrayList<String> differentKeywordList) {
            boolean contains;

            for (String itemTwo : fileTwo) {
                contains = false;
                for (String itemOne : fileOne) {
                    if((itemTwo.compareTo(itemOne) == 0)){
                        contains = true;
                    }
                }
                if(contains == false && (!differentKeywordList.contains(itemTwo))){
                    differentKeywordList.add(itemTwo);
                }
            }
    }
    //Compare words in first file to words in second file.

}//end of class

