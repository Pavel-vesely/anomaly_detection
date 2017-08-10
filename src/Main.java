import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class Main {

    public static void main(String[] args) {
//        SyllableCounter counter = new SyllableCounter();
//        System.out.println(parseWord(counter, "fridge").toString());
        String pathname = "src\\tutorial.xml";
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            InputStream xmlInput = new FileInputStream("src\\mini.xml");

            SAXParser saxParser = factory.newSAXParser();
            StanfordNLPSaxHandler handler = new StanfordNLPSaxHandler();
            saxParser.parse(xmlInput, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//    public static ADWord parseWord(SyllableCounter counter, String word) {
//        ADWord adWord = new ADWord();
//        adWord.setWord(word);
//        adWord.setCharacters(word.length());
//        adWord.setSyllables(counter.count(word));
//        return adWord;
//    }


