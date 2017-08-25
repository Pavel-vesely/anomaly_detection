package preprocess;

import entities.ADSentenceBlock;
import entities.InputSentence;
import entities.InputToken;
import eu.crydee.syllablecounter.SyllableCounter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class StanfordNLPSaxHandler extends DefaultHandler {

    private ADSentenceBlock adSentenceBlock;
    private InputSentence inSentence;
    private ArrayList<InputToken> tokens;
    private InputToken token;
    private SyllableCounter counter;
    private String header;
    private Boolean passive;

    private StringBuilder content;
    private String word;

    private ADSentenceBlock sumADSB;

    public StanfordNLPSaxHandler(String header) {
        tokens = new ArrayList<InputToken>();
        content = new StringBuilder();
        counter = new SyllableCounter();
        this.header = header;
        System.out.println(ADSentenceBlock.getCSVHeader());

        sumADSB = new ADSentenceBlock(0, "Sum");
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
        } else if (qName.equalsIgnoreCase("lemma")) {
            token.setLemma(content.toString());
        } else if (qName.equalsIgnoreCase("pos")) {
            token.setPOS(content.toString());

        } else if (qName.equalsIgnoreCase("token")) {
            tokens.add(token);

        } else if (qName.equalsIgnoreCase("sentence")) {
            if (passive) {
                inSentence.setPassive(true);
            }
            inSentence.setTokens(tokens);
            adSentenceBlock = new ADSentenceBlock(inSentence, header);
            System.out.println(adSentenceBlock.toCSVLine());
            sumADSB.increase(adSentenceBlock);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content.append(ch, start, length);
    }

    public void endDocument() throws SAXException {
        // you can do something here for example send
        // the Channel object somewhere or whatever.
        System.out.println(sumADSB.toCSVLine());
    }
}


