package preprocess;

import entities.ADSentenceBlock;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            InputStream xmlInput = new FileInputStream("resources\\mini.xml");

            SAXParser saxParser = factory.newSAXParser();
            StanfordNLPSaxHandler handler = new StanfordNLPSaxHandler("mini.xml");
            saxParser.parse(xmlInput, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ADSentenceBlock remade = new ADSentenceBlock("\"mini.xml\",1,1,43,116,39,0,1,35,0,3,0,1,1, 0, 0, 0, 0,0, 1, 1, 0, 0, 4, 0, 1, 3, 1, 0, 0, 0, 0, 4, 1, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 12, 0, 4, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0, 0");
        System.out.println(remade.toCSVLine());

    }
}

//    public static ADWord parseWord(SyllableCounter counter, String word) {
//        ADWord adWord = new ADWord();
//        adWord.setWord(word);
//        adWord.setCharacters(word.length());
//        adWord.setSyllables(counter.count(word));
//        return adWord;
//    }


