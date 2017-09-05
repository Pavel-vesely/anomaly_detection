package preprocess;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import entities.ADSentenceBlock;
import entities.InputSentence;
import entities.InputToken;
import eu.crydee.syllablecounter.SyllableCounter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StanfordNLPSaxHandler extends DefaultHandler {

    private ADSentenceBlock adSentenceBlock;
    private InputSentence inSentence;
    private ArrayList<InputToken> tokens;
    private InputToken token;
    private SyllableCounter counter;
    private String header;
    private boolean passive;

    private StringBuilder content;
    private String word;
    private BufferedWriter outBw;

    private ADSentenceBlock sumADSB;

    public StanfordNLPSaxHandler(String header, String outFilePath) throws IOException {
        tokens = new ArrayList<InputToken>();
        content = new StringBuilder();
        counter = new SyllableCounter();
        this.header = header;

        outBw = new BufferedWriter(new FileWriter(outFilePath));

        try {
            outBw.write(ADSentenceBlock.getCSVHeader());
            outBw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {
        content = new StringBuilder();
        if (qName.equalsIgnoreCase("sentence")) {
            inSentence = new InputSentence();
            passive = false;
            if (atts.getValue("id") != null) {
                inSentence.setId(Integer.valueOf(atts.getValue("id")));
            }
            if (atts.getValue("sentimentValue") != null) {
                inSentence.setSentiment(Integer.valueOf(atts.getValue("sentimentValue")));
            }
        } else if (qName.equalsIgnoreCase("tokens")) {
            tokens.clear();
        } else if (qName.equalsIgnoreCase("token")) {
            token = new InputToken();
        } else if (qName.equalsIgnoreCase("dep")) {
            if (atts.getValue("type").equalsIgnoreCase("nsubjpass")) {
                passive = true;
            }
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("word")) {
            word = content.toString();
            token.setWord(word);
            token.setCharacters(word.length());
            token.setSyllables(counter.count(word));
//            System.out.println("word: " + word);
        } else if (qName.equalsIgnoreCase("lemma")) {
            token.setLemma(content.toString());
        } else if (qName.equalsIgnoreCase("pos")) {
            token.setPOS(content.toString());

//            System.out.println("pos: " + content.toString());

        } else if (qName.equalsIgnoreCase("token")) {
            tokens.add(token);

        } else if (qName.equalsIgnoreCase("sentence")) {
            if (passive) {
                inSentence.setPassive(true);
            }
            inSentence.setTokens(tokens);
            adSentenceBlock = new ADSentenceBlock(inSentence, header);
            try {
                outBw.write(adSentenceBlock.toCSVLine());
                outBw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("Sentence: " + Integer.toString(inSentence.getId()));
            //sumADSB.increase(adSentenceBlock);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content.append(ch, start, length);
    }

    public void endDocument() throws SAXException {
        // you can do something here for example send
        // the Channel object somewhere or whatever.
//        System.out.println(sumADSB.toCSVLine());
        try {
            outBw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


