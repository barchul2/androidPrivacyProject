import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyWordExtraction {

    public static void extract(String fileString,  ArrayList<String> fileKeywords ){

        //Regex matcher to extract keywords.
        Matcher androidAppTokenMatcher;
        Pattern androidAppToken = Pattern.compile("ANDROID_APP_TOKEN.[\": ]*.[a-z_]*");

        //Match the regex to the fileString
        androidAppTokenMatcher = androidAppToken.matcher(fileString);

        while(androidAppTokenMatcher.find()){

            fileKeywords.add(androidAppTokenMatcher.group(0).substring(21));
        }
    }
}
