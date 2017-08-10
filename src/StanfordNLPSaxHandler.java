import eu.crydee.syllablecounter.SyllableCounter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class StanfordNLPSaxHandler extends DefaultHandler {

    private ADSentence adSentence;
    private InputSentence inSentence;
    private ArrayList<InputToken> tokens;
    private InputToken token;
    private SyllableCounter counter;

    private StringBuilder content;
    private String word;

    public StanfordNLPSaxHandler() {
        tokens = new ArrayList<InputToken>();
        content = new StringBuilder();
        counter = new SyllableCounter();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {
        content = new StringBuilder();
        System.out.println("start:" + qName);
        if (qName.equalsIgnoreCase("sentence")) {
            inSentence = new InputSentence();
            if (atts.getValue("id") != null) {
                inSentence.setId(Integer.valueOf(atts.getValue("id")));
            }
        } else if (qName.equalsIgnoreCase("tokens")) {
            tokens.clear();
        } else if (qName.equalsIgnoreCase("token")) {
            token = new InputToken();
            System.out.println(token.toString());
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("end:" + qName + ": " + content.toString());
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
            System.out.println(token.toString());
            tokens.add(token);

        } else if (qName.equalsIgnoreCase("sentence")) {
            inSentence.setTokens(tokens);
            System.out.println(inSentence.toString());
            adSentence = new ADSentence(inSentence, counter);
            System.out.println(adSentence.toString());
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content.append(ch, start, length);
    }

    public void endDocument() throws SAXException {
        // you can do something here for example send
        // the Channel object somewhere or whatever.
    }


}


