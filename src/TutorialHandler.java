import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TutorialHandler extends DefaultHandler {

    private ArrayList<NLPToken> tokens;
    private NLPToken token;

    private StringBuilder content;

    public TutorialHandler() {
        tokens = new ArrayList<NLPToken>();
        content = new StringBuilder();

    }

    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {
        content = new StringBuilder();
        System.out.println("start:" + qName);
        if (qName.equalsIgnoreCase("company")) {
            tokens.clear();

        } else if (qName.equalsIgnoreCase("staff")) {
            token = new NLPToken();
            System.out.println(token.toString());
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("end:" + qName);
        if (qName.equalsIgnoreCase("firstname")) {
            token.setWord(content.toString());
        } else if (qName.equalsIgnoreCase("lastname")) {
            token.setLemma(content.toString());
        } else if (qName.equalsIgnoreCase("nickname")) {
            token.setPOS(content.toString());
        } else if (qName.equalsIgnoreCase("salary")) {
            token.setCharacters(Integer.getInteger(content.toString()));

        } else if (qName.equalsIgnoreCase("staff")) {
            System.out.println("Ends Token:inCase");
            System.out.println(token.toString());
            tokens.add(token);

        } else if (qName.equalsIgnoreCase("company")) {
            String listString = tokens.stream().map(Object::toString)
                    .collect(Collectors.joining(", "));
            System.out.println(listString);
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


