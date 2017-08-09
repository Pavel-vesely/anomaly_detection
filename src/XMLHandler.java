import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLHandler extends DefaultHandler {

        private ArrayList<NLPToken> tokens;
        private NLPToken token;

        private StringBuilder content;

        public XMLHandler() {
            tokens = new  ArrayList<NLPToken>();
            content = new StringBuilder();
        }

        public void startElement(String uri, String localName, String qName,
                                 Attributes atts) throws SAXException {
            content = new StringBuilder();
            if(localName.equalsIgnoreCase("tokens")) {
                tokens.clear();
                System.out.println("tokens start");
            } else if(localName.equalsIgnoreCase("token")) {
                token = new NLPToken();
            }
        }

        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if(localName.equalsIgnoreCase("word")) {
                token.setWord(content.toString());
            } else if(localName.equalsIgnoreCase("lemma")) {
                token.setLemma(content.toString());
            } else if(localName.equalsIgnoreCase("POS")) {
                token.setPOS(content.toString());

            } else if(localName.equalsIgnoreCase("token")) {
                tokens.add(token);
                System.out.println("Token: " + token.toString());
            } else if(localName.equalsIgnoreCase("tokens")) {
                System.out.println(tokens.toString());
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
