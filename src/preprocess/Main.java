package preprocess;

import entities.ADSentenceBlock;
import entities.ADVector;

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

        ADSentenceBlock line1 = new ADSentenceBlock("\"mini.xml\",1,1,43,116,39,0,1,35,0,3,0,1,1, 0, 0, 0, 0,0, 1, 1, 0, 0, 4, 0, 1, 3, 1, 0, 0, 0, 0, 4, 1, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 12, 0, 4, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0, 0");
        ADSentenceBlock line2 = new ADSentenceBlock("\"mini.xml\",2,1,32,128,45,0,1,18,2,8,0,0,1, 0, 0, 0, 0,0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 5, 0, 0, 4, 1, 0, 0, 0, 1, 7, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 2, 0, 2, 1, 1, 1, 0, 0, 0, 0");
        ADSentenceBlock line3 = new ADSentenceBlock("\"mini.xml\",3,1,19,81,26,0,1,14,2,6,0,0,0, 0, 1, 0, 0,0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 2, 0, 0, 4, 2, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0");
        ADSentenceBlock sum = new ADSentenceBlock("\"Sum\",0,58,1052,3957,1285,2,33,691,81,189,6,1,24, 18, 14, 2, 0,0, 5, 5, 7, 7, 60, 0, 58, 7, 30, 33, 131, 3, 1, 106, 79, 4, 2, 0, 16, 156, 16, 0, 40, 2, 1, 52, 0, 40, 0, 0, 5, 0, 24, 0, 61, 3, 23, 20, 30, 22, 2, 0, 0, 1");
        ADSentenceBlock[] lines = {line1, line2, line3};
        ADVector vector;
        ADVector complVector;
        double distance;

        for (ADSentenceBlock line: lines) {
            vector = new ADVector(line, false);
            sum.decrease(line);
            complVector = new ADVector(sum, true);
            sum.increase(line);
            distance = vector.manhattanDistanceTo(complVector);
            System.out.println(vector.toCSVLine() + "," + Double.toString(distance));
            System.out.println(complVector.toCSVLine() + "," + Double.toString(distance));
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


